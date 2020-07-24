package Interview;

import java.util.Scanner;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Main1 {
    public List<Integer> searchRepeatE(int[] array){
        Map<Integer,Integer> map = new HashMap();
        int target = -1;
        for(int i = 0;i < array.length;i++){
            if(map.get(array[i]) != null){
                if(map.get(array[i])+1 > 1){
                    target = array[i];
                    break;
                }
                map.put(array[i],map.get(array[i])+1);
            }
            else{
                map.put(array[i],1);
            }
        }

        List<Integer> list = new ArrayList();
        for(int i = 0;i < array.length;i++){
            if(array[i] == target){
                list.add(i);
            }
        }

        return list;
    }

    public static void main(String[] args) {
        int[] array = {1,3,5,5,7,8};
        Main1 m = new Main1();
        List<Integer> result = m.searchRepeatE(array);
        System.out.println(result.toString());
    }
}