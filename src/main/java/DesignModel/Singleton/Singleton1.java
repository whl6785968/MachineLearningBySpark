package DesignModel.Singleton;

public class Singleton1 {
    private static volatile Singleton1 singleton1;

    private Singleton1(){

    }

    public static synchronized Singleton1 getInstance(){
        if(singleton1 == null){
            singleton1 = new Singleton1();
        }

        return singleton1;
    }

    public static Singleton1 getInstance2(){
        if(singleton1 == null){
            synchronized (Singleton1.class){
                singleton1 = new Singleton1();
            }
        }

        return singleton1;
    }

    public static Singleton1 getInstance3(){
        if(singleton1 == null){
                if(singleton1 == null){
                    singleton1 = new Singleton1();
                }

        }

        return singleton1;
    }


}
