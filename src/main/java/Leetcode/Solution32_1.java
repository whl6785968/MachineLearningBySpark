package Leetcode;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Solution32_1 {
    public int[] levelOrder(TreeNode root) {
        LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<>();

        queue.add(root);
        List<Integer> list = new ArrayList<>();

        while (!queue.isEmpty()){
            TreeNode node = queue.poll();

            list.add(node.val);
            if(node.left != null) queue.add(node.left);
            if(node.right != null) queue.add(node.right);
        }

        int[] result = new int[list.size()];
        for(int i=0;i<list.size();i++){
            result[i] = list.get(i);
        }

        return result;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        Solution32_1 solution32_1 = new Solution32_1();
        solution32_1.levelOrder(root);

    }
}
