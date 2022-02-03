package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Valid-Palindrome-5757898db8614f448b0d573552efa593
 */
public class ValidPalindrome {

  /**
   * Big-O: O(n)
   * Justification: toLowerCase method takes O(n) time and while loop iterates n/2 times with
   *                each iteration taking O(1) time.
   */
  public boolean isPalindrome(String s) {
    int l = 0;
    int r = s.length() - 1;

    while (l < r) {
      if (!valid(s.charAt(l)) && valid(s.charAt(r))) {
        l++;
      } else if (valid(s.charAt(l)) && !valid(s.charAt(r))) {
        r--;
      } else if (valid(s.charAt(l)) && valid(s.charAt(r))
          && (lower(s.charAt(l)) != lower(s.charAt(r)))) {
        return false;
      } else {
        l++;
        r--;
      }
    }

    return true;
  }

  /**
   * Big-O: O(n)
   * Justification: n/2 recursive calls are made each costing O(1) time.
   */
  public boolean isPalindromeRecursive(String s) {
    return isPalindrome(s, 0, s.length() - 1);
  }

  private boolean isPalindrome(String s, int l, int r) {
    if (l >= r) {
      return true;
    } else if (!valid(s.charAt(l)) && valid(s.charAt(r))) {
      return isPalindrome(s, l + 1, r);
    } else if (valid(s.charAt(l)) && !valid(s.charAt(r))) {
      return isPalindrome(s, l, r - 1);
    } else if (valid(s.charAt(l)) && valid(s.charAt(r))
        && (lower(s.charAt(l)) != lower(s.charAt(r)))) {
      return false;
    } else {
      return isPalindrome(s, l + 1, r - 1);
    }
  }

  private boolean valid(char c) {
    return Character.isLetterOrDigit(c);
  }

  private char lower(char c) {
    return Character.toLowerCase(c);
  }
}
