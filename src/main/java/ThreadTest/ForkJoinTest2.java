package ThreadTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest2 extends RecursiveTask<Integer> {
    private static final int threshould = 5000;
    private int[] array;
    private int low;
    private int high;

    ForkJoinTest2(int[] array,int low,int high){
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if(high - low <= threshould){
            for (int i = 0;i < high;i++){
                sum += array[i];
            }
        }
        else {
            int mid = (high+low) >>> 1;
            ForkJoinTest2  left = new ForkJoinTest2(array,low,mid);
            ForkJoinTest2 right = new ForkJoinTest2(array,mid+1,high);
            left.fork();
            right.fork();

            sum = left.join() + right.join();

        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int size = 1000000;
        int[] array = new int[size];
        for (int i = 0;i<size;i++){
            array[i] = i;
        }

//        for (int i : array){
//            System.out.println(i);
//        }
        ForkJoinTest2 forkJoinTest2 = new ForkJoinTest2(array,0,array.length-1);
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(forkJoinTest2);
        Integer result = forkJoinTest2.get();

        System.out.println(String.format("result:%s cost:%s",result,System.currentTimeMillis() - start));
    }
}
