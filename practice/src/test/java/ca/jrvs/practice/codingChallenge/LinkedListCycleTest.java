package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.LinkedListCycle.ListNode;
import org.junit.Before;
import org.junit.Test;

public class LinkedListCycleTest {

  LinkedListCycle linkedListCycle;

  @Before
  public void setUp() throws Exception {
    linkedListCycle = new LinkedListCycle();
  }

  @Test
  public void hasNoCycle1Node() {
    ListNode head = new ListNode(1);
    assertFalse(linkedListCycle.hasCycle(head));
  }

  @Test
  public void hasNoCycle2Nodes() {
    ListNode node2 = new ListNode(2);
    ListNode head = new ListNode(1, node2);
    assertFalse(linkedListCycle.hasCycle(head));
  }

  @Test
  public void hasCycle2Nodes() {
    ListNode node2 = new ListNode(2);
    ListNode head = new ListNode(1, node2);
    node2.next = head;
    assertTrue(linkedListCycle.hasCycle(head));
  }

  @Test
  public void hasNoCycleMultipleNodes() {
    ListNode node5 = new ListNode(5);
    ListNode node4 = new ListNode(4, node5);
    ListNode node3 = new ListNode(3, node4);
    ListNode node2 = new ListNode(2, node3);
    ListNode head = new ListNode(1, node2);
    assertFalse(linkedListCycle.hasCycle(head));
  }

  @Test
  public void hasCycleMultipleNodes() {
    ListNode node5 = new ListNode(5);
    ListNode node4 = new ListNode(4, node5);
    ListNode node3 = new ListNode(3, node4);
    ListNode node2 = new ListNode(2, node3);
    ListNode head = new ListNode(1, node2);
    node5.next = head;
    assertTrue(linkedListCycle.hasCycle(head));
  }
}