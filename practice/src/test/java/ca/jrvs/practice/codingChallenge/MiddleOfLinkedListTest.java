package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.MiddleOfLinkedList.ListNode;
import org.junit.Before;
import org.junit.Test;

public class MiddleOfLinkedListTest {

  MiddleOfLinkedList middleOfLinkedList;
  ListNode head;

  @Before
  public void setUp() throws Exception {
    middleOfLinkedList = new MiddleOfLinkedList();
    ListNode node5 = new ListNode(5);
    ListNode node4 = new ListNode(4, node5);
    ListNode node3 = new ListNode(3, node4);
    ListNode node2 = new ListNode(2, node3);
    head = new ListNode(1, node2);
  }

  @Test
  public void middleNode() {
    ListNode middleNode = middleOfLinkedList.middleNode(head);
    assertEquals(3, middleNode.val);
    assertEquals(4, middleNode.next.val);
    assertEquals(5, middleNode.next.next.val);
    assertNull(middleNode.next.next.next);
  }
}