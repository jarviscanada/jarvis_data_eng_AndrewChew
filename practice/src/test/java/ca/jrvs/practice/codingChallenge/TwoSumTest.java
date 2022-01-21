package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TwoSumTest {

  private TwoSum twoSum;

  @Before
  public void setUp() {
    twoSum = new TwoSum();
  }

  @Test
  public void bruteTwoSum() {
    int[] test = {2, 7, 11, 15};
    int target1 = 9;
    int target2 = 22;
    int target3 = 4;
    int[] expected1 = {0, 1};
    int[] expected2 = {1, 3};
    int[] expected3 = new int[2];
    int[] actual1 = twoSum.bruteTwoSum(test, target1);
    Arrays.sort(actual1);
    int[] actual2 = twoSum.bruteTwoSum(test, target2);
    Arrays.sort(actual2);
    int[] actual3 = twoSum.bruteTwoSum(test, target3);
    Arrays.sort(actual3);
    Assert.assertArrayEquals(expected1, actual1);
    Assert.assertArrayEquals(expected2, actual2);
    Assert.assertArrayEquals(expected3, actual3);
  }

  @Test
  public void mapTwoSum() {
    int[] test = {2, 7, 11, 15};
    int target1 = 9;
    int target2 = 22;
    int target3 = 4;
    int[] expected1 = {0, 1};
    int[] expected2 = {1, 3};
    int[] expected3 = new int[2];
    int[] actual1 = twoSum.mapTwoSum(test, target1);
    Arrays.sort(actual1);
    int[] actual2 = twoSum.mapTwoSum(test, target2);
    Arrays.sort(actual2);
    int[] actual3 = twoSum.mapTwoSum(test, target3);
    Arrays.sort(actual3);
    Assert.assertArrayEquals(expected1, actual1);
    Assert.assertArrayEquals(expected2, actual2);
    Assert.assertArrayEquals(expected3, actual3);
  }
}