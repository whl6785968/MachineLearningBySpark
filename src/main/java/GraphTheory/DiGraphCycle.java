package GraphTheory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DiGraphCycle {
    private  boolean[] marked = null;
    private  int[] edgeTo = null;
    private Stack<Integer> cycle;
    private  boolean[] onStack = null;
    private List<ArrayList<Integer>> allCylce;
    private ArrayList<Integer> path;
    private List<Map<Integer,Set<Integer>>> pInv;

    public DiGraphCycle(){

    }

    public DiGraphCycle(DiGraph g,Set<Integer> data){
        edgeTo = new int[g.V()];
        onStack = new boolean[g.V()];
        allCylce = new ArrayList<>();
        marked = new boolean[g.V()];
        pInv = g.getP();

        for (int source:data){
            System.out.println("========process " + source+"========");
            path = new ArrayList<>();
            dfs(g,source,source,1,path);
        }


    }

    public final void dfs(DiGraph g,int v,int source,int currentLength,ArrayList<Integer> path){
            onStack[v] = true;
            marked[v] = true;
            path.add(v);


            for (int w:g.adj(v)) {
                if (!marked[w] && w >= source) {
//                    edgeTo[w] = v;
                    if(currentLength < 6){
                        dfs(g, w, source, currentLength + 1, path);
                    }
                }
                else if(onStack[w] && currentLength >= 3){
                    if(source == w){
                        ArrayList<Integer> temp = (ArrayList<Integer>) path.clone();
                        allCylce.add(temp);
//                            System.out.println(temp);
                        //                        System.out.println("length < 6"+temp.toString());
                        //                        cycle = new Stack<>();
                        //                        for(int x=v;x!=w;x=edgeTo[x]){
                        //                            cycle.push(x);
                        //                        }
                        //                        cycle.push(w);
                        //                        if(cycle.size()>=3){
                        //                            allCylce.add(cycle);
                        //                        }
                    }

                }
            }

            if(currentLength == 6){
                Set<Integer> ks = pInv.get(source).get(v);
                if(ks != null){
                    for(int k:ks){
                        if(marked[k] || k < source) continue;
                        ArrayList<Integer> temp = (ArrayList<Integer>) path.clone();
                        temp.add(k);
                        allCylce.add(temp);
//                        System.out.println(temp);
                    }

                }
            }
            marked[v] = false;
            onStack[v] = false;
            if(!path.isEmpty())
            {
                path.remove(path.size()-1);
            }

//            outter:for (int w:g.adj(v)){
//                if(!marked[w] && w >= source){
//                    edgeTo[w] = v;
//                    if(currentLength < 6) {
//                        dfs(g, w, source, currentLength + 1,path);
//                    }
//                    else if(currentLength == 6) {
//                        Set<Integer> ks = pInv.get(source).get(v);
//                        if(ks != null){
//                            for(int k:ks){
//                                if(marked[k] || k < source) continue;
//                                ArrayList<Integer> temp = (ArrayList<Integer>) path.clone();
//                                temp.add(k);
//                                allCylce.add(temp);
//                            }
//
//                        }
//                    }
////                    marked[w]=false;
//                }

//        marked[v] = false;
//        if(!path.isEmpty())
//        {
//            path.remove(path.size()-1);
//        }
//        onStack[v] = false;

    }

    public boolean hasCycle(){
        return cycle != null;
    }

    public Stack<Integer> cycle(){
        return cycle;
    }

    public final List<ArrayList<Integer>> getAllCylce(){
        return allCylce;
    }



    public final void save_file(String path,List<List<Integer>> results) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(results.size()+"\n");
        for (List<Integer> result : results){
            String r = result.toString().replaceAll("\\[|\\]|\\s+","");
            bw.write(r+"\n");
            bw.flush();
        }
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

    public List<List<Integer>> totalOrder(List<List<Integer>> list){
        Collections.sort(list, (o1, o2) -> sort(o1,o2,0));

        return list;
    }

    public int sort(List<Integer> o1,List<Integer> o2,int start){

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

    public DiGraph getSubGraph(List<Integer> subData,DiGraph g){
        List<int[]> subList = new ArrayList<>();
        Set<Integer> set = new TreeSet<>();

        for(int v:subData){
            Iterable<Integer> adj = g.adj(v);
            for(int w:adj){
                int[] array = new int[2];
                array[0] = v;
                array[1] = w;
                set.add(v);
                set.add(w);
                subList.add(array);
            }
        }

        int E = subList.size();
        int V = ((TreeSet<Integer>) set).last()+1;

        DiGraph diGraph = new DiGraph(V, E, subList);
        return diGraph;
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        String filename = "D:\\huaweichussai\\test_data2.txt";
        Map<String, Object> map = GraphUtils.getDiGraphData(filename);
        System.out.println("the cost of load data:"+(System.currentTimeMillis()-start));
        int E = (int) map.get("E");
        int V = (int) map.get("V")+1;
        List<int[]> data = (List<int[]>) map.get("data");
        TreeSet<Integer> sorted_data = (TreeSet<Integer>) map.get("sorted_data");
//        DiGraph diGraph = GraphUtils.getDiGraph();
        DiGraph diGraph = new DiGraph(V, E, data);

//        Iterator<Integer> it = sorted_data.iterator();
//
//        while (it.hasNext()){
//            int source = it.next();
//            if(diGraph.indegree(source) == 0){
//                diGraph.removeAllEdge(source);
//                it.remove();
//            }
//        }

//        System.out.println("the cost of create graph:"+(System.currentTimeMillis()-start));
//
//        KosarajuSCC kosarajuSCC = new KosarajuSCC(diGraph);
//        List<Integer>[] components = kosarajuSCC.components(diGraph);
//        List<List<Integer>> stronglyConnections = new ArrayList<>();
//        System.out.println(components.length);
//        for(List<Integer> i : components){
//            if(i.size() > 2){
//                System.out.println(i);
//                stronglyConnections.add(i);
//            }
//            System.out.println(i);
//        }
//
        DiGraphCycle diGraphCycle = new DiGraphCycle(diGraph,sorted_data);
        ArrayList<Integer> path = new ArrayList<>();
//        diGraphCycle.dfs(diGraph,387,387,1,path);
//        List<Stack<Integer>> results = new ArrayList<>();

//        for(List<Integer> connection : stronglyConnections){
//            DiGraph subGraph = diGraphCycle.getSubGraph(connection, diGraph);
//            TreeSet<Integer> subSet = new TreeSet<>(connection);
//            DiGraphCycle subCycle = new DiGraphCycle(subGraph,subSet);
//            List<Stack<Integer>> cycles = subCycle.getAllCylce();
//            results.addAll(cycles);
//        }
//
//        System.out.println(results.size());
//        List<Integer> path = new ArrayList<>();
//        diGraphCycle.dfs(diGraph,3889,3889,1,path);

        List<ArrayList<Integer>> allCylce = diGraphCycle.getAllCylce();
        System.out.println(allCylce.size());
//
//        allCylce.sort(Comparator.comparingInt(Stack::size));
//        List<List<List<Integer>>> splitLevel = diGraphCycle.splitLevel(allCylce);
//
//        List resultList = new ArrayList();
//
//        splitLevel.stream().forEach(task -> {
//            List<List<Integer>> totalOrder = diGraphCycle.totalOrder(task);
//            resultList.addAll(totalOrder);
//        });


//        String save_path = "D:\\huaweichussai\\my_result_scc.txt";
//        diGraphCycle.save_file(save_path,resultList);

        System.out.println(System.currentTimeMillis()-start);
    }
}
