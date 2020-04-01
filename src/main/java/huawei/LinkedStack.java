package huawei;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Queue<Item> {
    private int N = 0;
    private Node first;
    private Node last;

    private class Node{
        Item item;
        Node next;

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public Queue(){
        first = null;
        last = null;
        N = 0;

    }

    public void push(Item item){
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty()){
            first = last;
        }
        else {
            oldLast.next = last;
        }

        N++;
    }

    public int find(Item item){
        Node temp = first;
        int index = -1;
        if(temp.next == null){
            return index;
        }

        while (temp.next != null){
            temp = temp.next;
            index++;
            if(temp.item == item){
                temp = temp.next;
                return index;
            }
        }
        return index;
    }

    public Item pop(){
        Item item = first.item;
        first = first.next;
        if (isEmpty()){
            last = null;
        }
        N--;
        return item;
    }

    public int size(){
        return N;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public Node getLast() {
        return last;
    }

    public void setLast(Node last) {
        this.last = last;
    }

    public static void main(String[] args) throws IOException {
        String filename = "D:\\huaweichussai\\test_data.txt";
        DataProceesor proceesor = new DataProceesor(filename);
        Map<Integer, Integer> map = proceesor.load_data();
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        List<Queue> queueList = new ArrayList<>();
        Queue<Integer> queue = new Queue<>();
        //Two Cases:
        //1.first和last相同的时候，该条队列结束
        //2.下一条准备进入队列的数据和last数据不同，该条队列结束
        System.out.println(map.get(map.size()-1));
        for (Map.Entry<Integer, Integer> entry : entries){
            int source = entry.getKey();
            int dest = entry.getValue();
            if(queue.getLast() == null && queue.getFirst() == null){
                queue.push(source);
                queue.push(dest);
            }
            else if(queue.getLast().getItem() == source && queue.getFirst().getItem() != dest){
//                queue.push(source);
                queue.push(dest);
            }
            else if(queue.getLast().getItem() == source && queue.getFirst().getItem() == dest){
//                queue.push(source);
                queue.push(dest);
                queueList.add(queue);
                queue = new Queue<>();
            }
            else {
                queueList.add(queue);
                queue = new Queue<>();
                queue.push(source);
                queue.push(dest);
            }
        }

        System.out.println(queueList.size());
        System.out.println(queueList.get(queueList.size()-1).getFirst().getItem());
    }

}

class DataProceesor{
    private String filename;

    public DataProceesor(String filename) {
        this.filename = filename;
    }

    public Map<Integer, Integer> load_data() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filename));
        String line = null;

        Map<Integer, Integer> map = new LinkedHashMap<>();

        while ((line = reader.readLine()) != null){
            String[] strings = line.split(",");
            map.put(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));

        }
        System.out.println(map.size()   );
        return map;
    }
}

