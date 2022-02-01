package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.JsonUtil;
import ca.jrvs.apps.twitter.dao.helper.TweetUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  @Test
  public void postTweet() throws Exception {
    // Test failed request.
    String hashTag = "#abc";
    String text = "@someone some text " + hashTag + " " + System.currentTimeMillis();
    Double lon = -1d;
    Double lat = 1d;
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.create(TweetUtil.buildTweet(text, lon, lat));
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    // Test request.
    String tweetJsonStr = "{\n"
        + "   \"created_at\":\"Mon Jan 31 15:00:00 +0000 2022\",\n"
        + "   \"id\":9999,\n"
        + "   \"id_str\":\"9999\",\n"
        + "   \"text\":\"test with loc223\",\n"
        + "   \"entities\":{\n"
        + "      \"hashtags\":[],"
        + "      \"user_mentions\":[]"
        + "   },\n"
        + "   \"coordinates\":null,"
        + "   \"retweet_count\":0,\n"
        + "   \"favorite_count\":0,\n"
        + "   \"favorited\":false,\n"
        + "   \"retweeted\":false\n"
        + "}";
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr);
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.create(TweetUtil.buildTweet(text, lon, lat));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void showTweet() throws Exception {
    // Test failed request.
    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.findById("9999");
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    // Test request.
    String tweetJsonStr = "{\n"
        + "   \"created_at\":\"Mon Jan 31 15:00:00 +0000 2022\",\n"
        + "   \"id\":9999,\n"
        + "   \"id_str\":\"9999\",\n"
        + "   \"text\":\"test with loc223\",\n"
        + "   \"entities\":{\n"
        + "      \"hashtags\":[],"
        + "      \"user_mentions\":[]"
        + "   },\n"
        + "   \"coordinates\":null,"
        + "   \"retweet_count\":0,\n"
        + "   \"favorite_count\":0,\n"
        + "   \"favorited\":false,\n"
        + "   \"retweeted\":false\n"
        + "}";
    when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr);
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.findById("9999");
    assertNotNull(tweet);
    assertEquals("9999", tweet.getId_str());
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteTweet() throws Exception {
    // Test failed request.
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.deleteById("9999");
      fail();
    } catch (RuntimeException e) {
      assertTrue(true);
    }

    // Test request.
    String tweetJsonStr = "{\n"
        + "   \"created_at\":\"Mon Jan 31 15:00:00 +0000 2022\",\n"
        + "   \"id\":9999,\n"
        + "   \"id_str\":\"9999\",\n"
        + "   \"text\":\"test with loc223\",\n"
        + "   \"entities\":{\n"
        + "      \"hashtags\":[],"
        + "      \"user_mentions\":[]"
        + "   },\n"
        + "   \"coordinates\":null,"
        + "   \"retweet_count\":0,\n"
        + "   \"favorite_count\":0,\n"
        + "   \"favorited\":false,\n"
        + "   \"retweeted\":false\n"
        + "}";
    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr);
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.deleteById("9999");
    assertNotNull(tweet);
    assertEquals("9999", tweet.getId_str());
    assertNotNull(tweet.getText());
  }

}