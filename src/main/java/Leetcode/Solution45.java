package Leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution45 {
    public String minNumber(int[] nums) {
        if(nums == null || nums.length == 0) return null;

        List<String> list = new ArrayList<>();
        for(int i:nums){
            list.add(Integer.toString(i));
        }

        Collections.sort(list, (o1, o2) -> {
            if ((o1+o2).compareTo(o2+o1) > 0){
                return 1;
            }
            else if((o1+o2).compareTo(o2+o1) < 0){
                return -1;
            }
            return 0;
        });

        String res = "";

        for(String s : list){
            res += s;
        }

        return res;

    }

    public static void main(String[] args) {
        int[] nums = {3,30,34,5,9};


        Solution45 solution45 = new Solution45();
        String s = solution45.minNumber(nums);
        System.out.println(s);
    }
}
