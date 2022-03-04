package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * MarketDataDao is responsible for getting Quotes from IEX.
 */
@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

  private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
  private final String IEX_BATCH_URL;

  private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager httpClientConnectionManager;

  private static final int HTTP_OK = 200;
  private static final int HTTP_NOT_FOUND = 404;

  @Autowired
  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
      MarketDataConfig marketDataConfig) {
    this.httpClientConnectionManager = httpClientConnectionManager;
    IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
  }

  /**
   * Get an IexQuote.
   *
   * @param ticker ticker string
   * @return an IexQuote
   * @throws IllegalArgumentException if a given ticker is invalid
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public Optional<IexQuote> findById(String ticker) {
    Optional<IexQuote> iexQuote;
    List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

    if (quotes.size() == 0) {
      return Optional.empty();
    } else if (quotes.size() == 1) {
      iexQuote = Optional.of(quotes.get(0));
    } else {
      throw new DataRetrievalFailureException("Unexpected number of quotes");
    }
    return iexQuote;
  }

  /**
   * Get IexQuotes.
   *
   * @param tickers list of ticker strings
   * @return a list of IexQuotes
   * @throws IllegalArgumentException if any ticker is invalid or tickers is empty
   * @throws DataRetrievalFailureException if HTTP request failed
   */
  @Override
  public List<IexQuote> findAllById(Iterable<String> tickers) {
    // Build ticker string.
    StringBuilder allTickers = new StringBuilder();
    int expectedQuotes = 0;
    for (Iterator<String> iterator = tickers.iterator(); iterator.hasNext(); expectedQuotes++) {
      String ticker = iterator.next();
      allTickers.append(ticker);
      if (iterator.hasNext()) {
        allTickers.append(",");
      }
    }

    Optional<String> jsonString = executeHttpGet(String.format(IEX_BATCH_URL, allTickers));

    if (jsonString.isPresent()) {
      JSONObject iexQuotesJson = new JSONObject(jsonString.get());
      ObjectMapper objectMapper = new ObjectMapper();
      List<IexQuote> quotes = new ArrayList<>(iexQuotesJson.length());
      Iterator<String> keys = iexQuotesJson.keys();

      while (keys.hasNext()) {
        String key = keys.next();
        try {
          quotes.add(objectMapper.readValue(
              iexQuotesJson.getJSONObject(key).get("quote").toString(), IexQuote.class));
        } catch (IOException e) {
          logger.error("Failed to convert json to IexQuote object", e);
          throw new RuntimeException("Failed to convert json to IexQuote object", e);
        }
      }

      // Check if all quotes returned.
      if (quotes.size() != expectedQuotes) {
        throw new IllegalArgumentException("Invalid ticker");
      }

      return quotes;
    } else {
      logger.error("Invalid ticker");
      throw new IllegalArgumentException("Invalid ticker");
    }
  }

  /**
   * Execute a HTTP GET and return HTTP entity as a string.
   *
   * @param url resource URL
   * @return HTTP response string or Optional.empty() for 404 response
   * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
   */
  public Optional<String> executeHttpGet(String url) {
    Optional<String> jsonString;
    HttpClient httpClient = this.getHttpClient();
    HttpGet request = new HttpGet(url);
    HttpResponse response;

    try {
      response = httpClient.execute(request);
    } catch (IOException e) {
      logger.error("HTTP failed", e);
      throw new DataRetrievalFailureException("HTTP failed", e);
    }

    int status = response.getStatusLine().getStatusCode();
    if (status == HTTP_OK) {
      try {
        jsonString = Optional.of(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        logger.error("Failed to convert entity to json String", e);
        throw new DataRetrievalFailureException("Failed to convert entity to json String", e);
      }
    } else if (status == HTTP_NOT_FOUND) {
      jsonString = Optional.empty();
    } else {
      logger.error("Unexpected HTTP status:" + status);
      throw new DataRetrievalFailureException("Unexpected HTTP status:" + status);
    }

    return jsonString;
  }

  /**
   * Borrow a HTTPClient from the httpClientConnectionManager.
   *
   * @return a httpClient
   */
  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(httpClientConnectionManager)
        // prevent connectionManager shutdown when calling httpClient.close().
        .setConnectionManagerShared(true)
        .build();
  }

  @Override
  public <S extends IexQuote> S save(S s) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public boolean existsById(String s) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public Iterable<IexQuote> findAll() {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public long count() {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public void deleteById(String s) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public void delete(IexQuote iexQuote) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public void deleteAll(Iterable<? extends IexQuote> iterable) {
    throw new UnsupportedOperationException("Not Implemented.");
  }

  @Override
  public void deleteAll() {
    throw new UnsupportedOperationException("Not Implemented.");
  }
}
