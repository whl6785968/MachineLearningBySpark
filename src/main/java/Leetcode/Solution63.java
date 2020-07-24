package Leetcode;

public class Solution63 {
    private int[][] dirs = {{0,1},{1,0}};
    private boolean[][] marked;
    int C,R;
    private int[][] obstacleGrid;
    private int cnt = 0;

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid == null || obstacleGrid.length == 0) return 0;
        R = obstacleGrid.length;
        C = obstacleGrid[0].length;

        marked = new boolean[R][C];

        this.obstacleGrid = obstacleGrid;

        if(obstacleGrid[0][0] == 0)
            dfs(0,0);
        else {
            return 0;
        }

        return cnt;
    }

    public void dfs(int x,int y){
        marked[x][y] = true;
        if(x == R - 1 && y == C - 1){
            cnt += 1;
            marked[x][y] = false;
            this.obstacleGrid[x][y] = 2;
            return;
        }

        for(int i = 0;i < dirs.length;i++){
            int nextx = x + dirs[i][0];
            int nexty = y + dirs[i][1];

            if(inArea(nextx,nexty) && !marked[nextx][nexty]){
                if(this.obstacleGrid[nextx][nexty] == 0) dfs(nextx,nexty);
                else if(this.obstacleGrid[nextx][nexty] == 2) {
                    cnt += 1;
                }
            }

        }
        marked[x][y] = false;
        this.obstacleGrid[x][y] = 2;
    }

    public boolean inArea(int x,int y){
        return (x >= 0 && x < R) && (y >= 0 && y < C);
    }

    public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        int n = obstacleGrid.length, m = obstacleGrid[0].length;
        int[] f = new int[m];

        f[0] = obstacleGrid[0][0] == 0 ? 1 : 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (obstacleGrid[i][j] == 1) {
                    f[j] = 0;
                    continue;
                }
                if (j - 1 >= 0 && obstacleGrid[i][j - 1] == 0) {
                    f[j] += f[j - 1];
                }
            }
        }

        return f[m - 1];
    }

    public int uniquePathsWithObstacles3(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0) {
            return 0;
        }

        // 定义 dp 数组并初始化第 1 行和第 1 列。
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m && obstacleGrid[i][0] == 0; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n && obstacleGrid[0][j] == 0; j++) {
            dp[0][j] = 1;
        }

        // 根据状态转移方程 dp[i][j] = dp[i - 1][j] + dp[i][j - 1] 进行递推。
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        int[][] matrix = {{0,0,0},{0,1,0},{0,0,0}};
        Solution63 solution63 = new Solution63();
        int i = solution63.uniquePathsWithObstacles3(matrix);
        System.out.println(i);
    }
}
