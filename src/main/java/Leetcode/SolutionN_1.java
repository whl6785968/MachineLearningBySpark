package Leetcode;

public class SolutionN_1 {
    public int getLastMoment(int n, int[] left, int[] right) {
        if (n == 0) {
            return 0;
        }

        if (left.length == 0 && right.length != 0 || left.length != 0 && right.length == 0) {
            return n;
        }

        int index = left.length - 1;

        if(index == 0){
            while (right[0] != left[0]){
                left[0] -= 1;
                right[0] += 1;
            }
        }
        while (index > 0) {
            if (right[index] == left[index]) {
                index--;
            }

            for (int i = 0; i < left.length; i++) {
                if (left[i] != right[i]) {
                    left[i] -= 1;
                    right[i] += 1;
                }
            }

        }


        int[] tmp = right;
        right = left;
        left = tmp;

        if(left[0] == right[0]){
            return n;
        }
        else{
            if(left[0] + 1 > n - right[0]){
                return left[0] + 1;
            }
            else{
                return n - right[0];
            }
        }
    }

    public static void main(String[] args) {
        int n = 9;
        int[] left = {5};
        int[] right = {4};

        SolutionN_1 solutionN_1 = new SolutionN_1();
        int lastMoment = solutionN_1.getLastMoment(n, left, right);
        System.out.println(lastMoment);
    }

}
