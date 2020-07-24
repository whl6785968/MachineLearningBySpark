package OtherTst;

import huawei.GraphUtils;

import java.io.IOException;
import java.util.*;

public class Topological {

    public Topological(DiGraphByDiEdge g,TreeSet<Integer> data){

    }

    public List<Integer> order(DiGraphByDiEdge g,TreeSet<Integer> data){
        ArrayList<Integer> list = new ArrayList<>(data);
        System.out.println(list.size());
        Iterator<Integer> it = list.iterator();


        while (it.hasNext()){
            int source = it.next();
            if(g.inDegree(source) == 0 || g.outDegree(source) == 0){
                g.removeEdge(source);
                it.remove();
            }

        }
        System.out.println(list.size());
        return list;
    }
    
    public static void main(String[] args) throws IOException {
        String filename = "D:\\huaweichussai\\test_data_i.txt";
        Map<String, Object> map = GraphUtils.getDiGraphData(filename);

        int E = (int) map.get("E");
        int V = (int) map.get("V")+1;
        List<int[]> data = (List<int[]>) map.get("data");
        TreeSet<Integer> sorted_data = (TreeSet<Integer>) map.get("sorted_data");

        DiGraphByDiEdge diGraphByDiEdge = new DiGraphByDiEdge(V, E, data);

        Topological topological = new Topological(diGraphByDiEdge, sorted_data);
        List<Integer> order = topological.order(diGraphByDiEdge, sorted_data);

//        int i = diGraphByDiEdge.inDegree(6002);
//        System.out.println(i);


    }
}
