package OthersSvm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

public class svm {

	
	private static DenseMatrix64F getMatrixRow(DenseMatrix64F dataMatIn,int row) {
		
		DenseMatrix64F rs = new DenseMatrix64F(1,dataMatIn.numCols);
		
		for(int i=0;i<dataMatIn.numCols;i++) {
			rs.set(0, i, dataMatIn.get(row, i));
		}
		
		return rs;
	}
	
	
	private static DenseMatrix64F getMatrixCol(DenseMatrix64F dataMatIn,int col) {

		DenseMatrix64F rs = new DenseMatrix64F(dataMatIn.numRows,1);
		
		for(int i=0;i<dataMatIn.numRows;i++) {
			rs.set(i,0, dataMatIn.get(i,col));
		}
		
		return rs;
	}
	
	
	private static double calcEk(int k) {
		
		DenseMatrix64F multRs = new DenseMatrix64F(os.getM(),1);
		
		CommonOps.elementMult(os.getAlphas(), os.getLabelMat(), multRs); 
		
		DenseMatrix64F mrt = new  DenseMatrix64F(1,os.getM());
		
		CommonOps.transpose(multRs,mrt);
		
		DenseMatrix64F mrMutRs = new  DenseMatrix64F(1,1);
		
		CommonOps.mult(mrt,getMatrixCol(os.getK(),k), mrMutRs);

		return mrMutRs.get(0, 0)+os.getB()-os.getLabelMat().get(k, 0);
	}
	
	
	private static int selectJrand(int i,int m) {
		int j=i;
		Random r=new Random();
		
		while(j == i) {
			j = r.nextInt(m);
		}
		
		return j;
	}
	
