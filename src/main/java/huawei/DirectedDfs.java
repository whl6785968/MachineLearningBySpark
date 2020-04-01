package huawei;

import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Stack;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class DirectedDfs {
    private boolean[] marked;
    private int[] edgeTo;
    Stack<Integer> cycle;
    private boolean[] onQueue;


    public DirectedDfs(DiGraph g){
        marked = new boolean[g.getMaxNode()];
        edgeTo = new int[g.getMaxNode()];
        onQueue = new boolean[g.getMaxNode()];
//        for(int i = 0;i < g.getMaxNode();i++){
//            Iterable<DirectedEdge> adj = g.adj(i);
//            edgeTo[i] = adj;
//        }

    }

    public void dfs(DiGraph g,int v){
        this.onQueue[v] = true;
        this.marked[v] = true;
        for(DirectedEdge edge : g.adj(v)){
            int w = edge.other(v);
            if(this.hasCycle()) return;
            else if(!this.marked[w]){
                edgeTo[w] = v;
                dfs(g,w);
            }
            else if(this.onQueue[w]){
                this.cycle = new Stack<>();
                for(int x=v;x!=w;x=this.edgeTo[x]){
                    this.cycle.push(x);
                }
                this.cycle.push(w);
                this.cycle.push(v);
            }

//            System.out.println(edge.toString()+"---"+w);
//            if(!marked[w]){
//                edgeTo[w] = v;
//                dfs(g,w);
//            }
        }
        onQueue[v] = false;

//        return path;
    }

    public boolean hasCycle(){
        return cycle != null;
    }

    public Iterable<Integer> cycle(){
        return cycle;
    }

    public void relax(DiGraph g,int v){
        for (DirectedEdge edge : g.adj(v)){
            int other = edge.other(v);
        }
    }

    public boolean marked(int v){
        return marked[v];
    }

    public boolean hasPathTo(int v){
        return marked[v];
    }

//    public Queue<Integer> pathTo(DiGraph g,int v){
//        if(!hasPathTo(v)) return null;
//
//        Bag<DirectedEdge> adj = g.adj(v);
//        if(adj.size() == 0){
//            return null;
//        }
//        else {
//            queue.push(v);
//            Iterator<DirectedEdge> iterator = adj.iterator();
//            while (iterator.hasNext()){
//                DirectedEdge next = iterator.next();
//                int other = next.other(v);
//
//            }
//        }
//
//
//        return queue;
//    }

    public static void main(String[] args) throws IOException {
        String filename = "D:\\huaweichussai\\test_data_i.txt";
        DataGenerator dataGenerator = new DataGenerator(filename);
        List<int[]> data = dataGenerator.loadData();
        int num_nodes = dataGenerator.getNum_nodes();
        int max_node = dataGenerator.getMaxNode();

        DiGraph diGraph = new DiGraph(data,num_nodes,max_node+1);
//        Iterable<DirectedEdge> adj = diGraph.adj(6001);
//        for(DirectedEdge edge : adj){
//            System.out.println(edge.toString());
//        }

        DirectedDfs directedDfs = new DirectedDfs(diGraph);
        directedDfs.dfs(diGraph, 6001);
        Iterable<Integer> cycle = directedDfs.cycle();
        for (int i : cycle){
            System.out.println(i);
        }
        System.out.println(cycle);

    }
}
