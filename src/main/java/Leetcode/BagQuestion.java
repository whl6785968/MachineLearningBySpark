package Leetcode;

import java.util.Arrays;

public class BagQuestion {
    public int knapSack(int[] w,int[] v,int C){
        int n = w.length;

        if(n == 0){
            return 0;
        }

        int[][] memo = new int[n][C+1];
//        Arrays.fill(memo,-1);

        for(int j = 0;j <= C;j++ ){
            memo[0][j] = j >= w[0] ? v[0] : 0;
        }

        for(int i = 1;i < n;i++){
            for(int j = 0;j <= C;j++){
                //不装第i个物体
                memo[i][j] = memo[i-1][j];
                if(j >= w[i])
                    memo[i][j] = Math.max(memo[i][j],v[i] + memo[i-1][j-w[i]]);
            }
        }

        return memo[n-1][C];
    }

    public int knapSack2(int[] w,int[] v,int C){
        int n = w.length;

        if(n == 0){
            return 0;
        }

        int[] memo = new int[C+1];

        for(int j = 0;j <= C;j++){
            memo[j] = j >= w[0] ? v[0] : 0;
        }

        for(int i = 1;i < n;i++){
            for(int j = C;j >= w[i];j--){
                memo[j] = Math.max(memo[j],v[i]+memo[j-w[i]]);
            }
        }

        return memo[C];
    }

    public static void main(String[] args) {
        BagQuestion bagQuestion = new BagQuestion();
        int[] w = {1,2,3};
        int[] v = {6,10,12};
        int C = 5;
        int i = bagQuestion.knapSack2(w, v, C);
        System.out.println(i);

    }
}
