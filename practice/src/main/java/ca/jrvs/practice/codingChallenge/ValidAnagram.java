package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

/**
 * Ticket: https://www.notion.so/jarvisdev/Valid-Anagram-d124f84ec33f49128e277ef897d130c6
 */
public class ValidAnagram {

  /**
   * Big-O: O(nlgn)
   * Justification: sort method takes O(nlgn).
   */
  public boolean isValidSort(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }

    char[] charArrayS = s.toCharArray();
    char[] charArrayT = t.toCharArray();
    Arrays.sort(charArrayS);
    Arrays.sort(charArrayT);

    return Arrays.equals(charArrayS, charArrayT);
  }

  /**
   * Big-O: O(n)
   * Justification: Array access takes O(1) time and for loop iterates n times.
   */
  public boolean isValidArray(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }
    int[] alphabet = new int[26];

    for (int i = 0; i < s.length(); i++) {
      alphabet[s.charAt(i) - 'a']++;
      alphabet[t.charAt(i) - 'a']--;
    }

    for (int i : alphabet) {
      if (i != 0) {
        return false;
      }
    }

    return true;
  }
}
