package MooC.chapter3;

public class LoopQueue<E>{
    private E[] data;
    private int front,tail;
    private int size;

    public LoopQueue(int capacity){
        data  = (E[]) new Object[capacity];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length - 1;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return front == tail;
    }

    public void enqueue(E item){
        if((tail + 1) % data.length == front){
            resize(getCapacity() * 2);
        }

        data[tail] = item;
        tail = (tail + 1) % data.length;
    }

    public void resize(int capacity){
        E[] new_data = (E[]) new Object[capacity + 1];
        for(int i = 0;i < size;i++){
            //防止越界，取原数组前面的元素存在新数组后面
            new_data[i] = data[(i+front) % data.length];
        }

        data = new_data;
    }

    public E deque(){
         if(this.isEmpty()){
             throw new IllegalArgumentException(("queue is empty!"));
         }

         E item = data[front];
         data[front] = null;
         front = (front + 1) % data.length;
         size--;

         if(size == this.getCapacity() / 4 && getCapacity() /2 != 0){
             this.resize(this.getCapacity() / 2);
         }

         return item;
    }



}
