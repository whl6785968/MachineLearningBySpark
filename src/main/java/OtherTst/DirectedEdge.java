package OtherTst;

public class DirectedEdge {
    private int v;
    private int w;

    public DirectedEdge(int v,int w){
        this.v = v;
        this.w = w;
    }

    public int from(){
        return v;
    }

    public int to(){
        return w;
    }

    public int other(int v){
        return w;
    }
}
