package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwoQueueStackTest {

  private TwoQueueStack twoQueueStack;

  @Before
  public void setUp() {
    twoQueueStack = new TwoQueueStack();
  }

  @Test
  public void push() {
    assertTrue(twoQueueStack.empty());
    twoQueueStack.push(1);
    assertFalse(twoQueueStack.empty());
    Assert.assertEquals(1, twoQueueStack.top());
  }

  @Test
  public void pop() {
    twoQueueStack.push(1);
    twoQueueStack.push(2);
    twoQueueStack.push(3);
    Assert.assertEquals(3, twoQueueStack.pop());
    Assert.assertEquals(2, twoQueueStack.top());
  }

  @Test
  public void top() {
    twoQueueStack.push(1);
    twoQueueStack.push(2);
    Assert.assertEquals(2, twoQueueStack.top());
  }

  @Test
  public void empty() {
    assertTrue(twoQueueStack.empty());
    twoQueueStack.push(0);
    assertFalse(twoQueueStack.empty());
  }

}