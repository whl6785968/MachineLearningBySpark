package FactoryTst.AbastractFactory;

import FactoryTst.Apple;
import FactoryTst.Fruit;

public class FactoryForApple implements Factory {
    @Override
    public Fruit getFruit() {
        return new Apple();
    }
}
