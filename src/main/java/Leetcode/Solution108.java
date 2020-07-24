package Leetcode;

public class Solution108 {
    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums.length == 1){
            return new TreeNode(nums[0]);
        }

        if(nums.length == 0){
            return null;
        }

        int rootIndex = nums.length / 2;
        TreeNode root = new TreeNode(nums[rootIndex]);

        int[] leftNodes = new int[rootIndex];
        int[] rightNodes = new int[nums.length-rootIndex-1];

        if(leftNodes.length != 0){
            for(int i = 0;i < rootIndex;i++){
                leftNodes[i] = nums[i];
            }
        }

        if(rightNodes.length != 0){
            int rightIndex = 0;
            for(int i = rootIndex+1;i < nums.length;i++){
                rightNodes[rightIndex] = nums[i];
                rightIndex += 1;
            }
        }

        root.left = sortedArrayToBST(leftNodes);
        root.right = sortedArrayToBST(rightNodes);

        return root;

    }

    public static void main(String[] args) {
        Solution108 solution108 = new Solution108();
        int[] nums = {-10,-3,0,5,9};

        TreeNode treeNode = solution108.sortedArrayToBST(nums);
        System.out.println(treeNode);
    }
}
