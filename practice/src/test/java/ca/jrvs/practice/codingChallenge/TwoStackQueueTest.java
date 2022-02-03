package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TwoStackQueueTest {

  private TwoStackQueue twoStackQueue;

  @Before
  public void setUp() {
    twoStackQueue = new TwoStackQueue();
  }

  @Test
  public void push() {
    assertTrue(twoStackQueue.empty());
    twoStackQueue.push(1);
    assertFalse(twoStackQueue.empty());
    assertEquals(1, twoStackQueue.peek());
    twoStackQueue.push(2);
    assertEquals(1, twoStackQueue.peek());
  }

  @Test
  public void pop() {
    twoStackQueue.push(1);
    twoStackQueue.push(2);
    twoStackQueue.push(3);
    assertEquals(1, twoStackQueue.pop());
  }

  @Test
  public void peek() {
    twoStackQueue.push(1);
    twoStackQueue.push(2);
    assertEquals(1, twoStackQueue.peek());
    twoStackQueue.push(3);
    assertEquals(1, twoStackQueue.peek());
  }

  @Test
  public void empty() {
    assertTrue(twoStackQueue.empty());
    twoStackQueue.push(1);
    assertFalse(twoStackQueue.empty());
  }
}