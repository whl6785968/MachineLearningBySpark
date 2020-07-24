package huawei;

import GraphTheory.GraphUtils;

import java.io.IOException;
import java.util.*;

public class Tarjan{
    private int index = 0;
    private int[] dfn;
    private int[] low;
    private boolean[] marked;
    private Stack<Integer> stack;
    private DiGraph diGraph;
    private int count = 0;
    private boolean[] onStack;
    private List<List<Integer>> allC;

    public Tarjan(DiGraph diGraph,TreeSet<Integer> sorted_data){
        this.diGraph = diGraph;
        dfn = new int[diGraph.V()];
        low = new int[diGraph.V()];
        marked = new boolean[diGraph.V()];
        stack = new Stack<>();
        onStack = new boolean[diGraph.V()];
        allC = new ArrayList<>();

        Arrays.fill(dfn,-1);
        Arrays.fill(low,-1);

        for(int v:sorted_data){
            if(dfn[v] == -1){
                tarjan(v,1,v);
            }
        }
    }

    public void tarjan(int v,int depth,int source){
        int j = -1;
        dfn[v] = low[v] = index++;
        stack.push(v);
        onStack[v] = true;

        for(int w:this.diGraph.adj(v)){
            j = w;
            if(dfn[w] == -1 ){
                tarjan(w,depth+1,source);

                low[v] = Math.min(low[v],low[w]);
            }
            else if(onStack[w]){
                low[v] = Math.min(low[v],dfn[w]);
            }
        }

        if(dfn[v] == low[v]){
            List<Integer> c = new ArrayList<>();

            while (j!=v){
                j = stack.pop();
                onStack[j] = false;
                c.add(j);
            }

            if(c.size() > 2)
            {
                count = count + 1;
                allC.add(c);
            }

        }
    }

    public List<List<Integer>> getAllC(){
        return allC;
    }

    public int count(){
        return count;
    }

    public static void main(String[] args) throws IOException {
        String filename = "D:\\huaweichussai\\test_data1.txt";
        Map<String, Object> map = GraphUtils.getDiGraphData(filename);

        int E = (int) map.get("E");
        int V = (int) map.get("V")+1;
        List<int[]> data = (List<int[]>) map.get("data");
        TreeSet<Integer> sorted_data = (TreeSet<Integer>) map.get("sorted_data");
        DiGraph diGraph = new DiGraph(V, E, data);

        Tarjan tarjan = new Tarjan(diGraph,sorted_data);
        List<List<Integer>> allC = tarjan.getAllC();
        System.out.println(allC.size());
        for (List<Integer> c : allC){
            System.out.println(c.toString());
        }
    }
}