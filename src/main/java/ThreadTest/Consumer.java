package ThreadTest;

public class Consumer implements Runnable {
    Resource r;
    Consumer(Resource r){
        this.r = r;
    }

    @Override
    public void run() {
        while (true){
            r.out();
        }
    }
}
