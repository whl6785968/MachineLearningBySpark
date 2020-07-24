package Interview;

import MooC.chapter2.Array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main2 {
    public void getMaxValue(int number, int N) {
        String s = Integer.toString(number);
        int[] numbers = new int[s.length()];
        Map<Integer,Integer> eleIndex = new HashMap<>();

        for(int i = 0;i < s.length();i++){
            numbers[i] = Integer.parseInt(Character.toString(s.charAt(i)));
            eleIndex.put(numbers[i],i);
        }

        Arrays.sort(numbers);

        int[] result = new int[numbers.length-N];
        int r_index = 0;
        int cur = -1;

        for(int i = numbers.length-1; i > 0 ;i--){
            Integer index = eleIndex.get(numbers[i]);

            if(i == numbers.length-1 && index < N + 1){
                result[r_index] = numbers[i];
                r_index += 1;
                cur = index;
            }
            else if(index > cur){
                result[r_index] = numbers[i];
                r_index += 1;
                cur = index;
            }

        }

        System.out.println(result);

    }

    public static void main(String[] args) {
        Main2 main2 = new Main2();
//        main2.getMaxValue(3975720,2);

    }
}
