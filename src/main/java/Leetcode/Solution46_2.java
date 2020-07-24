package Leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution46_2 {
    private List<List<Integer>> result = new ArrayList<>();
    private boolean[] used;

    public List<List<Integer>> permute(int[] nums) {
        if(nums == null || nums.length == 0) return result;

        used = new boolean[nums.length];
        List<Integer> vector = new ArrayList<>();
        dfs(nums,0,vector);
        return result;

    }

    public void dfs(int[] nums,int index,List<Integer> vector){
        if(index == nums.length){
            List<Integer> tmp = new ArrayList<>(vector);
            result.add(tmp);
            return;
        }

        for(int i = 0;i < nums.length;i++){
            if(!used[i]){
                used[i] = true;
                vector.add(nums[i]);
                dfs(nums,index+1,vector);
                used[i] = false;
                vector.remove(vector.size()-1);
            }
        }
    }

    public static void main(String[] args) {
        Solution46_2 solution46_2 = new Solution46_2();
        int[] nums = {1,2,3};
        List<List<Integer>> permute = solution46_2.permute(nums);
        System.out.println(permute);
    }
}
