package MooC.chapter4;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}


public class Solution203 {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        //如果这里把pre换成cur，则不知道头节点是否是target
        ListNode pre = dummyNode;
        while (pre.next != null){
            if(pre.next.val == val){
                pre.next = pre.next.next;
            }
            else {
                pre = pre.next;
            }
        }

        return dummyNode.next;
    }

    public ListNode removeElements2(ListNode head,int val){
        if(head == null){
            return null;
        }

        ListNode res = removeElements2(head.next,val);

        if(head.val == val){
            return res;
        }
        else {
            head.next = res;
            return head;
        }
    }

    public ListNode removeElement3(ListNode head,int val){
        if(head == null)
            return null;

        head.next = removeElement3(head.next,val);

        return head.val == val ? head.next : head;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(6);
        listNode.next.next.next = new ListNode(3);

        Solution203 solution203 = new Solution203();
        ListNode head = solution203.removeElements2(listNode, 6);
        System.out.println(head);
    }
}
