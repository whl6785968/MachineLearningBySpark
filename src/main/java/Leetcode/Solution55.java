package Leetcode;

public class Solution55 {
    public boolean isBalanced(TreeNode root) {
        if(root == null){
            return true;
        }

        boolean result = isBalanced(root.left) && isBalanced(root.right);

        if(!(Math.abs(recur(root.left)-recur(root.right)) <= 1)){
            result = false;
        }

        return result;
    }

    public int recur(TreeNode root){
        if(root == null){
            return 0;
        }

        return Math.max(recur(root.left),recur(root.right)) + 1;
    }

    //方法二
//    public boolean isBalanced(TreeNode root) {
//        return recur(root) != -1;
//    }
//
//    private int recur(TreeNode root) {
//        if (root == null) return 0;
//        int left = recur(root.left);
//        if(left == -1) return -1;
//        int right = recur(root.right);
//        if(right == -1) return -1;
//        return Math.abs(left - right) < 2 ? Math.max(left, right) + 1 : -1;
//    }


}
