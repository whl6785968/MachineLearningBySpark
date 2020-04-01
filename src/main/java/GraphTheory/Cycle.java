package GraphTheory;

import java.io.IOException;

public class Cycle {
    private boolean[] marked;
    private boolean hasCycle;

    public Cycle(Graph g){
        marked = new boolean[g.V()];
        for(int s=0;s<g.V();s++){
            if(!marked[s]){
                dfs(g,s,s);
            }
        }
    }

    public void dfs(Graph g,int v,int u){
        marked[v] = true;
        for(int w:g.adj(v)){
            if(!marked[w]){
                dfs(g,w,v);
            }
            else if(w != u) hasCycle = true;
        }
    }

    public boolean hasCycle(){
        return hasCycle;
    }

    public static void main(String[] args) throws IOException {
        Graph graph = GraphUtils.getGraph();
        Cycle cycle = new Cycle(graph);
        boolean b = cycle.hasCycle();
        System.out.println(b);
    }
}
