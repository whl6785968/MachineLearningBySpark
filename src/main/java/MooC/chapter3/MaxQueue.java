package MooC.chapter3;

import edu.princeton.cs.algs4.Queue;

public class MaxQueue {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    public MaxQueue(){

    }

    private class Node{
        int item;
        Node next;
        boolean isMax;

        public Node(){

        }
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public void push_back(int value) {
        Node oldLast = this.last;
        this.last = new Node();
        this.last.item = value;
        this.last.next = null;

        if(this.isEmpty()){
            this.first = this.last;
        }
        else {
            oldLast.next = this.last;
        }

        this.size ++;
    }

    public int pop_front() {
        if(this.isEmpty()){
            return -1;
        }
        else {
            int value = this.first.item;
            this.first = this.first.next;

            this.size --;

            if(this.isEmpty()){
                this.last = null;
            }

            return value;
        }

    }

    public int max_value() {
        int maxValue = -1000000000;
        Node currentNode = this.first;
        while (currentNode != null){
            int value = currentNode.item;
            if(value > maxValue){
                maxValue = value;
            }
            currentNode = currentNode.next;
        }
        return maxValue;
    }

    public int getSize(){
        return this.size;
    }


}
