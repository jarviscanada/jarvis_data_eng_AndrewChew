package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.Collections;
import java.util.OptionalInt;

/**
 * Ticket: https://www.notion.so/jarvisdev/Find-Largest-Smallest-028d99a702e74e9597e513c94de6ff63
 */
public class LargestSmallest {

  /**
   * Big-O: O(n)
   * Justification: For loop iterates n times with each iteration costing O(1) time.
   */
  public int getLargestSmallestFor (int[] arr, boolean findLargest) {
    if (arr.length == 0) {
      throw new RuntimeException("Array is empty");
    }
    int largestSmallest = arr[0];
    for (int i = 1; i < arr.length; i++) {
      if ((findLargest && arr[i] > largestSmallest) || (!findLargest && arr[i] < largestSmallest)) {
        largestSmallest = arr[i];
      }
    }
    return largestSmallest;
  }

  /**
   * Big-O: Runtime of max/ min method
   * Justification: N/A.
   */
  public int getLargestSmallestStream (int[] arr, boolean findLargest) {
    OptionalInt largestSmallest = findLargest ? Arrays.stream(arr).max() : Arrays.stream(arr).min();
    return largestSmallest.orElseThrow(() -> new RuntimeException("Array is empty"));
  }

  /**
   * Big-O: Runtime of max/ min method
   * Justification: N/A.
   */
  public int getLargestSmallestApi (Integer[] arr, boolean findLargest) {
    return findLargest ? Collections.max(Arrays.asList(arr)) : Collections.min(Arrays.asList(arr));
  }

}
