package Leetcode;

import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution21 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        List<Integer> nodes = new ArrayList<>();

        ListNode cur = l1;
        while (cur != null){
            nodes.add(cur.val);
            cur = cur.next;
        }

        ListNode cur2 = l2;
        while (cur2 != null){
            nodes.add(cur2.val);
            cur2 = cur2.next;
        }

        Collections.sort(nodes);

        ListNode head = new ListNode(nodes.get(0));
        ListNode last = head;

        for(int i = 1;i < nodes.size();i++){
            ListNode oldLast = last;
            last = new ListNode(nodes.get(i));
            oldLast.next = last;
        }


        return head;
    }


}
