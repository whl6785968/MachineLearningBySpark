package GraphTheory;


import java.util.ArrayList;
import java.util.List;

public class KosarajuSCC {
    private boolean[] marked;
    private int[] id;
    private int count = 0;

    public KosarajuSCC(DiGraph g){
        marked = new boolean[g.V()];
        id = new int[g.V()];
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(g.reverse());
        for(int s : depthFirstOrder.reversePost()){
            if(!marked[s]){
                dfs(g,s,1,count);
                count++;
            }
        }
    }

    private void dfs(DiGraph g,int v,int currentLength,int count){
            marked[v] = true;
            id[v] = count;
            Stack<Integer> stack = new Stack<>();
            stack.push(v);
            while (!stack.isEmpty()){
                int i;
                i= stack.pop();
                for(int j : g.adj(i)){
                    if(!marked[j]){
                        marked[j] = true;
                        id[j] = count;
                        stack.push(j);
                    }
                }
            }
//            for(int w:g.adj(v)){
//                if(currentLength < 30){
//                    if(!marked[w]){
//                        dfs(g,w,currentLength+1,count);
//                    }
//                }
//
//            }
    }

    public List<Integer>[] components(DiGraph g){

        ArrayList<Integer>[] res = new ArrayList[count];
        for(int i = 0; i < count; i ++)
            res[i] = new ArrayList<>();

        for(int v = 0; v < g.V(); v++)
            res[id[v]].add(v);
        return res;
    }

    public boolean stronglyConnected(int v,int w){
        return id[v] == id[w];
    }

    public int id(int v){
        return id[v];
    }

    public int count(){
        return count;
    }


}
