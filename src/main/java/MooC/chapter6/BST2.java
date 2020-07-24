package MooC.chapter6;

public class BST2<Key extends Comparable<Key>,Value> {
    private Node root;

    private class Node{
        private Key key;
        private Value val;
        private int N;
        private Node left,right;

        public Node(Key key,Value val,int N){
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public int size(){
        return size(root);
    }

    private int size(Node x){
        if(x == null) return 0;
        else return x.N;
    }

    public void put(Key key,Value val){
        root = put(root,key,val);
    }

    private Node put(Node x,Key key,Value val){
        if(x == null){
            return new Node(key,val,1);
        }

        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            x.left = put(x.left,key,val);
        }
        else if(cmp > 0){
            x.right = put(x.right,key,val);
        }
        else{
            x.val = val;
        }

        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Value get(Key key){
        return get(root,key);
    }

    public Value get(Node x,Key key){
        if(x == null){
            return null;
        }

        int cmp = key.compareTo(x.key);

        if(cmp < 0){
            return get(x.left,key);
        }
        else if(cmp > 0){
            return get(x.right,key);
        }
        else{
            return x.val;
        }
    }

    public Key min(){
        return min(root).key;
    }

    private Node min(Node x){
        if(x.left == null){
            return x;
        }

        return min(x.left);
    }

    public Key floor(Key key){
        Node x = floor(root,key);
        if(x == null) return null;
        return x.key;
    }

    private Node floor(Node x,Key key){
        if(x == null) return null;

        int cmp = key.compareTo(x.key);

        if(cmp == 0) return x;
        else if(cmp < 0) return floor(x.left,key);

        Node t = floor(x.right,key);
        //回溯最接近待查找值得值
        if(t != null) return t;
        else return x;
    }

    public Key select(int k){
        return select(root,k).key;
    }

    //树的插入是按照先左后右得顺序插入，即左边的插入序号要小于右边
    private Node select(Node x,int k){
        if(x == null) return null;

        int t = size(x.left);

        if(t > k) return select(x.left,k);
        //应该在右子树的第k-t-1位置，k-（左子树+父节点）
        else if(t < k) return select(x.right,k-t-1);
        else return x;
    }

    public int rank(Key key){
        return rank(root,key);
    }

    public int rank(Node x,Key key){
        if(x == null) return 0;
        int cmp = key.compareTo(x.key);

        if(cmp < 0) return rank(x.left,key);
        else if(cmp > 0) return 1 + size(x.left) + rank(x.right,key);
        else return size(x.left);
    }

    public static void main(String[] args) {
        BST2<Integer,Integer> bst2 = new BST2<>();

        int[] ns = {5,3,7,2,4,6,8};

        for(int n : ns){
            bst2.put(n,n);
        }

        Integer integer = bst2.get(3);
        System.out.println(integer);

        Integer floor = bst2.floor(7);
        System.out.println(floor);

        System.out.println(bst2.select(2));

        System.out.println(bst2.rank(4));
    }
}
