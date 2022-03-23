package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StringDigitsOnlyTest {

  String empty = "";
  String digitsOnly = "15987108435696120";
  String decimal = "1.2";
  String comma = "1,000,000";
  String negative = "-125";
  String alphanumeric = "123897d";
  String space = "123 123";

  StringDigitsOnly stringDigitsOnly;

  @Before
  public void setUp() throws Exception {
    stringDigitsOnly = new StringDigitsOnly();
  }

  @Test
  public void digitsOnlyAscii() {
    assertFalse(stringDigitsOnly.digitsOnlyAscii(empty));
    assertTrue(stringDigitsOnly.digitsOnlyAscii(digitsOnly));
    assertFalse(stringDigitsOnly.digitsOnlyAscii(decimal));
    assertFalse(stringDigitsOnly.digitsOnlyAscii(comma));
    assertFalse(stringDigitsOnly.digitsOnlyAscii(negative));
    assertFalse(stringDigitsOnly.digitsOnlyAscii(alphanumeric));
    assertFalse(stringDigitsOnly.digitsOnlyAscii(space));
  }

  @Test
  public void digitsOnlyJavaApi() {
    assertFalse(stringDigitsOnly.digitsOnlyJavaApi(empty));
    assertTrue(stringDigitsOnly.digitsOnlyJavaApi(digitsOnly));
    assertFalse(stringDigitsOnly.digitsOnlyJavaApi(decimal));
    assertFalse(stringDigitsOnly.digitsOnlyJavaApi(comma));
    assertFalse(stringDigitsOnly.digitsOnlyJavaApi(negative));
    assertFalse(stringDigitsOnly.digitsOnlyJavaApi(alphanumeric));
    assertFalse(stringDigitsOnly.digitsOnlyJavaApi(space));
  }

  @Test
  public void digitsOnlyRegex() {
    assertFalse(stringDigitsOnly.digitsOnlyRegex(empty));
    assertTrue(stringDigitsOnly.digitsOnlyRegex(digitsOnly));
    assertFalse(stringDigitsOnly.digitsOnlyRegex(decimal));
    assertFalse(stringDigitsOnly.digitsOnlyRegex(comma));
    assertFalse(stringDigitsOnly.digitsOnlyRegex(negative));
    assertFalse(stringDigitsOnly.digitsOnlyRegex(alphanumeric));
    assertFalse(stringDigitsOnly.digitsOnlyRegex(space));
  }
}