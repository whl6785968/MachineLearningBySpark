package Leetcode;

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
 }

public class Solution07 {
    //1.前序中第一个节点为根节点
    //2.前序中第二个节点为左子节点
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length == 1){
            return new TreeNode(preorder[0]);
        }

        TreeNode root = new TreeNode(preorder[0]);
        int splitPosition = findPosition(inorder, preorder[0]);
        int[] leftInorder = new int[splitPosition];
        int[] rightInorder = new int[inorder.length-splitPosition-1];

        for(int i=0;i<splitPosition;i++){
            leftInorder[i] = inorder[i];
        }
        int[] leftPreorder = findPreOrder(leftInorder,preorder);

        int index = 0;
        for(int j=splitPosition+1;j<inorder.length;j++){
            rightInorder[index] = inorder[j];
            index += 1;
        }
        int[] rightPreorder = findPreOrder(rightInorder,preorder);

        root.left = buildTree(leftPreorder,leftInorder);
        root.right = buildTree(rightPreorder,rightInorder);

        return root;
    }

    public int[] findPreOrder(int[] inorder,int[] preorder){
        int[] subPreOrder = new int[inorder.length];
        int index = 0;
        for(int i=0;i<preorder.length;i++){
            for(int j=0;j<inorder.length;j++){
                if(preorder[i] == inorder[j]){
                    subPreOrder[index] = preorder[i];
                    index += 1;
                }
            }
        }

        return subPreOrder;
    }


    public int findPosition(int[] inorder,int root){
        for(int i=0;i<inorder.length;i++){
            if(inorder[i] == root){
                return i;
            }
        }

        return -1;
    }


    public static void main(String[] args) {
        int[] preorder = {3,9,20,15,7};
        int[] inorder = {9,3,15,20,7};

        Solution07 solution07 = new Solution07();
        TreeNode treeNode = solution07.buildTree(preorder, inorder);
        System.out.println(treeNode);
    }

}
