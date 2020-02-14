package ThreadTest;

public class Resource {
    private String name;
    private int count = 1;
    private boolean flag = false;

    public synchronized void set(String name){
        while (flag){
            try {
                wait();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        this.name = name + count;
        count++;
        System.out.println(Thread.currentThread().getName() + " produces " + this.name);
        flag = true;
        notifyAll();
    }

    public synchronized void out(){
        while (!flag){
            try {
                wait();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " consumes " + this.name);
        flag = false;
        notifyAll();
    }
}
