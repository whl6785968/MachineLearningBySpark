package ThreadTest;

public class OtherTst {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int size = 1000000;
        int[] array = new int[size];
        for (int i = 0;i<size;i++){
            array[i] = i;
        }

        int sum = 0;
        for(int i = 0;i < size;i++){
            sum += array[i];
        }

        System.out.println(String.format("result:%s cost:%s",sum,System.currentTimeMillis() - start));
    }
}
