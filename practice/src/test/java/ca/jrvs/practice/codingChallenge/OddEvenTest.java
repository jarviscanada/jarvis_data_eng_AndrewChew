package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

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
    Assert.assertEquals("even", oddEven.oddEvenMod(2));
    Assert.assertEquals("even", oddEven.oddEvenMod(-2));
    Assert.assertEquals("odd", oddEven.oddEvenMod(1));
    Assert.assertEquals("odd", oddEven.oddEvenMod(-1));
  }
}