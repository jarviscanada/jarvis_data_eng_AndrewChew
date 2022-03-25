package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PrintLetterNumberTest {

  private PrintLetterNumber printLetterNumber;

  private String test1 = "";
  private String expected1 = "";
  private String test2 = "a";
  private String expected2 = "a1";
  private String test3 = "B";
  private String expected3 = "B28";
  private String test4 = "abcABC";
  private String expected4 = "a1b2c3A27B28C29";
  private String test5 = "tesTiNg";
  private String expected5 = "t20e5s19T46i9N40g7";

  @Before
  public void setUp() throws Exception {
    printLetterNumber = new PrintLetterNumber();
  }

  @Test
  public void print() {
    assertEquals(expected1, printLetterNumber.print(test1));
    assertEquals(expected2, printLetterNumber.print(test2));
    assertEquals(expected3, printLetterNumber.print(test3));
    assertEquals(expected4, printLetterNumber.print(test4));
    assertEquals(expected5, printLetterNumber.print(test5));
  }
}