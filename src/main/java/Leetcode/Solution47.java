package Leetcode;

public class Solution47 {
    public int maxValue(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        for(int i = 0;i < grid.length;i++){
            for(int j = 0;j < grid[i].length;j++){
                if(i == 0 && j != 0){
                    grid[i][j] = grid[i][j-1] + grid[i][j];
                }
                else if(i != 0 && j == 0){
                    grid[i][j] = grid[i-1][j] + grid[i][j];
                }
                else if(i != 0 && j != 0){
                    grid[i][j] = Math.min(grid[i][j-1],grid[i-1][j]) + grid[i][j];
                }
            }
        }

        return grid[grid.length-1][grid[0].length-1];
    }

    public static void main(String[] args) {
        int[][] grid = {{1,3,1},{1,5,1},{4,2,1}};
        Solution47 solution47 = new Solution47();
        solution47.maxValue(grid);
    }
}
