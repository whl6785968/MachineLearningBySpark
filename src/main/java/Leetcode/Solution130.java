package Leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution130 {
    private int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int R,C;
    private char[][] board;
    private boolean[][] marked;
    private List<int[]> feas;

    public void solve(char[][] board) {
        R = board.length;
        C = board[0].length;
        this.board = board;
        marked = new boolean[R][C];


        for(int i=0;i<R;i++){
            for(int j=0;j<C;j++){
                if('O' == (board[i][j]) && !marked[i][j]){
                    feas = new ArrayList<>();
                    if(dfs(i,j)) {
                        for(int[] f : feas){
                            int x = f[0];
                            int y = f[1];
                            board[x][y] = 'X';
                        }
                    }
                }
            }
        }


    }

    public boolean dfs(int x,int y){
        int[] fea = new int[2];
        fea[0] = x;
        fea[1] = y;
        feas.add(fea);
        marked[x][y] = true;
        for(int d=0;d<4;d++){
            int nextX = x + dirs[d][0],nextY = y + dirs[d][1];
            if(inArea(nextX,nextY)){
                if('O'== board[nextX][nextY] && !marked[nextX][nextY]){
                    if(!dfs(nextX,nextY)) return false;
                }
                else {
                 if('O' == board[nextX][nextY] && isBorder(nextX,nextY)){
                     return false;
                 }
                }
            }
            else {
                return false;
            }
        }
        return true;
    }

    public boolean inArea(int x,int y){
        return x >= 0 && x < R && y >= 0 && y < C;
    }

    public boolean isBorder(int x,int y){
        return x == 0 || x == R-1 || y == 0 || y == C-1;
    }

    public static void main(String[] args) {
        char[][] board = {{'O','O','O','O','X','X'}, {'O','O','O','O','O','O'},{'O','X','O','X','O','O'},
                {'O','X','O','O','X','O'},{'O','X','O','X','O','O'},{'O','X','O','O','O','O'}};
        Solution130 solution130 = new Solution130();
        solution130.solve(board);
        System.out.println("11");
    }
}
