package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.RemoveNthNodeEndLinkedList.ListNode;
import org.junit.Before;
import org.junit.Test;

public class RemoveNthNodeEndLinkedListTest {

  RemoveNthNodeEndLinkedList removeNthNodeEndLinkedList;
  ListNode head;

  @Before
  public void setUp() throws Exception {
    removeNthNodeEndLinkedList = new RemoveNthNodeEndLinkedList();
    ListNode node5 = new ListNode(5);
    ListNode node4 = new ListNode(4, node5);
    ListNode node3 = new ListNode(3, node4);
    ListNode node2 = new ListNode(2, node3);
    head = new ListNode(1, node2);
  }

  @Test
  public void removeNthFromEnd() {
    ListNode newHead = removeNthNodeEndLinkedList.removeNthFromEnd(head, 2);
    assertEquals(1, newHead.val);
    assertEquals(2, newHead.next.val);
    assertEquals(3, newHead.next.next.val);
    assertEquals(5, newHead.next.next.next.val);
    assertNull(newHead.next.next.next.next);
  }
}