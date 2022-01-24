package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringToIntTest {

  private StringToInt stringToInt;

  @Before
  public void setUp() {
    stringToInt = new StringToInt();
  }

  @Test
  public void javaStringToInt() {
    String test1 = "0";
    String test2 = "+42";
    String test3 = "0003";
    String test4 = "3000";
    String test5 = "-123";
    Assert.assertEquals(0, stringToInt.javaStringToInt(test1));
    Assert.assertEquals(42, stringToInt.javaStringToInt(test2));
    Assert.assertEquals(3, stringToInt.javaStringToInt(test3));
    Assert.assertEquals(3000, stringToInt.javaStringToInt(test4));
    Assert.assertEquals(-123, stringToInt.javaStringToInt(test5));
  }

  @Test
  public void myStringToInt() {
    String test1 = "";
    String test2 = "+42";
    String test3 = "0003";
    String test4 = "-3124ab";
    String test5 = String.valueOf(Long.MAX_VALUE);
    String test6 = String.valueOf(Long.MIN_VALUE);
    String test7 = "-91283472332";
    String test8 = "2147483648";
    Assert.assertEquals(0, stringToInt.myStringToInt(test1));
    Assert.assertEquals(42, stringToInt.myStringToInt(test2));
    Assert.assertEquals(3, stringToInt.myStringToInt(test3));
    Assert.assertEquals(-3124, stringToInt.myStringToInt(test4));
    Assert.assertEquals(Integer.MAX_VALUE, stringToInt.myStringToInt(test5));
    Assert.assertEquals(Integer.MIN_VALUE, stringToInt.myStringToInt(test6));
    Assert.assertEquals(Integer.MIN_VALUE, stringToInt.myStringToInt(test7));
    Assert.assertEquals(Integer.MAX_VALUE, stringToInt.myStringToInt(test8));
  }
}