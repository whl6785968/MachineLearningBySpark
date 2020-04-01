package GraphTheory;


//import edu.princeton.cs.algs4.Stack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DiGraphCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    private boolean[] onStack;
    private List<Stack<Integer>> allCylce;

    public DiGraphCycle(DiGraph g,TreeSet<Integer> data){
        edgeTo = new int[g.V()];
        onStack = new boolean[g.V()];
        allCylce = new ArrayList<>();
        marked = new boolean[g.V()];
        for (int source : data){
            int count = 0;
//            marked = new boolean[g.V()];
            dfs(g,source,source,count);
        }

    }

    public void dfs(DiGraph g,int v,int source,int count){
        //假如0已经在栈中了，某次某条边又指向了该点，即0又是起点又是终点，则有环
        //0-6-0
        if(!marked[source] || count != 0){
            onStack[v] = true;
            marked[v] = true;
            for (int w:g.adj(v)){
//            if (this.hasCycle()) return;
                if(!marked[w]){
                    edgeTo[w] = v;
                    dfs(g,w,source,count+1);
                    marked[w]=false;
                }
                else if(onStack[w]){
                    if(source == w){
                        cycle = new Stack<>();
//                    cycle.push(w);
                        for(int x=v;x!=w;x=edgeTo[x]){
                            cycle.push(x);
                        }
                        cycle.push(w);

                        allCylce.add(cycle);
                    }
//                cycle.push(v);
                }
            }
            onStack[v] = false;
        }

    }

    public boolean hasCycle(){
        return cycle != null;
    }

    public Stack<Integer> cycle(){
        return cycle;
    }

    public List<Stack<Integer>> getAllCylce(){
        return allCylce;
    }

    public void save_file(String path,List<Stack<Integer>> results) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(results.size()+"\n");
        for (Stack<Integer> result : results){
            bw.write(result.toString()+"\n");
            bw.flush();
        }
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String filename = "D:\\huaweichussai\\test_data_i.txt";
        Map<String, Object> map = GraphUtils.getDiGraphData(filename);
        int E = (int) map.get("E");
        int V = (int) map.get("V")+1;
        List<int[]> data = (List<int[]>) map.get("data");
        TreeSet<Integer> sorted_data = (TreeSet<Integer>) map.get("sorted_data");
        DiGraph diGraph = new DiGraph(V, E, data);
        DiGraphCycle diGraphCycle = new DiGraphCycle(diGraph,sorted_data);

        //0-6-4-3-0
//        diGraphCycle.dfs(diGraph,61,61);

        List<Stack<Integer>> allCylce = diGraphCycle.getAllCylce();
        System.out.println(allCylce.get(55).size());
        for(int i=0;i<allCylce.size();i++){
            int pathLength = allCylce.get(i).size();
            if (pathLength < 3 || pathLength > 7){
                allCylce.remove(i);
            }
        }

        Collections.sort(allCylce, (o1, o2) -> {
            int a = o1.peek();
            int b = o2.peek();
            if(a > b){
                return 1;
            }
            else if(a < b){
                return -1;
            }
            return 0;
        });

        allCylce.sort(Comparator.comparingInt(Stack::size));

        String save_path = "D:\\huaweichussai\\my_result.txt";
        diGraphCycle.save_file(save_path,allCylce);

        System.out.println(System.currentTimeMillis()-start);
        System.out.println(String.format("the cost of time is %s",((System.currentTimeMillis()-start))/1000).toString());
    }
}
