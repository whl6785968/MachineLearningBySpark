package Leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution77 {
    private List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        if(n < 0 || k < 0 || k > n) return result;
        List<Integer> vector = new ArrayList<>();
        generateCombinations(n,k,1,vector);
        return result;
    }

    public void generateCombinations(int n,int k,int start,List<Integer> vector){
        if(vector.size() == k){
            List<Integer> tmp = new ArrayList<>(vector);
            result.add(tmp);
            return;
        }

        //如果还剩下两个位置，当n=4时，i最多为3
        //如果还剩下一个位置，当n=4时，i最多为4
        for(int i = start;i <= n-(k-vector.size())+1;i++){
            vector.add(i);
            generateCombinations(n,k,i+1,vector);
            vector.remove(vector.size()-1);
        }
    }

    public static void main(String[] args) {
        Solution77 solution77 = new Solution77();
        List<List<Integer>> combine = solution77.combine(4, 2);
        System.out.println(combine);
    }
}
