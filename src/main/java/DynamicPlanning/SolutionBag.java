package DynamicPlanning;

import java.util.HashMap;
import java.util.Map;

public class SolutionBag {

    int[] value = {8,5,12};
    int[] w = {3,2,5};

    public int getMostValue(int n,int rest){
        int currValue = value[n];
        int currW = w[n];

        int up = (int) Math.floor(rest/currW);


        if(n == 0){
            int f1 = currValue*up;
            return f1;
        }

        int[] candidateMax = new int[up+1];

        int max = 0;
        for (int i=0;i<=up;i++){
            candidateMax[i] = currValue*i+getMostValue(n-1,rest-currW*i);
        }

        for (int j=0;j<candidateMax.length;j++){
            if(candidateMax[j]>max){
                max = candidateMax[j];
            }
        }


        return max;
    }

    public static void main(String[] args) {
        SolutionBag solutionBag = new SolutionBag();
        int mostValue = solutionBag.getMostValue(2, 5);
        System.out.println(mostValue);
    }
}
