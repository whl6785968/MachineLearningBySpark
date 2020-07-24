package Leetcode;

import java.util.Arrays;

public class Solution300 {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] memo = new int[n];
        Arrays.fill(memo,1);

        for(int i = 1; i < n;i++){
            for(int j = 0;j < i;j++){
                if(nums[j] < nums[i]){
                    memo[i] = Math.max(memo[i],1+memo[j]);
                }
            }
        }

        int res = 1;
        for(int tmp:memo){
            res = Math.max(res,tmp);
        }

        return res;
    }
}
