package DesignModel.Adapter.classAdapter;

public class ConcreateTarget implements Target {
    @Override
    public void request() {
        System.out.println("concreteTarget");
    }
}
