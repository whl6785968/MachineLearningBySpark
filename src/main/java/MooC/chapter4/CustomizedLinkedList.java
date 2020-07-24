package MooC.chapter4;

public class CustomizedLinkedList<E> {
    private class Node{
        E e;
        Node next;

        public Node(E e,Node next){
            this.e = e;
            this.next = next;
        }
    }

    //虚拟头节点
    private Node dummyHead;
    private int size = 0;

    public CustomizedLinkedList(){
        dummyHead = new Node(null,null);
        size = 0;
    }


    public boolean isEmpty(){
        return size == 0;
    }

    public void add(int index,E item){

        Node pre = dummyHead;
        for(int i = 0;i < index;i++){
            pre = pre.next;
        }

        // 1 -> 2 -> 3
        pre.next = new Node(item,pre.next);
        size++;

    }

    public void addFirst(E item){
        add(0,item);
    }

    public E get(int index){
        Node cur = dummyHead.next;

        for(int i = 0;i < index;i++){
            cur = cur.next;
        }

        return cur.e;
    }

    public E getFirst(){
        return get(0);
    }

    public E getLast(){
        return get(size-1);
    }

    public void update(int index,E e){
        Node cur = dummyHead.next;

        for(int i = 0;i < index;i++){
            cur = cur.next;
        }

        cur.e = e;
    }

    public boolean contains(E e){
        Node cur = dummyHead.next;

        for(int i = 0;i < size;i++){
            if(cur.e.equals(e)){
                return true;
            }
        }

        return false;
    }

    public E delete(int index){
        Node prev = dummyHead;

        for(int i = 0;i < index;i++){
            prev = prev.next;
        }

        Node retNode = prev.next;

        prev.next = retNode.next;
        retNode.next = null;
        size--;

        return retNode.e;
    }

    public E removeFirst(){
        return delete(0);
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();

        Node cur = dummyHead.next;
        while(cur != null){
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL");

        return res.toString();
    }

    public static void main(String[] args) {
        CustomizedLinkedList<Integer> linkedList = new CustomizedLinkedList<>();
        linkedList.addFirst(1);
        linkedList.addFirst(2);
        linkedList.add(2,3);

        System.out.println(linkedList);

        System.out.println(linkedList.delete(1));

        System.out.println(linkedList);
    }


}
