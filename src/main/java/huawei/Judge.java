package huawei;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Judge {
    public static List<String>  load_file(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        List<String> list = new ArrayList<>();
        while ((line = reader.readLine()) != null){
            list.add(line);
        }

        return list;
    }

    public static void main(String[] args) throws IOException {
        String test_file = "D:\\huaweichussai\\m3.txt";
        String answer = "D:\\HWcode2020-TestData-master\\testData/result.txt";

//        String test_file = args[0];
//        String answer = args[1];

        List<String> test_result = load_file(test_file);
        List<String> answers = load_file(answer);

        int count = 0;
        boolean flag = false;
        for(int i = 0;i < test_result.size();i++){
            if(!test_result.get(i).equals(answers.get(i)) && !flag && i != 0){
                System.out.println(i);
                flag = true;
                count += 1;
            }
        }

        System.out.println(count);
    }
}
