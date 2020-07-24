package AcKing;

import java.util.Scanner;

public class Package_02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[] f = new int[m+1];


        for(int i = 0;i < n;i++){
            int v = scanner.nextInt();
            int w = scanner.nextInt();

            for(int j = v;j <= m;j++){
                f[j] = Math.max(f[j],w + f[j-v]);
            }
        }

        System.out.println(f[m]);
    }



}
