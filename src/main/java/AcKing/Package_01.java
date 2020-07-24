package AcKing;

import java.util.Scanner;

public class Package_01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int V = scanner.nextInt();

        int[] f = new int[V+1];

        int[] v = new int[N];
        int[] w = new int[N];

        for(int i = 0;i < N;i++){
            v[i] = scanner.nextInt();
            w[i] = scanner.nextInt();
        }

        for(int j = 0;j <= V;j++){
            f[j] = j >= w[0] ? v[0] : 0;
        }

        for(int i = 1;i < N;i++){
            for(int j = V;j >= w[i];j--){
                f[j] = Math.max(f[j],v[i] + f[j-w[i]]);
            }
        }

        System.out.println(f[N]);


        //转移方程:
        //不放i，f(i) = f(i-1)
        //放i,f(i) =  f(i-1)(j-v[i])
    }
}
