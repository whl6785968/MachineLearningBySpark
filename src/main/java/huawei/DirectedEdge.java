package huawei;

public class DirectedEdge{
    private final int v;
    private final int w;

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

    public int either(){
        return v;
    }

    public int other(int vertex){
        if(vertex == v){
            return w;
        }
        else if(vertex == w){
            return v;
        }
        else {
            throw new RuntimeException("Inconsistent Edge");
        }
    }

    @Override
    public String toString() {
        return String.format("%d --> %d",v,w);
    }


}
