package Leetcode;

public class Solution43 {
//    public int countDigitOne(int n) {
//        int digit = 1,res = 0;
//        int high = n / 10,cur = n % 10,low = 0;
//
//        while (high != 0 || cur != 0){
//            if(cur == 0) res += high * digit;
//            else if(cur == 1) res += high * digit + low + 1;
//            else res += (high + 1) *  digit;
//            low += cur * digit;
//            cur = high % 10;
//            high /= 10;
//            digit *= 10;
//        }
//
//        return res;
//    }

    public int countDigitOne(int n) {
        return f(n);
    }

    //如果high位为1，只看high位，即为pow
    //如果high位不为1，看低位，即f(pow-1)
    private int f(int n ) {
        if (n <= 0)
            return 0;
        String s = String.valueOf(n);
        int high = s.charAt(0) - '0';
        int pow = (int) Math.pow(10, s.length()-1);
        int last = n - high*pow;
        if (high == 1) {
            return f(pow-1) + last + 1 + f(last);
        } else {
            return pow + high*f(pow-1) + f(last);
        }
    }

    public static void main(String[] args) {
        Solution43 solution43 = new Solution43();
//        int n = 3234;
//        String s = String.valueOf(n);
//        int h = s.charAt(0) - '0';
//        System.out.println(h);
        int i = solution43.countDigitOne(3234);
//        System.out.println(i);
    }
}
