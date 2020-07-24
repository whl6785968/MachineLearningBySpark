package Leetcode;

public class Solution785 {
    private boolean[] marked;
    private int[] colors;

    public boolean isBipartite(int[][] graph) {
        int V = graph.length;
        marked = new boolean[V];
        colors = new int[V];

        for (int v=0;v<V;v++){
            if(!dfs(graph,v,0)){
                return false;
            }
        }
        return true;
    }

    public boolean dfs(int[][] graph,int v,int color){
        marked[v] = true;
        colors[v] = color;
        for(int w:graph[v]){
            if(!marked[w]){
                if(!dfs(graph,v,1-color)) return false;
            }
            else if(colors[w] == colors[v]) return false;
        }

        return true;
    }
}
