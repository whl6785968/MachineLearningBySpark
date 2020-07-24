package Leetcode;

public class Solution27 {
    //左子树左节点变为右子树右节点
    //左子树右节点变为右子树左节点
    //右子树左节点变为左子树右节点
    //右子树右节点变为左子树左节点
    public TreeNode mirrorTree(TreeNode root) {
        if(root == null) return null;

        root.left = mirrorTree(root.right);
        root.right = mirrorTree(root.left);

        return root;
    }
}
