package Leetcode;

public class Solution64 {
    //1.如果是最右边，只能向下走
    //2.如果是最下边，只能往右走
    public int minPathSum(int[][] grid) {
        if(grid.length == 0 || grid == null) return 0;


        for(int i = 0;i < grid.length;i++){
            for(int j = 0;j < grid[i].length;j++){
                if(i == 0 && j != 0){
                    grid[i][j] = grid[i][j-1] + grid[i][j];
                }
                else if(i != 0 && j == 0){
                    grid[i][j] = grid[i-1][j] + grid[i][j];
                }
                else if(i != 0 && j != 0){
                    grid[i][j] = Math.min(grid[i][j-1],grid[i-1][j])+grid[i][j];
                }

            }
        }

        return grid[grid.length-1][grid[0].length-1];
    }


    public static void main(String[] args) {
        int[][] grid = {{1,3,1},{1,5,1},{4,2,1}};
//        int[][] grid = {{1,2,5},{3,2,1}};
        Solution64 solution64 = new Solution64();
        int i = solution64.minPathSum(grid);
        System.out.println(i);
    }
}
