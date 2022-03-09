package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
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
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void setUp() {
    quoteDao.deleteAll();
  }

  @Test
  public void findIexQuoteByTicker() {
    IexQuote iexQuote = quoteService.findIexQuoteByTicker("AAPL");
    assertEquals("AAPL", iexQuote.getSymbol());
  }

  @Test(expected = IllegalArgumentException.class)
  public void findIexQuoteByTickerFail() {
    quoteService.findIexQuoteByTicker("test123");
  }

  @Test
  public void updateMarketData() throws InterruptedException {
    List<String> tickers = new ArrayList<>();
    tickers.add("AAPL");
    tickers.add("FB");
    tickers.add("JNJ");
    List<Quote> quotes = quoteService.saveQuotes(tickers);
    List<Double> beforeUpdatePrices = new ArrayList<>();
    quotes.forEach(quote -> beforeUpdatePrices.add(quote.getLastPrice()));
    Thread.sleep(30000);
    quoteService.updateMarketData();
    quotes = quoteService.findAllQuotes();
    List<Double> afterUpdatePrices = new ArrayList<>();
    quotes.forEach(quote -> afterUpdatePrices.add(quote.getLastPrice()));
    assertNotEquals(beforeUpdatePrices, afterUpdatePrices);
    System.out.println(beforeUpdatePrices.toString());
    System.out.println(afterUpdatePrices.toString());
  }

  @Test
  public void findAllQuotes() {
    List<Quote> quotes = quoteService.findAllQuotes();
    assertEquals(0, quotes.size());
    List<String> tickers = new ArrayList<>();
    tickers.add("AAPL");
    tickers.add("FB");
    tickers.add("JNJ");
    quoteService.saveQuotes(tickers);
    quotes = quoteService.findAllQuotes();
    assertEquals(3, quotes.size());
  }
}