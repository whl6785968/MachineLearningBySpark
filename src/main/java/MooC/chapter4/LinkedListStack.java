package MooC.chapter4;

public class LinkedListStack<E> {
    private CustomizedLinkedList<E> customizedLinkedList;

    public LinkedListStack(){
        customizedLinkedList = new CustomizedLinkedList<>();
    }

    public void push(E e){
        customizedLinkedList.add(0,e);
    }

    public E pop(){
        return customizedLinkedList.removeFirst();
    }

    public E peek(){
        return customizedLinkedList.getFirst();
    }

    public static void main(String[] args) {
        LinkedListStack<Integer> stack = new LinkedListStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.pop());
        System.out.println(stack.peek());
    }
}
