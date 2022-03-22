package ca.jrvs.practice.codingChallenge;

import java.util.Map;

/**
 * Ticket: https://www.notion.so/jarvisdev/How-to-compare-two-maps-390f66a1c0f84801aaea626e8fe7176f
 */
public class MapComparer {

  /**
   * Big-O: O(n)
   * Justification: Equals method compares each entry in the maps.
   */
  public <K, V> boolean compareMaps(Map<K, V> m1, Map<K, V> m2) {
    return m1.equals(m2);
  }

  /**
   * Big-O: O(n)
   * Justification: Stream iterates through each entry in the maps.
   */
  public <K, V> boolean mapEquals(Map<K, V> m1, Map<K, V> m2) {
    // First check Maps have the same number of entries.
    if (m1.size() != m2.size()) {
      return false;
    }

    // Check that all entries match.
    return m1.entrySet().stream().allMatch(e -> e.getValue().equals(m2.get(e.getKey())));
  }
}
