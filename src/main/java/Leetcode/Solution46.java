package Leetcode;

public class Solution46 {
    public int translateNum(int num){
        String s = String.valueOf(num);

        int a = 1,b = 1;
        for(int i = 2;i <= s.length();i++){
            String tmp = s.substring(i-2,i);
            int c = tmp.compareTo("10") > 0 && tmp.compareTo("25") <= 0 ? a+b :a;
            b = a;
            a = c;
        }

        return a;
    }

    public int translateNum2(int num){
        int a = 1,b = 1,x,y = num % 10;
        while (num != 0){
            num /= 10;
            x = num % 10;
            int tmp = 10 * x + y;
            int c = (tmp >= 10 && tmp <= 25) ? a + b : a;
            b = a;
            a = c;
            y = x;

        }

        return a;
    }
}
