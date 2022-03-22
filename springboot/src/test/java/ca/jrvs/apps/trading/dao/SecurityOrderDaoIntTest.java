package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;
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
public class SecurityOrderDaoIntTest {

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private QuoteDao quoteDao;

  private SecurityOrder savedSecurityOrder;

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

    savedSecurityOrder = new SecurityOrder();
    savedSecurityOrder.setId(1);
    savedSecurityOrder.setAccountId(1);
    savedSecurityOrder.setStatus("PENDING");
    savedSecurityOrder.setTicker("TEST");
    savedSecurityOrder.setSize(1);
    savedSecurityOrder.setPrice(10.2d);
    savedSecurityOrder.setNotes("notes for testing purposes");
    securityOrderDao.save(savedSecurityOrder);
  }

  @After
  public void deleteOne() {
    securityOrderDao.deleteById(1);

    accountDao.deleteById(1);

    traderDao.deleteById(1);

    quoteDao.deleteById("TEST");
  }

  @Test
  public void findAllById() {
    List<SecurityOrder> securityOrders = Lists
        .newArrayList(securityOrderDao.findAllById(Arrays.asList(savedSecurityOrder.getId(), -1)));
    assertEquals(1, securityOrders.size());
    assertEquals(savedSecurityOrder.getId(), securityOrders.get(0).getId());
    assertEquals(savedSecurityOrder.getAccountId(), securityOrders.get(0).getAccountId());
    assertEquals(savedSecurityOrder.getTicker(), securityOrders.get(0).getTicker());
  }

  @Test
  public void updateValues() {
    String original = "PENDING";
    String updated = "CANCELLED";
    Optional<SecurityOrder> securityOrder = securityOrderDao.findById(1);
    assertTrue(securityOrder.isPresent());
    assertEquals(original, securityOrder.get().getStatus());
    savedSecurityOrder.setStatus(updated);
    securityOrderDao.save(savedSecurityOrder);
    Optional<SecurityOrder> updatedSecurityOrder = securityOrderDao.findById(1);
    assertTrue(updatedSecurityOrder.isPresent());
    assertEquals(updated, updatedSecurityOrder.get().getStatus());
  }
}