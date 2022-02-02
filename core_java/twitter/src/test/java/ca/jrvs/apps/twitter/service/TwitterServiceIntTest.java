package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.JsonUtil;
import ca.jrvs.apps.twitter.dao.helper.TweetUtil;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TwitterServiceIntTest {

  private static TwitterService twitterService;
  private static Map<Integer, String> ids;

  @BeforeClass
  public static void setUp() {
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
    ids = new HashMap<>();
  }

  @Test
  public void firstPostTweetSuccessTweetUtil() {
    String text = "testing tweet";
    Double lon = 1.0;
    Double lat = -1.0;
    Tweet actual = twitterService.postTweet(TweetUtil.buildTweet(text, lon, lat));
    assertNotNull(actual);
    assertNotNull(actual.getId_str());
    assertEquals(text, actual.getText());
    assertEquals(lon, actual.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, actual.getCoordinates().getCoordinates().get(1));
    ids.put(1, actual.getId_str());
  }

  @Test
  public void firstPostTweetSuccessManual() {
    String text = "testing tweet manually";
    Tweet tweet = new Tweet();
    tweet.setText(text);
    Tweet actual = twitterService.postTweet(tweet);
    assertNotNull(actual);
    assertNotNull(actual.getId_str());
    assertNull(actual.getCoordinates());
    assertEquals(text, actual.getText());
    ids.put(2, actual.getId_str());
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstPostTweetFailureNoText() {
    Double lon = 1.0;
    Double lat = -1.0;
    twitterService.postTweet(TweetUtil.buildTweet("", lon, lat));
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstPostTweetFailureLongText() {
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
  public void firstPostTweetFailureBadLongitude() {
    Double lon = 1000.0;
    Double lat = -1.0;
    twitterService.postTweet(TweetUtil.buildTweet("", lon, lat));
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstPostTweetFailureBadLatitude() {
    Double lon = 1.0;
    Double lat = -1000.0;
    twitterService.postTweet(TweetUtil.buildTweet("", lon, lat));
  }

  @Test
  public void secondShowTweetSuccessAllFields() throws Exception{
    String[] fields = {};
    Tweet actual1 = twitterService.showTweet(ids.get(1), fields);
    Tweet actual2 = twitterService.showTweet(ids.get(2), fields);

    assertNotNull(actual1);
    assertNotNull(actual1.getCreated_at());
    assertNotNull(actual1.getId());
    assertNotNull(actual1.getId_str());
    assertEquals(ids.get(1), actual1.getId_str());
    assertNotNull(actual1.getText());
    assertNotNull(actual1.getEntities());
    assertNotNull(actual1.getCoordinates());
    assertNotNull(actual1.getRetweet_count());
    assertNotNull(actual1.getFavorite_count());
    assertNotNull(actual1.isFavorited());
    assertNotNull(actual1.isRetweeted());

    assertNotNull(actual2);
    assertNotNull(actual2.getCreated_at());
    assertNotNull(actual2.getId());
    assertNotNull(actual2.getId_str());
    assertEquals(ids.get(2), actual2.getId_str());
    assertNotNull(actual2.getText());
    assertNotNull(actual2.getEntities());
    assertNull(actual2.getCoordinates());
    assertNotNull(actual2.getRetweet_count());
    assertNotNull(actual2.getFavorite_count());
    assertNotNull(actual2.isFavorited());
    assertNotNull(actual2.isRetweeted());

    System.out.println(JsonUtil.toPrettyJson(actual1));
    System.out.println(JsonUtil.toPrettyJson(actual2));
  }

  @Test
  public void secondShowTweetSuccessSelectFields() throws Exception {
    String[] fields = {"id_str", "text"};
    Tweet actual1 = twitterService.showTweet(ids.get(1), fields);
    Tweet actual2 = twitterService.showTweet(ids.get(2), fields);

    assertNotNull(actual1);
    assertNull(actual1.getCreated_at());
    assertNull(actual1.getId());
    assertNotNull(actual1.getId_str());
    assertEquals(ids.get(1), actual1.getId_str());
    assertNotNull(actual1.getText());
    assertNull(actual1.getEntities());
    assertNull(actual1.getCoordinates());
    assertNull(actual1.getRetweet_count());
    assertNull(actual1.getFavorite_count());
    assertNull(actual1.isFavorited());
    assertNull(actual1.isRetweeted());

    assertNotNull(actual2);
    assertNull(actual2.getCreated_at());
    assertNull(actual2.getId());
    assertNotNull(actual2.getId_str());
    assertEquals(ids.get(2), actual2.getId_str());
    assertNotNull(actual2.getText());
    assertNull(actual2.getEntities());
    assertNull(actual2.getCoordinates());
    assertNull(actual2.getRetweet_count());
    assertNull(actual2.getFavorite_count());
    assertNull(actual2.isFavorited());
    assertNull(actual2.isRetweeted());

    System.out.println(JsonUtil.toPrettyJson(actual1));
    System.out.println(JsonUtil.toPrettyJson(actual2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void secondShowTweetFailureBadId() {
    String[] fields = {};
    twitterService.showTweet("someid", fields);
  }

  @Test
  public void thirdDeleteTweetSuccess() {
    String[] idsArray = ids.values().toArray(new String[0]);
    List<Tweet> tweetsDeleted = twitterService.deleteTweets(idsArray);
    assertNotNull(tweetsDeleted.get(0));
    assertEquals(ids.get(1), tweetsDeleted.get(0).getId_str());
    assertNotNull(tweetsDeleted.get(0).getText());
    System.out.println(tweetsDeleted.get(0).getText());
    assertNotNull(tweetsDeleted.get(1));
    assertEquals(ids.get(2), tweetsDeleted.get(1).getId_str());
    assertNotNull(tweetsDeleted.get(1).getText());
    System.out.println(tweetsDeleted.get(1).getText());
  }

  @Test(expected = IllegalArgumentException.class)
  public void thirdDeleteTweetFailure() {
    String[] idsArray = {"some id", "1234"};
    twitterService.deleteTweets(idsArray);
  }
}