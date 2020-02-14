package ThreadTest;

public class ThredDemo {
    public static void main(String[] args) {
        Resource resource = new Resource();
        Producer producer = new Producer(resource);
        Consumer consumer = new Consumer(resource);

        Producer producer1 = new Producer(resource);
        Producer producer2 = new Producer(resource);

        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        Thread t3 = new Thread(producer1);
        Thread t4 = new Thread(producer2);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }

}
