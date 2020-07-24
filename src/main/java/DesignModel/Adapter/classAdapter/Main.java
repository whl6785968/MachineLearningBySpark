package DesignModel.Adapter.classAdapter;

public class Main {
    public static void main(String[] args) {
//        Target target = new ConcreateTarget();
//        target.request();

        Target target1 = new Adapter();
        target1.request();
    }
}
