package GraphTheory;

import edu.princeton.cs.algs4.StdOut;

import java.io.IOException;
import java.util.Stack;

public class DirectedDfs {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public DirectedDfs(DiGraph g,int s){
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        this.s = s;
        dfs(g,s);
    }

    public void dfs(DiGraph g,int v){
        marked[v] = true;
        for(int w : g.adj(v)){
            if(!marked[w]){
                edgeTo[w] = v;
                dfs(g,w);
            }
        }
    }

    public boolean hasPath(int v){
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v){
        if(!hasPath(v)) return null;

        Stack<Integer> stack = new Stack<>();
        for(int x=v;x!=s;x=edgeTo[x]){
            stack.push(x);
        }
        stack.push(s);
        return stack;

    }

    public boolean marked(int v){
        return marked[v];
    }

    public static void main(String[] args) throws IOException {
        int s = 0;
        DiGraph diGraph = GraphUtils.getDiGraph();
        DirectedDfs directedDfs = new DirectedDfs(diGraph, s);
        for(int v = 0;v < diGraph.V();v++){
            if(directedDfs.hasPath(v)){
                for(int x : directedDfs.pathTo(v)){
                    if(x==s) StdOut.print(x);
                    else StdOut.print(x+"-");
                }
            }

            StdOut.println();
        }
    }


}
