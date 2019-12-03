package HanLpTest.collection;

public class Node<V> extends BaseNode {
    public Node(char c, Status status, V value)
    {
        this.c = c;
        this.status = status;
        this.value = value;
    }

    @Override
    protected boolean addChild(BaseNode node) {
        boolean add = false;
        if(child == null){
            child = new BaseNode[0];
        }
        int index  = ArrayTool.binarySearch(child,node);

        if(index > 0){
            BaseNode target = child[index];
            switch (node.status){
                case UNDIFINED_0:
                    if (target.status != Status.NOT_WORD_1)
                    {
                        target.status = Status.NOT_WORD_1;
                        target.value = null;
                        add = true;
                    }
                    break;
                case NOT_WORD_1:
                    if (target.status == Status.WORD_END_3)
                    {
                        target.status = Status.WORD_MIDDLE_2;
                    }
                    break;
                case WORD_END_3:
                    if (target.status != Status.WORD_END_3)
                    {
                        target.status = Status.WORD_MIDDLE_2;
                    }
                    if (target.getValue() == null)
                    {
                        add = true;
                    }
                    target.setValue(node.getValue());
                    break;

            }
        }
        else {
            BaseNode newChild[] = new BaseNode[child.length + 1];
            int insert = -(index + 1);
            System.arraycopy(child, 0, newChild, 0, insert);
            System.arraycopy(child, insert, newChild, insert + 1, child.length - insert);
            newChild[insert] = node;
            child = newChild;
            add = true;
        }
        return add;
    }

    @Override
    public BaseNode getChild(char c) {
        if (child == null) return null;
        int index = ArrayTool.binarySearch(child, c);
        if (index < 0) return null;

        return child[index];
    }

    public Node(){

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
