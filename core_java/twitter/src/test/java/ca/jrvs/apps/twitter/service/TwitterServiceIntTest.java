package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TweetUtil;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import java.awt.TextComponent;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwitterServiceIntTest {

  private static TwitterService twitterService;
  private static List<String> ids;

  @BeforeClass
  public static void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey + " | " + consumerSecret + " | "
        + accessToken + " | " + tokenSecret);
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
        accessToken, tokenSecret);
    TwitterDao twitterDao = new TwitterDao(httpHelper);
    twitterService = new TwitterService(twitterDao);
    ids = new ArrayList<>();
  }

  @Test
  public void postTweetSuccessTweetUtil() {
    String text = "testing tweet";
    Double lon = 1.0;
    Double lat = -1.0;
    Tweet actual = twitterService.postTweet(TweetUtil.buildTweet(text, lon, lat));
    assertNotNull(actual);
    assertNotNull(actual.getId_str());
    assertEquals(text, actual.getText());
    assertEquals(lon, actual.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, actual.getCoordinates().getCoordinates().get(1));
    ids.add(actual.getId_str());
  }

  @Test
  public void postTweetSuccessManual() {
    String text = "testing tweet manually";
    Tweet tweet = new Tweet();
    tweet.setText(text);
    Tweet actual = twitterService.postTweet(tweet);
    assertNotNull(actual);
    assertNotNull(actual.getId_str());
    assertNull(actual.getCoordinates());
    assertEquals(text, actual.getText());
    ids.add(actual.getId_str());
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweetFailureNoText() {
    Double lon = 1.0;
    Double lat = -1.0;
    twitterService.postTweet(TweetUtil.buildTweet("", lon, lat));
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweetFailureLongText() {
    String longText = "This is some ridiculously long text that I am coming up with here."
        + "It really has no business being this long other than to test my app when handling"
        + " ridiculously long texts such as the one I am coming up with here. I think this "
        + "should be enough to test what I want to test but I have to be sure so I should keep"
        + " writing... I am not going to count if this is longer than the 140 characters I need"
        + " to test my code so I shall keep writing. However, I am pretty sure I have hit my "
        + "required amount so I shall stop. Please be enough.........";
    Double lon = 1.0;
    Double lat = -1.0;
    twitterService.postTweet(TweetUtil.buildTweet(longText, lon, lat));
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweetFailureBadLongitude() {
    Double lon = 1000.0;
    Double lat = -1.0;
    twitterService.postTweet(TweetUtil.buildTweet("", lon, lat));
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweetFailureBadLatitude() {
    Double lon = 1.0;
    Double lat = -1000.0;
    twitterService.postTweet(TweetUtil.buildTweet("", lon, lat));
  }

  @Test
  public void showTweet() {
  }

  @Test
  public void deleteTweets() {
  }
}