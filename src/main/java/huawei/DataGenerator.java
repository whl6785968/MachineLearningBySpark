package huawei;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataGenerator {
    private String filename;
    private int num_nodes;
    private int maxNode;

    public DataGenerator(String filename){
        this.filename = filename;
        this.num_nodes = 0;
    }

    public List<int[]> loadData() throws IOException {
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

            array[0] = source;
            array[1] = dest;
            array_list.add(array);
        }

        this.maxNode = ((TreeSet<Integer>) set).last();
        System.out.println(maxNode);
        this.num_nodes = set.size();
        return array_list;
    }

    public int getNum_nodes() {
        return num_nodes;
    }

    public void setNum_nodes(int num_nodes) {
        this.num_nodes = num_nodes;
    }

    public int getMaxNode() {
        return maxNode;
    }

    public void setMaxNode(int maxNode) {
        this.maxNode = maxNode;
    }
}
