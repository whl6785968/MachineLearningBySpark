package MooC.chapter2;

import edu.princeton.cs.algs4.Stack;

public class Array<E> {
    private E[] data;
    private int size;

    public Array(int capacity){
        data = (E[]) new Object[capacity];
        size = 0;
    }

    public Array(){
        this(10);
    }

    public int getSize(){
        return size;
    }

    public int getCapacity(){
        return data.length;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void addLast(E e){
        add(size,e);
    }

    public void addFirst(E e){
        add(0,e);
    }

    public void add(int index,E e){
        if(index == getCapacity()){
            throw new IllegalArgumentException("Array is full");
        }

        if(index < 0 || index > size){
            throw new IllegalArgumentException();
        }

        for(int i = size - 1;i >= index;i--){
            data[i+1] = data[i];
        }

        data[index] = e;

        size ++;
    }

    public boolean contains(E e){
        for(int i=0;i < size;i++){
            if(data[i].equals(e)){
                return true;
            }
        }

        return false;
    }

    public int find(E e){
        for(int i=0;i<size;i++){
            if(data[i] == e){
                return i;
            }
        }

        return -1;
    }

    public void remove(int index){
        for(int i = index;i < size;i++){
            data[i] = data[i+1];
        }

        size --;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Array size:%d,Array Capacity:%d\n",size,data.length));
        sb.append("[");
        for(int i = 0;i < size - 1;i++){
            sb.append(data[i]);
            sb.append(",");
        }

        sb.append(data[size-1]);
        sb.append("]");


        return sb.toString();
    }

    public static void main(String[] args) {
        Array<Integer> array = new Array<>(20);

        for(int i=0;i<10;i++){
            array.addLast(i);
        }

        array.add(1,100);
        array.addFirst(20);

        array.remove(2);

        System.out.println(array);
    }
}
