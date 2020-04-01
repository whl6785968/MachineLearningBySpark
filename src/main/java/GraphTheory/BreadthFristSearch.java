package GraphTheory;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BreadthFristSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    private int count;


    public BreadthFristSearch(Graph g,int s){
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        this.s = s;
        bfs(g,s);
    }

    public void bfs(Graph g,int s){
        Queue<Integer> queue = new Queue<>();
        marked[s] = true;
        queue.enqueue(s);

        while (!queue.isEmpty()){
            int v = queue.dequeue();
            for(int w:g.adj(v)){
                if(!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v){
        return marked[v];
    }

    //从后向前遍历  edge[5] = 3  意味着 3 to 5 一直找到起点
    public Iterable<Integer> pathTo(int v){
        if(!hasPathTo(v)) return null;
        Stack<Integer> stack = new Stack<>();
        for(int x = v;x != s;x=edgeTo[x]){
            stack.push(x);
        }
        stack.push(s);
        return stack;
    }

    public static void main(String[] args) throws IOException {
        int s = 0;
        String filename = "F:\\test\\spark-test\\src\\main\\java\\GraphTheory\\tinyG.txt";
        DataProcessor dataProcessor = new DataProcessor(filename);
        Map<String, Object> map = dataProcessor.loadData();
        int E = (int) map.get("E");
        int V = (int) map.get("V");
        List<int[]> data = (List<int[]>) map.get("data");
        Graph graph = new Graph(V,E,data);

        BreadthFristSearch breadthFristSearch = new BreadthFristSearch(graph, s);

        for(int v = 0;v < graph.V();v++){
            if(breadthFristSearch.hasPathTo(v)){
                for(int x : breadthFristSearch.pathTo(v)){
                    if(x==s) StdOut.print(x);
                    else StdOut.print("-"+x);
                }
            }
            StdOut.println();
        }
    }
}
