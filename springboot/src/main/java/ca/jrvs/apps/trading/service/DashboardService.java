package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.PortfolioView;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityRow;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DashboardService {

  private TraderDao traderDao;
  private PositionDao positionDao;
  private AccountDao accountDao;
  private QuoteDao quoteDao;

  @Autowired
  public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao,
      QuoteDao quoteDao) {
    this.traderDao = traderDao;
    this.positionDao = positionDao;
    this.accountDao = accountDao;
    this.quoteDao = quoteDao;
  }

  /**
   * Create and return a traderAccountView by traderId.
   *  - Get trader account by id
   *  - Get trader info by id
   *  - Create and return a TraderAccountView
   *
   * @param traderId id of trader, must not be null
   * @return TraderAccountView of Trader with id traderId
   * @throws IllegalArgumentException if traderId is null or not found
   */
  public TraderAccountView getTraderAccount(Integer traderId) {
    if (traderId == null) {
      throw new IllegalArgumentException("traderId cannot be null");
    }
    Trader trader = findTraderByTraderId(traderId);
    Account account = findAccountByTraderId(traderId);
    TraderAccountView traderAccountView = new TraderAccountView();
    traderAccountView.setTrader(trader);
    traderAccountView.setAccount(account);
    return traderAccountView;
  }

  /**
   * Create and return PortfolioView by traderId.
   *  - Get account by trader id
   *  - Get positions by account id
   *  - Create and return a PortfolioView
   *
   * @param traderId id of trader, must not be null
   * @return PortfolioView of Trader with id traderId
   * @throws IllegalArgumentException if traderId is null or not found
   */
  public PortfolioView getProfileViewByTraderId(Integer traderId) {
    if (traderId == null) {
      throw new IllegalArgumentException("traderId cannot be null");
    }
    List<Position> positions = findPositionsById(traderId);
    PortfolioView portfolioView = new PortfolioView();
    portfolioView.setSecurityRows(buildSecurityRows(positions));
    return portfolioView;
  }

  /**
   * Helper method to find Trader with id traderId.
   *
   * @throws IllegalArgumentException if traderId is null or not found
   */
  private Trader findTraderByTraderId(Integer traderId) {
    return traderDao.findById(traderId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid traderId"));
  }

  /**
   * Helper method to find the Account of Trader with id traderId.
   *
   * @throws IllegalArgumentException if traderId is null or not found
   */
  private Account findAccountByTraderId(Integer traderId) {
    return accountDao.findById(traderId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid traderId"));
  }

  /**
   * Helper method to find List of Positions of a Trader with id traderId.
   */
  private List<Position> findPositionsById(Integer traderId) {
    List<Position> allPositions = positionDao.findAll();
    return allPositions.stream()
        .filter(position -> position.getAccountId().equals(traderId))
        .collect(Collectors.toList());
  }

  /**
   * Helper method to build a List of SecurityRows from a list of Positions.
   */
  private List<SecurityRow> buildSecurityRows(List<Position> positions) {
    List<SecurityRow> securityRows = new ArrayList<>();
    positions.forEach(position -> securityRows.add(buildSecurityRow(position)));
    return securityRows;
  }

  /**
   * Helper method to build a SecurityRow from a Position.
   */
  private SecurityRow buildSecurityRow(Position position) {
    String ticker = position.getTicker();
    SecurityRow securityRow = new SecurityRow();
    securityRow.setPosition(position);
    securityRow.setQuote(quoteDao.findById(ticker)
        .orElseThrow(() -> new IllegalArgumentException("Ticker: " + ticker + " cannot be found")));
    securityRow.setTicker(ticker);
    return securityRow;
  }
}
