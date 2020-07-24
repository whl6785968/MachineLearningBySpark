package GraphTheory;

public class TransitiveClosure {
    private DirectedDfs[] all;
    public TransitiveClosure(DiGraph g){
        all = new DirectedDfs[g.V()];
        for(int v=0;v<g.V();v++){
            all[v] = new DirectedDfs(g,v);
        }
    }

    public boolean reachable(int v,int w){
        return all[v].marked(w);
    }
}
