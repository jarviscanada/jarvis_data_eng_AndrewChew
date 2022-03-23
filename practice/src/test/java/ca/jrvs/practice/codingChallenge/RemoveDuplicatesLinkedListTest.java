package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.RemoveDuplicatesLinkedList.ListNode;
import org.junit.Before;
import org.junit.Test;

public class RemoveDuplicatesLinkedListTest {

  RemoveDuplicatesLinkedList removeDuplicatesLinkedList;

  @Before
  public void setUp() throws Exception {
    removeDuplicatesLinkedList = new RemoveDuplicatesLinkedList();
  }

  @Test
  public void removeDuplicates1Node() {
    ListNode head = new ListNode(1);
    ListNode newHead = removeDuplicatesLinkedList.removeDuplicates(head);
    assertEquals(1, newHead.val);
    assertNull(newHead.next);
  }

  @Test
  public void removeDuplicates2NodesNoDuplicates() {
    ListNode node2 = new ListNode(2);
    ListNode head = new ListNode(1, node2);
    ListNode newHead = removeDuplicatesLinkedList.removeDuplicates(head);
    assertEquals(1, newHead.val);
    assertEquals(2, newHead.next.val);
    assertNull(newHead.next.next);
  }

  @Test
  public void removeDuplicates2Nodes() {
    ListNode node2 = new ListNode(1);
    ListNode head = new ListNode(1, node2);
    ListNode newHead = removeDuplicatesLinkedList.removeDuplicates(head);
    assertEquals(1, newHead.val);
    assertNull(newHead.next);
  }

  @Test
  public void removeDuplicates3NodeNoDuplicates() {
    ListNode node3 = new ListNode(3);
    ListNode node2 = new ListNode(2, node3);
    ListNode head = new ListNode(1, node2);
    ListNode newHead = removeDuplicatesLinkedList.removeDuplicates(head);
    assertEquals(1, newHead.val);
    assertEquals(2, newHead.next.val);
    assertEquals(3, newHead.next.next.val);
    assertNull(newHead.next.next.next);
  }

  @Test
  public void removeDuplicates3Nodes() {
    ListNode node3 = new ListNode(1);
    ListNode node2 = new ListNode(2, node3);
    ListNode head = new ListNode(1, node2);
    ListNode newHead = removeDuplicatesLinkedList.removeDuplicates(head);
    assertEquals(1, newHead.val);
    assertEquals(2, newHead.next.val);
    assertNull(newHead.next.next);
  }

  @Test
  public void removeDuplicates10NodesMultipleDuplicates() {
    ListNode node10 = new ListNode(5);
    ListNode node9 = new ListNode(2, node10);
    ListNode node8 = new ListNode(1, node9);
    ListNode node7 = new ListNode(4, node8);
    ListNode node6 = new ListNode(1, node7);
    ListNode node5 = new ListNode(3, node6);
    ListNode node4 = new ListNode(2, node5);
    ListNode node3 = new ListNode(3, node4);
    ListNode node2 = new ListNode(2, node3);
    ListNode head = new ListNode(1, node2);
    ListNode newHead = removeDuplicatesLinkedList.removeDuplicates(head);
    assertEquals(1, newHead.val);
    assertEquals(2, newHead.next.val);
    assertEquals(3, newHead.next.next.val);
    assertEquals(4, newHead.next.next.next.val);
    assertEquals(5, newHead.next.next.next.next.val);
    assertNull(newHead.next.next.next.next.next);
  }
}