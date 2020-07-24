package Leetcode;

import java.util.HashSet;

public class Solution695 {
    private int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private HashSet<Integer>[] g;
    private int R,C;
    private int[][] grid;
    private boolean[] marked;

    public int maxAreaOfIsland(int[][] grid) {
        if(grid == null) return 0;
        R = grid.length;
        C = grid[0].length;

        createGraph();
        int maxArea = 0;
        for(int v=0;v < R*C;v++){
            if(!marked[v]){
                maxArea = Math.max(dfs(v),maxArea);
            }
        }
        return maxArea;
    }

    public int dfs(int v){
        marked[v] = true;
        int res = 1;
        for(int w:g[v]){
            if(!marked[w]){
                res += dfs(w);
            }
        }

        return res;

    }

    public void createGraph(){
        g = new HashSet[R*C];
        for(int v=0;v<g.length;v++){
            g[v] = new HashSet<>();
        }

        for(int v=0;v<g.length;v++){
            int x = v / C,y=v % C;
            if(grid[x][y]==1){
                for(int d=0;d<4;d++){
                   int nextx = x + dirs[d][0];
                   int nexty = y + dirs[d][1];
                   if(inArea(nextx,nexty) && grid[nextx][nexty]==1){
                       int next = nextx*C + nexty;
                       g[v].add(next);
                       g[next].add(v);
                   }
                }
            }
        }
    }

    public boolean inArea(int x,int y){
        return x > 0 && x < R && y > 0 && y < C;
    }
}
