package Leetcode;

import java.util.Stack;

public class Solution55_1 {
    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }

        int leftDepth = 1;
        int rightDepth = 1;

        leftDepth += maxDepth(root.left);
        rightDepth += maxDepth(root.right);

        if(leftDepth > rightDepth){
            return leftDepth;
        }
        else if(leftDepth < rightDepth){
            return rightDepth;
        }

        return leftDepth;
    }


    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);

        System.out.println(stack.pop());
    }
}
