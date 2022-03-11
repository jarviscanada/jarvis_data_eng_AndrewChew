package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderAccountService {

  private TraderDao traderDao;
  private AccountDao accountDao;
  private SecurityOrderDao securityOrderDao;
  private PositionDao positionDao;

  @Autowired
  public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
      SecurityOrderDao securityOrderDao, PositionDao positionDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.securityOrderDao = securityOrderDao;
    this.positionDao = positionDao;
  }

  /**
   * Create a new trader and initialize a new account with 0 amount.
   *  - Validate user input (all fields must be non-empty)
   *  - Create a trader
   *  - Create an account
   *  - Create, setup, and return a new TraderAccountView
   * Assumption: For simplification, each trader has only one account where traderId == accountId
   *
   * @param trader cannot be null. All fields cannot be null except for id (auto-generated by db)
   * @return TraderAccountView for this trader
   * @throws IllegalArgumentException if a trader has null fields or id is not null
   */
  public TraderAccountView createTraderAndAccount(Trader trader) {
    if (!isValidTrader(trader)) {
      throw new IllegalArgumentException(
          "Invalid trader: all fields must be non-null except for id");
    }
    Trader newTrader = traderDao.save(trader);
    Account newAccount = accountDao.save(createNewAccount(newTrader.getId()));
    return createNewTraderAccountView(newTrader, newAccount);
  }

  /**
   * A trader can be deleted iff it has no open positions and 0 cash balance.
   *  - Validate traderId
   *  - Get trader account by traderId and check account balance
   *  - Get positions by accountId and check positions
   *  - Delete all securityOrders, account, trader (in this order)
   *
   * @param traderId of trader to be deleted, cannot be null
   * @throws IllegalArgumentException if traderId is null or not found or unable to delete
   */
  public void deleteTraderById(Integer traderId) {
    if (traderId == null) {
      throw new IllegalArgumentException("traderId cannot be null");
    } else if (!traderExists(traderId)) {
      throw new IllegalArgumentException("Trader: " + traderId + "cannot be found");
    } else if (hasAccountBalance(traderId)) {
      throw new IllegalArgumentException("Trader's account must have 0 cash balance");
    } else if (hasOpenPosition(traderId)) {
      throw new IllegalArgumentException("Trader: " + traderId + "has open positions");
    } else {
      deleteTraderSecurityOrders(traderId);
      accountDao.deleteById(traderId);
      traderDao.deleteById(traderId);
    }
  }

  /**
   * Deposit a fund to an account of trader with traderId.
   *  - Validate user input
   *  - account = accountDao.findByTraderId
   *  - accountDao.updateAmountById
   *
   * @param traderId of trader depositing funds, cannot be null
   * @param fund amount to deposit, must be greater than 0
   * @return updated Account of trader
   * @throws IllegalArgumentException if traderId is null or not found or fund is <= 0
   */
  public Account deposit(Integer traderId, Double fund) {
    // TODO
    return null;
  }

  /**
   * Withdraw a fund to an account of trader with traderId.
   *  - Validate user input
   *  - account = accountDao.findByTraderId
   *  - accountDao.updateAmountById
   *
   * @param traderId of trader withdrawing funds, cannot be null
   * @param fund amount to withdraw, must be greater than 0
   * @return updated Account of trader
   * @throws IllegalArgumentException if traderId is null or not found or fund is <= 0 or
   *                                  insufficient funds
   */
  public Account withdraw(Integer traderId, Double fund) {
    // TODO
    return null;
  }

  /**
   * Helper method to check if Trader with id traderId is valid.
   */
  public boolean isValidTrader(Trader trader) {
    return trader.getId() == null && trader.getCountry() != null && trader.getDob() != null
        && trader.getEmail() != null && trader.getFirstName() != null
        && trader.getLastName() != null;
  }

  /**
   * Helper method to check if Trader with id traderId exists.
   */
  private boolean traderExists(Integer traderId) {
    return traderDao.existsById(traderId);
  }

  /**
   * Helper method to create a new account for Trader with id traderId.
   */
  private Account createNewAccount(Integer traderId) {
    Account newAccount = new Account();
    newAccount.setId(traderId);
    newAccount.setAmount(0.0d);
    newAccount.setTraderId(traderId);
    return newAccount;
  }

  /**
   * Helper method to create a new TraderAccountView for trader and account.
   */
  private TraderAccountView createNewTraderAccountView(Trader trader, Account account) {
    TraderAccountView newTraderAccountView= new TraderAccountView();
    newTraderAccountView.setTrader(trader);
    newTraderAccountView.setAccount(account);
    return newTraderAccountView;
  }

  /**
   * Helper method to check if Trader with traderId has an account balance.
   */
  private boolean hasAccountBalance(Integer traderId) {
    return accountDao.findById(traderId).get().getAmount() != 0;
  }

  /**
   * Helper method to check if Trader with traderId has open positions.
   */
  private boolean hasOpenPosition(Integer traderId) {
    List<SecurityOrder> securityOrders = securityOrderDao.findAll();
    long openPositions = securityOrders.stream()
        .filter(securityOrder -> securityOrder.getAccountId().equals(traderId))
        .filter(securityOrder -> securityOrder.getStatus().equals("PENDING"))
        .count();
    return openPositions != 0;
  }

  /**
   * Helper method to delete every SecurityOrder that belongs to the Trader with id traderId.
   */
  private void deleteTraderSecurityOrders(Integer traderId) {
    List<SecurityOrder> securityOrders = securityOrderDao.findAll();
    securityOrders.stream()
        .filter(securityOrder -> securityOrder.getAccountId().equals(traderId))
        .forEach(securityOrder -> securityOrderDao.deleteById(securityOrder.getId()));
  }
}
