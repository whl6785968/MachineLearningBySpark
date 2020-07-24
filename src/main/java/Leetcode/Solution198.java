package Leetcode;

import java.util.Arrays;

public class Solution198 {
    //状态：考虑偷取[x...n-1]个房子
    //转移方程：f(0) = max(v(0)+f(2),v(1)+f(3)+...+v(n-2)+f(n-1))
    int[] memo;
    public int rob(int[] nums) {
        memo = new int[nums.length+1];
        Arrays.fill(memo,-1);

        return tryRob(nums,0);
    }

    public int tryRob(int[] nums,int index){
        if(index >= nums.length) return 0;

        if(memo[index] != -1){
            return memo[index];
        }

        int res = 0;
        for(int i = index;i < nums.length;i++){
            res  = Math.max(res,nums[i]+tryRob(nums,i+2));
        }

        memo[index] = res;

        return res;
    }

    public int rob2(int[] nums){
        if(nums == null || nums.length == 0) return 0;
        memo = new int[nums.length];
        Arrays.fill(memo,-1);
        int n = nums.length;
        memo[n-1] = nums[n-1];
        for(int i = n-2;i >= 0;i--){
            for(int j = i;j < n;j++){
                memo[i] = Math.max(memo[i],nums[j]+(j+2 < n ? memo[j+2] :0));
            }
        }

        return memo[0];

    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,1};

        Solution198 solution198 = new Solution198();
        int i = solution198.rob2(nums);
    }
}
