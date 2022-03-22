package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonUtil {

  /**
   * Convert Tweet object to json string.
   *
   * @param tweet Tweet object to convert
   * @return a json string
   * @throws JsonProcessingException exception from failed json processing operations
   */
  public static String toPrettyJson(Tweet tweet) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    return objectMapper.writeValueAsString(tweet);
  }

  /**
   * Parse json string to a Tweet object.
   *
   * @param json json string to parse
   * @return Tweet
   * @throws IOException exception from failed I/O operation
   */
  public static Tweet toObjectFromJson(String json) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, Tweet.class);
  }
}
