package SVM;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SVM {
    List<double[][]> xs;
    List<Double> ys;
    double[] a;
    double[][] w;
    double b;
    double C = 0.6;
    int depth = -1;
    int old_index = 0;
    Random random = new Random();

    public SVM(){

    }

    public SVM(String filepath) throws IOException {
        init(filepath);
    }

    public void load_File(String filePath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line = null;
        xs = new ArrayList<double[][]>();
        ys = new ArrayList<Double>();
        while ((line = bufferedReader.readLine())!= null){
            String[] strings = line.split(" ");
            double x[][] = new double[2][1];
            for(int i = 0; i < 2; i++){
                x[i][0] = Double.parseDouble(strings[i+1]);
            }
            xs.add(x);
            ys.add(Double.parseDouble(strings[3]));
        }
    }

    public void init(String filepath) throws IOException {
        load_File(filepath);
        int size = xs.size();
        a = new double[size];
        Random random = new Random();

        for(int i = 0;i < size;i++){
            a[i] = 0;
        }

        w = new double[1][2];
        w[0][0] = random.nextDouble();
        w[0][1] = random.nextDouble();
        b = random.nextDouble();
    }

    public double calcgx(double[][] x){
        double result = 0;
        for(int i=0;i<xs.size();i++){
            double kernel = kernel(x, xs.get(i));
            result += a[i] * ys.get(i) * kernel;
        }

        return result + b;
    }

    public double calcE(int index){
        double[][] xi = xs.get(index);
        double e = calcgx(xi) - ys.get(index);
        return e;
    }

    public int searchWorstKKTPoint(){
        for(int i = 0;i < a.length;i++){
            if((0< a[i] && a[i] < C && Math.round(ys.get(i) * calcgx(xs.get(i))) != 1)){
                return i;
            }
        }
        return 0;
    }

    public int searchWorstKKTPoint(int index){
        for(int i = 0;i < a.length;i++){
            if((0< a[i] && a[i] < C && Math.round(ys.get(i) * calcgx(xs.get(i))) != 1) && i != index){
                return i;
            }
        }
        return 0;
    }

    public boolean isSatisfiedKKTCond(){
        boolean satisfied = true;
        for(int i = 0; i < a.length; i++){
            if(a[i] == 0 && !(ys.get(i) * calcgx(xs.get(i)) >= 1.0)){
                satisfied = false;
                break;
            }
            else if(0 < a[i] && a[i] < C && Math.round(ys.get(i) * calcgx(xs.get(i))) != 1){
                satisfied = false;
                break;
            }
            else if(a[i] == C && !(ys.get(i) * calcgx(xs.get(i)) <= 1.0)){
                satisfied = false;
                break;
            }
        }

        return satisfied;
    }

    public int selectSecondVariable(int index){
        double max = 0;
        int index2 = 0;
        double e1 = calcE(index);
//        for(int i = 0;i < xs.size();i++){
//            if(index != i){
//                double e2 = calcE(i);
//                if(Math.abs(e1 - e2) > max){
//                    max = Math.abs(e1 - e2);
//                    index2 = i;
//                }
//            }
//        }

        int i = index;
        while (i == index){
             i =random.nextInt(a.length);
        }

        return i;
    }

    public boolean isObviousDescent(double old_a2,double new_a2){
        boolean isDescent = true;
        if(Math.abs(new_a2 - old_a2) < 0.01){
            isDescent = false;
        }
        return isDescent;
    }

    public void descent(int index1, int index2){
        double old_a1 = a[index1];
        double old_a2 = a[index2];

        double y1 = ys.get(index1);
        double y2 = ys.get(index2);

        double L;
        double H;
        if(y1 != y2){
            L = Math.max(0.0,old_a2-old_a1);
            H = Math.min(C,C+old_a2-old_a1);
        }
        else {
            L = Math.max(0.0,old_a2+old_a1-C);
            H = Math.min(C,old_a2+old_a1);
        }

        double standardFactor = kernel(xs.get(index1),xs.get(index1)) + kernel(xs.get(index2),xs.get(index2)) - 2 * kernel(xs.get(index1),xs.get(index2));

        double unc_a2 = old_a2 + ys.get(index2) * (calcE(index1) - calcE(index2)) / standardFactor;

        double new_a2;

        if(unc_a2 > H){
            new_a2 = H;
        }
        else if(unc_a2 < L){
            new_a2 = L;
        }
        else{
            new_a2 = unc_a2;
        }

        double new_a1 = old_a1 + ys.get(index1) * ys.get(index2) * (old_a2 - new_a2);


        update(index1,index2,old_a1,old_a2,new_a1,new_a2);
//        if(isObviousDescent(old_a2,new_a2)){
//            update(index1,index2,old_a1,old_a2,new_a1,new_a2);
//            depth = -1;
//        }
//        else {
//            if(depth < a.length){
//                depth += 1;
//                descent(index1,depth);
//            }
//            else {
//                int index11 = searchWorstKKTPoint(index1);
//                int index22 = selectSecondVariable(index11);
//                descent(index11,index22);
//            }
//        }
    }

    public void update(int index1,int index2,double old_a1,double old_a2,double new_a1,double new_a2){
        a[index1] = new_a1;
        a[index2] = new_a2;

        getW();

        double new_b1 = -calcE(index1) - ys.get(index1) * kernel(xs.get(index1),xs.get(index1)) * (new_a1 - old_a1) -
                ys.get(index2) * kernel(xs.get(index2),xs.get(index1)) * (new_a2 - old_a2) + b;

        double new_b2 = -calcE(index2) - ys.get(index1) * kernel(xs.get(index1),xs.get(index2)) * (new_a1 - old_a1) -
                ys.get(index2) * kernel(xs.get(index2),xs.get(index2)) * (new_a2 - old_a2) + b;

        b = (new_b1 + new_b2) / 2;
    }

    public double[] train(){
        long start = System.currentTimeMillis();
        System.out.println("开始训练于 :" + start);
        int count = 0;
        while (count < 100000){
            for(int i = 0;i < a.length;i++){
                try {
                    int index2 = selectSecondVariable(i);
                    double old_a1 = a[i];
                    double old_a2 = a[index2];

                    double y1 = ys.get(i);
                    double y2 = ys.get(index2);

                    double L;
                    double H;
                    if(y1 != y2){
                        L = Math.max(0.0,old_a2-old_a1);
                        H = Math.min(C,C+old_a2-old_a1);
                    }
                    else {
                        L = Math.max(0.0,old_a2+old_a1-C);
                        H = Math.min(C,old_a2+old_a1);
                    }

                    double standardFactor = kernel(xs.get(i),xs.get(i)) + kernel(xs.get(index2),xs.get(index2)) - 2 * kernel(xs.get(i),xs.get(index2));

                    double unc_a2 = old_a2 + ys.get(index2) * (calcE(i) - calcE(index2)) / standardFactor;

                    double new_a2;

                    if(unc_a2 > H){
                        new_a2 = H;
                    }
                    else if(unc_a2 < L){
                        new_a2 = L;
                    }
                    else{
                        new_a2 = unc_a2;
                    }

                    double new_a1 = old_a1 + ys.get(i) * ys.get(index2) * (old_a2 - new_a2);

                    if((new_a2 - old_a2) < 0.00001){
                        continue;
                    }

                    update(i,index2,old_a1,old_a2,new_a1,new_a2);

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
//            int index1 = searchWorstKKTPoint(old_index);
//            int index2 = selectSecondVariable(index1);
//            descent(index1,index2);

            count += 1;
        }

        System.out.println("结束训练于 :" + (System.currentTimeMillis()-start));
        return a;
    }

    public void getW(){
        for(int i = 0;i < a.length; i++){
           for(int j = 0; j < w.length; j++){
               for(int k = 0; k < w[j].length;k++){
                   w[j][k] += a[i] * ys.get(i) * w[j][k];
               }
           }
        }
    }
    
    public double kernel(double[][] x1,double[][] x2){
        double[][] transposeX1 = transpose(x1);
        double[][] matrixMul = matrixMul(transposeX1, x2);
        double result = matrixMul[0][0];
        double kernelResult = Math.pow(result,2);

        return kernelResult;
    }

    public double[][] matrixMul(double[][] martrix1,double[][] martrix2){
        if(martrix1[0].length == martrix2.length){
            if(martrix1.length == 1 && martrix2[0].length == 1){
                double tmp = 0;
                for(int i = 0; i < martrix2.length; i++){
                    tmp += martrix1[0][i] * martrix2[i][0];
                }

                double[][] result = new double[1][1];
                result[0][0] = tmp;

                return result;
            }
            else {
                int row = martrix1.length;
                int col = martrix2[0].length;
                double[][] result = new double[row][col];

                for(int i = 0;i< martrix1.length;i++){
                    for(int j = 0;j< martrix2[0].length;j++){
                        for(int k = 0;k < martrix2.length;k++){
                            result[i][j] += martrix1[i][k] * martrix2[k][j];
                        }
                    }
                }
                return result;
            }
        }
        else {
            throw new IllegalArgumentException("第一矩阵的列数要等于第二个矩阵的行数");
        }

    }

    public double[][] transpose(double[][] matrix){
        int row = matrix.length;
        int col = matrix[0].length;

        double[][] transposedMatrix = new double[col][row];
        for(int i = 0; i < col; i++){
            for(int j = 0;j < row; j++){
                transposedMatrix[i][j] = matrix[j][i];
            }
        }

        return transposedMatrix;
    }

    public void predict(double[][] x){
        double result = matrixMul(w, x)[0][0] + b;
        System.out.println("b is " + b);
        if(result >= 0){
            System.out.println(1);
        }
        else {
            System.out.println(-1);
        }

    }


    public static void main(String[] args) throws IOException {
        String filepath = "C:\\Users\\dell\\Desktop\\data\\waterMelon2.0.txt";
        SVM svm = new SVM();
        svm.init(filepath);
        double[][] w = svm.w;
        for(double[] d1 : w){
            for(double d : d1){
                System.out.println(d);
            }
        }
        svm.train();

        double[][] w1 = svm.w;
        for(double[] d1 : w1){
            for(double d : d1){
                System.out.println(d);
            }
        }

        double[][] x = new double[2][1];
        x[0][0] = 0.630;
        x[1][0] = 0.260;


        svm.predict(x);

    }
}
