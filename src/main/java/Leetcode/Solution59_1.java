package Leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution59_1 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums == null || nums.length == 0){
            return new int[0];
        }

        if(k == 1){
            return nums;
        }

        List<Integer> list = new ArrayList<>();
        for(int i=0;i < nums.length;i++){
            int[] subNums = new int[k+1];
            for(int j = i;i < i + k;j++){
                subNums[j] = nums[i];
            }
            int max = getMax(subNums);
            list.add(max);
        }

        int[] result = new int[list.size()];
        for(int i = 0;i < list.size();i++){
            result[i] = list.get(i);
        }

        return result;
    }

    public int getMax(int[] nums){
        int maxValue = -1000000000;
        for(int num:nums){
            if(num > maxValue){
                maxValue = num;
            }
        }

        return maxValue;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        Solution59_1 solution59_1 = new Solution59_1();
        solution59_1.maxSlidingWindow(nums,3);
    }
}
