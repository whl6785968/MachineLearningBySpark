package ThreadTest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ThreadPoolTst {
    public static void main(String[] args) {
//        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

//        pool.execute(() -> System.out.println(Thread.currentThread().getName()));
//        pool.shutdown();



    }

    @Test
    public void tst(){
        long start = System.currentTimeMillis();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 200, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());

//        Future<String> future1 = executor.submit(this::test1);
//        Future<String> future2 = executor.submit(this::test2);
//        Future<String> future3 = executor.submit(this::test3);
//
//        List<Future<String>> futureList = Arrays.asList(future1, future2, future3);

        List<Future<String>> futureList = new ArrayList<>();

        for (int i = 0;i < 3 ;i++){
            futureList.add(executor.submit(this::test1));
        }

        List<String> list = futureList.stream().map(future -> {
            try {
                String result = future.get();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());

        System.out.println("cost:" + (System.currentTimeMillis()-start));

        System.out.println(list.toString());

    }

    private String test1(){
        try {
            Thread.sleep(100);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "test1";
    }

    private String test2(){
        try {
            Thread.sleep(100);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "test2";
    }

    private String test3(){
        try {
            Thread.sleep(100);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "test3";
    }

    @Test
    public void tst1() throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                System.out.println("This is ThreadPoolExetor#submit(Callable<T> task) method.");
                return "result";
            }
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(callable);
        System.out.println(future.get());
    }

}
