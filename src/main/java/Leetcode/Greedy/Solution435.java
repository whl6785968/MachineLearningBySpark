package Leetcode.Greedy;

import java.util.Arrays;

public class Solution435 {
    public int eraseOverlapIntervals(int[][] intervals) {
        if(intervals.length == 0) return 0;

        Arrays.sort(intervals, (o1, o2) -> {
            if(o1[1] != o2[1]) {
                return o1[1] - o2[1];
            }
            return o1[0] - o2[0];
        });

        int res = 1;
        int pre = 0;

        for(int i = 1; i < intervals.length;i++){
            if(intervals[i][0] >= intervals[pre][1]){
                res ++;
                pre = i;
            }
        }

        return intervals.length - res;
    }

    public static void main(String[] args) {
        int[][] intervals = {{1,2},{2,3},{3,4},{1,3}};
        Solution435 solution435 = new Solution435();
        int i = solution435.eraseOverlapIntervals(intervals);
        System.out.println(i);
    }
}
