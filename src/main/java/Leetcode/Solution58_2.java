package Leetcode;

public class Solution58_2 {
    public String reverseLeftWords(String s, int n) {
        String head = s.substring(0,n);
        String tail = s.substring(n,s.length());

        return tail + head;
    }

    public static void main(String[] args) {
        String s = "abcdefg";

        Solution58_2 solution58_2 = new Solution58_2();
        String s1 = solution58_2.reverseLeftWords(s, 2);
        System.out.println(s1);
    }
}
