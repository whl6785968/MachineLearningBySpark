package DynamicPlanning;

public class Dp {
    public static int solutionFab(int n){
        if(n == 0){
            return 0;
        }
        else if(n == 1){
            return 1;
        }else {
            int[] result = new int[n+1];
            result[0] = 0;
            result[1] = 1;
            for(int i=2;i<=n;i++){
                result[i] = result[i-1]+result[i-2];
            }


            return result[n];
        }

    }

    public static int maxChildOrder(int[] a){
        int n = a.length;
        int[] temp = new int[n];

        for(int i=0;i<n;i++){
            temp[i] = 1;
        }

        for(int i=1;i<n;i++){
            for (int j=0;j<i;j++){
                if(a[i]>a[j] && temp[j]+1>temp[i]){
                    temp[i] = temp[j] + 1;
                }
            }
        }

        int max = 0;
        for(int i=0;i<n;i++){
//            System.out.println(temp[i]);
            if(temp[i]>max){
                max = temp[i];
            }
        }

        return max;
    }

    public static int maxContinueSum(int[] a){
        int max = a[0];
        int sum = a[0];
        int n = a.length;

        for(int i=1;i<n;i++){
            sum = Math.max(sum+a[i],a[i]);
            if(sum>=max){
                max = sum;
            }
        }

        return max;
    }

    public static int minNumberInRotateArray(int n[][]){
        int max = 0;
        int dp[][] = new int[n.length][n.length];
        dp[0][0] = n[0][0];

        for(int i=1;i<n.length;i++){
            for(int j=0;j<=i;j++){
                if(j==0){
                    dp[i][j] = dp[i-1][j] + n[i][j];
                }
                else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i-1][j-1]) + n[i][j];
                }

                max = Math.max(dp[i][j],max);
            }
        }

        return max;
    }

    public static void main(String[] args) {
//        int i = solutionFab(5);
//        System.out.println(i);

//        int[] a = {3,1,4,1,5,9,2,6,5};
//        int result = maxChildOrder(a);

        int[] a = {6,-1,3,-4,-6,9,2,-2,5};
        int result = maxContinueSum(a);

        int[][] a1 = {
                {3},
                {1,5},
                {8,4,3},
                {2,6,7,9},
                {6,2,3,5,1}
        };

        int r1 = minNumberInRotateArray(a1);
        System.out.println(r1);
    }
}
