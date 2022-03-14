package ca.jrvs.apps.trading.model.domain;

import java.util.List;

public class PortfolioView {

  private List<SecurityRow> securityRows;

  public List<SecurityRow> getSecurityRows() {
    return securityRows;
  }

  public void setSecurityRows(List<SecurityRow> securityRows) {
    this.securityRows = securityRows;
  }
}
