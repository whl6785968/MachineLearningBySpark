package FactoryTst.AbastractFactory;

import FactoryTst.Banana;
import FactoryTst.Fruit;

public class FactoryForBanana implements Factory {
    @Override
    public Fruit getFruit() {
        return new Banana();
    }

    public static void main(String[] args) {
        new FactoryForApple().getFruit();
        new FactoryForBanana().getFruit();
    }
}
