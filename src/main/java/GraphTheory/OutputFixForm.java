package GraphTheory;

import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class OutputFixForm implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Stack<Integer> stack1 = (Stack<Integer>) o1;
        Stack<Integer> stack2 = (Stack<Integer>) o2;
        if (stack1.pop() > stack2.pop()){
            return 1;
        }
        else if(stack1.pop() < stack2.pop()) {
            return -1;
        }
        return 0;
    }
}
