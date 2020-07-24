package Leetcode;

import java.util.HashMap;
import java.util.Map;

public class Solution07_2 {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || preorder.length == 0){
            return null;
        }

        Map<Integer,Integer> indexMap = new HashMap<>();

        for(int i = 0;i < inorder.length;i++){
            indexMap.put(inorder[i],i);
        }

        return buildTree(preorder,0,preorder.length-1,inorder,
                0,inorder.length-1,indexMap);
    }

    public TreeNode buildTree(int[] preorder, int preStart, int preEnd, int[] inorder,
                              int inorderStart, int inorderEnd, Map<Integer,Integer> indexMap){
        if(preStart > preEnd){
            return null;
        }

        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        if(preStart == preEnd){
            return root;
        }

        int rootIndex = indexMap.get(rootVal);
        int leftNodes = rootIndex-inorderStart,rightNodes=inorderEnd-rootIndex;
        root.left = buildTree(preorder,preStart+1,preStart+leftNodes,inorder,inorderStart,rootIndex-1,indexMap);
        root.right = buildTree(preorder,preEnd-rightNodes+1,preEnd,inorder,rootIndex+1,inorderEnd,indexMap);

        return root;
    }
}
