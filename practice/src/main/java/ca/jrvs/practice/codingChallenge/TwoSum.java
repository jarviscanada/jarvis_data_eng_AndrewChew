package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * Ticket: https://www.notion.so/jarvisdev/Two-Sum-23258f60af684131adc9afa3c07c4669
 */
public class TwoSum {

  /**
   * Big-O: O(n^2)
   * Justification: Both the inner and outer loop iterates n times for a total of n^2 times.
   */
  public int[] bruteTwoSum(int[] nums, int target) {
    int[] answer = new int[2];
    int n = nums.length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i != j && nums[i] + nums[j] == target) {
          answer[0] = i;
          answer[1] = j;
        }
      }
    }

    return answer;
  }

  /**
   * Big-O: O(n)
   * Justification: Map operations are O(1) and the for loop iterates only n times.
   */
  public int[] mapTwoSum(int[] nums, int target) {
    int[] answer = new int[2];
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
      if (map.containsKey(target - nums[i])) {
        answer[0] = i;
        answer[1] = map.get(target - nums[i]);
      }
      map.put(nums[i], i);
    }

    return answer;
  }

}
