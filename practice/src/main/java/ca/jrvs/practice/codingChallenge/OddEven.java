package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-8d967bfa003e44aa8a9da5e573116792
 */
public class OddEven {

  /**
   * Big-O: O(1)
   * Justification: Comparison takes O(1) time.
   */
  public String oddEvenMod(int i) {
    return i % 2 == 0 ? "even" : "odd";
  }
}
