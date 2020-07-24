package Leetcode;

import java.util.HashMap;
import java.util.Map;

public class Solution48 {
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0) return 0;

        String dp = s.substring(0,1);
        int maxLength = 1;
        int init_index = 0;

        for(int i = 1;i < s.length();i++){
            if(!dp.contains(s.substring(i,i+1))){
                dp = s.substring(init_index,i+1);
            }
            else {
                init_index = s.substring(0,i).lastIndexOf(s.substring(i,i+1))+1;
                dp = s.substring(init_index,i+1);
            }

            maxLength = Math.max(maxLength, dp.length());
        }

        return maxLength;
    }

    public int lengthOfLongestSubstring2(String s) {
        Map<Character,Integer> map = new HashMap<>();

        int tmp = 0;
        int res = 0;
        for(int j = 0;j < s.length();j++){
            int i = map.getOrDefault(s.charAt(j),-1);
            map.put(s.charAt(j),j);
            tmp = tmp < j - i ? tmp + 1:j - i;
            res = Math.max(res,tmp);
        }

        return res;
    }

//    public int getMaxIndexForLastEle(String s,String target){
//
//    }

    public static void main(String[] args) {
        String s = "abcabcbb";

        Solution48 solution48 = new Solution48();
        int i = solution48.lengthOfLongestSubstring(s);
        System.out.println(i);
    }
}
