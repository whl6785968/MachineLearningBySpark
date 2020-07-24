package Leetcode.Greedy;

public class Solution392 {
    public boolean isSubsequence(String s, String t) {
        if(s.length() > t.length()) return false;

        if(s.length() == t.length()) return s.equals(t);


        int index = 0;
        int cnt = 0;
        for(int i = 0;i < s.length();i++){
            for(;index < t.length();index++){
                if(s.charAt(i) == t.charAt(index)){
                    cnt += 1;
                    index++;
                    break;
                }
            }
        }

        return cnt == s.length();
    }

    public static void main(String[] args) {
        String s = "bb";
        String t = "ahbgdc";

//        System.out.println(s.equals(t));

        Solution392 solution392 = new Solution392();
        boolean subsequence = solution392.isSubsequence(s, t);
        System.out.println(subsequence);
    }
}
