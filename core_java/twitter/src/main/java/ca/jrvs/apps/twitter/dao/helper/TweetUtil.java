package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;

public class TweetUtil {

  /**
   * Build Tweet object from provided inputs.
   *
   * @param text status of Tweet
   * @param lat latitude
   * @param lon longitude
   * @return Tweet object
   */
  public static Tweet buildTweet(String text, Double lon, Double lat) {
    Coordinates coordinates = new Coordinates();
    List<Double> coords = new ArrayList<>();
    coords.add(lon);
    coords.add(lat);
    coordinates.setCoordinates(coords);
    coordinates.setType("Point");

    Tweet tweet = new Tweet();
    tweet.setText(text);
    tweet.setCoordinates(coordinates);

    return tweet;
  }

}
