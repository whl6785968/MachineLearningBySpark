package DesignModel.Adapter.classAdapter;

public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        super.adapteeReuqest();
    }
}
