package DesignModel.Factory;

public class VedioFactory {
    public Vedio getVedio(String type){
        if(type.equalsIgnoreCase("java")){
            return new Java();
        }
        else{
            return new Python();
        }
    }


    public Vedio getVedio(Class c) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Vedio vedio = null;

        vedio = (Vedio) Class.forName(c.getName()).newInstance();
        return vedio;
    }
}
