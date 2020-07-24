package MooC.chapter3;

import java.util.NoSuchElementException;

public class Stack<Item> {
    private class Node<Item> {
        Item item;
        Node next;

        public Node(){

        }
    }

    private Node<Item> first = null;
    private int n;

    public int getSize(){
        return this.n;
    }

    public boolean isEmpty(){
        return this.n == 0;
    }

    public void push(Item item){
        Node oldFirst = first;
        Node node = new Node();
        node.item = item;
        node.next = oldFirst;
        this.first = node;
        this.n++;
    }

    public Item pop(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }

        Item item = this.first.item;
        this.first = this.first.next;
        --n;

        return item;
    }

    public Item peek(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }

        return this.first.item;
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);

        System.out.println(stack.getSize());

        System.out.println(stack.pop());

        System.out.println(stack.getSize());

        System.out.println(stack.pop());

        System.out.println(stack.getSize());
    }
}
