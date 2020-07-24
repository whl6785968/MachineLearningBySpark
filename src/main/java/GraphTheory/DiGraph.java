package GraphTheory;

import edu.princeton.cs.algs4.Bag;


import edu.princeton.cs.algs4.In;

import java.util.*;

public class DiGraph {
    private final int V;
    private int E;
    private LinkedList<Integer>[] adj;
    private int[] inDegree;
    private int[] outDegree;
    private List<Map<Integer,Set<Integer>>> p;

    public DiGraph(int V, int E, List<int[]> data){
        this.V = V;
        this.E = E;

        p = new ArrayList<>();
        adj = (LinkedList<Integer>[])new LinkedList[V];
        inDegree = new int[V];
        outDegree = new int[V];

        for(int i = 0;i < V;i++){
            //每个顶点的临边都是一个链表
            adj[i] = new LinkedList<Integer>();
            p.add(new HashMap<>());
        }

        for(int i=0;i<data.size();i++){
            int v = data.get(i)[0];
            int w = data.get(i)[1];
            addEdge(v,w);
        }

        construct();
    }

    public DiGraph(int V){
        this.V = V;
        this.E = 0;
        this.adj = (LinkedList[])(new LinkedList[V]);
        inDegree = new int[V];
        outDegree = new int[V];

        for(int v = 0; v < V; ++v) {
            this.adj[v] = new LinkedList();
        }
    }

    public void construct(){
        for(int i=0;i<this.V();i++){
            for(int k:this.adj(i)){
                for(int j:this.adj(k)){
                    if(k > j){
                        Set<Integer> set = p.get(j).get(i);
                        if(set != null){
                            set.add(k);
                        }
                        else {
                            set = new TreeSet<>();
                            set.add(k);
                        }
                        p.get(j).put(i,set);
                    }
                }
            }
        }
    }

    public List<Map<Integer,Set<Integer>>> getP(){
        return p;
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

    public DiGraph reverse() {
        DiGraph reverse = new DiGraph(this.V);

        for(int v = 0; v < this.V; ++v) {
            Iterator i$ = this.adj(v).iterator();

            while(i$.hasNext()) {
                int w = (Integer)i$.next();
                reverse.addEdge(w, v);
            }
        }

        return reverse;
    }


}
