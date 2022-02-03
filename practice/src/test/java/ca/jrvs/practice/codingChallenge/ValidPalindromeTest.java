package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidPalindromeTest {

  ValidPalindrome validPalindrome;

  @Before
  public void setUp() throws Exception {
    validPalindrome = new ValidPalindrome();
  }

  @Test
  public void isPalindrome() {
    assertTrue(validPalindrome.isPalindrome(""));
    assertTrue(validPalindrome.isPalindrome("mAdaM"));
    assertTrue(validPalindrome.isPalindrome("         1racecar1"));
    assertTrue(validPalindrome.isPalindrome("A man, a plan, a canal: Panama"));
    assertTrue(validPalindrome.isPalindrome(",."));
    assertFalse(validPalindrome.isPalindrome("12   3"));
    assertFalse(validPalindrome.isPalindrome("raceAcar"));
  }

  @Test
  public void isPalindromeRecursive() {
    assertTrue(validPalindrome.isPalindromeRecursive(""));
    assertTrue(validPalindrome.isPalindromeRecursive("mAdaM"));
    assertTrue(validPalindrome.isPalindromeRecursive("         1racecar1"));
    assertTrue(validPalindrome.isPalindromeRecursive("A man, a plan, a canal: Panama"));
    assertTrue(validPalindrome.isPalindromeRecursive(",."));
    assertFalse(validPalindrome.isPalindromeRecursive("12   3"));
    assertFalse(validPalindrome.isPalindromeRecursive("raceAcar"));
  }
}