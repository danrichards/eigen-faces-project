package csc450Lib.linalg.base;

/**
 * MatrixGenerator Class provided by Herve
 * 
 * @author Professor Herve
 *
 */
public class MatrixGenerator {

	/**
	 * creates an identity matrix with n columns and rows
	 * @param n the number of rows and columns in identity matrix
	 * @return an identity matrix with n rows and n columns
	 */
	public static Matrix getIdentity(int n){
		float[][] element = new float[n][n];
		for (int i=0; i<n; i++){
			element[i][i] = 1;
		}
		return new Matrix(element);
	}
	
	
	public static Matrix getRandom(int m, int n){
		float[][] elements = new float[m][n];
		for(int i=0; i<m; i++){
			for(int j=0; j<n; j++){
				elements[i][j] = (float) Math.random();
			}
		}
		return new Matrix(elements);
	}
	
	
	public static Matrix getRandomSymmetric(int n){
		float[][] elements = new float[n][n];
		for(int i=0; i<n; i++){
			for(int j=i; j<n; j++){
				elements[i][j] = (float) Math.random();
				if(i!=j){
					elements[j][i] = elements[i][j];
				}
			}
		}
		return new Matrix(elements);
	}
	
	
	public static Matrix getRandomUpperDiagonal(int n){
		float[][] elements = new float[n][n];
		for(int i=0; i<n; i++){
			for(int j=i; j<n; j++){
				elements[i][j] = (float) Math.random();
				if(i!=j){
					elements[j][i] = 0;
				}
			}
		}
		return new Matrix(elements);
	}
	
	public static Matrix getRandomLowerDiagonal(int n){
		float[][] elements = new float[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<=i; j++){
				elements[i][j] = (float) Math.random();
			}
		}
		return new Matrix(elements);
	}
	
	public static Matrix getRandomLowerUnitDiagonal(int n){
		float[][] elements = new float[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<=i; j++){
				if(i!=j){
					elements[i][j] = (float) Math.random();
				}
				else{
					elements[i][j] = 1;
				}
			}
		}
		return new Matrix(elements);
	}
	
	
	
	public static Matrix getSquareDiagonal(float[] d){
		int n = d.length;
		float[][] element = new float[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(i==j){
					element[i][j] = d[i];
				}
				else{
					element[i][j] = 0;
				}
			}
		}
		return new Matrix(element);
	}
	
	
	public static Matrix getRandomHessenberg(int n){
		Matrix A = MatrixGenerator.getRandomUpperDiagonal(n);
		float[][] a = A.getMatrix();
		for(int i=1; i<n; i++){
			a[i][i-1] = (float) Math.random();
		}
		return new Matrix(a);
		
	}
	
	public static Matrix getHilbert(int n){
		float[][] h = new float[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				h[i][j] = 1.0f/(i+j+1.0f);
			}
		}
		return new Matrix(h);
	}


	public static ColumnVector getRandomColumn(int m) {
		return new ColumnVector(MatrixGenerator.getRandom(m, 1).getMatrix());
	}
	
	public static RowVector getRandomRow(int n) {
		return new RowVector(MatrixGenerator.getRandom(1, n).getMatrix());
	}
	
}