	private static Integer[] nonzero(DenseMatrix64F src,int col) {
		
		List<Integer> rs = new ArrayList<Integer>();
		
		for(int i=0;i<src.numRows;i++) {
			if(src.get(i, col) != 0) {
				rs.add(i);
			}
		}
		
		Integer[] ints = new Integer[rs.size()];

		rs.toArray(ints);
		
		return ints;
		
	}
	
	
	private static SelectRs selectJ(int i,double Ei) {
		
		int maxK = -1;
	    double maxDeltaE = 0;
	    double Ej = 0;
	    
	    os.geteCache().set(i,0, 1);
	    os.geteCache().set(i,1, Ei);

	    Integer[] validEcacheList = nonzero(os.geteCache(),0);
	    
	    SelectRs sr = new SelectRs();

	    if(validEcacheList.length > 1) {
	    	
	    	for(int k=0;k<validEcacheList.length;k++) {
	    		if(validEcacheList[k] == i)
	    			continue;
	    		
	    		double Ek = calcEk(validEcacheList[k]);
	    		
	    		double deltaE = Math.abs(Ei - Ek);
	    		
	    	    if (deltaE >= maxDeltaE) {
	                maxK = validEcacheList[k];
	                maxDeltaE = deltaE;
	    	    }
	    	}
	    	
	    	sr.setJ(maxK);
	    	sr.setEj(Ej);
	    	
	    	
	    }else {
	    	
	    	sr.setJ(selectJrand(i,os.getM()));
	    	sr.setEj(calcEk(sr.getJ()));
	    }
		
	    
	    return sr;
		
	}
	
	
	private static double clipAlpha(double aj,double H,double L) {
	    if(aj > H)
	        aj = H;
	    if(L > aj)
	        aj = L;
	    return aj;
	}
	
	
	private static int innerL(int i) {
		
		double Ei = calcEk(i);
		System.out.println(" y is "+os.getLabelMat().get(i,0));
		System.out.println("os alpha " + os.getAlphas().get(i,0));
		System.out.println(os.getTol());
	    if (((os.getLabelMat().get(i, 0) * Ei < -os.getTol()) && (os.getAlphas().get(i, 0) < os.getC())) 
	    	   || ((os.getLabelMat().get(i, 0)  * Ei > os.getTol()) && (os.getAlphas().get(i, 0) > 0))){
	    	SelectRs sr = selectJ(i,Ei);
	    	int j = sr.getJ();
	        double alphaIold = os.getAlphas().get(i, 0);

	        double alphaJold = os.getAlphas().get(sr.getJ(), 0);
	        double L,H;
	        if (os.getLabelMat().get(i, 0) != os.getLabelMat().get(sr.getJ(), 0)) {
	            L = Math.max(0, os.getAlphas().get(sr.getJ(), 0) - os.getAlphas().get(i, 0));
	            H = Math.min(os.getC(), os.getC() + os.getAlphas().get(sr.getJ(), 0) - os.getAlphas().get(i, 0));
	        }else{
	            L = Math.max(0, os.getAlphas().get(sr.getJ(), 0) + os.getAlphas().get(i, 0) - os.getC());
	            H = Math.min(os.getC(), os.getAlphas().get(sr.getJ(), 0) + os.getAlphas().get(i, 0));
	        }
	        
	        if(L == H) {
	        	System.out.println("L==H");
	        	return 0;
	        }
	        double eta = 2.0 * os.getK().get(i, sr.getJ()) - os.getK().get(i, i) - os.getK().get(sr.getJ(),sr.getJ());
	        if(eta >= 0) {
	        	System.out.println("eta>=0");
	            return 0;
	        }
	        
	        os.getAlphas().set(sr.getJ(), 0,os.getAlphas().get(sr.getJ(), 0)- os.getLabelMat().get(sr.getJ(), 0)*(Ei-sr.getEj())/eta);
	        os.getAlphas().set(sr.getJ(), 0, clipAlpha(os.getAlphas().get(sr.getJ(), 0), H, L));
	        os.geteCache().set(sr.getJ(), 0, 1);
			os.geteCache().set(sr.getJ(),1, calcEk(sr.getJ()));
	        if (Math.abs(os.getAlphas().get(sr.getJ(), 0) - alphaJold) < 0.00001) {
	        	System.out.println("j not moving enough");
	            return 0;
	        }
	        os.getAlphas().set(i, 0,os.getAlphas().get(i, 0)+os.getLabelMat().get(sr.getJ(), 0) * os.getLabelMat().get(i, 0) * (alphaJold - os.getAlphas().get(sr.getJ(), 0)));
	        os.geteCache().set(sr.getJ(), 0, 1);
			os.geteCache().set(sr.getJ(),1, calcEk(i));
	        double b1 = os.getB() - Ei - os.getLabelMat().get(i, 0) * (os.getAlphas().get(i, 0) - alphaIold) * os.getK().get(i, sr.getJ()) - os.getLabelMat().get(sr.getJ(), 0) * (
	        		os.getAlphas().get(sr.getJ(), 0) - alphaJold) * os.getK().get(i, sr.getJ());
	        double b2 = os.getB() - sr.getEj() - os.getLabelMat().get(i, 0) * (os.getAlphas().get(i, 0) - alphaIold) * os.getK().get(i, sr.getJ()) - os.getLabelMat().get(sr.getJ(), 0) * (
	        		os.getAlphas().get(sr.getJ(), 0) - alphaJold) * os.getK().get(i, sr.getJ());

	        if ((0 < os.getAlphas().get(i, 0)) && (os.getC() > os.getAlphas().get(i, 0)))
	            os.setB(b1);
	        else if ((0 < os.getAlphas().get(sr.getJ(), 0)) && (os.getC() > os.getAlphas().get(sr.getJ(), 0)))
	        	os.setB(b2);
	        else
	        	os.setB((b1 + b2) / 2.0);

			System.out.println("B is" + os.getB());
	        return 1;
	    }
	    else{
	        return 0;
	    }
		
	}
	
	
	private static Integer[] between0C(DenseMatrix64F src,int col,double min,double max) {
		
		List<Integer> rs = new ArrayList<Integer>();
		
		for(int i=0;i<src.numRows;i++) {
			if(src.get(i, col) > min && src.get(i, col) < max) {
				rs.add(i);
			}
		}
		
		Integer[] ints = new Integer[rs.size()];

		rs.toArray(ints);
		
		return ints;
		
	}
	
	public static OptStruct os;
	
