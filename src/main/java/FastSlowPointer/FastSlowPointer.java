package FastSlowPointer;

class Node{
    private int value;
    private Node next;
    public Node(int value){
        this.value = value;
        this.next = null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

public class FastSlowPointer {
    public static boolean isLoop(Node head){
        Node p1 = head;
        Node p2 = head;

        while(null != p1 && null != p2.getNext()){
            p1 = p1.getNext();
            p2 = p2.getNext().getNext();
            if(p1==p2){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Node n1 = new Node(8);
        Node n2 = new Node(2);
        Node n3 = new Node(5);
        Node n4 = new Node(6);
        Node n5 = new Node(3);
        Node n6 = new Node(9);
        Node n7 = new Node(7);

        n1.setNext(n2);
        n2.setNext(n3);
        n3.setNext(n4);
        n4.setNext(n5);
        n5.setNext(n6);
        n6.setNext(n7);
//        n7.setNext(n4);
        boolean loop = isLoop(n1);
        System.out.println(loop);
    }
}
