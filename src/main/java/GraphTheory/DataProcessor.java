package GraphTheory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataProcessor {
    private String filename;

    public DataProcessor(String filename){
        this.filename = filename;
    }

    public Map<String,Object> loadData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        Map<String,Object> map = new HashMap<>();
        List<int[]> array_list = new ArrayList<>();
        int V = Integer.parseInt(reader.readLine());
        int E = Integer.parseInt(reader.readLine());
        while ((line = reader.readLine()) != null){
            int[] array = new int[2];
            String[] strings = line.split(" ");
            int source = Integer.parseInt(strings[0]);
            int dest = Integer.parseInt(strings[1]);

            array[0] = source;
            array[1] = dest;
            array_list.add(array);
        }

        map.put("V",V);
        map.put("E",E);
        map.put("data",array_list);

        return map;
    }

    public final Map<String,Object> load_huawei_data(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        Map<String,Object> map = new HashMap<>();
        List<int[]> array_list = new ArrayList<>();
        Set<Integer> set = new TreeSet<>();
        Set<Integer> sources = new TreeSet<>();

        while ((line = reader.readLine()) != null){
            int[] array = new int[2];
            String[] strings = line.split(",");
            int source = Integer.parseInt(strings[0]);
            int dest = Integer.parseInt(strings[1]);

            set.add(source);
            set.add(dest);

            sources.add(source);

            array[0] = source;
            array[1] = dest;
            array_list.add(array);
        }

        int V = ((TreeSet<Integer>) set).last();
        int E = array_list.size();

        map.put("V",V);
        map.put("E",E);
        map.put("data",array_list);
        map.put("sorted_data",sources);

        return map;
    }
}
