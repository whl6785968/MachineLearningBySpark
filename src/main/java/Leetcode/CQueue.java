package Leetcode;

import java.util.Stack;

public class CQueue {
    private Stack <Integer> stack1;
    private Stack<Integer> stack2;
    boolean isQueue1 = false;
    boolean isQueue2 = false;

    public CQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void appendTail(int value) {
        if(!stack1.isEmpty() && !isQueue1){
            stack1.push(value);
        }
        else if(!stack2.isEmpty() && !isQueue2){
            stack2.push(value);
        }
        else{
            stack1.push(value);
        }
    }

    public int deleteHead() {
        int result = -1;
        if(isQueue1){
            result = stack1.pop();
        }
        else if(isQueue2){
            result = stack2.pop();
        }
        else {
            if(!stack1.isEmpty()){
                while (!stack1.isEmpty()){
                    Integer element = stack1.pop();
                    stack2.push(element);
                }

                isQueue2 = true;
                result = stack2.pop();
            }
            else if(!stack2.isEmpty()){
                while (!stack2.isEmpty()){
                    Integer element = stack2.pop();
                    stack1.push(element);
                }
                isQueue1 = true;
                result = stack1.pop();
            }
        }

        if(stack1.isEmpty()){
            isQueue1 = false;
        }

        if(stack2.isEmpty()){
            isQueue2 = false;
        }


        return result;


    }

    public static void main(String[] args) {
//        ["deleteHead","deleteHead","deleteHead","deleteHead"]
//[[],[],[],[]]
        CQueue cQueue = new CQueue();
        cQueue.appendTail(97);
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(15);
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(1);
        cQueue.appendTail(43);
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(18);
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(36);
        cQueue.appendTail(69);
        cQueue.appendTail(21);
        cQueue.appendTail(91);
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        cQueue.appendTail(22);
        cQueue.appendTail(40);

        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());
        System.out.println(cQueue.deleteHead());

    }
}
