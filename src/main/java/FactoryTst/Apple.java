package FactoryTst;

public class Apple implements Fruit {

    public Apple(){
        System.out.println("I am apple");
    }

    @Override
    public void eat() {
        System.out.println("eat apple");
    }
}
