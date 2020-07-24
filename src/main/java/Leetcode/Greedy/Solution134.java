package Leetcode.Greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Solution134 {
    //如果能走一周，则gas一定大于等于cost
    //只有gas大于等于cost的站点才能作为出发站点
    //计算出发获取的gas是否能对冲掉cost
    public int canCompleteCircuit(int[] gas, int[] cost) {
       int n = gas.length;

       int total_tank = 0;
       int curr_tank = 0;
       int starting_station = 0;

       for(int i = 0;i < n;i++){
           total_tank += gas[i] - cost[i];
           curr_tank += gas[i] - cost[i];

           if(curr_tank < 0){
               starting_station = i + 1;
               curr_tank = 0;
           }
       }

       return total_tank >= 0 ? starting_station : -1;
    }

    public static void main(String[] args) {
        int[] gas = {3,3,4};
        int[] cost = {3,4,4};

        Solution134 solution134 = new Solution134();
        int i = solution134.canCompleteCircuit(gas, cost);
        System.out.println(i);
    }
}
