package ImproveGraphAttempt;


import GraphTheory.GraphUtils;
import GraphTheory.Stack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DirectedCycleByEdge {
    private  boolean[] marked;
    private  DiEdge[] edgeTo;
    private  boolean[] onStack;
    private List<Stack<Integer>> allCycle;
    private Stack<DiEdge> diEdgeStack;
    private Stack<Integer> cycle;

    public DirectedCycleByEdge(DiGraphByDiEdge g, TreeSet<Integer> data, boolean all){
        allCycle = new ArrayList<>();
        diEdgeStack = new Stack<>();

        if(all){
            for(int source : data){
                marked = new boolean[g.V()];
                edgeTo = new DiEdge[g.V()];
                onStack = new boolean[g.V()];
                System.out.println("============"+source+"===========");
                if(source == 1627)
                    dfs(g,source,source,1,marked,edgeTo,onStack);
                dfs(g,source,source,1,marked,edgeTo,onStack);

            }
        }
    }

//    class SubThread implements Callable<List<Stack<Integer>>> {
//        private DiGraphByDiEdge g;
//        private int v;
//        private int source;
//        private int currentLength;
//        private boolean[] marked;
//        private boolean[] onStack;
//        private int[] edgeTo;
//
//        public SubThread(DiGraphByDiEdge g, int v, int source, int currentLength,boolean[] marked,boolean[] onStack,int[] edgeTo) {
//            this.g = g;
//            this.v = v;
//            this.source = source;
//            this.currentLength = currentLength;
//            this.marked = marked;
//            this.onStack = onStack;
//            this.edgeTo = edgeTo;
//        }
//
//
//        @Override
//        public List<Stack<Integer>> call() throws Exception {
//            List<Stack<Integer>> stackList = dfs(g, v, source, currentLength,marked,onStack,edgeTo);
//            return stackList;
//        }
//    }

//    public final List<Stack<Integer>> run(DiGraphByDiEdge g,TreeSet<Integer> data){
//        List<Stack<Integer>> allCylce = new ArrayList<>();
//        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("pool-%d").build();
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 1000L,
//                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(data.size()+1));
//
//        List<Future<List<Stack<Integer>>>> futureList = new ArrayList<>();
//        for(int source=0;source<5000;source++){
//            System.out.println("========process " + source+"========");
//            boolean[] marked = new boolean[g.V()];
//            boolean[] onStack = new boolean[g.V()];
//            int[] edgeTo = new int[g.V()];
//            SubThread subThread = new SubThread(g, source, source, 1,marked,onStack,edgeTo);
//            futureList.add(executor.submit(subThread));
//        }
//
//        futureList.parallelStream().forEachOrdered(future -> {
//            try {
//                List<Stack<Integer>> cycles = future.get();
//                allCylce.addAll(cycles);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        executor.shutdown();
//
//        return allCylce;
//    }

    public final void dfs(DiGraphByDiEdge g, int v, int source,int currentLength,boolean[] marked,DiEdge[] edgeTo,boolean[] onStack){
        //假如0已经在栈中了，某次某条边又指向了该点，即0又是起点又是终点，则有环
        //0-6-0
        onStack[v] = true;
        marked[v] = true;
        for (DiEdge edge:g.adj(v)){
            int w = edge.to();
            int weights = edge.weights();
            if(!marked[w] && w >= source && weights == 0){
                edgeTo[w] = edge;
                if(currentLength < 7){
                    dfs(g, w, source, currentLength + 1,marked,edgeTo,onStack);
                }
                marked[w]=false;
            }
            else if(weights != 0 && !onStack[w] && w >= source){
                if(currentLength + weights < 7){
                    edgeTo[w] = edge;
                    dfs(g, w, source, currentLength + 1,marked,edgeTo,onStack);
                }
                marked[w] = false;
            }
            else if(onStack[w]){
                if(source == w){
                    cycle = new Stack<>();
                    int step = 1;
                    for(int x=v;x!=w;x=edgeTo[x].from()){
                        cycle.push(x);
                        edgeTo[x].setWeights(step);
                        step += 1;
                    }
                    cycle.push(w);
                    if(cycle.size() >= 3){
//                        System.out.println(cycle);
                        allCycle.add(cycle);
                    }
                }
            }
        }
        onStack[v] = false;
    }


    public final void save_file(String path, List<Stack<Integer>> results) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(results.size()+"\n");
        for (Stack<Integer> result : results){
            bw.write(result.toString()+"\n");
            bw.flush();
        }
    }

    public List<Stack<Integer>> getAllCycle(){
        return allCycle;
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String filename = "D:\\huaweichussai\\test_data1.txt";
        Map<String, Object> map = GraphUtils.getDiGraphData(filename);

        int E = (int) map.get("E");
        int V = (int) map.get("V") + 1;
        List<int[]> data = (List<int[]>) map.get("data");
        TreeSet<Integer> sorted_data = (TreeSet<Integer>) map.get("sorted_data");

        DiGraphByDiEdge diGraphByDiEdge = new DiGraphByDiEdge(V, E, data);
       
        DirectedCycleByEdge directedCycleByEdge = new DirectedCycleByEdge(diGraphByDiEdge,sorted_data ,true);
//        directedCycleByEdge.dfs(diGraphByDiEdge,1131,1131,1);

        boolean[] marked = new boolean[diGraphByDiEdge.V()];
        boolean[] onStack = new boolean[diGraphByDiEdge.V()];
        DiEdge[] edgeTo = new DiEdge[diGraphByDiEdge.V()];
//        directedCycleByEdge.dfs(diGraphByDiEdge,1359,1359,1,marked,edgeTo,onStack);
//        directedCycleByEdge.dfs(diGraphByDiEdge,6003,6003,0);
        List<Stack<Integer>> allCycle = directedCycleByEdge.getAllCycle();
//        allCycle.sort(Comparator.comparingInt(Stack::size));
//        for(Stack<Integer> c:allCycle){
//            System.out.println(c.toString());
//        }
        System.out.println(allCycle.size());



//
//
//        String save_path = "D:\\huaweichussai\\my_result2.txt";
//        directedCycleByEdge.save_file(save_path,result_list);
//
        System.out.println("cost:"+(System.currentTimeMillis()-start));
    }
}
