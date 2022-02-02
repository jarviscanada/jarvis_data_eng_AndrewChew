package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TwitterControllerIntTest {

  private static TwitterController twitterController;
  private static String id;

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
    id = actual.getId_str();
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
  public void secondShowTweetSuccessAllFields() {
    String[] args = {"show", id};
    Tweet actual = twitterController.showTweet(args);
    assertNotNull(actual);
    assertNotNull(actual.getCreated_at());
    assertNotNull(actual.getId());
    assertNotNull(actual.getId_str());
    assertNotNull(actual.getText());
    assertNotNull(actual.getEntities());
    assertNotNull(actual.getCoordinates());
    assertNotNull(actual.getRetweet_count());
    assertNotNull(actual.getFavorite_count());
    assertNotNull(actual.isFavorited());
    assertNotNull(actual.isRetweeted());
    assertEquals("test tweet", actual.getText());
    assertEquals(id, actual.getId_str());
  }

  @Test
  public void secondShowTweetSuccessSomeFields() {
    String[] args = {"show", id, "id_str,text"};
    Tweet actual = twitterController.showTweet(args);
    assertNotNull(actual);
    assertNull(actual.getCreated_at());
    assertNull(actual.getId());
    assertNotNull(actual.getId_str());
    assertNotNull(actual.getText());
    assertNull(actual.getEntities());
    assertNull(actual.getCoordinates());
    assertNull(actual.getRetweet_count());
    assertNull(actual.getFavorite_count());
    assertNull(actual.isFavorited());
    assertNull(actual.isRetweeted());
    assertEquals("test tweet", actual.getText());
    assertEquals(id, actual.getId_str());
  }

  @Test(expected = IllegalArgumentException.class)
  public void secondShowTweetFailureWrongNumArgs() {
    String[] args = {"show"};
    twitterController.showTweet(args);
  }

  @Test(expected = IllegalArgumentException.class)
  public void secondShowTweetFailureInvalidId() {
    String[] args = {"show", "some_id"};
    twitterController.showTweet(args);
  }

  @Test(expected = IllegalArgumentException.class)
  public void secondShowTweetFailureInvalidField() {
    String[] args = {"show", id, "some_field"};
    twitterController.showTweet(args);
  }

  @Test
  public void thirdDeleteTweetSuccess() {
    String[] args = {"delete", id};
    List<Tweet> actual = twitterController.deleteTweet(args);
    assertNotNull(actual.get(0));
    assertNotNull(actual.get(0).getCreated_at());
    assertNotNull(actual.get(0).getId());
    assertNotNull(actual.get(0).getId_str());
    assertNotNull(actual.get(0).getText());
    assertNotNull(actual.get(0).getEntities());
    assertNotNull(actual.get(0).getCoordinates());
    assertNotNull(actual.get(0).getRetweet_count());
    assertNotNull(actual.get(0).getFavorite_count());
    assertNotNull(actual.get(0).isFavorited());
    assertNotNull(actual.get(0).isRetweeted());
    assertEquals("test tweet", actual.get(0).getText());
    assertEquals(id, actual.get(0).getId_str());
  }

  @Test(expected = IllegalArgumentException.class)
  public void thirdDeleteTweetFailureWrongNumArgs() {
    String[] args = {"delete"};
    twitterController.deleteTweet(args);
  }

  @Test(expected = IllegalArgumentException.class)
  public void thirdDeleteTweetFailureInvalidId() {
    String[] args = {"delete", "some_id"};
    twitterController.deleteTweet(args);
  }
}