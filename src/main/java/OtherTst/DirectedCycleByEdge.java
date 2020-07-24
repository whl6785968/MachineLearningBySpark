package OtherTst;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import huawei.GraphUtils;
import huawei.Stack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class DirectedCycleByEdge {
    private final boolean[] marked;
    private final int[] edgeTo;
    private final boolean[] onStack;
    private List<Stack<Integer>> allCycle;

    public DirectedCycleByEdge(DiGraphByDiEdge g,TreeSet<Integer> data,boolean all){
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        onStack = new boolean[g.V()];
        allCycle = new ArrayList<>();
        if(all){
            for(int source : data){
                System.out.println("============"+source+"===========");
                dfs(g,source,source,1);

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
//,boolean[] marked,boolean[] onStack,int[] edgeTo
    public final void dfs(DiGraphByDiEdge g,int v,int source,int currentLength){
        Stack<Integer> cycle;
        Stack<int[]> stack = new Stack<>();
        Stack<DirectedEdge> directedEdges = new Stack<>();
        List<Stack<Integer>> subCycle = new ArrayList<>();
        Stack<Integer> currentLengthStack = new Stack<>();
        Set<Integer> currentSourceNodes = new TreeSet<>();
        marked[v] = true;
        onStack[v] = true;
        int[] a1 = new int[2];
        a1[0]=v;
        a1[1]=currentLength;
        stack.push(a1);
        currentLengthStack.push(currentLength);
        directedEdges.push(new DirectedEdge(v,0));

        while(!stack.isEmpty()){
            int[] i;
            i = stack.pop();
            if (!currentLengthStack.isEmpty()){
                currentLength = i[1];
            }
            directedEdges.pop();
            if(currentLength < 8){
                for(DirectedEdge j : g.adj(i[0])){
                    int w = j.to();
                    if(!marked[w] && w >= source){
                        marked[w] = true;
                        onStack[w] = true;
                        edgeTo[w] = i[0];
                        int[] a2 = new int[2];
                        a2[0] = w;
                        a2[1] = currentLength+1;
                        stack.push(a2);
                        directedEdges.push(j);
                        currentSourceNodes.add(w);
                    }
                    else if(onStack[w] && w >= source){
                        if(source==w){
                            cycle = new Stack<>();
                            for(int x=i[0];x!=w;x=edgeTo[x]){
                                cycle.push(x);
                            }
                            cycle.push(w);
                            allCycle.add(cycle);
                        }
                    }
                }
            }
            if(!directedEdges.isEmpty()){
                DirectedEdge de = directedEdges.peek();
                int intersection = de.from();
                for(int x=i[0];x!=intersection;x=edgeTo[x]){
                    marked[x] = false;
                    onStack[x] = false;
                }
            }
        }



        onStack[source] = false;
        allCycle.addAll(subCycle);

        currentSourceNodes.remove(source);
        marked[source]=true;
        if(currentSourceNodes.size() != 0){
            for(int s : currentSourceNodes){
                if(marked[s]){
                    marked[s] = false;
                    onStack[s] = false;
                }
            }
        }

    }


    public final void save_file(String path,List<Stack<Integer>> results) throws IOException {
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
        String filename = "D:\\huaweichussai\\test_data_i.txt";
        Map<String, Object> map = GraphUtils.getDiGraphData(filename);

        int E = (int) map.get("E");
        int V = (int) map.get("V")+1;
        List<int[]> data = (List<int[]>) map.get("data");
        TreeSet<Integer> sorted_data = (TreeSet<Integer>) map.get("sorted_data");

        DiGraphByDiEdge diGraphByDiEdge = new DiGraphByDiEdge(V, E, data);
        Topological topological = new Topological(diGraphByDiEdge, sorted_data);
        List<Integer> order = topological.order(diGraphByDiEdge, sorted_data);

        TreeSet<Integer> set = new TreeSet<>(order);
        DirectedCycleByEdge directedCycleByEdge = new DirectedCycleByEdge(diGraphByDiEdge,set ,true);
//        directedCycleByEdge.dfs(diGraphByDiEdge,1131,1131,1);
//        directedCycleByEdge.dfs(diGraphByDiEdge,6002,6002,0);
//        directedCycleByEdge.dfs(diGraphByDiEdge,6003,6003,0);
        List<Stack<Integer>> allCycle = directedCycleByEdge.getAllCycle();
        System.out.println(allCycle.size());

//        List<Stack<Integer>> allCycle = directedCycleByEdge.run(diGraphByDiEdge, sorted_data);

        List<Stack<Integer>> result_list = new ArrayList<>();
        for(int i=0;i<allCycle.size();i++){
            int pathLength = allCycle.get(i).size();
            if (pathLength >= 3 && pathLength <= 7){
                result_list.add(allCycle.get(i));
            }
        }

        Collections.sort(result_list, (o1, o2) -> {
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

        result_list.sort(Comparator.comparingInt(Stack::size));
        String save_path = "D:\\huaweichussai\\my_result2.txt";
        directedCycleByEdge.save_file(save_path,result_list);

        System.out.println("cost:"+(System.currentTimeMillis()-start));
    }
}
