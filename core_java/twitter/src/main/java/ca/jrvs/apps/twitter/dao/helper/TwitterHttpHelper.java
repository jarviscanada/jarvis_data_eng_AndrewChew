package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class TwitterHttpHelper implements HttpHelper {

  /**
   * Dependencies are specified as private member variables.
   */
  private OAuthConsumer consumer;
  private HttpClient httpClient;

  /**
   * Constructor: Setup dependencies using secrets.
   *
   * @param consumerKey consumer key
   * @param consumerSecret consumer secret
   * @param accessToken access token
   * @param tokenSecret token secret
   */
  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken,
      String tokenSecret) {
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);
    httpClient = new DefaultHttpClient(); // Single connection.
  }

  /**
   * Execute a HTTP Post call
   *
   * @param uri
   * @return
   */
  @Override
  public HttpResponse httpPost(URI uri) {
    return null;
  }

  /**
   * Execute a HTTP Get call
   *
   * @param uri
   * @return
   */
  @Override
  public HttpResponse httpGet(URI uri) {
    return null;
  }
}
