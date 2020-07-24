package concurrentProgramming;


import sun.misc.Unsafe;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

public class CustomizeLock {
    //状态：判断当前线程是否得到锁，共享变量，需要使用volatile保持线程间的可见性
    private volatile int state = 0;

    private Thread lockHolder;

    private ConcurrentLinkedQueue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(Thread lockHolder) {
        this.lockHolder = lockHolder;
    }

    public boolean acquire(){
        Thread current = Thread.currentThread();
        int c = getState();
        if(c == 0){
            if((waiters.size() == 0 || current == waiters.peek()) && compareAndSwapState(0,1)){
                setLockHolder(current);
                return true;
            }
        }

        return false;
    }

    public void lock(){
        if(acquire()){
            return;
        }

        Thread current = Thread.currentThread();
        waiters.add(current);

        for(;;){
            if((waiters.peek() == current) && acquire()){
                waiters.poll();
                break;
            }

            LockSupport.park(current);
        }
    }

    public void unlock(){
        if(Thread.currentThread() != lockHolder){
            throw new RuntimeException("");
        }

        int state = getState();

        if(compareAndSwapState(state,0)){
            setLockHolder(null);
            Thread first = waiters.peek();

            if(first != null){
                LockSupport.unpark(first);
            }
        }
    }

    //将当前对象的状态由except置为update，如果当前状态已经和update的值相等，返回false
    public final boolean compareAndSwapState(int except,int update){
        return unsafe.compareAndSwapInt(this,stateOffset,except,update);
    }

    private static final Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

    private static long stateOffset;
    static {
        try{
            stateOffset = unsafe.objectFieldOffset(CustomizeLock.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
