package huawei;

import java.io.BufferedReader;
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

        reader.close();
        map.put("V",V);
        map.put("E",E);
        map.put("data",array_list);

        return map;
    }

    public Map<String,Object> load_data(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        Set<Integer> set = new TreeSet<>();
        List<int[]> array_list = new ArrayList<>();

        while ((line = reader.readLine()) != null){
            int[] array = new int[2];
            String[] strings = line.split(",");
            int source = Integer.parseInt(strings[0]);
            int dest = Integer.parseInt(strings[1]);
            set.add(source);
            set.add(dest);
            array[0] = source;
            array[1] = dest;
            array_list.add(array);
        }

        List<Integer> listSet = new ArrayList<>(set);
        Map<Integer,Integer> dict = new HashMap<>();
        Map<Integer,Integer> reverse_dict = new HashMap<>();
        TreeSet<Integer> sorted_data = new TreeSet<>();
        for(int i = 0;i < listSet.size();i++){
            dict.put(i,listSet.get(i));
            reverse_dict.put(listSet.get(i),i);
            sorted_data.add(i);
        }

       array_list.parallelStream().forEach(data -> {
           data[0] = reverse_dict.get(data[0]);
           data[1] = reverse_dict.get(data[1]);
       });

        int V = sorted_data.last();
        int E = array_list.size();

        Map<String,Object> result = new HashMap<>();
        result.put("V",V);
        result.put("E",E);
        result.put("data",array_list);
        result.put("sorted_data",sorted_data);
        result.put("dict",dict);

        return result;
    }

    public Map<String,Object> load_huawei_data(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        Map<String,Object> map = new HashMap<>();
        List<int[]> array_list = new ArrayList<>();
        Set<Integer> set = new TreeSet<>();
        Set<Integer> sources = new TreeSet<>();
//        List<Integer> noramlSources = new ArrayList<>();

        while ((line = reader.readLine()) != null){
            int[] array = new int[2];
            String[] strings = line.split(",");
            int source = Integer.parseInt(strings[0]);
            int dest = Integer.parseInt(strings[1]);

            set.add(source);
            set.add(dest);

            sources.add(source);
//            noramlSources.add(source);

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
//        map.put("normal_data",noramlSources);

        return map;
    }
}
