package FactoryTst;

public class Banana implements Fruit{
    public Banana(){
        System.out.println("I am banana");
    }


    @Override
    public void eat() {
        System.out.println("eat banana");
    }
}
