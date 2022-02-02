package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.helper.TweetUtil;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.List;

public class TwitterController implements Controller {

  public static final String COORD_SEP = ":";
  public static final String COMMA = ",";

  private Service service;

  public TwitterController(Service service) {
    this.service = service;
  }

  /**
   * Parse user arguments and post a tweet by calling TwitterService.
   *
   * @param args user arguments
   * @return a posted tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet postTweet(String[] args) {
    // Check number of arguments.
    if (args.length != 3) {
      throw new IllegalArgumentException(
          "USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    String text = args[1];
    String coords = args[2];
    String[] coordsArray = coords.split(COORD_SEP);
    // Check text and coordinates format.
    if (coordsArray.length != 2) {
      throw new IllegalArgumentException(
          "Invalid location format\n"
              + " USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    try {
      Double lat = Double.parseDouble(coordsArray[0]);
      Double lon = Double.parseDouble(coordsArray[1]);
      return service.postTweet(TweetUtil.buildTweet(text, lon, lat));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid location format", e);
    }
  }

  /**
   * Parse user argument and search a tweet by calling service classes
   *
   * @param args
   * @return a tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet showTweet(String[] args) {
    return null;
  }

  /**
   * Parse user argument and delete tweets by calling service classes
   *
   * @param args
   * @return a list of deleted tweets
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public List<Tweet> deleteTweet(String[] args) {
    return null;
  }
}
