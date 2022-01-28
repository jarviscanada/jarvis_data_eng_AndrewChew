package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonUtil {

  /**
   * Parse JSON string to a Tweet object.
   *
   * @param json json string to parse
   * @return Tweet
   * @throws IOException exception from failed I/O operation
   */
  public static Tweet toObjectFromJson(String json) throws IOException {
    ObjectMapper m = new ObjectMapper();
    return m.readValue(json, Tweet.class);
  }
}
