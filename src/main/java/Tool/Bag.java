package Tool;

public class Bag {
    private Node first;
    private int N;


    public Bag(){
        first = null;
        N = 0;
    }

    public class Node{
        public int number;
        public Node next;
    }

    public void add(int number){
       Node oldFirst = first;
       first = new Node();
       first.number = number;
       first.next = oldFirst;
       N++;
    }

}
