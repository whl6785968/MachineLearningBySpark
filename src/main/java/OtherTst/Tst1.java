package OtherTst;

import org.ejml.data.DenseMatrix64F;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tst1 {
    public static void powTest(){
        double pow = Math.pow(3, 2);
        System.out.println(pow);
    }

    public static void tstDoubleArray(){
//        double[][] tst = new double[2][2];
//        tst[0][0] = 3;
//        tst[0][1] = 5;
//        tst[1][0] = 2;
//        tst[1][1] = 9;
//        tst[2][1] = 7;
        double a = 1.532;
        System.out.println(Math.round(1.321) == 1);

    }

    public static void denseMatrixTst(){
        DenseMatrix64F denseMatrix64F = new DenseMatrix64F(4, 1);
//        System.out.println(denseMatrix64F);
        denseMatrix64F.zero();
//        System.out.println(denseMatrix64F);
    }

    public int fByRecurrent(int n){
        if(n==1){
            return 1;
        }
        else {
            return n*fByRecurrent(n-1);
        }
    }

    public int fbyIter(int n){
        int result = 1;
        for(int i=1;i<=n;i++){
            result *= i;
        }
        return result;
    }

    public void testMatrix(){
        int[][] matrix = new int[280000][10];
    }

    public void testFile() throws IOException {
        String filename = "D:\\huaweichussai\\test_data2.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> list = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null){
            list.add(line);
        }


    }

    public static void main(String[] args) throws IOException {
//        powTest();
//        tstDoubleArray();
//        denseMatrixTst();
        Tst1 tst1 = new Tst1();
        tst1.testFile();
//        tst1.testMatrix();
//        long start = System.currentTimeMillis();
//        int i = tst1.fByRecurrent(1000);
//        System.out.println("recurrent:"+(System.currentTimeMillis()-start));
//        System.out.println(i);
//
//        long start2 = System.currentTimeMillis();
//        int i1 = tst1.fbyIter(1000);
//        System.out.println("iter:"+(System.currentTimeMillis()-start2));
//        System.out.println(i1);
    }
}
