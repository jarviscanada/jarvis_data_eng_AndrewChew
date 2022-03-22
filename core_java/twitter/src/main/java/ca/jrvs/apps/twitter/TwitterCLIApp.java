package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.JsonUtil;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterCLIApp {

  public static final String USAGE = "USAGE: TwitterCLIApp post|show|delete [options]";

  private TwitterController twitterController;

  @Autowired
  public TwitterCLIApp(TwitterController twitterController) {
    this.twitterController = twitterController;
  }

  public void run(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException(USAGE);
    }
    switch (args[0].toLowerCase()) {
      case "post":
        printTweet(twitterController.postTweet(args));
        break;
      case "show":
        printTweet(twitterController.showTweet(args));
        break;
      case "delete":
        twitterController.deleteTweet(args).forEach(this::printTweet);
        break;
      default:
        throw new IllegalArgumentException(USAGE);
    }
  }

  private void printTweet(Tweet tweet) {
    try {
      System.out.println(JsonUtil.toPrettyJson(tweet));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to print tweet", e);
    }

  }

  public static void main(String[] args) {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,
        accessToken, tokenSecret);
    TwitterDao twitterDao = new TwitterDao(httpHelper);
    TwitterService twitterService = new TwitterService(twitterDao);
    TwitterController twitterController = new TwitterController(twitterService);
    TwitterCLIApp twitterCLIApp = new TwitterCLIApp(twitterController);

    twitterCLIApp.run(args);
  }

}
