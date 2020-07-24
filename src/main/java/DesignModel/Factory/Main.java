package DesignModel.Factory;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        VedioFactory vedioFactory = new VedioFactory();
        Vedio vedio = vedioFactory.getVedio("javpythob");
        vedio.produce();

        Vedio vedio1 = vedioFactory.getVedio(Java.class);
        vedio1.produce();
    }
}
