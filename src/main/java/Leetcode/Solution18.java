package Leetcode;

public class Solution18 {
    public ListNode deleteNode(ListNode head, int val) {
        ListNode dummyNode = new ListNode(-1);

        while (head.val == val){
            head = head.next;
        }

        dummyNode.next = head;
        ListNode pre = dummyNode;

        while (pre.next != null){
            if(pre.next.val == val){
                pre.next = pre.next.next;
            }
            else{
                pre = pre.next;
            }
        }

        return dummyNode.next;

//        if(head == null){
//            return null;
//        }
//
//        head.next = deleteNode(head.next,val);
//
//        if(head.val == val){
//            return head.next;
//        }
//        else{
//            return head;
//        }


    }
}
