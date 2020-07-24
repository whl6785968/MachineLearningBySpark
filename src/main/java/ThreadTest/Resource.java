package ThreadTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {
    private String name;
    private int count = 1;
    private boolean flag = false;
    ReentrantLock lock = new ReentrantLock();
    Condition producer_con = lock.newCondition();
    Condition consumer_con = lock.newCondition();

    public void set(String name){
        lock.lock();
        while (flag){
            try {
                producer_con.await();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        this.name = name + count;
        count++;
        System.out.println(Thread.currentThread().getName() + " produces " + this.name);
        flag = true;
//        notifyAll();
        consumer_con.signal();
        lock.unlock();
    }

    public void out(){
        lock.lock();
        while (!flag){
            try {
//                wait();
                consumer_con.await();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " consumes " + this.name);
        flag = false;

        producer_con.signal();
        lock.unlock();
//        notifyAll();
    }
}
