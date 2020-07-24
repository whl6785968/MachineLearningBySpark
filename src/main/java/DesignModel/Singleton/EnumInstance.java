package DesignModel.Singleton;

//天然可序列化机制
public enum EnumInstance {
    INSTANCE{
        protected void printest(){
            System.out.println("print test");
        }
    };

    protected abstract void printest();

    private Object data;

    public Object getData(){
        return data;
    }

    public void setData(Object data){
        this.data = data;
    }

    public static Enum getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        EnumInstance instance = (EnumInstance) EnumInstance.getInstance();
        instance.printest();
    }

}
