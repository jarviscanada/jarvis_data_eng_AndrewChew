package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
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
public class AccountDaoIntTest {

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  private Account savedAccount;

  private Trader savedTrader;

  @Before
  public void insertOne() {
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
  }

  @After
  public void deleteOne() {
    accountDao.deleteById(1);

    traderDao.deleteById(1);
  }

  @Test
  public void findAllById() {
    List<Account> accounts = Lists
        .newArrayList(accountDao.findAllById(Arrays.asList(savedAccount.getId(), -1)));
    assertEquals(1, accounts.size());
    assertEquals(savedAccount.getAmount(), accounts.get(0).getAmount());
  }

  @Test
  public void updateValues() {
    Double original = savedAccount.getAmount();
    Double updated = 20.1d;
    Optional<Account> account = accountDao.findById(1);
    assertTrue(account.isPresent());
    assertEquals(original, account.get().getAmount());
    savedAccount.setAmount(updated);
    accountDao.save(savedAccount);
    Optional<Account> updatedAccount = accountDao.findById(1);
    assertTrue(updatedAccount.isPresent());
    assertEquals(updated, updatedAccount.get().getAmount());
  }
}