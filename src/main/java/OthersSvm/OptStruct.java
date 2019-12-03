package OthersSvm;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

public class OptStruct {

	private DenseMatrix64F X;
	
	private DenseMatrix64F labelMat;
	
	private double C;
	
	private double tol;
	
	private int m;
	
	private DenseMatrix64F alphas;
	
	private double b;
	
	private DenseMatrix64F eCache;
	
	private DenseMatrix64F K;
	
	public OptStruct(DenseMatrix64F dataMatIn,double [] classLabels,double C,double toler,String[] kTup) {
		
		this.X = dataMatIn;
		this.labelMat = new DenseMatrix64F(classLabels.length,1);
		
		for(int i=0;i<classLabels.length;i++)
			this.labelMat.set(i, 0, classLabels[i]);
		
		this.C = C;
		this.tol = toler;
		this.m = dataMatIn.numRows;
		this.alphas = new DenseMatrix64F(dataMatIn.numRows,1);
		this.alphas.zero();
		this.b=0;
		this.eCache = new DenseMatrix64F(dataMatIn.numRows,2);
		this.eCache.zero();
		
		this.K = kernelTrans(dataMatIn,kTup,dataMatIn.numRows);
		
		
	}
	
	private DenseMatrix64F getMatrixRow(DenseMatrix64F dataMatIn,int row) {
		
		DenseMatrix64F rs = new DenseMatrix64F(1,dataMatIn.numCols);
		
		for(int i=0;i<dataMatIn.numCols;i++) {
			rs.set(0, i, dataMatIn.get(row, i));
		}
		
		return rs;
	}
	
	
	private DenseMatrix64F kernelTrans(DenseMatrix64F dataMatIn,String[] kTup,int m) {
		
		DenseMatrix64F rs = new  DenseMatrix64F(m,m);
		rs.zero();
		
		for(int i=0;i<m;i++) {
			
			DenseMatrix64F ki = new  DenseMatrix64F(m,1);
			ki.zero();
			
			if(kTup[0].equals("lin")) {
				
				DenseMatrix64F mrt = new  DenseMatrix64F(dataMatIn.numCols,1);

				CommonOps.transpose(getMatrixRow(dataMatIn,i),mrt);
				
				CommonOps.mult(dataMatIn,mrt, ki);
				
				for(int j=0;j<m;j++) {
					rs.set(j, i, ki.get(j, 0));
				}
				
			}else if(kTup[0].equals("rbf")) {
				for(int j=0;j<m;j++) {
					
					DenseMatrix64F deltaRow = new  DenseMatrix64F(1,m);
					CommonOps.subtract(getMatrixRow(dataMatIn,j),getMatrixRow(dataMatIn,i),deltaRow);
					
					DenseMatrix64F mrt = new  DenseMatrix64F(m,1);
					CommonOps.transpose(deltaRow,mrt);
					
					DenseMatrix64F mrMutRs = new  DenseMatrix64F(1,1);
					
					CommonOps.mult(deltaRow,mrt, mrMutRs);
					
                    rs.set(j, i, Math.exp(mrMutRs.get(0, 0)/(-1*Double.parseDouble(kTup[1])*Double.parseDouble(kTup[1]))));
                    
				}
				
			}else {
				
				System.out.println("Houston We Have a Problem -- That Kernel is not recognized");
			}
			
		}
		
		
		return rs;
		
	}
	
	

	public DenseMatrix64F getX() {
		return X;
	}

	public void setX(DenseMatrix64F x) {
		X = x;
	}



	public DenseMatrix64F getLabelMat() {
		return labelMat;
	}

	public void setLabelMat(DenseMatrix64F labelMat) {
		this.labelMat = labelMat;
	}

	public double getC() {
		return C;
	}

	public void setC(double c) {
		C = c;
	}

	public double getTol() {
		return tol;
	}

	public void setTol(double tol) {
		this.tol = tol;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public DenseMatrix64F getAlphas() {
		return alphas;
	}

	public void setAlphas(DenseMatrix64F alphas) {
		this.alphas = alphas;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public DenseMatrix64F geteCache() {
		return eCache;
	}

	public void seteCache(DenseMatrix64F eCache) {
		this.eCache = eCache;
	}

	public DenseMatrix64F getK() {
		return K;
	}

	public void setK(DenseMatrix64F k) {
		K = k;
	}
	
	
	
	
	
}
