package Leetcode;

public class Solution12 {
    private boolean[][] marked;
    private int[][] dirs = {{-1,0},{0,1},{1,0},{0,-1}};
    private int R,C;
    private int index;
    private char[][] board;
    private String word;

    //维护一个指针j用于判断是否存在word
    public boolean exist(char[][] board, String word) {
        if(board == null) return false;

        R = board.length;
        if(R == 0) return false;

        C = board[0].length;

        marked = new boolean[R][C];
        this.board = board;
        this.word = word;


        for(int i = 0;i < R;i++){
            for(int j = 0;j < C;j++){
                if(!marked[i][j] && board[i][j] == word.charAt(index)){
                    if(dfs(i,j,1)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean dfs(int w,int v,int depth){
        if(depth == this.word.length()) return true;

        boolean result = false;
        marked[w][v] = true;

        for(int i = 0;i < dirs.length;i++){
            int nextx = w + dirs[i][0];
            int nexty = v + dirs[i][1];

            if(inArea(nextx,nexty) && !marked[nextx][nexty] && this.board[nextx][nexty] == this.word.charAt(depth)){
                if(dfs(nextx,nexty,depth+1)){
                    result = true;
                    break;
                }
            }
        }

        marked[w][v] = false;

        return result;
    }

    public boolean inArea(int x,int y){
        return (x >= 0 && x < R) && (y >= 0 && y < C);
    }

    public static void main(String[] args) {
//        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        char[][] board = {{'C','A','A'},{'A','A','A'},{'B','C','D'}};
        String word = "AAB";
        Solution12 solution12 = new Solution12();

        boolean exist = solution12.exist(board, word);
        System.out.println(exist);
    }
}
