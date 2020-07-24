package MooC.chapter6;

import edu.princeton.cs.algs4.Queue;
import huawei.Stack;

public class BST<E extends Comparable<E>> {
    private class Node{
        public E e;
        public Node left,right;

        public Node(E e){
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BST(){
        root = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(E e){
        if(root == null){
            root = new Node(e);
        }
        else{
            add(root,e);
        }
    }

    public Node add(Node node,E e){
        if (node == null){
            size++;
            return new Node(e);
        }

        if(e.compareTo(node.e) < 0){
            node.left = add(node.left,e);
//            add(node.left,e);
        }
        else {
            node.right = add(node.right,e);
//            add(node.right,e);
        }

        return node;

    }

    public void preOrder(){
        preOrder(root);
    }

    private void preOrder(Node node){
        if (node == null){
            return;
        }

        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    public void preOrderIter(){
        preOrderIter(root);
    }

    private void preOrderIter(Node node){
        Stack<Node> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()){
            Node n = stack.pop();
            System.out.println(n.e);
            if (n.right != null){
                stack.push(n.right);
            }
            if(n.left != null){
                stack.push(n.left);
            }

        }

    }

    public void bfs(){
        bfs(root);
    }

    private void bfs(Node node){
        Queue<Node> queue = new Queue<>();
        queue.enqueue(node);

        while (!queue.isEmpty()){
            Node n = queue.dequeue();
            System.out.println(n.e);
            if(n.left != null){
                queue.enqueue(n.left);
            }

            if(n.right != null){
                queue.enqueue(n.right);
            }
        }
    }

    public void inOrder(){
        inOrder(root);
    }

    private void inOrder(Node node){
        if (node == null){
            return;
        }

        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }


    public boolean contains(E e){
        return contains(root,e);
    }

    public boolean contains(Node node,E e){
        if(node == null)
            return false;

        if(e.equals(node.e)){
            return true;
        }
        else if(e.compareTo(node.e) < 0){
            return contains(node.left,e);
        }
        else{
            return contains(node.right,e);
        }
    }

    private void generateBSTString(Node node,int depth,StringBuilder res){
        if(node == null){
            res.append(generateDepthString(depth) + "null"+"\n");
            return;
        }

        res.append(generateDepthString(depth) + node.e + "\n");
        generateBSTString(node.left,depth+1,res);
        generateBSTString(node.right,depth+1,res);
    }

    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0;i < depth;i++){
            res.append("--");
        }

        return res.toString();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root,0,res);

        return res.toString();
    }

    public E mininum(){
        return mininum(root).e;
    }

    private Node mininum(Node node){
        if(node.left == null){
            return node;
        }

        return mininum(node.left);
    }

    public E maxim(){
        return maxim(root);
    }

    private E maxim(Node node){
        if(node.right == null){
            return node.e;
        }

        return maxim(node.right);
    }

    public E removeMin(){
        E ret = mininum();
        root = removeMin(root);
        return ret;
    }

    private Node removeMin(Node node){
        if(node.left == null){
            Node right = node.right;
            node.right = null;
            size--;
            return right;
        }

        node.left = removeMin(node.left);
        return node;
    }

    public E removeMax(){
        E ret = maxim();
        return ret;
    }

    private Node removeMax(Node node){
        if(node.right == null){
            Node left = node.left;
            node.left = null;
            size--;
            return left;
        }

        node.right = removeMax(node.right);
        return node;
    }

    public void remove(E e){
        root = remove(root,e);
    }

    private Node remove(Node node,E e){
        if(e.compareTo(node.e) < 0){
            node.left = remove(node.left,e);
            return node;
        }
        else if(e.compareTo(node.e) > 0){
            node.right = remove(node.right,e);
            return node;
        }
        else{
            if(node.left == null){
                Node right = node.right;
                node.right = null;
                size--;
                return right;
            }

            if(node.right == null){
                Node left = node.left;
                node.left = null;
                size--;
                return left;
            }

            Node successor = mininum(node.right);
            //removeMin是右子树删除了后继node的子树
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;
            return successor;

        }
    }

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();

        int[] nodes = {5,3,6,8,4,2};
        for(int node:nodes){
            bst.add(node);
        }

//        System.out.println(bst.toString());
        bst.preOrder();
        System.out.println();
//        bst.inOrder();

        bst.preOrderIter();

        System.out.println();
        bst.bfs();

        Integer maxim = bst.maxim();
        System.out.println("maxim:" + maxim);

        Integer minimum = bst.mininum();
        System.out.println("minimum:"+ minimum);

    }
}
