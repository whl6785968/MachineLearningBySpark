package Leetcode;

public class Solution13 {
    int[][] dirs = {{-1,0},{0,1},{1,0},{0,-1}};
    boolean[][] marked;
    int m,n,k;

    public int movingCount(int m, int n, int k) {
        if(k == 0) return 1;

        marked = new boolean[m][n];
        this.m = m;
        this.n = n;
        this.k = k;

        int res;
//        for(int i = 0;i < m;i++){
//            for(int j = 0;j < n; j++){
//                if(!marked[i][j] && getValue(i,j) <= k){
//                    res += dfs(i,j);
//                }
//            }
//        }
        res = dfs(0,0);

        return res;
    }

    public int dfs(int x,int y){
        marked[x][y] = true;
        int res = 1;

        for(int i = 0;i < dirs.length;i++){
            int nextx = x + dirs[i][0];
            int nexty = y + dirs[i][1];

            if(inArea(nextx,nexty) && !marked[nextx][nexty] && getValue(nextx,nexty) <= this.k){
                res += dfs(nextx,nexty);
            }
        }

        return res;
    }

    public int getValue(int x,int y){
        return x / 10 + x % 10 + y / 10 + y % 10;
    }

    public boolean inArea(int x,int y){
        return (x >= 0 && x < this.m) && (y >= 0 && y < this.n);
    }

    public static void main(String[] args) {
        int m = 2,n = 3, k = 1;
        Solution13 solution13 = new Solution13();
        int i = solution13.movingCount(m, n, k);
        System.out.println(i);
    }


}
