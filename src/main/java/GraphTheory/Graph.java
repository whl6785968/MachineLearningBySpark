package GraphTheory;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.List;

public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Graph(int V, int E, List<int[]> data){
        this.V = V;
        this.E = E;


        adj = (Bag<Integer>[])new Bag[V];

        for(int i = 0;i < V;i++){
            //每个顶点的临边都是一个链表
            adj[i] = new Bag<Integer>();
        }

        for(int i=0;i<data.size();i++){
            int v = data.get(i)[0];
            int w = data.get(i)[1];
            addEdge(v,w);
        }
    }

//    public Graph(In in){
//        this(in.readInt());
//        this.E = in.readInt();
//        for(int i = 0;i < E;i++){
//            int v = in.readInt();
//            int w = in.readInt();
//            addEdge(v,w);
//        }
//    }

    public void addEdge(int v,int w){
        adj[v].add(w);
        adj[w].add(v);
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

    public int degree(Graph g,int v){
        int degree = 0;
        for(int d : g.adj(v)){
            degree++;
        }

        return degree;
    }

    public int maxDegree(Graph g) {
        int max = 0;
        for (int v = 0; v < g.V(); v++) {
            if (degree(g,v) > max) {
                max = degree(g,v);
            }
        }
        return max;
    }


}
