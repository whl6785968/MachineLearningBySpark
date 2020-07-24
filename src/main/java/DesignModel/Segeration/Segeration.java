package DesignModel.Segeration;

public class Segeration {
    public static void main(String[] args) {
        A a = new A();
        a.depend1(new B());
        a.depend2(new B());
        a.depend3(new B());

        C c = new C();
        c.depend1(new D());
        c.depend4(new D());
        c.depend5(new D());
    }
}


interface interface1 {
    void op1();
}

interface  interface2{
    void op2();
    void op3();
}

interface interface3{
    void op4();
    void op5();
}

class B implements interface1,interface2{

    @Override
    public void op1() {
        System.out.println("B op1");
    }

    @Override
    public void op2() {
        System.out.println("B op2");
    }

    @Override
    public void op3() {
        System.out.println("B op3");
    }
}

class D implements interface1,interface3{

    @Override
    public void op1() {
        System.out.println("D op1");
    }

    @Override
    public void op4() {
        System.out.println("D op4");
    }

    @Override
    public void op5() {
        System.out.println("D op5");
    }
}

class A {
    public void depend1(interface1 i1){
        i1.op1();
    }

    public void depend2(interface2 i2){
        i2.op2();
    }

    public void depend3(interface2 i2){
        i2.op3();
    }
}

class C {
    public void depend1(interface1 i1){
        i1.op1();
    }

    public void depend4(interface3 i4){
        i4.op4();
    }

    public void depend5(interface3 i5){
        i5.op5();
    }
}