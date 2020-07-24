package Leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution120 {
    public int minimumTotal(List<List<Integer>> triangle) {
        if(triangle.size() == 1){
            return triangle.get(0).get(0);
        }

        if( triangle == null || triangle.size() == 0){
            return 0;
        }

        for(int i = triangle.size()-2;i >= 0;i--){
            for(int j = 0;j < triangle.get(i).size();j++){
                triangle.get(i).set(j,triangle.get(i).get(j)+Math.min(triangle.get(i+1).get(j),triangle.get(i+1).get(j+1)));
            }
        }

        return triangle.get(0).get(0);
    }


    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        list1.add(-1);

        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(2);

        List<Integer> list3 = new ArrayList<>();
        list3.add(-3);
        list3.add(1);
        list3.add(-1);

        List<List<Integer>> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);
        list.add(list3);

        Solution120 solution120 = new Solution120();
        int i = solution120.minimumTotal(list);
        System.out.println(i);

    }
}