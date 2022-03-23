package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;

/**
 * Ticket: https://www.notion.so/jarvisdev/Duplicate-LinkedList-Node-3cc59e2242274fa3a31a2061f5bc748e
 */
public class RemoveDuplicatesLinkedList {

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
   * Justification: While loop iterates n times.
   */
  public ListNode removeDuplicates(ListNode head) {

    HashSet<Integer> values = new HashSet<>();
    ListNode slow = new ListNode(0,head), fast = head;

    while (fast != null) {
      if (values.contains(fast.val)) {
        fast = fast.next;
        slow.next = slow.next.next;
      } else {
        values.add(fast.val);
        fast = fast.next;
        slow = slow.next;
      }
    }

    return head;
  }

}
