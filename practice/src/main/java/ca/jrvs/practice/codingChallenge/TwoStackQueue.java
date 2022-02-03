package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * Ticket: https://www.notion.so/jarvisdev/Implement-Queue-using-Stacks-5dbad284fddf4afbbf5a8219ccf7b348
 */
public class TwoStackQueue {

  private Stack<Integer> stack1;
  private Stack<Integer> stack2;

  /**
   * Big-O: O(1)
   * Justification: Construction does not depend on input.
   */
  public TwoStackQueue() {
    stack1 = new Stack<>();
    stack2 = new Stack<>();
  }

  /**
   * Big-O: O(n)
   * Justification: First while loop iterates n-1 times with each iteration taking O(1) time.
   *                Second while loop iterates n-1 times with each iteration taking O(1) time.
   */
  public void push(int x) {
    while (!stack1.empty()) {
      stack2.push(stack1.pop());
    }
    stack1.push(x);
    while (!stack2.empty()) {
      stack1.push(stack2.pop());
    }
  }

  /**
   * Big-O: O(1)
   * Justification: pop method takes constant time.
   */
  public int pop() {
    return stack1.pop();
  }

  /**
   * Big-O: O(1)
   * Justification: peek method takes constant time.
   */
  public int peek() {
    return stack1.peek();
  }

  /**
   * Big-O:
   * Justification: empty method takes constant time.
   */
  public boolean empty() {
    return stack1.empty();
  }

}
