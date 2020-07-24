package Leetcode;

import java.util.Arrays;

public class SolutionN {
    public boolean ca(int[] arr){
        if(arr == null || arr.length == 0) return false;
        if(arr.length == 1) return true;
        Arrays.sort(arr);
        int diff = arr[1] - arr[0];
        boolean result = true;
        for(int i = 2;i < arr.length;i++){
            if(arr[i] - arr[i-1] != diff){
                result = false;
                break;
            }
        }

        return result;

//        class Solution {
//            public int getLastMoment(int n, int[] left, int[] right) {
//                if(n == 0){
//                    return 0;
//                }
//
//                if(left.length == 0 && right.length != 0 || left.length != 0 && right.length == 0){
//                    return n;
//                }
//
//                Stack<Integer> stack = new Stack<>();
//
//                for(int i = 0; i < n;i++){
//                    int leftIndex = left.length - 1;
//                    int rightIndex = 0;
//                    if(right[leftIndex] == left[rightIndex]){
//                        stack.push(left[rightIndex]);
//                        stack.push(right(leftIndex));
//                    }
//
//                    leftIndex ++;
//                    rightIndex --;
//                }
//            }
//        }
    }

    public static void main(String[] args) {
//        SolutionN solutionN = new SolutionN();
        int[] arr = {3,5,1};
//        solutionN.ca(arr);

    }
}
