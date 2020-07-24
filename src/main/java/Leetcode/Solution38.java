package Leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Solution38 {
    List<String> list = new ArrayList<>();
    char[] c;
    public String[] permutation(String s) {
        c = s.toCharArray();
        dfs(0);
        return list.toArray(new String[list.size()]);
    }

    void dfs(int x){
        if(x == c.length-1){
            list.add(String.valueOf(c));
            return;
        }

        HashSet<Character> set = new HashSet<>();
        for(int i = x;i < c.length;i++){
            if (set.contains(c[i])) continue;

            set.add(c[i]);
            swap(i,x);
            dfs(x+1);
            swap(i,x);
        }
    }

    void swap(int a,int b){
        char tmp = c[a];
        c[a] = c[b];
        c[b] = tmp;
    }

    public static void main(String[] args) {
        Solution38 solution38 = new Solution38();
        String s = "abc";
        String[] permutation = solution38.permutation(s);
        for(String subs : permutation){
            System.out.println(subs);
        }
    }
}
