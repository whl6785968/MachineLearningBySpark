package GraphTheory;


import edu.princeton.cs.algs4.Queue;

import java.io.IOException;
import java.security.DigestException;

public class DepthFirstOrder {
    private boolean[] marked;
    private Queue<Integer> pre;
    private Queue<Integer> post;
    private Stack<Integer> reversePost;

    public DepthFirstOrder(DiGraph g){
        pre = new Queue<Integer>();
        post = new Queue<>();
        reversePost = new Stack<>();
        marked = new boolean[g.V()];
        for(int v=0;v<g.V();v++){
            if(!marked[v]){
                dfs(g,v,1);
            }
        }
    }

    public void dfs(DiGraph g,int v,int currentLength){
        pre.enqueue(v);

        marked[v] = true;
        for(int w:g.adj(v)){
            if(currentLength < 7){
                if(!marked[w]){
                    dfs(g,w,currentLength+1);
                }
            }

        }
        post.enqueue(v);
        reversePost.push(v);
    }

    public Iterable<Integer> pre(){
        return pre;
    }

    public Iterable<Integer> post(){
        return post;
    }

    public Iterable<Integer> reversePost(){
        return reversePost;
    }

    public static void main(String[] args) throws IOException {
        DiGraph diGraph = GraphUtils.getDiGraph();
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(diGraph);

        Iterable<Integer> pre = depthFirstOrder.pre();
        System.out.println(pre.toString());

        Iterable<Integer> post = depthFirstOrder.post();
        System.out.println(post.toString());

        Iterable<Integer> reversePost = depthFirstOrder.reversePost();
        System.out.println(reversePost.toString());

    }
}
