package MooC.SortAlgo;

import edu.princeton.cs.algs4.StdRandom;

public class Example {
    private static Comparable[] aux;

    public static void selectSort(Comparable[] a){
        for(int i = 0;i < a.length;i++){
            int min = i;
            for(int j=i+1;j < a.length;j++){
                if(less(a[j],a[min])){
                    min = j;
                }
            }

            exch(a,i,min);
        }
    }

    public static void insertSort(Comparable[] a){
        int N = a.length;
        for(int i = 1;i < N;i++){
            for(int j = i;j > 0 && less(a[j],a[j-1]);j--){
                exch(a,j,j-1);
            }
        }
    }

    public static void hillSort(Comparable[] a){
        int N = a.length;
        int h = 1;

        while(h < N/3){
            h = 3*h + 1;
        }

        while (h >= 1){
            for(int i=h;i<N;i++){
                for(int j=i;j>=h && less(a[j],a[j-h]);j=j-h){
                    exch(a,j,j-h);
                }
            }

            h = h / 3;
        }
    }

    public static void merge(Comparable[] a,int lo,int mid,int hi){
        int i= lo,j = mid+1;

        for(int k=lo;k<=hi;k++){
            aux[k] = a[k];
        }

        for(int k=lo;k<=hi;k++){
            //如果左半边元素取尽，取右半边
            if(i > mid) a[k] = aux[j++];
            //如果右半边取尽，取左半边
            else if(j > hi) a[k] = aux[i++];
            //如果右半边元素小于左半边，取右半边
            else if(less(aux[j],aux[i])) a[k] = aux[j++];
            //如果左半边元素小于右半边，取左半边
            else a[k] = aux[i++];
        }
    }

    public static void mergeSort(Comparable[] a){
        aux = new Comparable[a.length];
        mergeSort(a,0,a.length-1);
    }

    public static void mergeSort(Comparable[] a,int lo,int hi){
        if(hi <= lo) return;

        int mid = lo + (hi - lo) / 2;
        mergeSort(a,lo,mid);
        mergeSort(a,mid+1,hi);
        merge(a,lo,mid,hi);
    }

    public static int partition(Comparable[] a,int lo,int hi){
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];

        while (true){
            //从左到右扫描比切分元素v大的
            //即如果a[i]不小于v，则跳出循环
            while (less(a[++i],v)){
                if(i == hi) break;
            }

            //从右到左扫描比切分元素v小的

            while (less(v,a[--j])){
                if(j == lo) break;
            }

            if(i >= j) break;
            exch(a,i,j);
        }

        exch(a,lo,j);

        return j;
    }

    public static void quickSort(Comparable[] a){
        StdRandom.shuffle(a);
        quickSort(a,0,a.length-1);
    }

    public static void quickSort(Comparable[] a,int lo,int hi){
        if(hi <= lo) return;
        int j = partition(a,lo,hi);

        quickSort(a,lo,j-1);
        quickSort(a,j+1,hi);
    }

    private static boolean less(Comparable v,Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a,int i,int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a){
        for(int i = 0; i < a.length;i++){
            System.out.println(a[i] + " ");
        }
    }

    private static void sink(Comparable[] a,int k,int N){
        while (2*k <= N){
            int j = 2*k;

            if(j < N && less(a[j],a[j+1])) j++;

            if(less(a[k],a[j])) break;

            exch(a,k,j);

            k = j;
        }
    }

    public static void heapSort(Comparable[] a){
        int N = a.length;

        //堆有序
        for(int k=N/2;k>=1;k--){
            sink(a,k,N);
        }

        while (N > 1){
            exch(a,1,N--);
            sink(a,1,N);
        }
    }

    public static void main(String[] args) {
        Comparable[] a = {3,5,2,7,1,8};
//        selectSort(a);
//        insertSort(a);
//        hillSort(a);
//        mergeSort(a);
//        quickSort(a);
        heapSort(a);
        show(a);

    }
}
