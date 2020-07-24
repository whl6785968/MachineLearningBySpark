package MooC.dynamicPlan;

import java.util.Arrays;

public class Fab {
    private int[] memo;

    public Fab(int N){
        memo = new int[N+1];
        Arrays.fill(memo,-1);
    }

    public int fab(int N){
        if(N == 0) return 0;
        if(N == 1) return 1;

        if(memo[N] == -1){
            memo[N] = fab(N-1) + fab(N-2);
        }

        return memo[N];
    }

    public int fab2(int N){
        memo[0] = 0;
        memo[1] = 1;

        for(int i=2;i <= N;i++){
            memo[i] = memo[i-1] + memo[i-2];
        }

        return memo[N];
    }

    public static void main(String[] args) {
        Fab fab = new Fab(20);
        System.out.println(fab.fab(20));
        System.out.println(fab.fab2(20));

    }
}
