package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FibonacciTest {

  private Fibonacci fibonacci;

  @Before
  public void setUp() {
    fibonacci = new Fibonacci();
  }

  @Test
  public void recursiveFib() {
    assertEquals(0, fibonacci.recursiveFib(0));
    assertEquals(1, fibonacci.recursiveFib(1));
    assertEquals(1, fibonacci.recursiveFib(2));
    assertEquals(2, fibonacci.recursiveFib(3));
    assertEquals(55, fibonacci.recursiveFib(10));
  }
}