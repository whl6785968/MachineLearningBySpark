package GraphTheory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UnionFind {
    private int[] id;
    private int count;

    public UnionFind(int n){
        count = n;
        id = new int[n];
        for(int i=0;i<n;i++){
            id[i] = i;
        }
    }

    public int count(){
        return count;
    }

    public boolean connect(int p,int q){
        return id[p] == id[q];
    }

    public int find(int p){
        return id[p];
    }

    public void union(int p,int q){
        int pID = find(p);
        int qID = find(q);

        if(pID == qID) return;

        for(int i = 0;i < id.length;i++){
            if(id[i] == pID){
                id[i] = qID;
            }
        }
        count--;
    }

    public int[] getId(){
        return id;
    }


    //不断的将新添加来的边变成最后添加进来的触点的id
    public static void main(String[] args) throws IOException {
        Map<String, Object> diGraphData = GraphUtils.getDiGraphData("D:\\huaweichussai\\test_data_i.txt");
        int V = (int) diGraphData.get("V")+1;
        List<int[]> data = (List<int[]>) diGraphData.get("data");
        UnionFind unionFind = new UnionFind(V);
        for(int[] pair:data){
            int p = pair[0];
            int q = pair[1];
            if(unionFind.connect(p,q)) continue;
            unionFind.union(p,q);
//            System.out.println(p+"-"+q);
        }


        int[] id = unionFind.getId();
        for(int i=0;i < id.length;i++){

        }


        System.out.println(unionFind.count());
        System.out.println(unionFind.connect(6001,6001));
        System.out.println(unionFind.find(6001));
        System.out.println(unionFind.find(6003));
    }
}
