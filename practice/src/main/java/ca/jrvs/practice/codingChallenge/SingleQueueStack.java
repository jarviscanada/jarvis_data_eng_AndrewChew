package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Ticket: https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-ec8f3dddedb84e47a03e2accc14e4ee4
 */
public class SingleQueueStack {

  private final Queue<Integer> queue;

  /**
   * Big-O: O(1)
   * Justification: Construction does not depend on input.
   */
  public SingleQueueStack() {
    queue = new LinkedList<>();
  }

  /**
   * Big-O: O(n)
   * Justification: for loop iterates n times with each iteration taking O(1) time.
   */
  public void push(int x) {
    queue.add(x);
    for (int i = 0; i < queue.size() - 1; i++) {
      queue.add(queue.remove());
    }
  }

  /**
   * Big-O: O(1)
   * Justification: remove method takes constant time.
   */
  public int pop() {
    return queue.remove();
  }

  /**
   * Big-O: O(1)
   * Justification: peek method takes constant time.
   */
  public int top() {
    return queue.peek();
  }

  /**
   * Big-O: O(1)
   * Justification: isEmpty method takes constant time.
   */
  public boolean empty() {
    return queue.isEmpty();
  }
}
