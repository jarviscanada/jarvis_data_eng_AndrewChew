package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-c0b1d640d2134f389fd0e09b26056155
 */
public class Fibonacci {

  /**
   * Big-O:
   * Justification:
   */
  public int recursiveFib(int i) {
    if (i == 0) {
      return 0;
    } else if (i == 1) {
      return 1;
    } else {
      return recursiveFib(i - 1) + recursiveFib(i - 2);
    }
  }

}
