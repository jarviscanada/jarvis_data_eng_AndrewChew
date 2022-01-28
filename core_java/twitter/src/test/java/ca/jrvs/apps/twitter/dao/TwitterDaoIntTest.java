package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.JsonUtil;
import ca.jrvs.apps.twitter.dao.helper.TweetUtil;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;

public class TwitterDaoIntTest {

  private TwitterDao twitterDao;

  private static String id;
  private static String text;

  @Before
  public void setup() {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey + " | " + consumerSecret + " | "
        + accessToken + " | " + tokenSecret);
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
        accessToken, tokenSecret);
    twitterDao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() throws Exception {
    String hashTag = "#abc";
    String text = "@someone some text " + hashTag + " " + System.currentTimeMillis();
    Double lon = -1d;
    Double lat = 1d;
    Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
    System.out.println(JsonUtil.toPrettyJson(postTweet));

    Tweet tweet = twitterDao.create(postTweet);
    System.out.println(JsonUtil.toPrettyJson(tweet));
    TwitterDaoIntTest.id = tweet.getId_str();
    TwitterDaoIntTest.text = tweet.getText();

    assertEquals(text, tweet.getText());

    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
  }

  @Test
  public void findByID() throws Exception {
    System.out.println(TwitterDaoIntTest.id);
    Tweet tweet = twitterDao.findById(TwitterDaoIntTest.id);
    System.out.println(JsonUtil.toPrettyJson(tweet));
    Double expectedLong = -1d;
    Double expectedLat = 1d;
    assertEquals(TwitterDaoIntTest.text, tweet.getText());

    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    assertEquals(expectedLong, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(expectedLat, tweet.getCoordinates().getCoordinates().get(1));
  }

  @Test
  public void deleteById() throws Exception {
    Tweet tweet = twitterDao.deleteById(TwitterDaoIntTest.id);
    System.out.println(JsonUtil.toPrettyJson(tweet));
    Double expectedLong = -1d;
    Double expectedLat = 1d;
    assertEquals(TwitterDaoIntTest.text, tweet.getText());

    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().size());
    assertEquals(expectedLong, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(expectedLat, tweet.getCoordinates().getCoordinates().get(1));
  }
}