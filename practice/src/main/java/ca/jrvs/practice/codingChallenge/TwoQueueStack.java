package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Ticket: https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-ec8f3dddedb84e47a03e2accc14e4ee4
 */
public class TwoQueueStack {

  private Queue<Integer> q1;
  private Queue<Integer> q2;
  private int top;

  /**
   * Big-O: O(1)
   * Justification: Construction does not depend on input.
   */
  public TwoQueueStack() {
    q1 = new LinkedList<>();
    q2 = new LinkedList<>();
  }

  /**
   * Big-O: O(1)
   * Justification: add method takes constant time.
   */
  public void push(int x) {
    q1.add(x);
    top = x;
  }

  /**
   * Big-O: O(n)
   * Justification: Have to remove n-1 elements.
   */
  public int pop() {
    while (q1.size() > 1) {
      top = q1.remove();
      q2.add(top);
    }
    int toRemove = q1.remove();
    Queue<Integer> temp = q1;
    q1 = q2;
    q2 = temp;
    return toRemove;
  }

  /**
   * Big-O: O(1)
   * Justification: Returns a field.
   */
  public int top() {
    return top;
  }

  /**
   * Big-O: O(1)
   * Justification: isEmpty method takes constant time.
   */
  public boolean empty() {
    return q1.isEmpty();
  }
}
