package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FastTwoStackQueueTest {

  private FastTwoStackQueue fastTwoStackQueue;

  @Before
  public void setUp() {
    fastTwoStackQueue = new FastTwoStackQueue();
  }

  @Test
  public void push() {
    assertTrue(fastTwoStackQueue.empty());
    fastTwoStackQueue.push(1);
    assertFalse(fastTwoStackQueue.empty());
    assertEquals(1, fastTwoStackQueue.peek());
    fastTwoStackQueue.push(2);
    assertEquals(1, fastTwoStackQueue.peek());
  }

  @Test
  public void pop() {
    fastTwoStackQueue.push(1);
    fastTwoStackQueue.push(2);
    fastTwoStackQueue.push(3);
    assertEquals(1, fastTwoStackQueue.pop());
    fastTwoStackQueue.push(4);
    assertEquals(2, fastTwoStackQueue.pop());
  }

  @Test
  public void peek() {
    fastTwoStackQueue.push(1);
    fastTwoStackQueue.push(2);
    assertEquals(1, fastTwoStackQueue.peek());
    fastTwoStackQueue.push(3);
    assertEquals(1, fastTwoStackQueue.peek());
  }

  @Test
  public void empty() {
    assertTrue(fastTwoStackQueue.empty());
    fastTwoStackQueue.push(1);
    assertFalse(fastTwoStackQueue.empty());
  }
}