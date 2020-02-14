package OtherTst;

import org.ejml.data.DenseMatrix64F;

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
        System.out.println(denseMatrix64F);
        denseMatrix64F.zero();
        System.out.println(denseMatrix64F);
    }

    public static void main(String[] args) {
//        powTest();
        tstDoubleArray();
        denseMatrixTst();
        Tst1 tst1 = new Tst1();
    }
}
