package DynamicPlanning;

import Tool.Bag;

public class ShortestPath {

    public static int shortestPathSolution(int[][] a){
        Bag[] bag = new Bag[a.length];
        int[] edgeTo = new int[a.length+1];

        for(int m=0;m<a.length;m++){
            bag[m] = new Bag();
        }

        int[] n = new int[a.length];

        for (int i=a.length-2;i>=0;i--){
            int[] compare = new int[a.length];
            for(int j=0;j<a.length;j++){
                if(a[i][j]!=0){
                    compare[j] = a[i][j] + n[j];
                }
            }
            int min = 10000;
            int index = 0;
            for(int k=0;k<compare.length;k++){
                if(compare[k]!=0 && compare[k]<min){
                    min = compare[k];
                    index = k;
                }
            }
            edgeTo[i+1] = index+1;
            n[i] = min;
        }

        System.out.println(1);
        for(int i=1;edgeTo[i]!=0;){
            System.out.println(edgeTo[i]);
            i = edgeTo[i];
        }

        return n[0];
    }


    public static void main(String[] args) {
        int[][] a = {
                {0,2,5,1,0,0,0},
                {0,0,0,0,12,14,0},
                {0,0,0,0,6,4,0},
                {0,0,0,0,13,11,0},
                {0,0,0,0,0,0,5},
                {0,0,0,0,0,0,2},
                {0,0,0,0,0,0,0}
        };

        int i = shortestPathSolution(a);
        System.out.println(i);
    }

    public static class Node{
        public int number;
        public Node next;

        public Node(){

        }

        public Node(int Number){
            this.number = Number;
        }
    }
}
