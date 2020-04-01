package huawei;

import java.io.IOException;
import java.util.List;

public class DiGraph {
    private final int V;
    private int E;
    private int max_node;
    private Bag<DirectedEdge>[] adj;

    public DiGraph(int V,int max_node){
        this.V = V;
        this.E = 0;
        this.max_node = max_node;
        adj = (Bag<DirectedEdge>[]) new Bag[max_node];

        for(int i = 0;i < max_node;i++){
            adj[i] = new Bag<DirectedEdge>();
        }
    }

    public DiGraph(List<int[]> data,int num_nodes,int max_node){
        this(num_nodes,max_node);
        this.E = data.size();
        for(int[] array : data){
            int source = array[0];
            int dest = array[1];
            DirectedEdge directedEdge = new DirectedEdge(source, dest);
            addEdge(directedEdge);
        }
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public int getMaxNode(){
        return max_node;
    }

    public void addEdge(DirectedEdge edge){
        adj[edge.from()].add(edge);
        E++;
    }

    public Bag<DirectedEdge> adj(int v){
        return adj[v];
    }

//    public DiGraph reverse(){
//        DiGraph diGraph = new DiGraph(V);
//        for(int i=0;i<V;i++){
//            for(int w:adj[i]){
//                diGraph.addEdge(w,i);
//            }
//        }
//
//        return diGraph;
//    }



}
