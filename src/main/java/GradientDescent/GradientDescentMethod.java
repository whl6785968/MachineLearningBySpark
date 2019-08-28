package GradientDescent;

public class GradientDescentMethod {
    public double getFuntion(Double[] x){
        Double f = x[0] - x[1] + 2*Math.pow(x[0],2)+2*x[0]*x[1]+Math.pow(x[1],2);
        return f;
    }

    public double getBiaDerivative1(Double[] x){
        Double d1 = 1 + 4*x[0] + 2*x[1];
        return d1;
    }

    public double getBiaDerivative2(Double[] x){
        Double d2 = -1 + 2*x[0] + 2*x[1];
        return d2;
    }

    public Double[] getGradient(Double[] x){
        Double[] gradient = new Double[2];
        gradient[0] = getBiaDerivative1(x);
        gradient[1] = getBiaDerivative2(x);
        return gradient;
    }

    public Double[] getSearchDirective(Double[] gradient){
        Double[] d = new Double[2];
        d[0] = -gradient[0];
        d[1] = -gradient[1];
        return d;
    }

//    public void updateX(){
//        x[0] = x[0] + step*d[0];
//        x[1] = x[1] + step*d[1];
//    }

    public Double getNorm2(Double[] gradient){
        Double norm2 = Math.sqrt(Math.pow(gradient[0],2)+Math.pow(gradient[1],2));
        return norm2;
    }

    public void calc(){
        Double[] x = new Double[2];
        Double[] gradient;
        Double[] d;
        Double newValue = 0.0;
        Double oldValue = 0.0;
        Double step = 0.05;
        int sum = 0;
        x[0] = 0.0;
        x[1] = 0.0;
        while (true){
            System.out.println("x:[" + x[0] + "," +x[1] +"]");
            gradient = getGradient(x);
            System.out.println("gradient:[" + gradient[0] + "," +gradient[1] +"]");
            Double norm2 = getNorm2(gradient);
            System.out.println("norm2:" + norm2);
//            if(norm2 < 0.3){
//                break;
//            }
            if(newValue>oldValue){
                break;
            }
            oldValue = newValue;
            d = getSearchDirective(gradient);
            System.out.println("d:[" + d[0] + "," +d[1] +"]");
            System.out.println("value is " + getFuntion(x));
            step = step/(sum+1)+0.001;
            x[0] = x[0] + step*d[0];
            x[1] = x[1] + step*d[1];
            newValue = getFuntion(x);
            sum += 1;
        }
        System.out.println("[" + x[0] + "," + x[1] +"]");
        System.out.println("the min value is " + getFuntion(x));
    }

    public static void main(String[] args) {
        GradientDescentMethod gradientDescentMethod = new GradientDescentMethod();
        gradientDescentMethod.calc();
    }
}
