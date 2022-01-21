package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-c0b1d640d2134f389fd0e09b26056155
 */
public class Fibonacci {

  /**
   * Big-O: O(2^n)
   * Justification: Each function call recursively calls the function 2 more times.
   */
  public int recursiveFib(int i) {
    if (i == 0) {
      return 0;
    } else if (i == 1) {
      return 1;
    } else {
      return recursiveFib(i - 1) + recursiveFib(i - 2);
    }
  }

  /**
   * Big-O: O(n)
   * Justification: The for loop runs n-1 times with each loop costing O(1).
   */
  public int dynamicFib(int i) {
    if (i == 0) {
      return 0;
    } else if (i == 1) {
      return 1;
    }
    int[] fibArr = new int[i + 1];
    fibArr[0] = 0;
    fibArr[1] = 1;

    for (int j = 2; j <= i; j++) {
      fibArr[j] = fibArr[j - 1] + fibArr[j - 2];
    }
    return fibArr[i];
  }
}
