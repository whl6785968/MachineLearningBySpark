package Leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution378 {
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0] - b[0];
            }
        });
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{matrix[i][0], i, 0});
        }
        for (int i = 0; i < k - 1; i++) {
            int[] now = pq.poll();
            if (now[2] != n - 1) {
                pq.offer(new int[]{matrix[now[1]][now[2] + 1], now[1], now[2] + 1});
            }
        }
        return pq.poll()[0];
    }

    public static void main(String[] args) {
//        int[][] matrix = {{1,5,9},{10,11,13},{12,13,15}};
        int[][] matrix = {{1,2},{1,3}};
        Solution378 solution378 = new Solution378();
        int i = solution378.kthSmallest(matrix, 3);
        System.out.println(i);

    }
}
