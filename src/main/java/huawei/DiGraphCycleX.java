package huawei;

import edu.princeton.cs.algs4.Queue;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class DiGraphCycleX {
    private Stack<Integer> cycle;

    public DiGraphCycleX(DiGraph diGraph){
        int[] indegree = new int[diGraph.V()];

        for(int v=0;v<diGraph.V();v++){
            indegree[v] = diGraph.indegree(v);
        }

        Queue<Integer> queue = new Queue<>();

        int v;
        for(v = 0; v < diGraph.V(); ++v) {
            if (indegree[v] == 0) {
                queue.enqueue(v);
            }
        }


        while(!queue.isEmpty()) {
            v = (Integer)queue.dequeue();
            Iterator i$ = diGraph.adj(v).iterator();

            while(i$.hasNext()) {
                v = (Integer)i$.next();
                int var10002 = indegree[v]--;
                if (indegree[v] == 0) {
                    queue.enqueue(v);
                }
            }
        }

        int[] edgeTo = new int[diGraph.V()];
        int root = -1;

        for(v = 0; v < diGraph.V(); ++v) {
            if (indegree[v] != 0) {
                root = v;
                Iterator i$ = diGraph.adj(v).iterator();

                while(i$.hasNext()) {
                    int w = (Integer)i$.next();
                    if (indegree[w] > 0) {
                        edgeTo[w] = v;
                    }
                }
            }
        }

        if (root != -1) {
            for(boolean[] visited = new boolean[diGraph.V()]; !visited[root]; root = edgeTo[root]) {
                visited[root] = true;
            }

            this.cycle = new Stack();
            int w = root;

            do {
                this.cycle.push(w);
                w = edgeTo[w];
            } while(w != root);

            this.cycle.push(root);
        }
    }

    public Stack<Integer> getCycle(){
        return cycle;
    }

    public static void main(String[] args) throws IOException {
        String filename = "D:\\huaweichussai\\test_data2.txt";

        Map<String, Object> map = GraphUtils.getDiGraphData(filename);

        int E = (int) map.get("E");
        int V = (int) map.get("V")+1;
        List<int[]> data = (List<int[]>) map.get("data");
        TreeSet<Integer> sorted_data = (TreeSet<Integer>) map.get("sorted_data");
        DiGraph diGraph = new DiGraph(V, E, data);

        DiGraphCycleX diGraphCycleX = new DiGraphCycleX(diGraph);
        Stack<Integer> cycle = diGraphCycleX.getCycle();
        System.out.println(cycle);
    }
}
