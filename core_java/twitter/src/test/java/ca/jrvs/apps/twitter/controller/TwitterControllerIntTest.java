package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.HashMap;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TwitterControllerIntTest {

  private static TwitterController twitterController;

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
    TwitterService twitterService = new TwitterService(twitterDao);
    twitterController = new TwitterController(twitterService);
  }

  @Test
  public void firstPostTweetSuccess() {
    String[] args = {"post", "test tweet", "1:-1"};
    Double expectedLon = Double.parseDouble(args[2].split(":")[1]);
    Double expectedLat = Double.parseDouble(args[2].split(":")[0]);
    Tweet actual = twitterController.postTweet(args);
    assertNotNull(actual);
    assertEquals(args[1], actual.getText());
    assertEquals(expectedLon, actual.getCoordinates().getCoordinates().get(0));
    assertEquals(expectedLat, actual.getCoordinates().getCoordinates().get(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstPostTweetFailureWrongNumArgs() {
    String[] args = {"test tweet"};
    twitterController.postTweet(args);
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstPostTweetFailureWrongNumCoords() {
    String[] args = {"post", "test tweet", "1:1:1"};
    twitterController.postTweet(args);
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstPostTweetFailureInvalidCoords() {
    String[] args = {"post", "test tweet", "-10000:10"};
    twitterController.postTweet(args);
  }

  @Test
  public void secondShowTweetSuccess() {
  }

  @Test
  public void thirdDeleteTweetSuccess() {
  }
}