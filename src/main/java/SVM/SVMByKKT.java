package SVM;

import org.ejml.data.DenseMatrix64F;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SVMByKKT {
    List<double[][]> xs;
    List<Double> ys;
    double[] a;
    double b;
    double C = 0.6;
    Random random = new Random();
    DenseMatrix64F cache;


    public SVMByKKT(String filepath) throws IOException {
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

        for(int i = 0;i < size;i++){
            a[i] = 0;
        }

        cache = new DenseMatrix64F(a.length,1);
        cache.zero();

        b = 0;
    }

    public double calcgx(double[][] x){
        double result = 0;
        for(int i=0;i<xs.size();i++){
            double kernel = linearKernel(xs.get(i),x);
            result += a[i] * ys.get(i) * kernel;
        }

        return result + b;
    }

    public double calcE(int index){
        double[][] xi = xs.get(index);
        double e = calcgx(xi) - ys.get(index);
        return e;
    }

    public double kernel(double[][] x1,double[][] x2){
        double[][] transposeX1 = transpose(x1);
        double[][] matrixMul = matrixMul(transposeX1, x2);
        double result = matrixMul[0][0];
        double kernelResult = Math.pow(result,2);

        return kernelResult;
    }

    public double linearKernel(double[][] x1,double[][] x2){
        double[][] result = matrixMul(transpose(x1), x2);
        return result[0][0];
    }

    public double rbfKernel(double[][] x1,double[][] x2){
        double[][] sub = subForVec(x1, x2);
        double expo = -(vector2Norm(sub)/2*Math.pow(getStd(sub),2));
        double exp = Math.exp(expo);
        return exp;
    }

    public double[][] getW(){
        double[][] w = new double[xs.get(0).length][1];
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j < xs.get(i).length;j++){
                for(int k = 0; k< xs.get(i)[j].length;k++){
                    w[j][k] += a[i] * ys.get(i) * xs.get(i)[j][k];
                }
            }
        }

        return w;
    }

    public EijStorage selectSecondVariable(int index){
        double max = 0;
        int j = 0;
        double Ej = 0;
        double e1 = calcE(index);
        //cache里面存储的是已经被优化的乘子
        cache.set(index,0,1);
        List<Integer> validList = nonzero(cache, 0);
        //存储Ej和j
        EijStorage eijStorage = new EijStorage();


        if(validList.size() > 1){
            for(int i = 0;i < validList.size();i++){
                if(validList.get(i) == index){
                    continue;
                }
                double e2 = calcE(validList.get(i));
                if(Math.abs(e1 - e2) > max){
                    max = Math.abs(e1 - e2);
                    j = validList.get(i);
                    Ej = e2;
                }
            }

            eijStorage.setE(Ej);
            eijStorage.setIndex(j);
        }
        else {
            j = selectRandmJ(index);
            eijStorage.setIndex(j);
            eijStorage.setE(calcE(j));
        }


        return eijStorage;

    }

    public int selectRandmJ(int index){
        int j = index;
        while (j == index){
            j = random.nextInt(a.length);
        }
        return j;
    }

    public double getStd(double[][] x){
        double mean = getMean(x);
        double tmp = 0;
        double length = x.length;

        for(double[] d : x){
            for (double d1 : d){
                tmp += Math.pow(d1 - mean,2);
            }
        }

        return tmp / length;
    }

    public double getMean(double[][] x){
        double tmp = 0;
        double length = x.length;

        for(double[] d : x){
            for (double d1 : d){
                tmp += d1;
            }
        }

        return  tmp/length;
    }

    public int inner(int i){
        double Ei = calcE(i);
        if (((ys.get(i) * Ei < -0.001) && (a[i] < C)) || ((ys.get(i) * Ei > 0.001) && (a[i] > 0))) {
            EijStorage eijStorage = selectSecondVariable(i);
            int j = eijStorage.getIndex();
            double Ej = eijStorage.getE();


            double old_a1 = a[i];
            double old_a2 = a[j];

            double L;
            double H;

            if(ys.get(i) != ys.get(j)){
                L = Math.max(0,old_a2-old_a1);
                H = Math.min(C,C+old_a2 - old_a1);
            }
            else {
                L = Math.max(0,old_a2 + old_a1 - C);
                H = Math.min(C,old_a2 + old_a1);
            }

            if(L == H){
                return 0;
            }

            double eta = linearKernel(xs.get(i),xs.get(i)) + linearKernel(xs.get(j),xs.get(j)) - 2*linearKernel(xs.get(i),xs.get(j));
            if(eta < 0){
                return 0;
            }

            double unc_a2 = old_a2 + (ys.get(j) * (Ei - Ej)) / eta;

            double new_a2;
            if(unc_a2 > H){
                new_a2 = H;
            }
            else if(unc_a2 < L){
                new_a2 = L;
            }
            else
            {
                new_a2 = unc_a2;
            }

            double new_a1 = old_a1 + ys.get(i) * ys.get(j) * (old_a2 - new_a2);

            if(Math.abs(new_a2 - old_a2) < 0.00001){
                return 0;
            }

            a[i] = new_a1;
            a[j] = new_a2;

            double new_b1 = -Ei - ys.get(i) * linearKernel(xs.get(i),xs.get(i)) * (new_a1 - old_a1) -
                    ys.get(j) * linearKernel(xs.get(j),xs.get(j)) * (new_a2 - old_a2) + b;

            double new_b2 = -Ej - ys.get(i) * linearKernel(xs.get(i),xs.get(j)) * (new_a1 - old_a1) -
                    ys.get(j) * linearKernel(xs.get(j),xs.get(j)) * (new_a2 - old_a2) + b;

            if((a[i] > 0 && a[i] < C) && (a[j] > 0 && a[j] < C)){
                b = new_b1;
            }
            else {
                b = (new_b1 + new_b2) / 2.0;
            }

            return 1;
        }
        else {
            return 0;
        }

    }

    public void train(){
        int iter = 0;
        int maxIt = 40;
        int pair_changed = 0;
        boolean entireSet = true;
        //外循环停机条件
        //1.iter < maxIt
        //2.所有乘子对停止更新
        //3.entireSet是否遍历整个数据集
        //外层循环首先遍历间隔边界上的支持向量点，检查其是否满足KKT条件，如果这些样本都满足KKT条件，则遍历整个数据集
        //在此的逻辑是第一次遍历整个数据集，若有变量被优化，切换为false，即去遍历间隔区间上的支持向量点，若没有遍历被优化
        //依然遍历整个数据集
        while ((iter < maxIt) && ((pair_changed > 0) || entireSet)){
            pair_changed = 0;
            if(entireSet){
                for(int i=0; i < a.length;i++) {
                    int inner = inner(i);
                    pair_changed += inner;
                }
                iter += 1;
            }
            else {
                List<Integer> validAs = getABetween0C();
                for(Integer validA : validAs){
                    pair_changed += inner(validA);
                }
                iter += 1;
            }

            if(entireSet){
                entireSet = false;
            }
            else if(pair_changed == 0){
                entireSet = true;
            }

        }
    }

    public List<Integer> getABetween0C(){
        List<Integer> list = new ArrayList<Integer>();

        for(int i = 0;i< a.length;i++){
            if(a[i] > 0 && a[i] < C){
                list.add(i);
            }
        }

        return list;
    }

    public List<Integer> nonzero(DenseMatrix64F cache,int col){
        List<Integer> list = new ArrayList<Integer>();

        for (int i=0;i<cache.numRows;i++){
            if(cache.get(i,col) != 0){
                list.add(i);
            }
        }

        return list;
    }

    public void predict(double[][] x){
        double[][] w = getW();
        double result = matrixMul(transpose(w),x)[0][0] + b;
        if(result > 0){
            System.out.println(1);
        }
        else {
            System.out.println(-1);
        }
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

    public double vector2Norm(double[][] x){
        double result = 0;
        for(int i = 0;i<x.length;i++){
            for(int j = 0;j<x[i].length;j++){
                result += Math.pow(x[i][j],2);
            }
        }

        return Math.sqrt(result);
    }

    public double[][] subForVec(double[][] x1,double[][] x2){
        double[][] result = new double[x1.length][x1[0].length];
        for(int i = 0;i<x1.length;i++){
            for(int j = 0;j<x2[i].length;j++){
                result[i][j] = x1[i][j] - x2[i][j];
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
//        String filepath = "C:\\Users\\dell\\Desktop\\date.txt";
        String filepath = "C:\\Users\\dell\\Desktop\\data\\waterMelon2.0.txt";
//        String filepath = "D:\\Algorithm\\testSet.txt";
        SVMByKKT svmByKKT = new SVMByKKT(filepath);
        svmByKKT.train();
        double[][] w = svmByKKT.getW();
        for(double[] d : w){
            for (double d1 : d){
                System.out.println(d1);
            }
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line = null;
        List<double[][]> xs = new ArrayList();
        while ((line = bufferedReader.readLine()) != null){
            String[] s = line.split(" ");
            double x[][] = new double[2][1];
            for(int i = 0; i < 2; i++){
                x[i][0] = Double.parseDouble(s[i]);
            }
            xs.add(x);
        }

        for (int i = 0;i<xs.size();i++){
            svmByKKT.predict(xs.get(i));
        }

        System.out.println(svmByKKT.b);

    }
}
