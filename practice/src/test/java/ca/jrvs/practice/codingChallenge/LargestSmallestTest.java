package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

public class LargestSmallestTest {

  private LargestSmallest largestSmallest;

  private int[] arr1 = {1, 2, 3, 4, 5};
  private int[] arr2 = {-2, -1, 0, 1, 2};
  private int[] arr3 = {0};
  private int[] arr4 = {3, 200, -5, 10, -111};
  private int[] arr5 = {};

  @Before
  public void setUp() throws Exception {
    largestSmallest = new LargestSmallest();
  }

  @Test
  public void getLargestSmallestFor() {
    assertEquals(5, largestSmallest.getLargestSmallestFor(arr1, true));
    assertEquals(1, largestSmallest.getLargestSmallestFor(arr1, false));
    assertEquals(2, largestSmallest.getLargestSmallestFor(arr2, true));
    assertEquals(-2, largestSmallest.getLargestSmallestFor(arr2, false));
    assertEquals(0, largestSmallest.getLargestSmallestFor(arr3, true));
    assertEquals(0, largestSmallest.getLargestSmallestFor(arr3, false));
    assertEquals(200, largestSmallest.getLargestSmallestFor(arr4, true));
    assertEquals(-111, largestSmallest.getLargestSmallestFor(arr4, false));
  }

  @Test(expected = RuntimeException.class)
  public void getLargestForFail() {
    largestSmallest.getLargestSmallestFor(arr5, true);
  }

  @Test(expected = RuntimeException.class)
  public void getSmallestForFail() {
    largestSmallest.getLargestSmallestFor(arr5, false);
  }

  @Test
  public void getLargestSmallestStream() {
    assertEquals(5, largestSmallest.getLargestSmallestStream(arr1, true));
    assertEquals(1, largestSmallest.getLargestSmallestStream(arr1, false));
    assertEquals(2, largestSmallest.getLargestSmallestStream(arr2, true));
    assertEquals(-2, largestSmallest.getLargestSmallestStream(arr2, false));
    assertEquals(0, largestSmallest.getLargestSmallestStream(arr3, true));
    assertEquals(0, largestSmallest.getLargestSmallestStream(arr3, false));
    assertEquals(200, largestSmallest.getLargestSmallestStream(arr4, true));
    assertEquals(-111, largestSmallest.getLargestSmallestStream(arr4, false));
  }

  @Test(expected = RuntimeException.class)
  public void getLargestStreamFail() {
    largestSmallest.getLargestSmallestStream(arr5, true);
  }

  @Test(expected = RuntimeException.class)
  public void getSmallestStreamFail() {
    largestSmallest.getLargestSmallestStream(arr5, false);
  }

  @Test
  public void getLargestSmallestApi() {
    Integer[] newArr1 = Arrays.stream(arr1).boxed().toArray(Integer[]::new);
    Integer[] newArr2 = Arrays.stream(arr2).boxed().toArray(Integer[]::new);
    Integer[] newArr3 = Arrays.stream(arr3).boxed().toArray(Integer[]::new);
    Integer[] newArr4 = Arrays.stream(arr4).boxed().toArray(Integer[]::new);
    assertEquals(5, largestSmallest.getLargestSmallestApi(newArr1, true));
    assertEquals(1, largestSmallest.getLargestSmallestApi(newArr1, false));
    assertEquals(2, largestSmallest.getLargestSmallestApi(newArr2, true));
    assertEquals(-2, largestSmallest.getLargestSmallestApi(newArr2, false));
    assertEquals(0, largestSmallest.getLargestSmallestApi(newArr3, true));
    assertEquals(0, largestSmallest.getLargestSmallestApi(newArr3, false));
    assertEquals(200, largestSmallest.getLargestSmallestApi(newArr4, true));
    assertEquals(-111, largestSmallest.getLargestSmallestApi(newArr4, false));
  }

  @Test(expected = NoSuchElementException.class)
  public void getLargestApiFail() {
    Integer[] newArr5 = Arrays.stream(arr5).boxed().toArray(Integer[]::new);
    largestSmallest.getLargestSmallestApi(newArr5, true);
  }

  @Test(expected = NoSuchElementException.class)
  public void getSmallestApiFail() {
    Integer[] newArr5 = Arrays.stream(arr5).boxed().toArray(Integer[]::new);
    largestSmallest.getLargestSmallestApi(newArr5, false);
  }
}