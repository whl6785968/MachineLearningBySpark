package Leetcode.ArrayDemo;

import java.util.HashMap;
import java.util.Map;

public class Solution26 {
//    public int removeDuplicates(int[] nums) {
//        if(nums.length == 0) return 0;
//
//        Map<Integer,Integer> map = new HashMap<>();
//        for(int i = 0;i < nums.length;i++){
//            map.put(nums[i],0);
//        }
//
//        int index = 0;
//        for(int i = 0;i < nums.length;i++){
//            if(map.get(nums[i]) == 0){
//                nums[index++] = nums[i];
//                map.put(nums[i],1);
//            }
//        }
//
//        return index++;
//    }

    public int removeDuplicates(int[] nums) {
        if(nums.length < 2) return nums.length;

        int index = 0;
        for(int i = 1;i < nums.length;i++){
            if(nums[index] != nums[i]){
                nums[++index] = nums[i];
            }
        }

        return ++index;
    }

    public static void main(String[] args) {
        Solution26 solution26 = new Solution26();
        int[] nums = {1,1,1,2,2,2};
        int i = solution26.removeDuplicates(nums);
        System.out.println(i);
    }
}
