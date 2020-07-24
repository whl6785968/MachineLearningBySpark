package Leetcode;

import java.util.LinkedList;
//栈实现队列
public class CQueue2 {
    LinkedList<Integer> A,B;

    public CQueue2(){
        this.A = new LinkedList<>();
        this.B = new LinkedList<>();
    }

    public void appendTail(int value){
        this.A.addLast(value);
    }

    public int deleteHead(){
        if(!this.B.isEmpty()) return B.removeLast();
        if(A.isEmpty()) return -1;

        while (!this.A.isEmpty()){
            this.B.addLast(this.A.removeLast());
        }

        return B.removeLast();
    }
}
