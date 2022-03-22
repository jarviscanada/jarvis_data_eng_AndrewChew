package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Ticket: https://www.notion.so/jarvisdev/Valid-Parentheses-446ab97dfe644e8bbded8cc4200e1a83
 */
public class ValidParentheses {
  
  private Map<Character, Character> parentheses;
  private Stack<Character> stack;
  
  /**
   * Big-O: O(1)
   * Justification: Construction does not depend on input.
   */
  public ValidParentheses() {
    parentheses = new HashMap<>();
    parentheses.put('(', ')');
    parentheses.put('{', '}');
    parentheses.put('[', ']');
  }

  /**
   * Big-O: O(n)
   * Justification: for loop iterates n times with each iteration taking O(1) time.
   */
  public boolean isValid(String s) {
    stack = new Stack<>();
    for (int i = 0; i < s.length(); i++) {
      Character c = s.charAt(i);
      if (parentheses.containsKey(c)) {
        stack.push(parentheses.get(c));
      } else if (stack.empty() || stack.pop() != c) {
        return false;
      }
    }
    return stack.empty();
  }
}