	public static void smoP(DenseMatrix64F dataMatIn,double[] classLabels,double  C, double toler, int maxIter, String [] kTup) {
		
		os = new OptStruct(dataMatIn,classLabels, C, toler, kTup);
	    int iter = 0;
	    boolean entireSet = true;
	    double alphaPairsChanged = 0;
	    while( (iter < maxIter) && ((alphaPairsChanged > 0) || (entireSet))){
	        alphaPairsChanged = 0;
	        if(entireSet) {
	        	
	        	for(int i=0;i<os.getM();i++) {
	                alphaPairsChanged += innerL(i);
	                System.out.println("fullSet, iter: "+iter+" i:"+i+", pairs changed "+alphaPairsChanged);
	        	}
	            iter += 1;
	        }else{
	        	Integer[] nonBoundIs = between0C(os.getAlphas(),0,0,C);
	        	for(int i=0;i<nonBoundIs.length;i++) {
	        		 alphaPairsChanged += innerL(nonBoundIs[i]);
	        		 System.out.println("non-bound, iter: "+iter+" i:"+nonBoundIs[i]+", pairs changed "+alphaPairsChanged);
	        	}
	            iter += 1;
	        }
	        if(entireSet)
	            entireSet = false;
	        else if (alphaPairsChanged == 0)
	            entireSet = true;
	        System.out.println("iteration number: "+iter);
	    }   	
	}

	public static double[] calcWs(DenseMatrix64F alphas,DenseMatrix64F dataArr,double[] classLabels) {
		
		double [] w = new double[dataArr.numCols];
		
		for(int i=0;i<dataArr.numRows;i++) {
			
			double tmp = alphas.get(i, 0)*classLabels[i];
			
			for(int j=0;j<dataArr.numCols;j++) {
				
				w[j] += tmp*dataArr.get(i, j);
			}
		}
		
        return w;
	}
	
	public static double classify(double [] intX,double[]  ws,double b) {
		
		double prob = 0;
		
		for(int i=0;i<intX.length;i++) {
			prob += intX[i]*ws[i];
		}
		
		if((prob+b) > 0)
			return 1.0;
		else
			return -1.0;	
		
	}

	public static double getAccurate(String filePath,double[] ws) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line = null;
		List<double[]> list = new ArrayList<double[]>();
		List<Double> labels = new ArrayList<Double>();
		while ((line = br.readLine()) != null){
			String[] strings = line.split(",");
			double[] data = new double[5];
			for(int i = 0;i < strings.length-1;i++){
				data[i] = Double.parseDouble(strings[i]);
			}
			list.add(data);
			labels.add(Double.parseDouble(strings[5]));
		}

		double count = 0;
		for(int i = 0;i < list.size();i++){
			if(classify(list.get(i),ws,os.getB()) == labels.get(i)){
				count += 1;
			}
		}

		return count / list.size();
	}
    
    
	
	
	public static void main(String[] args) throws Exception {
		
		List<String> list = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\dell\\Desktop\\waterForSvm.txt"));
            String s = null;
            while((s = br.readLine())!=null){
            	list.add(s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        
        DenseMatrix64F dataMatIn = new DenseMatrix64F(list.size(),5);
        double[] classLabels = new double[list.size()];
        
        for(int i=0;i<list.size();i++) {
        	
        	String[] items = list.get(i).split(",");
        	dataMatIn.set(i, 0, Double.parseDouble(items[0]));
        	dataMatIn.set(i,1, Double.parseDouble(items[1]));
			dataMatIn.set(i, 2, Double.parseDouble(items[2]));
			dataMatIn.set(i,3, Double.parseDouble(items[3]));
			dataMatIn.set(i,3, Double.parseDouble(items[4]));
        	classLabels[i] = Double.parseDouble(items[5]);
        }
        
        smoP(dataMatIn,classLabels,0.6 ,0.001, 40, new String [] {"lin", "0"});
        
        System.out.println(os.getB());

        double[]  ws = calcWs(os.getAlphas(),dataMatIn,classLabels);
        
        System.out.println("w1 is "+ws[0]);
        System.out.println("w2 is "+ws[1]);


		double accurate = getAccurate("C:\\Users\\dell\\Desktop\\waterForTest.txt", ws);
		System.out.println(accurate);
//        for(int i=0;i<dataMatIn.numRows;i++) {
//
//        	double [] intX = new double[dataMatIn.numCols];
//
//        	for(int j=0;j<dataMatIn.numCols;j++)
//        		intX[j] = dataMatIn.get(i, j);
//
//
//        	System.out.println(list.get(i)+"    训练推测分类："+classify(intX,ws,os.getB()));
//        }
		double[] x = new double[5];
		x[0] = 7.85;
		x[1] = 8.5;
		x[2] = 0.15;
		x[3] = 1.77;
		x[4] = 0;
		System.out.println(classify(x,ws,os.getB()));
		
	}
}
