package GraphTheory;

//import huawei.GraphUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tst {
    public void dfs(){

    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String filename = "D:\\huaweichussai\\test_data_1.txt";
        Map<String, Object> map = GraphUtils.getDiGraphData(filename);

        int E = (int) map.get("E");
        int V = (int) map.get("V")+1;
        List<int[]> data = (List<int[]>) map.get("data");

        DiGraph diGraph = new DiGraph(V, E, data);
//        DiGraph diGraph = GraphUtils.getDiGraph();


        KosarajuSCC kosarajuSCC = new KosarajuSCC(diGraph);
//        boolean b = kosarajuSCC.stronglyConnected(6005, 158);
//        System.out.println(b);


        List<Integer>[] components = kosarajuSCC.components(diGraph);
        List<List<Integer>> stronglyConnections = new ArrayList<>();
        int count = 0;
        for(List<Integer> i : components){
            if(i.size() > 2){
                System.out.println(i.toString());
                count += 1;
                stronglyConnections.add(i);
            }
        }





        System.out.println(count);



//        TransitiveClosure transitiveClosure = new TransitiveClosure(diGraph);
//        boolean reachable = transitiveClosure.reachable(6001, 158);
//        System.out.println(reachable);

        System.out.println("cost:"+(System.currentTimeMillis()-start));
    }
}
