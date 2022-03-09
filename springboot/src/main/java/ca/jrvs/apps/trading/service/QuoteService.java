package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * QuoteService is responsible for the business logic for getting Quotes from IEX and CRUDing Quotes
 * to and from the database.
 */
@Transactional
@Service
public class QuoteService {

  private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;
  }

  /**
   * Update quote table against IEX source.
   *  - Get all Quotes from the Database
   *  - Foreach ticker get IexQuote
   *  - Convert IexQuote to Quote entity
   *  - Persist Quote to database
   *
   * @throws ResourceNotFoundException if ticker is not found from IEX
   * @throws DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException for invalid input
   */
  public void updateMarketData() {
    // TODO
  }

  /**
   * Helper method to convert IexQuote to Quote entity.
   * Note: 'iexQuote.getLatestPrice() == null' if the stock market is closed -> set default values.
   *
   * @param iexQuote to convert
   * @return converted Quote entity
   */
  protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
    Quote quote = new Quote();
    quote.setAskPrice(iexQuote.getIexAskPrice());
    quote.setAskSize(iexQuote.getIexAskSize());
    quote.setBidPrice(iexQuote.getIexBidPrice());
    quote.setBidSize(iexQuote.getIexBidSize());
    quote.setId(iexQuote.getSymbol());
    quote.setLastPrice(
        iexQuote.getLatestPrice() == null ? iexQuote.getClose() : iexQuote.getLatestPrice());
    return quote;
  }

  /**
   * Validate (against IEX) and save given tickers to quote table.
   *  - Get IexQuotes
   *  - Convert each IexQuote to Quote entity
   *  - Persist the quote to database
   *
   * @param tickers a list of tickers/ symbols
   * @return List of Quotes saved
   * @throws IllegalArgumentException if ticker is not found from IEX
   */
  public List<Quote> saveQuotes(List<String> tickers) {
    List<Quote> quotes = new ArrayList<>();
    tickers.forEach(quote -> quotes.add(saveQuote(quote)));
    return quotes;
  }

  /**
   * Helper method to save Quote.
   */
  public Quote saveQuote(String ticker) {
    IexQuote iexQuote = findIexQuoteByTicker(ticker);
    Quote quote = buildQuoteFromIexQuote(iexQuote);
    return saveQuote(quote);
  }

  /**
   * Update a given Quote to the quote table without validation.
   *
   * @param quote to update
   */
  public Quote saveQuote(Quote quote) {
    return quoteDao.save(quote);
  }

  /**
   * Find all Quotes from the quote table.
   *
   * @return a list of Quotes
   */
  public List<Quote> findAllQuotes() {
    return quoteDao.findAll();
  }

  /**
   * Find an IexQuote.
   *
   * @param ticker id
   * @return IexQuote object
   * @throws IllegalArgumentException if ticker is invalid
   */
  public IexQuote findIexQuoteByTicker(String ticker) {
    return marketDataDao.findById(ticker)
        .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
  }
}
