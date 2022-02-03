package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidAnagramTest {

  ValidAnagram validAnagram;

  @Before
  public void setUp() throws Exception {
    validAnagram = new ValidAnagram();
  }

  @Test
  public void isValidSort() {
    assertTrue(validAnagram.isValidSort("", ""));
    assertTrue(validAnagram.isValidSort("anagram", "nagaram"));
    assertFalse(validAnagram.isValidSort("", "car"));
    assertFalse(validAnagram.isValidSort("rat", ""));
    assertFalse(validAnagram.isValidSort("rat", "car"));
  }

  @Test
  public void isValidArray() {
    assertTrue(validAnagram.isValidArray("", ""));
    assertTrue(validAnagram.isValidArray("anagram", "nagaram"));
    assertFalse(validAnagram.isValidArray("", "car"));
    assertFalse(validAnagram.isValidArray("rat", ""));
    assertFalse(validAnagram.isValidArray("rat", "car"));
  }
}