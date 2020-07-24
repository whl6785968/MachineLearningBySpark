package huawei;

import edu.princeton.cs.algs4.Cycle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class DiGraphCycle {
    public DiGraphCycle(){

    }

    public List<Stack<Integer>> train(DiGraph g,List<List<Integer>> data){
        List<Stack<Integer>> allCylce = new ArrayList<>();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 200, 100L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024),new ThreadPoolExecutor.AbortPolicy());

        boolean[] marked;
        boolean[] onStack;
        int[] edgeTo;
        for(int i = 0,length=data.size();i < length;i++){
            List<Integer> subList = data.get(i);
            List<Future<List<Stack<Integer>>>> futureList = new ArrayList<>();
            for(int j = 0,length2=subList.size();j < length2;j++){
                System.out.println("=====process"+subList.get(j)+"=====");
                marked = new boolean[g.V()];
                onStack = new boolean[g.V()];
                edgeTo = new int[g.V()];
                SubThread subThread = new SubThread(g, subList.get(j), subList.get(j), 1,marked,onStack,edgeTo);
                futureList.add(executor.submit(subThread));
            }

            futureList.stream().forEachOrdered(future -> {
                try {
                    List<Stack<Integer>> list = future.get();
                    allCylce.addAll(list);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        return allCylce;
    }

    class SubThread implements Callable<List<Stack<Integer>>> {
        private DiGraph g;
        private int v;
        private int source;
        private int currentLength;
        private boolean[] marked;
        private boolean[] onStacked;
        private int[] edgeTo;

        public SubThread(DiGraph g, int v, int source, int currentLength,
                         boolean[] marked,boolean[] onStacked,int[] edgeTo) {
            this.g = g;
            this.v = v;
            this.source = source;
            this.currentLength = currentLength;
            this.marked = marked;
            this.onStacked = onStacked;
            this.edgeTo = edgeTo;
        }

        @Override
        public List<Stack<Integer>> call(){
            List<Stack<Integer>> stackList = dfs(g, v, source, currentLength,marked,onStacked,edgeTo);
            return stackList;
        }
    }

    public final List<Stack<Integer>> dfs(DiGraph g, int v, int source, int currentLength,boolean[] marked,boolean[] onStack,int[] edgeTo){
        //假如0已经在栈中了，某次某条边又指向了该点，即0又是起点又是终点，则有环
        //0-6-0
        List<Stack<Integer>> subList = new ArrayList<>();
        onStack[v] = true;
        marked[v] = true;
        for (int w:g.adj(v)){
            if(!marked[w] && w >= source){
                edgeTo[w] = v;
                if(currentLength < 7){
                    List<Stack<Integer>> stackList = dfs(g, w, source, currentLength + 1,marked,onStack,edgeTo);
                    subList.addAll(stackList);
                }
                marked[w]=false;
            }
            else if(onStack[w] && w >= source){
                if(source == w){
                    Stack<Integer> cycle = new Stack<>();
                    for(int x=v;x!=w;x=edgeTo[x]){
                        cycle.push(x);
                    }
                    cycle.push(w);
                    if(cycle.size() >= 3){
                        subList.add(cycle);
                    }
                }
            }
        }
        onStack[v] = false;
        return subList;
    }

    public final void save_file(String path,List<List<Integer>> results,Map<Integer,Integer> dict) throws IOException {
        results = origin(dict, results);

        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(results.size()+"\n");
        for (List<Integer> result : results){
            String r = result.toString().replaceAll("\\[|\\]|\\s+","");
            bw.write(r+"\n");
            bw.flush();
        }

        bw.close();
    }

    public final List<List<Integer>> origin(Map<Integer,Integer> dict,List<List<Integer>> results){
        results.parallelStream().forEach(list -> {
            for(int i=0;i<list.size();i++){
                list.set(i,dict.get(list.get(i)));
            }
        });

        return results;
    }

    public final List<List<Integer>> splitData(TreeSet<Integer> data){
        List<Integer> dataList = new ArrayList<>(data);
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> subList = new ArrayList<>();

        for(int i = 0,length=dataList.size();i < length;i++){
            if((i+1) % 1000 == 0){
                subList.add(dataList.get(i));
                result.add(subList);
                subList = new ArrayList<>();
            }
            else {
                if(data.size() > 0){
                    subList.add(dataList.get(i));
                }
            }
        }

        result.add(subList);

        return result;
    }

    public final List<List<List<Integer>>> splitLevel(List<Stack<Integer>> list){
        List<List<List<Integer>>> result = new ArrayList();
        int current_size = list.get(0).size();
        List<List<Integer>> subList = new ArrayList();

        for(Stack<Integer> stack:list){
            if(stack.size() != current_size){
                result.add(subList);
                subList = new ArrayList();
                current_size = stack.size();
                subList.add(stack.toList());
            }
            else {
                subList.add(stack.toList());
            }
        }

        result.add(subList);

        return result;
    }

    public final List<List<Integer>> totalOrder(List<List<Integer>> list){
        Collections.sort(list, (o1, o2) -> sort(o1,o2,0));

        return list;
    }

    public final int sort(List<Integer> o1,List<Integer> o2,int start){

        int up = o1.get(start),down = o2.get(start);
        if(o1.size() == 0 || o1 == null){
            return 0;
        }

        if(o2.size() == 0 || o2 == null){
            return 0;
        }
        if(start == o1.size()-1){
            if(up == down){
                return 0;
            }
            else if(up > down) {
                return 1;
            }
            else {
                return -1;
            }
        }

        if(up == down){
            return sort(o1,o2,start+1);
        }
        else if(up > down) {
            return 1;
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String filename = "D:\\huaweichussai\\test_data1.txt";
//        String filename = args[0];
        Map<String, Object> map = GraphUtils.getDiGraphData(filename);
        System.out.println("the cost of load data:"+(System.currentTimeMillis()-start));
        int E = (int) map.get("E");
        int V = (int) map.get("V")+1;
        List<int[]> data = (List<int[]>) map.get("data");
        TreeSet<Integer> sorted_data = (TreeSet<Integer>) map.get("sorted_data");
        DiGraph diGraph = new DiGraph(V, E, data);
        System.out.println("the cost of create graph:"+(System.currentTimeMillis()-start));




//        Iterator<Integer> it = sorted_data.iterator();
//
//        while (it.hasNext()){
//            int source = it.next();
//            if(diGraph.indegree(source) == 0){
//                diGraph.removeAllEdge(source);
//                it.remove();
//            }
//        }
//
        DiGraphCycle diGraphCycle = new DiGraphCycle();

        List<List<Integer>> splitData = diGraphCycle.splitData(sorted_data);

//
//
//        List<Stack<Integer>> result_list = diGraphCycle.train(diGraph, splitData);
//        for(Stack<Integer> stack:result_list){
//            System.out.println(stack.toString());
//        }
//
//        System.out.println(result_list.size());
////        System.out.println(result_list.size());
//        result_list.sort(Comparator.comparingInt(Stack::size));
//
//        List<List<List<Integer>>> splitLevel = diGraphCycle.splitLevel(result_list);
//
//        List resultList = new ArrayList();
//
//        splitLevel.parallelStream().forEachOrdered(task -> {
//            List<List<Integer>> totalOrder = diGraphCycle.totalOrder(task);
//            resultList.addAll(totalOrder);
//        });
//
//        Map<Integer,Integer> dict = (Map<Integer, Integer>) map.get("dict");
//        String save_path = "D:\\huaweichussai\\my_result1.txt";
////        String save_path = args[1];
//        diGraphCycle.save_file(save_path,resultList,dict);
//
        System.out.println(System.currentTimeMillis()-start);
//        System.out.println(String.format("the cost of time is %s",((System.currentTimeMillis()-start))/1000).toString());
    }
}
