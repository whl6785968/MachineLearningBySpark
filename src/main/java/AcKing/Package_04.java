package AcKing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Goods{
    int v;
    int w;

    public Goods(int v,int w){
        this.v = v;
        this.w = w;
    }
}

public class Package_04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[] f = new int[m+1];

        List<Goods> goods = new ArrayList<>();

        for(int i = 0;i < n;i++){
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            int s = scanner.nextInt();

            for(int k = 1;k <= s;k*=2){
                s -= k;
                goods.add(new Goods(v*k,w*k));
            }

            if(s > 0){
                goods.add(new Goods(v*s,w*s));
            }

        }

        for(Goods g : goods){
            for(int j = m;j >= g.v;j--){
                f[j] = Math.max(f[j],g.w + f[j-g.v]);
            }
        }

        System.out.println(f[m]);
    }
}
