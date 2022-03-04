package ca.jrvs.apps.trading.model.config;

/**
 * Configuration model used to setup IEX HTTP Client.
 */
public class MarketDataConfig {

  private String host;
  private String token;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
