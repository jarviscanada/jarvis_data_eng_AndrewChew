package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.util.Optional;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {

  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

  private AccountDao accountDao;
  private SecurityOrderDao securityOrderDao;
  private QuoteDao quoteDao;
  private PositionDao positionDao;

  @Autowired
  public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao, QuoteDao quoteDao,
      PositionDao positionDao) {
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.quoteDao = quoteDao;
    this.positionDao = positionDao;
  }

  /**
   * Execute a market order.
   *  - Validate the order (e.g. size and ticker)
   *  - Create a SecurityOrder
   *  - Handle buy or sell order
   *    - Buy order: check account balance
   *    - Sell order: check position for the ticker/symbol
   *    - Update SecurityOrder.status
   * Note: For simplicity, last price of ticker/symbol will be used for all orders.
   *
   * @param orderDto market order
   * @return SecurityOrder from security_order table
   * @throws DataAccessException if unable to get data from DAO
   * @throws IllegalArgumentException for invalid input
   */
  public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
    SecurityOrder securityOrder = validateAndCreateSecurityOrder(orderDto);
    Account account = accountDao.findById(orderDto.getAccountId()).get();
    if (orderDto.getSize() > 0) {
      handleBuyMarketOrder(orderDto, securityOrder, account);
    } else {
      handleSellMarketOrder(orderDto, securityOrder, account);
    }
    return securityOrderDao.save(securityOrder);
  }

  private SecurityOrder validateAndCreateSecurityOrder(MarketOrderDto orderDto) {
    Integer accountId = orderDto.getAccountId();
    Integer size = orderDto.getSize();
    String ticker = orderDto.getTicker();
    if (size == 0) {
      throw new IllegalArgumentException("Market order cannot be 0");
    }
    if (!quoteDao.existsById(ticker)) {
      throw new IllegalArgumentException("Ticker: " + ticker + " cannot be found");
    }
    if (!accountDao.existsById(accountId)) {
      throw new IllegalArgumentException("Account: " + accountId + " cannot be found");
    }
    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setAccountId(accountId);
    securityOrder.setPrice(quoteDao.findById(ticker).get().getLastPrice());
    securityOrder.setSize(size);
    securityOrder.setStatus("PENDING");
    securityOrder.setTicker(ticker);
    return securityOrderDao.save(securityOrder);
  }

  /**
   * Helper method that executes a buy order.
   *
   * @param marketOrderDto user order
   * @param securityOrder to be saved in database
   * @param account user account
   */
  protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
      Account account) {
    Double accountBalance = account.getAmount();
    Double transactionCost = marketOrderDto.getSize() * securityOrder.getPrice();
    if (accountBalance < transactionCost) {
      throw new RuntimeException("Insufficient funds in account to execute buy order");
    }
    account.setAmount(accountBalance - transactionCost);
    accountDao.save(account);
    securityOrder.setStatus("FILLED");
  }

  /**
   * Helper method that executes a sell order.
   *
   * @param marketOrderDto user order
   * @param securityOrder to be saved in database
   * @param account user account
   */
  protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder,
      Account account) {
    Integer size = marketOrderDto.getSize(); // Note: size < 0.
    Optional<Position> position = positionDao.findById(account.getId(), securityOrder.getTicker());
    if (!position.isPresent() || position.get().getPosition() < -size) {
      throw new RuntimeException("Unable to sell: position in " + securityOrder.getTicker()
          + " is not large enough for sell order");
    }
    Double transactionCost = size * securityOrder.getPrice();
    account.setAmount(account.getAmount() - transactionCost);
    accountDao.save(account);
    securityOrder.setStatus("FILLED");
  }

}
