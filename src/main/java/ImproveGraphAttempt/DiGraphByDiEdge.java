package ImproveGraphAttempt;



import java.util.LinkedList;
import java.util.List;

public class DiGraphByDiEdge {
    private final int V;
    private int E;
    private LinkedList<DiEdge>[] adj;
    private int[] inDegree;
    private int[] outDegree;


    public DiGraphByDiEdge(int V, int E, List<int[]> data){
        this.V = V;
        this.E = E;

        adj = (LinkedList<DiEdge>[])new LinkedList[V];
        inDegree = new int[V];
        outDegree = new int[V];

        for(int i = 0;i < V;i++){
            adj[i] = new LinkedList<DiEdge>();
        }

        for(int i=0;i<data.size();i++){
            int v = data.get(i)[0];
            int w = data.get(i)[1];
            DiEdge directedEdge = new DiEdge(v, w,0);
            addEdge(directedEdge);
        }
    }

    public int inDegree(int v){
        return inDegree[v];
    }

    public int outDegree(int v){
        return outDegree[v];
    }

    public void addEdge(DiEdge edge){
        inDegree[edge.to()] += 1;
        outDegree[edge.from()] += 1;
        adj[edge.from()].add(edge);
        E++;
    }

    public void removeEdge(int v){
        E -= adj[v].size();
        adj[v] = new LinkedList<>();
    }

    public Iterable<DiEdge> adj(int v){
        return adj[v];
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }




}
