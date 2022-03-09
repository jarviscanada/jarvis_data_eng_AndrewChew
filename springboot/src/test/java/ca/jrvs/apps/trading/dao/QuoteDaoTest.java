package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
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
public class QuoteDaoTest {

  @Autowired
  private QuoteDao quoteDao;

  private Quote savedQuote;

  @Before
  public void insertOne() {
    savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("aapl");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);
  }

  @After
  public void deleteOne() {
    quoteDao.deleteById(savedQuote.getId());
  }

  @Test
  public void findExistingTicker() {
    Optional<Quote> quote = quoteDao.findById("aapl");
    assertTrue(quote.isPresent());
  }

  @Test
  public void findNonExistingTicker() {
    Optional<Quote> quote = quoteDao.findById("test");
    assertFalse(quote.isPresent());
  }

  @Test
  public void existById() {
    assertTrue(quoteDao.existsById("aapl"));
    assertFalse(quoteDao.existsById("test"));
  }

  @Test
  public void count() {
    assertEquals(1, quoteDao.count());
  }

  @Test
  public void findAll() {
    Quote quote = new Quote();
    quote = new Quote();
    quote.setAskPrice(5d);
    quote.setAskSize(5);
    quote.setBidPrice(5.2d);
    quote.setBidSize(5);
    quote.setId("bb");
    quote.setLastPrice(5.1d);
    quoteDao.save(quote);

    List<Quote> quotes = quoteDao.findAll();
    assertEquals(2, quotes.size());
    assertEquals("aapl", quotes.get(0).getTicker());
    assertEquals("bb", quotes.get(1).getTicker());
    quoteDao.deleteById("bb");
    assertEquals(1, quoteDao.findAll().size());
  }
}