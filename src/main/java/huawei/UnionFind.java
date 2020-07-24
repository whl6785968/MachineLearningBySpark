package huawei;

public class UnionFind {
    private int[] id;
    private int[] sz;
    private int count;

    public UnionFind(int n){
        count = n;
        id = new int[n];
        sz = new int[n];
        for(int i = 0;i < n;i++){
            id[i] = i;
            sz[i] = i;
        }
    }

    public int count(){
        return count;
    }

    public boolean connected(int p,int q){
        return find(p) == find(q);
    }

    public int find(int p){
        while (p != id[p]){
            p = id[p];
        }
        return id[p];
    }

    public void union(int p,int q){
        int pid = find(p);
        int qid = find(q);

        if(pid == qid) return;

        id[pid] = qid;
        sz[qid] += sz[pid];
        count--;
    }
}
