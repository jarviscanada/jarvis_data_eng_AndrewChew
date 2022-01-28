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
  public static Tweet buildTweet(String text, Double lat, Double lon){
    Tweet tweet = new Tweet();
    Coordinates coordinates = new Coordinates();
    List<Double> coords = new ArrayList<>();
    coords.add(lat);
    coords.add(lon);
    coordinates.setCoordinates(coords);
    coordinates.setType("Point");
    tweet.setText(text);
    tweet.setCoordinates(coordinates);

    return tweet;
  }

}
