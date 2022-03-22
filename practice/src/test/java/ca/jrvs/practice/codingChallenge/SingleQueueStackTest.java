package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SingleQueueStackTest {

  private SingleQueueStack singleQueueStack;

  @Before
  public void setUp() {
    singleQueueStack = new SingleQueueStack();
  }

  @Test
  public void push() {
    assertTrue(singleQueueStack.empty());
    singleQueueStack.push(1);
    assertFalse(singleQueueStack.empty());
    Assert.assertEquals(1, singleQueueStack.top());
  }

  @Test
  public void pop() {
    singleQueueStack.push(1);
    singleQueueStack.push(2);
    singleQueueStack.push(3);
    Assert.assertEquals(3, singleQueueStack.pop());
    Assert.assertEquals(2, singleQueueStack.top());
  }

  @Test(expected = NoSuchElementException.class)
  public void popEmpty() {
    singleQueueStack.pop();
  }

  @Test
  public void top() {
    singleQueueStack.push(1);
    singleQueueStack.push(2);
    Assert.assertEquals(2, singleQueueStack.top());
  }

  @Test
  public void empty() {
    assertTrue(singleQueueStack.empty());
    singleQueueStack.push(0);
    assertFalse(singleQueueStack.empty());
  }
}