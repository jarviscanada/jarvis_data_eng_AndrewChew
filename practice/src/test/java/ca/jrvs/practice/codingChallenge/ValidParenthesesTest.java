package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidParenthesesTest {

  private ValidParentheses validParentheses;

  @Before
  public void setUp() throws Exception {
    validParentheses = new ValidParentheses();
  }

  @Test
  public void isValid() {
    assertTrue(validParentheses.isValid(""));
    assertTrue(validParentheses.isValid("()"));
    assertTrue(validParentheses.isValid("()[]{}"));
    assertTrue(validParentheses.isValid("([{}])"));

    assertFalse(validParentheses.isValid("("));
    assertFalse(validParentheses.isValid("]"));
    assertFalse(validParentheses.isValid("(])"));
    assertFalse(validParentheses.isValid("([})"));
  }
}