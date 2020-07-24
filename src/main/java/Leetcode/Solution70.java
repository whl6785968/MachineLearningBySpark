package Leetcode;


import java.util.Arrays;

public class Solution70 {
    private int[] memo;


    public int climbStairs(int n) {
        memo = new int[n+1];
        Arrays.fill(memo,-1);
//        memo[0] = 1;
//        memo[1] = 1;
//
//        for(int i = 2;i <= n;i++){
//            memo[i] = memo[i-1] + memo[i-2];
//        }
//
//        return memo[n];

        return memoSol(n);

    }

    private int memoSol(int n){
        if(n == 0){
            return 1;
        }

        if(n == 1){
            return 1;
        }

        if(memo[n] == -1){
            memo[n] = memoSol(n-1) + memoSol(n-2);
        }

        return memo[n];
    }

    public static void main(String[] args) {
        Solution70 solution70 = new Solution70();
        System.out.println(solution70.climbStairs(4));
    }
}
