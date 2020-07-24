package Leetcode;

import MooC.chapter2.Array;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Solution455 {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        int si = s.length-1,gi = g.length - 1,res = 0;
        while (gi >= 0 && si >= 0){
            if(s[si] >= g[gi]){
                res++;
                si--;
                gi--;
            }
            else{
                gi--;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] g = {1,2,3};
        int[] s = {1,2};

        Solution455 solution455 = new Solution455();
        int contentChildren = solution455.findContentChildren(g, s);
        System.out.println(contentChildren);
    }

}

