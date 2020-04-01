package GraphTheory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CC {
    private boolean[] marked;
    private int[] id;
    private int count;

    public CC(Graph g){
        marked = new boolean[g.V()];
        id = new int[g.V()];
        for(int s=0;s<g.V();s++){
            if(!marked[s]){
                count++;
                dfs(g,s);
            }
        }
    }

    public void dfs(Graph g,int v){
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

    public static void main(String[] args) throws IOException {
        Graph graph = GraphUtils.getGraph();
        CC cc = new CC(graph);
        boolean connected = cc.connected(0, 6);
        System.out.println(connected);
    }
}
