package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/String-to-Integer-atoi-7b6612ec61b14beea6b57b31a527487b
 */
public class StringToInt {

  /**
   * Big-O: O(n)
   * Justification: Each character in the string is looked at.
   */
  public int javaStringToInt(String string) {
    return Integer.parseInt(string.trim());
  }

  /**
   * Big-O: O(n)
   * Justification: Each character in the string is looked at.
   */
  public int myStringToInt(String string) {
    String trimmed = string.trim(); // Trim whitespaces - O(n).
    int limit = -Integer.MAX_VALUE;
    boolean isNegative = false;
    int result = 0;
    int i = 0;

    // Check for positive or negative.
    if (trimmed.length() >= 1) {
      char first = trimmed.charAt(0);
      if (first == '+') {
        i++;
      } else if (first == '-') {
        limit = Integer.MIN_VALUE;
        isNegative = true;
        i++;
      }
    }

    while (i < trimmed.length()) {
      char c = trimmed.charAt(i);
      if (!Character.isDigit(c)) {
        break; // Break at first non-digit.
      }
      int digit = Character.digit(c, 10);
      // Detect overflow/ underflow and clamp result to int range (per question's specification).
      if (result < limit / 10) {
        result = limit;
        break;
      }
      result *= 10;
      // Detect overflow/ underflow and clamp result to int range (per question's specification).
      if (result < limit + digit) {
        result = limit;
        break;
      }
      result -= digit;
      i++;
    }

    return (isNegative) ? result : -result;
  }

}
