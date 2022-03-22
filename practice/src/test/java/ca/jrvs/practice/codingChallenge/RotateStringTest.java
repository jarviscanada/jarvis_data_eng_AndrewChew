package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RotateStringTest {

  private RotateString rotateString;

  @Before
  public void setUp() throws Exception {
    rotateString = new RotateString();
  }

  @Test
  public void rotateString() {
    assertTrue(rotateString.rotateString("abcde", "bcdea"));
    assertTrue(rotateString.rotateString("", ""));
    assertFalse(rotateString.rotateString("abcde", "abced"));
    assertFalse(rotateString.rotateString("abcde", ""));
    assertFalse(rotateString.rotateString("", "abcde"));
  }
}