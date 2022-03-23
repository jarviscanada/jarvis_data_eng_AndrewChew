package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Check-if-a-String-contains-only-digits-0932a8d2fec547fb8bc7f5a52aacc815
 */
public class StringDigitsOnly {

  /**
   * Big-O: O(n)
   * Justification: For loop iterates over each character in the input String.
   */
  public boolean digitsOnlyAscii(String str) {

    if (str.isEmpty()) {
      return false;
    }

    for (int i : str.toCharArray()) {
      if (i < 48 || i > 57) {
        return false;
      }
    }

    return true;
  }

  /**
   * Big-O: O(n)
   * Justification: For loop iterates over each character in the input String.
   */
  public boolean digitsOnlyJavaApi(String str) {

    if (str.isEmpty()) {
      return false;
    }

    for (char i : str.toCharArray()) {
      int charVal = Integer.valueOf(i);
      if (charVal < 48 || charVal > 57) {
        return false;
      }
    }

    return true;
  }

  /**
   * Big-O: O(n)
   * Justification: Time complexity for matches with this regex should be O(n).
   */
  public boolean digitsOnlyRegex(String str) {
    return str.matches("\\d+");
  }

}
