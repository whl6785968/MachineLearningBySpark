package huawei;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class DiGraph {
    private final int V;
    private int E;
    private LinkedList<Integer>[] adj;
    private int[] inDegree;
    private int[] outDegree;


    public DiGraph(int V, int E, List<int[]> data){
        this.V = V;
        this.E = E;

        adj = (LinkedList<Integer>[])new LinkedList[V];
        inDegree = new int[V];
        outDegree = new int[V];
//        adj = new TreeSet[V];
        for(int i = 0;i < V;i++){
            //每个顶点的临边都是一个链表
//            adj[i] = new TreeSet<Integer>();
            adj[i] = new LinkedList<Integer>();
        }

        for(int i=0;i<data.size();i++){
            int v = data.get(i)[0];
            int w = data.get(i)[1];
            addEdge(v,w);
        }
    }

    public int indegree(int v){
        return inDegree[v];
    }

    public int outdegree(int v){
        return outDegree[v];
    }

    public void removeAllEdge(int v){
        E -= adj[v].size();
        adj[v] = new LinkedList<>();
    }

    public void addEdge(int v,int w){
        inDegree[v] += 1 ;
        outDegree[w] += 1;
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public int degree(DiGraph g,int v){
        int degree = 0;
        for(int d : g.adj(v)){
            degree++;
        }

        return degree;
    }

    public int maxDegree(DiGraph g) {
        int max = 0;
        for (int v = 0; v < g.V(); v++) {
            if (degree(g,v) > max) {
                max = degree(g,v);
            }
        }
        return max;
    }


}
