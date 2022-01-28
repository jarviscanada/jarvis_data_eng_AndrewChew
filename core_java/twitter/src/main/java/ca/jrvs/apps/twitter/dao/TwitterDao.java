package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.JsonUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterDao implements CrdDao<Tweet, String> {

  // URI constants.
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy";

  // URI symbols.
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  // Response code.
  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  /**
   * Create a Tweet object.
   *
   * @param entity Tweet to be created
   * @return Tweet
   */
  @Override
  public Tweet create(Tweet entity) {
    // Construct URI.
    URI uri;
    try {
      uri = getPostUri(entity);
    } catch (URISyntaxException | UnsupportedEncodingException e) {
      throw new IllegalArgumentException("Invalid Tweet", e);
    }
    HttpResponse response = httpHelper.httpPost(uri);
    return parseResponseBody(response, HTTP_OK);
  }

  /**
   * Find Tweet by its id.
   *
   * @param s id string of entity to find
   * @return Tweet
   */
  @Override
  public Tweet findById(String s) {
    try {
      String uriStr = API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + s;
      HttpResponse response = httpHelper.httpGet(new URI(uriStr));
      return parseResponseBody(response, HTTP_OK);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid id input", e);
    }
  }

  /**
   * Delete a Tweet by its id.
   *
   * @param s id string of Tweet to be deleted
   * @return deleted Tweet
   */
  @Override
  public Tweet deleteById(String s) {
    try {
      String uriStr = API_BASE_URI + DELETE_PATH + "/" + s + ".json";
      HttpResponse response = httpHelper.httpPost(new URI(uriStr));
      return parseResponseBody(response, HTTP_OK);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid id input", e);
    }
  }

  /**
   * Helper function to generate URI to create a tweet.
   *
   * @param tweet Tweet to create
   * @return URI to create tweet
   * @throws URISyntaxException exception from failure to parse String to URI
   */
  private URI getPostUri(Tweet tweet) throws URISyntaxException, UnsupportedEncodingException {
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    String text = tweet.getText();
    String uriStr = API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL
        + percentEscaper.escape(text) + AMPERSAND
        + "long" + EQUAL + tweet.getCoordinates().getCoordinates().get(0) + AMPERSAND
        + "lat" + EQUAL + tweet.getCoordinates().getCoordinates().get(1);

    return new URI(uriStr);
  }

  /**
   * Helper function to check response status code and convert response entity to a Tweet object.
   *
   * @param response response to check and convert
   * @param expectedStatusCode expected response status code
   * @return a Tweet object
   */
  private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
    // Check response status.
    int status = response.getStatusLine().getStatusCode();
    if (status != expectedStatusCode) {
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        throw new RuntimeException("Response has no entity", e);
      }
      throw new RuntimeException("Unexpected HTTP status:" + status);
    }

    if (response.getEntity() == null) {
      throw new RuntimeException("Response has no entity");
    }

    // Convert response to json string.
    String jsonStr;
    try {
      jsonStr = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert entity to json String", e);
    }

    // Convert json string to Tweet object and return.
    try {
      return JsonUtil.toObjectFromJson(jsonStr);
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert json String to Tweet object", e);
    }

  }

}
