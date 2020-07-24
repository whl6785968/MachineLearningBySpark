package ThreadTest;

import java.util.concurrent.Semaphore;

public class SemaphoreTst implements Runnable{
    private Semaphore semaphore;

    public SemaphoreTst(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println("thread" + Thread.currentThread().getName() + "working");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //一次允许5个线程使用资源
        Semaphore semaphore = new Semaphore(5);

        for(int i = 0;i < 19;i++){
            new Thread(new SemaphoreTst(semaphore)).start();
        }

        Thread t = new Thread(new SemaphoreTst(semaphore));
        t.start();
        t.join();
    }
}
