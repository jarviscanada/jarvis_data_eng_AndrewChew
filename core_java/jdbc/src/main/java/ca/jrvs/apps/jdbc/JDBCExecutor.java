package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {

  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
        "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = customerDAO.findById(1000);
      System.out.println(customer.getFirstName() + " " + customer.getLastName());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
