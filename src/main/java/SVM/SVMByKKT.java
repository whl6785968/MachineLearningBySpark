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
            String[] strings = line.split(",");
            double x[][] = new double[5][1];
            for(int i = 0; i < 5; i++){
                x[i][0] = Double.parseDouble(strings[i]);
            }
            xs.add(x);
            ys.add(Double.parseDouble(strings[5]));
        }

    }

    public void init(String filepath) throws IOException {
        load_File(filepath);
        int size = xs.size();
        a = new double[size];

        for(int i = 0;i < size;i++){
            a[i] = 0;
        }

        cache = new DenseMatrix64F(a.length,2);
        cache.zero();

        b = 0;
    }

    public double calcgx(double[][] x){
        double result = 0;
        for(int i=0;i<xs.size();i++){
            double kernel = rbfKernel(xs.get(i),x);
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
        double gamma = 0.2;
        double expo = -(gamma * vector2Norm(sub)/2*Math.pow(getStd(sub),2));
        double exp = Math.exp(expo);
        return exp;
    }

    public double rbfKernel2(double[][] x1,double[][] x2){
        double[][] sub = subForVec(x1, x2);
        double norm2 = vector2Norm(sub);
        double gamma = 0.2;

        double result = Math.exp(gamma * Math.pow(norm2,2));

        return result;
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

    public EijStorage selectSecondVariable(int index,double Ei){
        int maxK = -1;
        double maxDeltaE = 0;
        double Ej = 0;

        //cache里面存储的是已经被优化的乘子
        cache.set(index,0,1);
        cache.set(index,1,Ei);

        List<Integer> validList = nonzero(cache, 0);
        //存储Ej和j
        EijStorage eijStorage = new EijStorage();


        if(validList.size() > 1){
            for(int i = 0;i < validList.size();i++){
                if(validList.get(i) == index){
                    continue;
                }
                double Ek = calcE(validList.get(i));
                double delataE = Math.abs(Ei - Ek);
                if(delataE >= maxDeltaE){
                    maxDeltaE = delataE;
                    maxK = validList.get(i);
                    Ej = Ek;
                }
            }

            eijStorage.setEj(Ej);
            eijStorage.setJ(maxK);
        }
        else {

            eijStorage.setJ(selectRandmJ(index));
            eijStorage.setEj(calcE(eijStorage.getJ()));
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

    public void setAppropriateEj(double Ei,int j){
        if(Ei >= 0){
            double min = 0;
            for(int i = 0; i < cache.numRows;i++){
                if(cache.get(i,1) < min){
                    min = cache.get(i,1);
                }
            }

            cache.set(j,1,min);
        }
        else {
            double max = 0;
            for(int i = 0; i < cache.numRows;i++){
                if(cache.get(i,1) > max){
                    max = cache.get(i,1);
                }
            }

            cache.set(j,1,max);
        }
    }

    public void updateCache(int j){
        cache.set(j,0,1);
        cache.set(j,1,calcE(j));
    }

    public int inner(int i){
        double Ei = calcE(i);
//        System.out.println("Ei is " + Ei);
        if (((ys.get(i) * Ei < -0.001) && (a[i] < C)) || ((ys.get(i) * Ei > 0.001) && (a[i] > 0))) {
            EijStorage eijStorage = selectSecondVariable(i,Ei);
            int j = eijStorage.getJ();
            double Ej = eijStorage.getEj();

            double old_a1 = a[i];
            double old_a2 = a[eijStorage.getJ()];

            double L;
            double H;

            if(ys.get(i) != ys.get(eijStorage.getJ())){
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

            double eta =  2*rbfKernel(xs.get(i),xs.get(eijStorage.getJ())) - rbfKernel(xs.get(i),xs.get(i)) - rbfKernel(xs.get(eijStorage.getJ()),xs.get(eijStorage.getJ()));
            if(eta >= 0){
                return 0;
            }

            double new_a2;
//            double unc_a2 = old_a2 + (ys.get(eijStorage.getJ()) * (Ei - eijStorage.getEj())) / eta;
            double unc_a2 = -(old_a2 + (ys.get(j) * (Ei - Ej))) / eta;
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

            a[eijStorage.getJ()] = new_a2;
            updateCache(j);


            if(Math.abs(new_a2 - old_a2) < 0.00001){
                return 0;
            }

            double new_a1 = old_a1 + ys.get(i) * ys.get(eijStorage.getJ()) * (old_a2 - new_a2);
            a[i] = new_a1;
//            double new_Ei = calcE(i);
//            setAppropriateEj(Ei,eijStorage.getJ());
            updateCache(i);
            double new_b1 = b - Ei - ys.get(i) * rbfKernel(xs.get(i),xs.get(i)) * (new_a1 - old_a1) -
                    ys.get(j) * rbfKernel(xs.get(i),xs.get(j)) * (new_a2 - old_a2);

//            double new_b2 = -eijStorage.getEj() - ys.get(i) * linearKernel(xs.get(i),xs.get(eijStorage.getJ())) * (new_a1 - old_a1) -
//                    ys.get(eijStorage.getJ()) * linearKernel(xs.get(eijStorage.getJ()),xs.get(eijStorage.getJ())) * (new_a2 - old_a2) + b;

            double new_b2 = b - Ej - ys.get(i) * rbfKernel(xs.get(i),xs.get(j)) * (new_a1 - old_a1) -
                    ys.get(j) * rbfKernel(xs.get(j),xs.get(j)) * (new_a2 - old_a2);

            if(a[i] > 0 && a[i] < C){
                b = new_b1;
            }
            else if(a[eijStorage.getJ()] > 0 && a[eijStorage.getJ()] < C){
                b = new_b2;
            }
            else {
                b = (new_b1 + new_b2) / 2.0;
            }

            double new_Ei = calcE(i);
            cache.set(i,1,new_Ei);

            return 1;
        }
        else {
            return 0;
        }

    }

    public void train(){
        int iter = 0;
        int maxIt = 1000;
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

    public double predict(double[][] x){
        double[][] w = getW();
//        for(double[] d : w){
//            for (double d1 : d){
//                System.out.println(d1);
//            }
//        }
        double result = matrixMul(transpose(w),x)[0][0] + b;
        if(result >= 0){
            return 1.0;
        }
        else {
            return -1.0;
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

    public double prf(String filepath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line = null;
        List<double[][]> testData = new ArrayList();
        List<Double> testLabel = new ArrayList<Double>();

        while ((line = bufferedReader.readLine()) != null){
            String[] s = line.split(",");
            double xi[][] = new double[5][1];
            for(int i = 0; i < 5; i++){
                xi[i][0] = Double.parseDouble(s[i]);
            }
            testData.add(xi);

            testLabel.add(Double.parseDouble(s[5]));
        }



        double count = 0;

       for (int i = 0;i < testData.size();i++){

           double prediction = predict(testData.get(i));
           double label = testLabel.get(i);
           if(prediction == label){
               count += 1;
           }
       }

        return count/testData.size();
    }

    public static void main(String[] args) throws IOException {
//        String filepath = "C:\\Users\\dell\\Desktop\\date.txt";
        int count = 0;
        long start = System.currentTimeMillis();
        while (count < 20){
            String filepath = "C:\\Users\\dell\\Desktop\\waterData\\trainForSvm.txt";
//        String filepath = "D:\\Algorithm\\testSet.txt";
            SVMByKKT svmByKKT = new SVMByKKT(filepath);
            svmByKKT.train();

            String testFilePath = "C:\\Users\\dell\\Desktop\\waterData\\testForSvm.txt";
            double prf = svmByKKT.prf(testFilePath);
            System.out.println("准确率为" + prf);
            count++;
        }

        long elapse = System.currentTimeMillis() - start;
        System.out.println("花费时间" + elapse / 1000.0 + "s");

    }
}
