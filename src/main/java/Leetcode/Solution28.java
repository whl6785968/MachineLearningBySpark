package Leetcode;

public class Solution28 {
    //左子树的左节点等于右子树的右节点
    //左子树的右节点等于右子树的左节点
    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }

        return recur(root.left,root.right);
    }

    public boolean recur(TreeNode A,TreeNode B){
        if(A == null && B == null) return true;
        if(A != null && B == null || A == null && B != null || A.val != B.val) return false;

        return recur(A.left,B.right) && recur(A.right,B.left);
    }
}
