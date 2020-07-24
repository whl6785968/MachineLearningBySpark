package Leetcode;

import java.util.Arrays;

public class P343 {
    int[] memo;
    public int integerBreak(int n) {
        if(n <= 2) return 1;

        memo = new int[n+1];
        Arrays.fill(memo,-1);
        return ibreak(n);
    }

    public int ibreak(int n){
        if(n == 1){
            return 1;
        }

        if(memo[n] != -1) return memo[n];

        int res = -1;

        for(int i = 1;i <= n;i++){
            res = max(res,n*(n-i),n*ibreak(n-i));
        }

        memo[n] = res;
        return res;
    }

    public int integerBreak2(int n) {
        memo = new int[n+1];

        memo[1] = 1;
        for(int i = 2;i <= n;i++){
            for(int j = 1;j <= n-i;j++){
                memo[i] = max(memo[i],j*(i-j),memo[i-j]);
            }
        }

        return memo[n];
    }


    public int max(int a,int b,int c){
        return Math.max(a,Math.max(b,c));
    }
}
