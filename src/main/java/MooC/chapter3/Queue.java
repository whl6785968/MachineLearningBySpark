package MooC.chapter3;

public class Queue<Item> {
    private class Node<Item>{
        Item item;
        Node next;

        public Node(){
        }
    }

    private Node<Item> first = null;
    private Node<Item> last = null;
    private int size = 0;

    private int getSize(){
        return size;
    }

    private boolean isEmpty(){
        return this.size == 0;
    }

    private void enqueue(Item item){
        Node oldLast = last;
        this.last= new Node();
        this.last.item = item;
        this.last.next = null;

        if(this.isEmpty()){
            this.first = this.last;
        }
        else {
            oldLast.next = this.last;
        }

        this.size++;
    }

    private Item deque(){
        Item item = this.first.item;
        this.first = this.first.next;

        --this.size;

        if(this.isEmpty()){
            this.last = null;
        }

        return item;
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);

        System.out.println(queue.getSize());

        System.out.println(queue.deque());

        System.out.println(queue.getSize());

        System.out.println(queue.deque());

        System.out.println(queue.getSize());
    }

}
