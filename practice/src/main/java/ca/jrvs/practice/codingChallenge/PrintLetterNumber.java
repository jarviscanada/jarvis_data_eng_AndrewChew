package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Print-letter-with-number-400e3f110d3a4e2ca6344b1b39a1b176
 */
public class PrintLetterNumber {

  /**
   * Big-O: O(n)
   * Justification: For loop iterates n times with each iteration costing O(1) time.
   */
  public String print(String letters) {
    StringBuilder result = new StringBuilder();
    for (char c : letters.toCharArray()) {
      result.append(c);
      int number = c >= 97 ? c - 96 : c - 38;
      result.append(number);
    }
    return result.toString();
  }
}
