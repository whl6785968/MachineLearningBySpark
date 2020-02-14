package ThreadTest;

import edu.princeton.cs.algs4.ST;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTst {
    @Test
    public void tst1(){
        Stream.of("apple","banana","orange").map(e -> e.length()).forEach(e -> System.out.println(e));
    }

    @Test
    public void tst2(){
        Stream.of("a-b-c","dd-ee-ff").flatMap(e -> Stream.of(e.split("-"))).forEach(e -> System.out.println(e));
    }

    @Test
    public void tst3(){
        Stream.of(1,2,3,4).limit(3).forEach(e -> System.out.println(e));
    }

    @Test
    public void tst4(){
        Stream.of(1,2,3,4).filter(e -> e>2).forEach(e -> System.out.println(e));
    }

    @Test
    public void tst5(){
        User w = new User("w",10);
        User x = new User("x",11);
        User y = new User("y",12);
        Stream.of(w,x,y).peek(e -> {e.setName(e.getName() + e.getAge());}).forEach(e -> System.out.println(e));
    }

    static class User{
        private String name;
        private int age;

        public User(String name,int age){
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return name + age;
        }
    }

    @Test
    public void tst6(){
        //collect将数据流收集到List,set,Map等容器中
        Stream.of("apple", "banana", "orange", "waltermaleon", "grape")
                .collect(Collectors.toSet()) //set 容器
                .forEach(e -> System.out.println(e));
    }

    @Test
    public void tst7() {
        //collect将数据流收集到List,set,Map等容器中
        Stream.of("apple", "banana", "orange", "waltermaleon", "grape")
                .count();
    }

    @Test
    public void tst8(){
        Stream.of(1,2,5,7,1,9).min((e1,e2) -> e1.compareTo(e2)).ifPresent(e -> System.out.println(e));
    }

    @Test
    public void tst9(){
        Integer sum = Stream.of(1, 2, 5, 7, 1, 9).reduce(0, (e1, e2) -> e1 + e2);
        System.out.println(sum);
    }

    @Test
    public void tst10(){
        long start = System.currentTimeMillis();
        //并行输出数字
        Stream.of(1, 2, 5, 7, 1, 9).parallel().forEachOrdered(e -> {
            System.out.println(Thread.currentThread().getName()+":"+e);
        });

        System.out.println("cost:"+(System.currentTimeMillis() - start));
    }

    @Test
    public void tst11(){
        long start = System.currentTimeMillis();
        int[] array = {1,2,5,7,1,9};
        for(int i : array){
            System.out.println(i);
        }

        System.out.println("cost:"+(System.currentTimeMillis() - start));
    }

}
