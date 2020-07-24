package FactoryTst;

public class Factory {
    private Factory(){

    }

    public static Fruit eatApple(){
        return new Apple();
    }

    public static Fruit eatBanana(){
        return new Banana();
    }

    public static void main(String[] args) {
        Factory.eatApple().eat();
        Factory.eatBanana().eat();
    }
}
