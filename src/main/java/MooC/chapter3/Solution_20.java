package MooC.chapter3;

import java.util.NoSuchElementException;

public class Solution_20 {
    public class Stack<Item> {
        private class Node<Item> {
            Item item;
            Node next;

            public Node() {

            }
        }

        private Node<Item> first = null;
        private int n;

        public int getSize() {
            return this.n;
        }

        public boolean isEmpty() {
            return this.n == 0;
        }

        public void push(Item item) {
            Node oldFirst = first;
            Node node = new Node();
            node.item = item;
            node.next = oldFirst;

            this.first = node;
            this.n++;
        }

        public Item pop() {
            if (this.isEmpty()) {
                throw new NoSuchElementException();
            }

            Item item = this.first.item;
            this.first = this.first.next;
            --n;

            return item;
        }

        public Item peek() {
            if (this.isEmpty()) {
                throw new NoSuchElementException();
            }

            return this.first.item;
        }
    }

    public boolean isValid(String s) {
        Stack<Character> stringStack = new Stack<>();
        int leftNum = 0;
        int rightNum = 0;

        for(int i=0;i < s.length();i++){
            char c = s.charAt(i);
            if(c == '[' || c == '{' || c == '('){
                stringStack.push(c);
                leftNum += 1;
            }
            else {
                rightNum += 1;
                if(stringStack.getSize() != 0){
                    char character = stringStack.pop();

                    boolean valid = valid(character, c);
                    if (!valid){
                        return false;
                    }
                }
            }
        }

        if(stringStack.getSize() != 0 || rightNum > leftNum){
            return false;
        }

        return true;
    }

    public boolean valid(char left,char right){
        if(right == ')' && left =='(') return true;
        else if(right=='}' && left=='{') return true;
        else if(right==']' && left=='[') return true;

        return false;
    }

}
