package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OddEvenTest {

  private OddEven oddEven;

  @Before
  public void setUp() {
    oddEven = new OddEven();
  }

  @Test
  public void oddEvenMod() {
    Assert.assertEquals("even", oddEven.oddEvenMod(0));
    Assert.assertEquals("even", oddEven.oddEvenMod(2004006));
    Assert.assertEquals("even", oddEven.oddEvenMod(-2));
    Assert.assertEquals("odd", oddEven.oddEvenMod(1003005));
    Assert.assertEquals("odd", oddEven.oddEvenMod(-1));
  }

  @Test
  public void oddEvenBit() {
    Assert.assertEquals("even", oddEven.oddEvenBit(0));
    Assert.assertEquals("even", oddEven.oddEvenBit(2004006));
    Assert.assertEquals("even", oddEven.oddEvenBit(-2));
    Assert.assertEquals("odd", oddEven.oddEvenBit(1003005));
    Assert.assertEquals("odd", oddEven.oddEvenBit(-1));
  }
}