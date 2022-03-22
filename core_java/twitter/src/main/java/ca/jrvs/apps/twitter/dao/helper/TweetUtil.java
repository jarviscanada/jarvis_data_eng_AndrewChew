package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Entities;
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
    Entities entities = new Entities();
    entities.setHashtags(new ArrayList<>());
    entities.setUserMentions(new ArrayList<>());

    Coordinates coordinates = new Coordinates();
    List<Double> coords = new ArrayList<>();
    coords.add(lon);
    coords.add(lat);
    coordinates.setCoordinates(coords);
    coordinates.setType("Point");

    Tweet tweet = new Tweet();
    tweet.setText(text);
    tweet.setEntities(entities);
    tweet.setCoordinates(coordinates);
    tweet.setRetweet_count(0);
    tweet.setFavorite_count(0);
    tweet.setFavorited(false);
    tweet.setRetweeted(false);

    return tweet;
  }

}
