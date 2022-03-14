package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.PortfolioView;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
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
    Trader trader = traderDao.findById(traderId)
        .orElseThrow(() -> new IllegalArgumentException("traderId not found"));
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
    // TODO
    return null;
  }

  /**
   * Helper function to find the Account of Trader with id traderId.
   *
   * @throws IllegalArgumentException if traderId is null or not found
   */
  private Account findAccountByTraderId(Integer traderId) {
    return accountDao.findById(traderId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid traderId"));
  }

}
