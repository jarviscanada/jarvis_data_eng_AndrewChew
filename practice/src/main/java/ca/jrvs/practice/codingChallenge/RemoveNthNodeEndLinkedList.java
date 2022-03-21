package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/jarvisdev/Nth-Node-From-End-of-LinkedList-59b2fdcc58b34d6b96f8f04f7c4e3112
 */
public class RemoveNthNodeEndLinkedList {

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
   * Justification: Each loop iterates as most n times with each iteration costing O(1) time.
   */
  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode newHead = new ListNode(0, head);
    ListNode slow = newHead;
    ListNode fast = newHead;
    for (int i = 0; i < n ; i++) {
      fast = fast.next;
    }
    while (fast.next != null) {
      slow = slow.next;
      fast = fast.next;
    }
    slow.next = slow.next.next;
    return newHead.next;
  }
}
