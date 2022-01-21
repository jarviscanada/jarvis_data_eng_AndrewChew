package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class MapComparerTest {

  private MapComparer mapComparer;

  @Before
  public void setUp() { mapComparer = new MapComparer(); }

  @Test
  public void compareMaps() {
    Map<String, Integer> m1 = new HashMap<>();
    Map<String, Integer> m2 = new HashMap<>();
    Map<String, Integer> m3 = new HashMap<>();
    m1.put("test1", 1);
    m1.put("test2", 2);
    m1.put("test3", 3);
    m2.put("test1", 1);
    m2.put("test2", 2);
    m2.put("test3", 3);
    m3.put("test1", 1);
    m3.put("test2", 2);
    m3.put("test3", 4);
    assertTrue(mapComparer.compareMaps(m1, m2));
    assertFalse(mapComparer.compareMaps(m1, m3));
  }

  @Test
  public void mapEquals() {
    Map<String, Integer> m1 = new HashMap<>();
    Map<String, Integer> m2 = new HashMap<>();
    Map<String, Integer> m3 = new HashMap<>();
    m1.put("test1", 1);
    m1.put("test2", 2);
    m1.put("test3", 3);
    m2.put("test1", 1);
    m2.put("test2", 2);
    m2.put("test3", 3);
    m3.put("test1", 1);
    m3.put("test2", 2);
    m3.put("test3", 4);
    assertTrue(mapComparer.mapEquals(m1, m2));
    assertFalse(mapComparer.mapEquals(m1, m3));
  }
}