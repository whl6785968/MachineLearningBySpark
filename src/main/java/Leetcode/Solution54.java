package Leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution54 {
    private List<Integer> list;

    public Solution54(){
        list = new ArrayList<>();
    }

    public int kthLargest(TreeNode root, int k) {
        getInorder(root);
        int index = 0;
        int target = list.size() - k;
        for(int i : list){
            if(index == target){
                return i;
            }
            index += 1;
        }

        return -1;

    }

    public void getInorder(TreeNode x){
        if(x == null){
            return;
        }

        getInorder(x.left);
        list.add(x.val);
        getInorder(x.right);
    }
}
