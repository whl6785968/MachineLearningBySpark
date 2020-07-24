package ImproveGraphAttempt;



public class DiEdge {
    private int v;
    private int w;
    private int weights;

    public DiEdge(int v, int w, int weights) {
        this.v = v;
        this.w = w;
        this.weights = weights;
    }

    public int from(){
        return v;
    }

    public int to(){
        return w;
    }

    public int weights(){
        return weights;
    }

    public void setWeights(int weights){
        this.weights = weights;
    }

}
