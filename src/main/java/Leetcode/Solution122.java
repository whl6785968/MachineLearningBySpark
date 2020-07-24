package Leetcode;

public class Solution122 {
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) return false;

        boolean result = hasPathSum(root, sum, 0);
        return result;
    }

    public boolean hasPathSum(TreeNode x,int target,int sum){
        if(x == null) return false;

        sum += x.val;

        if(x.left == null && x.right == null){
            if(sum == target){
                return true;
            }
            else{
                return false;
            }
        }

        boolean left = hasPathSum(x.left,target,sum);
        boolean right = hasPathSum(x.right,target,sum);

        if(left || right) return true;

        return false;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left = new TreeNode(3);
        root.left = new TreeNode(4);
//
        Solution122 solution122 = new Solution122();
        boolean b = solution122.hasPathSum(root, -5);
        System.out.println(b);
    }
}
