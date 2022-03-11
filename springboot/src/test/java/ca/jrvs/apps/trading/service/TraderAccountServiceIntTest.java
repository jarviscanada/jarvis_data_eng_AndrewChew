package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import java.sql.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

  @Autowired
  private TraderAccountService traderAccountService;

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private QuoteDao quoteDao;

  private TraderAccountView savedView;

  @Before
  public void insertOne() {
    Trader trader = new Trader();
    trader.setCountry("Canada");
    trader.setDob(new Date(1995, 11, 06));
    trader.setEmail("andrewchew.ym@gmail.com");
    trader.setFirstName("Andrew");
    trader.setLastName("Chew");
    savedView = traderAccountService.createTraderAndAccount(trader);
  }

  @After
  public void deleteOne() {
    securityOrderDao.deleteAll();
    traderAccountService.deleteTraderById(savedView.getTrader().getId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void createTraderAndAccountIdSet() {
    Trader trader = new Trader();
    trader.setId(1000);
    traderAccountService.createTraderAndAccount(trader);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createTraderAndAccountNullFields() {
    Trader trader = new Trader();
    traderAccountService.createTraderAndAccount(trader);
  }

  @Test
  public void depositAndWithdraw() {
    Trader trader = savedView.getTrader();
    Account account = savedView.getAccount();
    Integer traderId = trader.getId();
    Double original = 0d;
    Double newAmount = 10d;
    Double fund = 10d;
    assertEquals(original, account.getAmount());
    account = traderAccountService.deposit(traderId, fund);
    assertEquals(newAmount, account.getAmount());
    account = traderAccountService.withdraw(traderId, fund);
    assertEquals(original, account.getAmount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void depositNullTrader() {
    Integer traderId = null;
    Double fund = 10d;
    traderAccountService.deposit(traderId, fund);
  }

  @Test(expected = IllegalArgumentException.class)
  public void depositBadTrader() {
    Integer traderId = 1000;
    Double fund = 10d;
    traderAccountService.deposit(traderId, fund);
  }

  @Test(expected = IllegalArgumentException.class)
  public void depositNegative() {
    Trader trader = savedView.getTrader();
    Integer traderId = trader.getId();
    Double fund = -10d;
    traderAccountService.deposit(traderId, fund);
  }

  @Test(expected = IllegalArgumentException.class)
  public void withdrawNullTrader() {
    Integer traderId = null;
    Double fund = 10d;
    traderAccountService.withdraw(traderId, fund);
  }

  @Test(expected = IllegalArgumentException.class)
  public void withdrawBadTrader() {
    Integer traderId = 1000;
    Double fund = 10d;
    traderAccountService.withdraw(traderId, fund);
  }

  @Test(expected = IllegalArgumentException.class)
  public void withdrawNegative() {
    Trader trader = savedView.getTrader();
    Integer traderId = trader.getId();
    Double fund = -10d;
    traderAccountService.withdraw(traderId, fund);
  }

  @Test(expected = IllegalArgumentException.class)
  public void withdrawInsufficientFunds() {
    Trader trader = savedView.getTrader();
    Integer traderId = trader.getId();
    Double fund = 10d;
    traderAccountService.withdraw(traderId, fund);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DeleteNullTrader() {
    Integer traderId = null;
    traderAccountService.deleteTraderById(traderId);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DeleteBadTrader() {
    Integer traderId = 2;
    traderAccountService.deleteTraderById(traderId);
  }

  @Test(expected = IllegalArgumentException.class)
  public void DeleteTraderAccountBalance() {
    Integer traderId = savedView.getTrader().getId();
    Double fund = 10d;
    try {
      traderAccountService.deposit(traderId, fund);
      traderAccountService.deleteTraderById(traderId);
    } finally {
      traderAccountService.withdraw(traderId, fund);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void DeleteTraderOpenPosition() {
    Integer traderId = savedView.getTrader().getId();

    Quote quote = new Quote();
    quote.setTicker("TEST");
    quote.setLastPrice(10d);
    quote.setBidPrice(10d);
    quote.setBidSize(10);
    quote.setAskPrice(10d);
    quote.setAskSize(10);
    quoteDao.save(quote);

    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setAccountId(traderId);
    securityOrder.setNotes("blah");
    securityOrder.setPrice(10d);
    securityOrder.setSize(10);
    securityOrder.setStatus("PENDING");
    securityOrder.setTicker("TEST");

    securityOrderDao.save(securityOrder);
    traderAccountService.deleteTraderById(traderId);
  }
}