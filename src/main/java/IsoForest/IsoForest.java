package IsoForest;

import HanLpTest.collection.BaseNode;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.ejml.data.DenseMatrix64F;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


class ITree implements Serializable{
}

class ITreeBranch extends ITree implements Serializable{
    ITree left;
    ITree right;
    double splitValue;
    int splitAttr;


    public ITreeBranch(ITree left,ITree right,double splitValue,int splitAttr){
        this.left = left;
        this.right = right;
        this.splitValue = splitValue;
        this.splitAttr = splitAttr;
    }

    public ITree getLeft() {
        return left;
    }

    public void setLeft(ITree left) {
        this.left = left;
    }

    public ITree getRight() {
        return right;
    }

    public void setRight(ITree right) {
        this.right = right;
    }

    public double getSplitValue() {
        return splitValue;
    }

    public void setSplitValue(double splitValue) {
        this.splitValue = splitValue;
    }

    public int getSplitAttr() {
        return splitAttr;
    }

    public void setSplitAttr(int splitAttr) {
        this.splitAttr = splitAttr;
    }
}

class ITreeLeaf extends ITree implements Serializable{
    int size;

    public ITreeLeaf(int size){
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

class IForest implements Serializable{
    List<ITree> iTrees;
    int maxSamples;
    double score;

    public IForest(List<ITree> iTrees, int maxSamples, double score) {
        this.iTrees = iTrees;
        this.maxSamples = maxSamples;
        this.score = score;
    }

    public double predict(DenseMatrix64F x){
        if(iTrees.size() == 0 || iTrees == null){
            throw new IllegalArgumentException("请训练后再预测");
        }

//        double[] logisticCoef = {0.53022157,0.41180608,-0.54287232,-1.35365107,0.05589461};
//
//        for (int i = 0 ;i < x.numCols;i++){
//            x.set(0,i,x.get(0,i) * logisticCoef[i]);
//        }

        double sum = 0;
        for(int i = 0;i < iTrees.size();i++){
            sum += pathLengh(x,iTrees.get(i),0);
        }

        double exponent = -(sum/iTrees.size())/cost(256);

        double score = Math.pow(2,exponent);

        boolean serious = isSerious(x);
        int errNum = getErrNum(x);

        if(serious){
            return -1;
        }


        if(score > this.score){

            return -1;
        }
        else {
            return 1;
        }
    }

    public double pathLengh(DenseMatrix64F x,ITree tree,int path_length){
        String simpleName = tree.getClass().getSimpleName();
        if(simpleName.equals("ITreeLeaf")){
            ITreeLeaf leaf = (ITreeLeaf) tree;
            int size = leaf.getSize();
            return path_length + cost(size);

        }

        ITreeBranch iTreeBranch = (ITreeBranch)tree;
        int splitAttr = iTreeBranch.getSplitAttr();
        double splitValue = iTreeBranch.getSplitValue();

        double value = x.get(0, splitAttr);

        if(value < splitValue){
            ITree left = iTreeBranch.getLeft();
            return pathLengh(x,left,path_length + 1);
        }
        else {
            ITree right = iTreeBranch.getRight();
            return pathLengh(x,right,path_length + 1);
        }

    }
    public double getExcessOfNh(DenseMatrix64F x){
        double result = Math.abs(x.get(0,2) - 1.0)/1.0;
        return result;
    }

    public double getExcessOfKmno(DenseMatrix64F x){
        return Math.abs(x.get(0,3) - 6.0) / 6.0;
    }


    public boolean isSerious(DenseMatrix64F x){
        boolean isSerious = false;

        if(x.get(0,0) > 9  && x.get(0,0) < 6){
            isSerious = true;
        }
        else if(x.get(0,1) < 5){
            isSerious = true;
        }
        else if(x.get(0,2) > 1.75){
            isSerious = true;
        }
        else if(x.get(0,3) > 10){
            isSerious = true;
        }

        return isSerious;
    }

    public int getErrNum(DenseMatrix64F x){
        int errNum = 0;
        if(x.get(0,1) >= 3 && x.get(0,1) < 5){
            errNum += 1;
        }
        else if(x.get(0,2) >= 1.0 && x.get(0,2) <= 1.5){
            errNum += 1;
        }
        else if(x.get(0,3) >= 6 && x.get(0,3) <=10){
            errNum += 1;
        }

        return errNum;
    }

    public double getHi(int i){
        double constantValue = 0.5772156649;
        return Math.log(i) + constantValue;
    }

    public double cost(int n){
        double hi = getHi(n-1);
        if(n <= 1){
            return 1.0;
        }
        double cost = 2 * hi - 2*(n-1)/n;
        return cost;
    }

    public double getLinear(DenseMatrix64F x){
        double[] coef = {-0.02038609,0.06952332,-0.37526936,-0.19289376,0.00958096};
        double intercept = 0.9680804016625262;

        double result = 0;

        for (int i = 0 ;i < x.numCols ;i++){
            result += coef[i] * x.get(0,i);
        }

        return result + intercept;
    }

    public double getAccurate(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line = null;
        List<String> lists = new ArrayList<String>();
        while ((line = reader.readLine()) != null){
            lists.add(line);
        }

        int cols = lists.get(0).split(",").length-1;


        List<DenseMatrix64F> testData = new ArrayList<DenseMatrix64F>();
        List<Double> ys = new ArrayList<Double>();

        for (int i = 0;i< lists.size();i++){
            String[] strings = lists.get(i).split(",");
            DenseMatrix64F denseMatrix64F = new DenseMatrix64F(1, cols);
            for (int j = 0;j < cols;j++){
                denseMatrix64F.set(0,j,Double.parseDouble(strings[j]));
            }
            testData.add(denseMatrix64F);
            ys.add(Double.parseDouble(strings[5]));
        }

        double count = 0.0;
        for (int i = 0; i < testData.size();i++){
            double predict = predict(testData.get(i));
            if(predict == ys.get(i)){
                count += 1.0;
            }
            else {
                double excessOfKmno = getExcessOfKmno(testData.get(i));
                double excessOfNh = getExcessOfNh(testData.get(i));
//                System.out.println("excessOfKmno: " + excessOfKmno);
//                System.out.println("excessOfNh: " + excessOfNh );
                System.out.println(testData.get(i) + ":" + ys.get(i) + ":" + predict);
            }

        }

        return count / ys.size();
    }
}

public class IsoForest {
    List<Integer> featureList = new ArrayList<>();
    public DenseMatrix64F loadFile(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line =  null;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null){
            lines.add(line);
        }

        int col = lines.get(0).split(",").length - 1;
        DenseMatrix64F data = new DenseMatrix64F(lines.size(),col);
//        double[] logisticCoef = {0.53022157,0.41180608,-0.54287232,-1.35365107,0.05589461};

        for (int i = 0;i < lines.size(); i++){
            String[] strings = lines.get(i).split(",");
            for (int j = 0;j < col;j++){
                data.set(i,j,Double.parseDouble(strings[j]));
            }
        }

        return data;
    }

    public DenseMatrix64F getSubSample(DenseMatrix64F dataSet,int subSampleCount){
        int features = dataSet.numCols;
        DenseMatrix64F subSample = new DenseMatrix64F(subSampleCount,features);

        for (int i = 0;i < subSampleCount; i++){
            for (int j = 0;j < features;j++){
                subSample.set(i,j,dataSet.get(i,j));
            }
        }

        return subSample;
    }

    public IForest train(String filepath,double score) throws IOException {
        DenseMatrix64F dataSet = loadFile(filepath);
        int rows = dataSet.numRows;

        int maxLength = (int) Math.ceil(bottomChanging(rows,2));
        int numTrees = 500;
        int numFeatures = dataSet.numCols;

        int maxSamples = 256;
        int subSampleSize = Math.min(256,rows);

        List<Future<ITree>> futureList = new ArrayList<>();

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 200, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), threadFactory,
                new ThreadPoolExecutor.AbortPolicy());


