package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
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
public class PositionDaoIntTest {

  @Autowired
  private PositionDao positionDao;

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private QuoteDao quoteDao;

  private Position savedPosition;

  private SecurityOrder savedSecurityOrder1;
  private SecurityOrder savedSecurityOrder2;
  private SecurityOrder savedSecurityOrder3;

  private Account savedAccount;

  private Trader savedTrader;

  private Quote savedQuote;

  @Before
  public void insertOne() {
    savedQuote = new Quote();
    savedQuote.setId("TEST");
    savedQuote.setLastPrice(10.0d);
    savedQuote.setBidPrice(10.1d);
    savedQuote.setBidSize(100);
    savedQuote.setAskPrice(10.2d);
    savedQuote.setAskSize(100);
    quoteDao.save(savedQuote);

    savedTrader = new Trader();
    savedTrader.setId(1);
    savedTrader.setCountry("Canada");
    savedTrader.setDob(new Date(1995, 11, 06));
    savedTrader.setEmail("andrewchew.ym@gmail.com");
    savedTrader.setFirstName("Andrew");
    savedTrader.setLastName("Chew");
    traderDao.save(savedTrader);

    savedAccount = new Account();
    savedAccount.setId(1);
    savedAccount.setAmount(10.2d);
    savedAccount.setTraderId(1);
    accountDao.save(savedAccount);

    savedSecurityOrder1 = new SecurityOrder();
    savedSecurityOrder1.setId(1);
    savedSecurityOrder1.setAccountId(1);
    savedSecurityOrder1.setStatus("FILLED");
    savedSecurityOrder1.setTicker("TEST");
    savedSecurityOrder1.setSize(100);
    savedSecurityOrder1.setPrice(10.2d);
    savedSecurityOrder1.setNotes("notes for testing purposes");
    securityOrderDao.save(savedSecurityOrder1);

    savedSecurityOrder2 = new SecurityOrder();
    savedSecurityOrder2.setId(2);
    savedSecurityOrder2.setAccountId(1);
    savedSecurityOrder2.setStatus("PENDING");
    savedSecurityOrder2.setTicker("TEST");
    savedSecurityOrder2.setSize(10);
    savedSecurityOrder2.setPrice(10.3d);
    savedSecurityOrder2.setNotes("notes for testing purposes");
    securityOrderDao.save(savedSecurityOrder2);

    savedSecurityOrder3 = new SecurityOrder();
    savedSecurityOrder3.setId(3);
    savedSecurityOrder3.setAccountId(1);
    savedSecurityOrder3.setStatus("FILLED");
    savedSecurityOrder3.setTicker("TEST");
    savedSecurityOrder3.setSize(50);
    savedSecurityOrder3.setPrice(10.4d);
    savedSecurityOrder3.setNotes("notes for testing purposes");
    securityOrderDao.save(savedSecurityOrder3);

    savedPosition = new Position();
    savedPosition.setAccountId(1);
    savedPosition.setTicker("TEST");
    savedPosition.setPosition(150);
  }

  @After
  public void deleteOne() {
    securityOrderDao.deleteAll();

    accountDao.deleteById(1);

    traderDao.deleteById(1);

    quoteDao.deleteById("TEST");
  }

  @Test
  public void findById() {
    Integer accountId = savedPosition.getAccountId();
    String ticker = savedPosition.getTicker();
    assertTrue(positionDao.existsById(accountId, ticker));
    Optional<Position> position = positionDao.findById(accountId, ticker);
    assertTrue(position.isPresent());
    assertEquals(savedPosition.getPosition(), position.get().getPosition());
    assertEquals(1, positionDao.count());
    List<Position> positions = positionDao.findAll();
    assertEquals(1, positions.size());
  }
}