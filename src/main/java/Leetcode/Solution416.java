package Leetcode;

import java.util.Arrays;

public class Solution416 {
    int[][] memo;

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int i = 0;i < nums.length;i++){
            sum += nums[i];
        }

        if(sum % 2 != 0) return false;

        int[] tmp = new int[sum];
        Arrays.fill(tmp,-1);

        memo = new int[nums.length][sum];
        Arrays.fill(memo,tmp);


        return tryPartition(nums,nums.length-1,sum/2);
    }

    public boolean tryPartition(int[] nums,int index,int sum){
        if(sum == 0){
            return true;
        }

        if(sum < 0 || index < 0){
            return false;
        }

        if(memo[index][sum] != -1){
            return memo[index][sum] == 1;
        }

        memo[index][sum] = (tryPartition(nums,index-1,sum) || tryPartition(nums,index-1,sum-nums[index])) ? 1 : 0;

        return memo[index][sum] == 1;
    }

    public boolean canPartition2(int[] nums){
        int sum = 0;
        for(int i = 0;i < nums.length;i++){
            sum += nums[i];
        }

        if(sum % 2 != 0) return false;

        int n = nums.length;
        int C = sum / 2;
        boolean[] dp = new boolean[C+1];

        for(int j = 0; j <= C;j++){
            dp[j] = nums[0] == j;
        }

        for(int i = 1;i < n;i++){
            for(int j = C;j >= nums[i];j--){
                dp[j] = dp[j] || dp[j-nums[i]];
            }
        }

        return dp[C];
    }

    public static void main(String[] args) {
        Solution416 solution416 = new Solution416();
        int[] nums = {1,2,5};
        boolean b = solution416.canPartition2(nums);
        System.out.println(b);

    }


}
