package OtherTst;

import edu.princeton.cs.algs4.Bag;

import java.util.List;

public class DiGraphByDiEdge {
    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;
    private int[] inDegree;
    private int[] outDegree;


    public DiGraphByDiEdge(int V, int E, List<int[]> data){
        this.V = V;
        this.E = E;

        adj = (Bag<DirectedEdge>[])new Bag[V];
        inDegree = new int[V];
        outDegree = new int[V];

        for(int i = 0;i < V;i++){
            adj[i] = new Bag<DirectedEdge>();
        }

        for(int i=0;i<data.size();i++){
            int v = data.get(i)[0];
            int w = data.get(i)[1];
            DirectedEdge directedEdge = new DirectedEdge(v, w);
            addEdge(directedEdge);
        }
    }

    public int inDegree(int v){
        return inDegree[v];
    }

    public int outDegree(int v){
        return outDegree[v];
    }

    public void addEdge(DirectedEdge edge){
        inDegree[edge.to()] += 1;
        outDegree[edge.from()] += 1;
        adj[edge.from()].add(edge);
        E++;
    }

    public void removeEdge(int v){
        E -= adj[v].size();
        adj[v] = new Bag<>();
    }

    public Iterable<DirectedEdge> adj(int v){
        return adj[v];
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }




}
