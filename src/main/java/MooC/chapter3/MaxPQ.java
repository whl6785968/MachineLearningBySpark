package MooC.chapter3;

public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N = 0;

    public MaxPQ(int maxN){
        pq = (Key[]) new Comparable[maxN+1];
    }

    public boolean isEmpty(){
        return this.N == 0;
    }

    public int size(){
        return N;
    }

    public boolean less(int v,int w){
        return pq[v].compareTo(pq[w]) < 0;
    }

    public void exch(int i,int j){
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    public void swim(int k){
        while (k > 1 && less(k/2,k)){
            exch(k/2,k);

            k = k/2;
        }
    }

    public void sink(int k){
        while (2*k <= N){
            int j = 2 * k;

            //选择大的子节点
            if(j < N && less(j,j+1)) j++;

            if(!less(k,j)) break;

            exch(k,j);

            k = j;
        }
    }

    public void insert(Key v){
        pq[++N] = v;
        swim(N);
    }

    public Key delMax(){
        Key max = pq[1];
        //和最后一个节点交换
        exch(1,N--);
        pq[N+1] = null;
        sink(1);
        return max;
    }

    public static void main(String[] args) {
        MaxPQ<Integer> maxPQ = new MaxPQ<>(10);
        maxPQ.insert(1);
        maxPQ.insert(2);
        maxPQ.insert(8);
        maxPQ.insert(3);

        Integer integer = maxPQ.delMax();
        System.out.println(integer);
    }
}
