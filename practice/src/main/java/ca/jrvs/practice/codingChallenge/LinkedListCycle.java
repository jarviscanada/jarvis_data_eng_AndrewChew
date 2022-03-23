package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/LinkedList-Cycle-7a12dc12896348e99bb55a060b4d0376
 */
public class LinkedListCycle {

  static class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }

  }

  /**
   * Big-O: O(n)
   * Justification: While loop iterates < 2n times.
   */
  public boolean hasCycle(ListNode head) {

    ListNode walker = head, runner = head;

    while (runner != null && runner.next != null) {
      walker = walker.next;
      runner = runner.next.next;
      if (walker == runner) {
        return true;
      }
    }
    return false;
  }

}
