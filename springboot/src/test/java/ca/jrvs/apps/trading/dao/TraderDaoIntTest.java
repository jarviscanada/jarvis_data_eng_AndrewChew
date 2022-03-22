package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
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
public class TraderDaoIntTest {

  @Autowired
  private TraderDao traderDao;

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
  }

  @After
  public void deleteOne() {
    traderDao.deleteById(1);
  }

  @Test
  public void findAllById() {
    List<Trader> traders = Lists
        .newArrayList(traderDao.findAllById(Arrays.asList(savedTrader.getId(), -1)));
    assertEquals(1, traders.size());
    assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
  }
}