package DecisionTree;

import org.ejml.data.DenseMatrix64F;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class ITree implements Serializable{

}

class ITreeBranch extends ITree implements Serializable{
    ITree left;
    ITree right;
    double splitValue;
    int splitFeature;
    int mark;

    public ITreeBranch(ITree left, ITree right, double splitValue, int splitFeature,int mark) {
        this.left = left;
        this.right = right;
        this.splitValue = splitValue;
        this.splitFeature = splitFeature;
        this.mark = mark;
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

    public int getSplitFeature() {
        return splitFeature;
    }

    public void setSplitFeature(int splitFeature) {
        this.splitFeature = splitFeature;
    }
}

class ItreeLeaf extends ITree implements Serializable{
    int size;
    int mark;

    public ItreeLeaf(int size,int mark) {
        this.size = size;
        this.mark = mark;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}

public class ParallelDecisionTree {
    public DenseMatrix64F load_data(String filename,boolean isTrain) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null){
            lines.add(line);
        }
        int col = 0;
        if(isTrain){
            col = lines.get(0).split(",").length;
        }else {
            col = lines.get(0).split(",").length;
        }

        DenseMatrix64F data = new DenseMatrix64F(lines.size(),col);

        for (int i = 0;i < lines.size(); i++){
            String[] strings = lines.get(i).split(",");
            for (int j = 0;j < col;j++){
                data.set(i,j,Double.parseDouble(strings[j]));
            }
        }

        return data;
    }

    public ParallelDecisionTree train(String filepath) throws IOException {
        DenseMatrix64F denseMatrix64F = load_data(filepath, true);
        growTree(denseMatrix64F);
        return null;
    }

    public ITree growTree(DenseMatrix64F data){
        double entD = getEntD(data);
        System.out.println(entD);
        return null;
    }

    public int getBestFeature(DenseMatrix64F data){
        return 0;
    }

    public double getEntD(DenseMatrix64F data){
        int rows = data.numRows;
        int cols = data.numCols;
        System.out.println(cols);
        double label1 = 0.0;
        double label2 = 0.0;
        for(int i = 0; i < rows; i++){
            double label = data.get(i,cols-1);
            if (label == 0.0){
                label1 += 1.0;
            }
            else {
                label2 += 1.0;
            }
        }

        double result = -(((label1/rows)*changeBase(label1/rows)) + ((label2/rows)*changeBase(label2/rows)));

        return result;
    }

    public double changeBase(double x){
        return Math.log10(x) / Math.log10(2);
    }

    public static void main(String[] args) throws IOException {
        String filepath = "C:\\Users\\dell\\Desktop\\data\\huaweicontest\\train_data.txt";
        ParallelDecisionTree parallelDecisionTree = new ParallelDecisionTree();
        parallelDecisionTree.train(filepath);
    }
}
