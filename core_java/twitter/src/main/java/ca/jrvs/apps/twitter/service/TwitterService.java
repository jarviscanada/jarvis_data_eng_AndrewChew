package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

  private CrdDao dao;

  @Autowired
  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }

  /**
   * Validate and post a user input Tweet.
   *
   * @param tweet tweet to be created
   * @return created tweet
   * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long
   *                                  out of range
   */
  @Override
  public Tweet postTweet(Tweet tweet) {

    validateTweetLength(tweet.getText());
    if (tweet.getCoordinates() != null) {
      validateCoordinates(tweet.getCoordinates().getCoordinates());
    }

    return (Tweet) dao.create(tweet);
  }

  /**
   * Search a tweet by id.
   *
   * @param id     tweet id
   * @param fields set fields not in the list to null
   * @return Tweet object which is returned by the Twitter API
   * @throws IllegalArgumentException if id or fields param is invalid
   */
  @Override
  public Tweet showTweet(String id, String[] fields) {

    validateId(id);
    Tweet tweet = (Tweet) dao.findById(id);
    setFields(tweet, fields);

    return tweet;
  }

  /**
   * Delete Tweet(s) by id(s).
   *
   * @param ids tweet IDs which will be deleted
   * @return A list of Tweets
   * @throws IllegalArgumentException if one of the IDs is invalid.
   */
  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    // Validate ids.
    Arrays.stream(ids).forEach(this::validateId);

    // Return list of tweets.
    return Arrays.stream(ids).map(id -> (Tweet) dao.deleteById(id)).collect(Collectors.toList());
  }

  /**
   * Helper function to validate id of a tweet.
   *
   * @param id id String to validate
   */
  void validateId(String id) {
    try {
      Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid id format", e);
    }
  }

  /**
   * Helper function to validate tweet length.
   *
   * @param text tweet text to validate
   */
  void validateTweetLength(String text) {
    if (text.length() == 0 || text.length() > 140) {
      throw new IllegalArgumentException("Invalid tweet length");
    }
  }

  /**
   * Helper function to validate tweet coordinates.
   *
   * @param coordinates tweet coordinates to validate
   */
  void validateCoordinates(List<Double> coordinates) {
    if (coordinates != null) {
      // Check longitude is within [-180.0, 180] and latitude is within [-90.0, 90.0].
      if (coordinates.get(0) < -180.0 || coordinates.get(0) > 180.0
          || coordinates.get(1) < -90.0 || coordinates.get(1) > 90.0) {
        throw new IllegalArgumentException("Invalid coordinates");
      }
    }
  }

  /**
   * Helper function to validate if fields are tweet fields.
   *
   * @param tweet a Tweet
   * @param fields fields to validate
   */
  void validateFields(Tweet tweet, String[] fields) {
    // Get list of all Tweet fields.
    List<String> tweetFields = Arrays.stream(tweet.getClass().getDeclaredFields())
        .map(Field::getName)
        .collect(Collectors.toList());
    if (Arrays.stream(fields).anyMatch(field -> !tweetFields.contains(field))) {
      throw new IllegalArgumentException("Invalid field");
    }
  }

  /**
   * Helper function to set fields not listed as null.
   *
   * @param tweet a Tweet
   * @param fields fields to keep
   */
  void setFields(Tweet tweet, String[] fields) {
    if (fields.length > 0) {
      validateFields(tweet, fields);
      List<String> fieldsList = Arrays.asList(fields);
      Arrays.stream(tweet.getClass().getDeclaredFields())
          .filter(field -> !fieldsList.contains(field.getName())) // Get fields not listed.
          .forEach(field -> {
            switch (field.getName()) {
              case "created_at":
                tweet.setCreated_at(null);
                break;
              case "id":
                tweet.setId(null);
                break;
              case "id_str":
                tweet.setId_str(null);
                break;
              case "text":
                tweet.setText(null);
                break;
              case "entities":
                tweet.setEntities(null);
                break;
              case "coordinates":
                tweet.setCoordinates(null);
                break;
              case "retweet_count":
                tweet.setRetweet_count(null);
                break;
              case "favorite_count":
                tweet.setFavorite_count(null);
                break;
              case "favorited":
                tweet.setFavorited(null);
                break;
              case "retweeted":
                tweet.setRetweeted(null);
                break;
              default:
                break;
            }
          });
    }
  }
}
