package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Rotate-String-456b50f544574b84b5fac8775627ecbe
 */
public class RotateString {

  /**
   * Big-O: O(n^2)
   * Justification: contains method takes O(nm) in the worst case, and m == n.
   */
  public boolean rotateString(String s, String goal) {
    return s.length() == goal.length() && (s + s).contains(goal);
  }
}
