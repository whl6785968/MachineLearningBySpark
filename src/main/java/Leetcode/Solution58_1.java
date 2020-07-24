package Leetcode;

public class Solution58_1 {
    public String reverseWords(String s) {
        s = s.trim();
        s = s.replaceAll("\\s+"," ");
        String[] strings = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i=strings.length-1;i > 0;i--){
            sb.append(strings[i] + " ");
        }

        sb.append(strings[0]);

        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "  hello world!  ";
        Solution58_1 solution58_1 = new Solution58_1();
        String s1 = solution58_1.reverseWords(s);
        System.out.println(s1);

    }
}
