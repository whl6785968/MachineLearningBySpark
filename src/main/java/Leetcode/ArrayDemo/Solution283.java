package Leetcode.ArrayDemo;

import java.util.ArrayList;

public class Solution283 {
    public void moveZeroes(int[] nums) {
        int[] arr = new int[nums.length];

        int index = 0;
        for(int i = 0;i < nums.length;i++){
            if(nums[i] != 0){
                arr[index++] = nums[i];
            }
        }

        nums = arr;
        System.out.println(nums);
    }

    public static void main(String[] args) {
        Solution283 solution283 = new Solution283();
        int[] nums = {0,1,0,3,12};
        solution283.moveZeroes(nums);
    }
}
