package Leetcode;

import java.util.ArrayList;
import java.util.List;

public class Solution17 {
    private String[] stringMap = {" ","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
    private List<String> result = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        findConmination(digits,0,"");
        return result;
    }


    //s保存了此时从digits[0,index-1]翻译得到的一个字母字符串
    private void findConmination(String digits,int index,String s){
        if(index == digits.length()){
            result.add(s);
            return;
        }

        char c = digits.charAt(index);
        String letters = stringMap[c-'0'];
        for(int i = 0;i < letters.length();i++){
            findConmination(digits,index+1,s+letters.charAt(i));
        }
    }

    public static void main(String[] args) {
        String a = "23";


        Solution17 solution17 = new Solution17();
        List<String> list = solution17.letterCombinations(a);
        System.out.println(list);
    }
}