        for(int i = 0;i < numTrees;i++){
            tThread tThread = new tThread(dataSet, subSampleSize, maxLength, numFeatures, 0);
            futureList.add(executor.submit(tThread));
        }

        List<ITree> iTrees = futureList.stream().map(future -> {
            try {
                ITree iTree = future.get();
                return iTree;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());


//        for (int i = 0;i < numTrees;i++){
//            DenseMatrix64F subSample = getSubSample(dataSet, subSampleSize);
//            ITree iTree = growTree(subSample, maxLength, numFeatures, 0);
//
//            iTrees.add(iTree);
//        }

        executor.shutdown();
        return new IForest(iTrees,maxSamples,score);

    }

    class tThread implements Callable<ITree>{
        private DenseMatrix64F dataset;
        private int subsample_size;
        private int maxLength;
        private int numFeatures;
        private int currLength;

        public tThread(DenseMatrix64F dataset, int subsample_size, int maxLength, int numFeatures, int currLength) {
            this.dataset = dataset;
            this.subsample_size = subsample_size;
            this.maxLength = maxLength;
            this.numFeatures = numFeatures;
            this.currLength = currLength;
        }

        @Override
        public ITree call() {
            DenseMatrix64F subSample = getSubSample(dataset, subsample_size);
            ITree iTree = growTree(subSample,maxLength, numFeatures, currLength);
            return iTree;
        }
    }


    public ITree growTree(DenseMatrix64F data,int maxLength,int numFeatures,int currentLength){
        if (currentLength >= maxLength || data.numRows <= 1){
            return new ITreeLeaf(data.numRows);
        }

        Random random = new Random();
        int feature = random.nextInt(numFeatures);
        int rows = data.numRows;
        int randomRow = random.nextInt(rows);
        double splitPoint = data.get(randomRow,feature);

        List<Integer> rightList = new ArrayList<Integer>();
        List<Integer> leftList = new ArrayList<Integer>();
        for(int i = 0; i < rows;i++){
            if(data.get(i,feature) >= splitPoint){
                rightList.add(i);
            }
            else {
                leftList.add(i);
            }
        }

        DenseMatrix64F left = new DenseMatrix64F(leftList.size(), numFeatures);
        DenseMatrix64F right = new DenseMatrix64F(rightList.size(), numFeatures);

        for (int i = 0; i < leftList.size();i++){
            for(int j = 0;j < numFeatures;j++){
                left.set(i,j,data.get(i,j));
            }
        }

        for (int i = 0; i < rightList.size();i++){
            for(int j = 0;j < numFeatures;j++){
                right.set(i,j,data.get(i,j));
            }
        }

        return new ITreeBranch(growTree(left,maxLength,numFeatures,currentLength+1),growTree(right,maxLength,numFeatures,currentLength+1),
                splitPoint,feature);

    }

    public double bottomChanging(int x,int bottom){
        double log = Math.log10(x) / Math.log10(bottom);
        return log;
    }

    public String save_model(Object obj,String path) throws IOException {
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.register(obj.getClass(),new JavaSerializer());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Output output = new Output(stream);
        kryo.writeClassAndObject(output,obj);
        output.flush();
        output.close();

        byte[] b = stream.toByteArray();
        try
        {
            stream.flush();
            stream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        String objStr = Base64.getEncoder().encodeToString(b);
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(objStr);
        writer.close();

        return objStr;
    }

    public <T extends Serializable> T load_model(String objStr,Class<T> clazz){
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.register(clazz,new JavaSerializer());
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(objStr));
        Input input = new Input(bais);
        return (T) kryo.readClassAndObject(input);
    }


    public static void main(String[] args) throws IOException {
        int count = 0;
        long start = System.currentTimeMillis();
        List<Double> maxScoreList = new ArrayList<Double>();
        String filepath = "C:\\Users\\dell\\Desktop\\waterData\\water2.txt";
        String testPath = "C:\\Users\\dell\\Desktop\\waterData\\testForIsoForest.txt";
//
//        while (count < 20){
//            double score = 0.40;
//            double maxAccurate = 0;
//            double maxScore = 0.0;
//            IsoForest isoForest = new IsoForest();
//            while (score < 0.7){
//                IForest forest = isoForest.train(filepath,score);
//                score += 0.001;
//                double accurate = forest.getAccurate(testPath);
//                if(accurate > maxAccurate){
//                    maxAccurate = accurate;
//                    maxScore = score;
//                }
//            }
//            System.out.println("maxAccurate is " + maxAccurate);
//            System.out.println("maxScore is " + maxScore);
//            maxScoreList.add(maxScore);
//            count++;
//        }
//        long elapse = System.currentTimeMillis() - start;
//        System.out.println("花费时间" + elapse / 1000.0 + "s");


//        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\dell\\Desktop\\waterData\\IsoModel.txt"));
//        String s = reader.readLine();
//        IForest iForest = isoForest.load_model(s, IForest.class);
//        double accurate = iForest.getAccurate("C:\\Users\\dell\\Desktop\\waterData\\testForIsoForest.txt");
//        System.out.println(accurate);

        double score = 0.5;
        IsoForest isoForest = new IsoForest();
        for (int i = 0;i < 10000;i++){
            IForest iForest = isoForest.train(filepath, score);
//            double accurate = iForest.getAccurate(testPath);
//            System.out.println(accurate);
        }

        long elapse = System.currentTimeMillis() - start;
        System.out.println("花费时间" + elapse / 1000.0 + "s");

//        double accurate = iForest.getAccurate(testPath);
//        System.out.println(accurate);


    }
}
