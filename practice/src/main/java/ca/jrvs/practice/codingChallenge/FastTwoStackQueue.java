package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * Ticket: https://www.notion.so/jarvisdev/Implement-Queue-using-Stacks-5dbad284fddf4afbbf5a8219ccf7b348
 */
public class FastTwoStackQueue {

  private Stack<Integer> stack1;
  private Stack<Integer> stack2;
  private int first;

  /**
   * Big-O: O(1)
   * Justification: Construction does not depend on input.
   */
  public FastTwoStackQueue() {
    stack1 = new Stack<>();
    stack2 = new Stack<>();
  }

  /**
   * Big-O: O(1)
   * Justification: empty and push methods takes O(1) time.
   */
  public void push(int x) {
    if (stack1.empty()) {
      first = x;
    }
    stack1.push(x);
  }

  /**
   * Big-O: amortized O(1)
   * Justification: pop method takes constant time.
   *                while loop iterates n times with each iteration taking O(1) time.
   *                WC time is O(n) but happens only once per n operations, it is O(1) for the
   *                other n-1 operations.
   */
  public int pop() {
    if (stack2.empty()) {
      while (!stack1.empty()) {
        stack2.push(stack1.pop());
      }
    }
    return stack2.pop();
  }

  /**
   * Big-O: O(1)
   * Justification: peek method takes constant time.
   */
  public int peek() {
    if (stack2.empty()) {
      return first;
    }
    return stack2.peek();
  }

  /**
   * Big-O:
   * Justification: empty method takes constant time.
   */
  public boolean empty() {
    return stack1.empty() && stack2.empty();
  }

}
