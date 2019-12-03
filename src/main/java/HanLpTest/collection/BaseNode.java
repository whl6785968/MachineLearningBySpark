package HanLpTest.collection;



import com.hankcs.hanlp.corpus.io.ByteArray;

import java.io.*;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseNode<V> implements Comparable<BaseNode> {
    static final Status[] ARRAY_STATUS = Status.values();

    protected BaseNode[] child;

    protected Status status;

    protected char c;

    protected V value;

    public BaseNode<V> transition(String path,int begin){
        BaseNode<V> cur = this;

        for (int i=begin;i<path.length();i++){
            cur = cur.getChild(path.charAt(i));
            if(cur == null || cur.status == Status.UNDIFINED_0) return null;
        }

        return cur;
    }

    public BaseNode<V> transition(char[] path,int begin){
        BaseNode<V> cur = this;

        for (int i=begin;i<path.length;i++){
            cur = cur.getChild(path[i]);
            if(cur == null || cur.status == Status.UNDIFINED_0) return null;
        }

        return cur;
    }

    protected abstract boolean addChild(BaseNode node);

    protected char getChar(){
        return c;
    }

    public abstract BaseNode getChild(char c);

    public final V getValue(){
        return value;
    }

    public final void setValue(V value){
        this.value = value;
    }

    public int compareTo(BaseNode other){
        return compareTo(other);
    }

    public int compareTo(char other){
        if(this.c > other){
            return 1;
        }

        if(this.c < other){
            return -1;
        }

        return 0;

    }

    public Status getStatus(){
        return status;
    }

    protected void walk(StringBuilder sb, Set<Map.Entry<String,V>> entrySet){
        sb.append(c);
        if(status == Status.WORD_MIDDLE_2 || status == Status.WORD_END_3){
            entrySet.add(new TrieEntry(sb.toString(),value));
        }

        if (child == null) return;

        for(BaseNode node : child){
            if(node == null) return;
            node.walk(new StringBuilder(sb.toString()),entrySet);
        }

    }

    protected void walkToSave(DataOutputStream out) throws IOException {
        out.writeChar(c);
        out.writeInt(status.ordinal());
        int childSize = 0;
        if(child != null) childSize = child.length;
        out.writeInt(childSize);
        if(child == null) return;
        for(BaseNode node:child){
            node.walkToSave(out);
        }
    }

    protected void walkToSave(ObjectOutput out) throws IOException {
        out.writeChar(c);
        out.writeInt(status.ordinal());

        if(status == Status.WORD_MIDDLE_2 || status == Status.WORD_END_3){
            out.writeObject(value);
        }

        int childSize = 0;
        if(child != null) childSize = child.length;
        out.writeInt(childSize);
        if(child == null) return;
        for(BaseNode node:child){
            node.walkToSave(out);
        }
    }

    protected void walkToLoad(ByteArray byteArray){

    }

    protected void walkToLoad(ObjectInput byteArray) throws IOException, ClassNotFoundException {
        c = byteArray.readChar();
        status = ARRAY_STATUS[byteArray.readInt()];
        if(status == Status.WORD_END_3 || status == Status.WORD_MIDDLE_2){
            value = (V)byteArray.readObject();
        }

        int childSize = byteArray.readInt();
        child = new BaseNode[childSize];

        for(int i=0;i<childSize;i++){
            child[i] = new Node<V>();
            child[i].walkToLoad(byteArray);
        }

    }



    public enum Status{
        UNDIFINED_0,
        NOT_WORD_1,
        WORD_MIDDLE_2,
        WORD_END_3,
    }

    public class TrieEntry extends AbstractMap.SimpleEntry<String,V> implements Comparable<TrieEntry>{
        public TrieEntry(String key,V value){
            super(key,value);
        }

        @Override
        public int compareTo(TrieEntry o) {
            return getKey().compareTo(o.getKey());
        }
    }
}
