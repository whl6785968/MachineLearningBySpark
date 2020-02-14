package ThreadTest;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest extends RecursiveTask<Integer> {
    private static final int threshould = 500000;
    private long[] array;
    private int low;
    private int high;

    ForkJoinTest(long[] array,int low,int high){
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if((high - low) <= threshould){
            for (int i = 0;i < high; i++){
                sum += array[i];
            }
        }
        else {
            int mid = (low + high) >>> 1;
            ForkJoinTest left = new ForkJoinTest(array,low,mid);
            ForkJoinTest right = new ForkJoinTest(array,mid,high);
            left.fork();
            right.fork();

            sum = left.join() + right.join();

        }
        return sum;
    }

    private void serCompute(){
        long start = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0;i < array.length;i++){
            sum += array[i];
        }
        System.out.println(String.format("result:%s cost:%s",sum,System.currentTimeMillis()-start));
    }

    private static long[] genArray(int size){
        long[] array = new long[size];
        for(int i = 0;i<size;i++){
            array[i] = new Random().nextLong();
        }

        return array;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] array = genArray(10);
        ForkJoinTest forkJoinTest = new ForkJoinTest(array, 0, array.length - 1);
        long begin = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(forkJoinTest);
        Integer result = forkJoinTest.get();
        long end = System.currentTimeMillis();
        System.out.println(String.format("result is %s task cost %s",result,end-begin));

        forkJoinTest.serCompute();

    }
}
