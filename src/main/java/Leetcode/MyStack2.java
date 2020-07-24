package Leetcode;

import java.util.LinkedList;

public class MyStack2 {
    private LinkedList<Integer> queue;
    private LinkedList<Integer> subQueue;
    int top;

    public MyStack2() {
        queue = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        subQueue.add(x);
        top = x;
        while (!queue.isEmpty()){
            subQueue.add(queue.remove());
        }

        LinkedList<Integer> temp = queue;
        queue = subQueue;
        subQueue = temp;
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        queue.remove();
        int res = top;
        while (!queue.isEmpty()){
            top = queue.peek();
        }
        return res;
    }

    /** Get the top element. */
    public int top() {
        return top;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue.isEmpty();
    }
}
