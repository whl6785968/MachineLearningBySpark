package huawei;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first = null;
    private int n = 0;

    public Stack() {
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.n;
    }

    public void push(Item item) {
        Node<Item> oldfirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldfirst;
        ++this.n;
    }

    public Item pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        } else {
            Item item = this.first.item;
            this.first = this.first.next;
            --this.n;
            return item;
        }
    }

    public Item peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        } else {
            return this.first.item;
        }
    }

    public List<Item> toList(){
        List<Item> list = new ArrayList<>();
        Iterator<Item> it = iterator();
        while (it.hasNext()){
            list.add(it.next());
        }

        return list;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<Item> it = this.iterator();

        while(it.hasNext()) {
            Item item = it.next();
            s.append(item);
            s.append(",");
        }

        return s.toString();
    }

    public Iterator<Item> iterator() {
        return new ListIterator(this.first);
    }


    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            this.current = first;
        }

        public boolean hasNext() {
            return this.current != null;
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                Item item = this.current.item;
                this.current = this.current.next;
                return item;
            }
        }
    }

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;

        private Node() {
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);

        System.out.println(stack.pop());
    }
}
