package Leetcode;

import java.util.ArrayList;
import java.util.List;

class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
 }

public class Solution06 {
    public int[] reversePrint(ListNode head) {
        ListNode reversetList = reversetList(head);
        List<Integer> list = new ArrayList<>();
        while (reversetList != null){
            list.add(reversetList.val);
            reversetList = reversetList.next;
        }

        int[] result = new int[list.size()];
        for(int i = 0;i < list.size();i++){
            result[i] = list.get(i);
        }

        return result;

    }

    public ListNode reversetList(ListNode head){
        ListNode pre = null;
        ListNode cur = head;

        while (cur != null){
            ListNode nextTemp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nextTemp;
        }

        return pre;
    }
}
