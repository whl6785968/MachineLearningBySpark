package ThreadTest;

import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

public class LambdaTs {
    //左侧Lambda表达式的参数列表
    //右侧lambda表达式中所需执行的功能


    @Test
    public void tst1(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程启动了");
            }
        };

        runnable.run();
    }

    @Test
    public void tst2(){
        //格式一：无参数，无返回值
        //() -> Sout()
        Runnable runnable = () -> System.out.println("线程启动了2");
        runnable.run();
    }

    @Test
    public void tst3(){
        //格式二：有参数，有返回值
        //这里相当于重写了comparator的compare方法
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };

        int compare = com.compare(100,244);
        System.out.println(compare);
    }

    @Test
    public void tst4(){
        Consumer<String> consumer = (t) -> System.out.println("aaaa"+t);
        consumer.accept("bbbbb");
    }

    @Test
    public void tst5(){
        //方法引用与构造器引用
        //引用的方法必须和实现的抽象方法的参数列表及返回值保持一致
        Comparator<Integer> comparator = Integer::compare;
        System.out.println(comparator.compare(100,244));
    }
}
