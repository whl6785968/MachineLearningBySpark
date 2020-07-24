package Leetcode;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class MyStack {
    private int size = 0;
    private LinkedList<Integer> queue;


    /** Initialize your data structure here. */
    public MyStack() {
        queue = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        queue.add(x);
        int sz = queue.size();

        while (sz > 1){
            queue.add(queue.remove());
            sz--;
        }
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        return queue.remove();
    }

    /** Get the top element. */
    public int top() {
        return queue.peek();
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue.isEmpty();
    }
}
