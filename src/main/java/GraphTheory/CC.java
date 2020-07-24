package GraphTheory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class CC {
    private boolean[] marked;
    private int[] id;
    private int count;

    public CC(DiGraph g){
        marked = new boolean[g.V()];
        id = new int[g.V()];
        for(int s=0;s<g.V();s++){
            if(!marked[s]){
                count++;
                dfs(g,s);
            }
        }
    }

    public void dfs(DiGraph g,int v){
        marked[v] = true;
        id[v] = count;
        for(int w:g.adj(v)){
            if(!marked[w]){
                dfs(g,w);
            }
        }
    }

    public boolean connected(int v,int w){
        return id[v] == id[w];
    }

    public int id(int v){
        return id[v];
    }

    public List<Integer>[] components(DiGraph g){

        ArrayList<Integer>[] res = new ArrayList[count];
        for(int i = 0; i < count; i ++)
            res[i] = new ArrayList<>();

        for(int v = 0; v < g.V(); v++)
            res[id[v]].add(v);
        return res;
    }

    public static void main(String[] args) throws IOException {
        String filename = "D:\\huaweichussai\\test_data1.txt";
        Map<String, Object> map = GraphUtils.getDiGraphData(filename);
        int E = (int) map.get("E");
        int V = (int) map.get("V")+1;
        List<int[]> data = (List<int[]>) map.get("data");

        DiGraph diGraph = new DiGraph(V, E, data);
        CC cc = new CC(diGraph);
        boolean connected = cc.connected(6001, 6005);
        System.out.println(connected);

        System.out.println(cc.count);
    }
}
