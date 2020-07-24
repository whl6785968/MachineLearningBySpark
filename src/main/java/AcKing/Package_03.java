package AcKing;

import java.util.Scanner;

public class Package_03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[] f = new int[m+1];

        for(int i = 0;i < n;i++){
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            int s = scanner.nextInt();

            for(int j = m;j >= v;j--){
                for(int k = 0;k <= s;k++){
                    if(j >= k*v){
                        f[j] = Math.max(f[j],k*w+f[j-k*v]);
                    }
                }
            }
        }

        System.out.println(f[m]);
    }

    //    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        int m = scanner.nextInt();
//
//        int[] f = new int[m+1];
//
//        for(int i = 0;i < n;i++){
//            int v = scanner.nextInt();
//            int w = scanner.nextInt();
//            int s = scanner.nextInt();
//
//            int vs = s*v >= m ? m : s*v;
//
//            for(int j=v;j<=vs;j++){
//                f[j] = Math.max(f[j],w+f[j-v]);
//            }
//        }
//
//        System.out.println(f[m]);
//    }

}
