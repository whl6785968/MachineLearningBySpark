package GraphTheory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GraphUtils {
    public static Graph getGraph() throws IOException {
        String filename = "D:\\huaweichussai\\test_data_i.txt";
        DataProcessor dataProcessor = new DataProcessor(filename);
        Map<String, Object> map = dataProcessor.loadData();
        int E = (int) map.get("E");
        int V = (int) map.get("V");
        List<int[]> data = (List<int[]>) map.get("data");
        Graph graph = new Graph(V,E,data);
        return graph;
    }

    public static DiGraph getDiGraph() throws IOException {
        String filename = "F:\\test\\spark-test\\src\\main\\java\\GraphTheory\\tinyDG.txt";
        DataProcessor dataProcessor = new DataProcessor(filename);
        Map<String, Object> map = dataProcessor.loadData();
        int E = (int) map.get("E");
        int V = (int) map.get("V");
        List<int[]> data = (List<int[]>) map.get("data");
        DiGraph diGraph = new DiGraph(V, E, data);
        return diGraph;
    }

    public final static Map<String, Object> getDiGraphData(String filename) throws IOException {
        DataProcessor dataProcessor = new DataProcessor(filename);
        Map<String, Object> map = dataProcessor.load_huawei_data(filename);

        return map;
    }

    public static List<int[]> getData(String filename) throws IOException {
        DataProcessor dataProcessor = new DataProcessor(filename);
        Map<String, Object> map = dataProcessor.load_huawei_data(filename);

        List<int[]> data = (List<int[]>) map.get("data");
        return data;
    }
}
