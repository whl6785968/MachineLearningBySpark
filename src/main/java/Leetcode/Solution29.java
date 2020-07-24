package Leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution29 {
    private boolean[][] marked;

    public int[] spiralOrder(int[][] matrix) {
        int R = matrix.length;
        if(R == 0) return new int[0];

        int C = matrix[0].length;
        if(C == 0) return new int[0];
        marked = new boolean[R][C];

        int rowIndex = 0;
        int colIndex = 0;
        List<Integer> result = new ArrayList<>();
        List<Integer> orderList = getOrderList(matrix,rowIndex,colIndex,R,C);
        result.addAll(orderList);

        R = R - 2;
        C = C - 2;

        while (R >= 0 && C >= 0){
            rowIndex = rowIndex + 1;
            colIndex = colIndex + 1;
            result.addAll(getOrderList(matrix,rowIndex,colIndex,R,C));
            R = R - 2;
            C = C - 2;
        }

        int[] final_result = new int[result.size()];
        for(int i = 0;i < result.size();i++){
            final_result[i] = result.get(i);
        }

        return final_result;
    }

    public List<Integer> getOrderList(int[][] matrix,int rowIndex,int colIndex,int R,int C){
        List<Integer> list = new ArrayList<>();
        int matrix_row_border = rowIndex;
        int matrix_col_border = colIndex;
        R = rowIndex + R - 1;
        C = colIndex + C - 1;

        while ((rowIndex >= 0 && colIndex >= 0) && !marked[rowIndex][colIndex]){
            marked[rowIndex][colIndex] = true;
            if(rowIndex == matrix_row_border && colIndex != C){
                list.add(matrix[rowIndex][colIndex]);
                colIndex++;
            }
            else if(colIndex == C && rowIndex != R){
                list.add(matrix[rowIndex][colIndex]);
                rowIndex++;
            }
            else if(rowIndex == R && colIndex != matrix_col_border){
                list.add(matrix[rowIndex][colIndex]);
                colIndex--;
            }
            else {
                list.add(matrix[rowIndex][colIndex]);
                rowIndex--;
            }
        }

        return list;
    }

    public static void main(String[] args) {
//        int[][] matrix = {{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18},{19,20,21,22,23,24},{25,26,27,28,29,30}};

        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        Solution29 solution29 = new Solution29();
        int[] result = solution29.spiralOrder(matrix);

        for(int r : result){
            System.out.println(r);
        }

    }
}
