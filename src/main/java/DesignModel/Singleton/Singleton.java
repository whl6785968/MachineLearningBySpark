package DesignModel.Singleton;

//构造器私有化
//类内部创建对象
//向外暴露一个公共方法

public class Singleton {
    private static Singleton singleton = new Singleton();

    private Singleton(){
    }

    public static Singleton getInstance(){
        return singleton;
    }

}
