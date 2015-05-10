package csc450Lib.linalg.base;

import java.math.BigDecimal;

import com.wolfram.jlink.*;

public class Matrix {

	private float[][] a;
	
	/**
	 * Creates and initializes a matrix at the dimensions specified.
	 * 
	 * @param a
	 */
	public Matrix(float[][] a) {
		this.a = a;
	}
	
	/**
	 * Creates and initializes a matrix with an array of ColumnVectors
	 * 
	 * @param a
	 */
	public Matrix(ColumnVector[] cv) {
		int cols = cv.length;
		int rows = cv[0].rows();
		this.a = new float[rows][cols];
		for (int j = 0; j < cols; j++) {
			for (int i = 0; i < rows; i++)
				this.a[i][j] = cv[j].get(i, 0);
		}
	}
	
	/**
	 * Creates a matrix at the dimensions specified
	 * 
	 * @param nbRows
	 * @param nbCols
	 */
	public Matrix(int nbRows, int nbCols) {
		this.a = new float[nbRows][nbCols];
		for (int i = 0; i < nbRows; i++) {
			for (int j = 0; j < nbCols; j++) {
				this.a[i][j] = 0;				
			}
		}
	}
	
	/**
	 * Creates a matrix at the dimensions specified with a specific value
	 * 
	 * @param nbRows
	 * @param nbCols
	 */
	public Matrix(int nbRows, int nbCols, float fill) {
		this.a = new float[nbRows][nbCols];
		for (int i = 0; i < nbRows; i++) {
			for (int j = 0; j < nbCols; j++) {
				this.a[i][j] = fill;				
			}
		}
	}
	
	/**
	 * Assigns new values to the elements of the matrix. 
	 * 
	 * @param a
	 */
	public void setMatrix(float[][] a) {
		this.a = a;
	}

	/**
	 * Adds two matrices.
	 * 
	 * @param matA
	 * @param matB
	 * @return
	 */
	public static Matrix add(Matrix matA, Matrix matB) {
		float[][] c = new float[matA.rows()][matA.cols()];
		for (int i = 0; i < matA.rows(); i++) {
			for (int j = 0; j < matA.cols(); j++) {
				c[i][j] = matA.get(i, j) + matB.get(i, j);				
			}
		}
		return new Matrix(c);
	}
	
	/**
	 * Subtracts two column vectors 
	 * 
	 * @param cvtA
	 * @param cvB
	 * @return float[][]
	 */
	public static float[] subtractCV(float[] cvA, float[] cvB) {
		int len = cvA.length;
		float[] c = new float[len];
		for (int i = 0; i < len; i++)
			c[i] = cvA[i] - cvB[i];
		return c;
	}
	
	/**
	 * Subtracts two matrices. 
	 * 
	 * @param matA
	 * @param matB
	 * @return int[][]
	 */
	public static int[][] subtract(int[][] matA, int[][] matB) {
		int rows = matA.length;
		int cols = matA[0].length;
		int[][] c = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				c[i][j] = matA[i][j] - matB[i][j];				
			}
		}
		return c;
	}
	
	/**
	 * Subtracts two matrices. 
	 * 
	 * @param matA
	 * @param matB
	 * @return
	 */
	public static Matrix subtract(Matrix matA, Matrix matB) {
		float[][] c = new float[matA.rows()][matA.cols()];
		for (int i = 0; i < matA.rows(); i++) {
			for (int j = 0; j < matA.cols(); j++) {
				c[i][j] = matA.get(i, j) - matB.get(i, j);				
			}
		}
		return new Matrix(c);
	}
	
	/**
	 * Subtracts two matrices. 
	 * 
	 * @param matA
	 * @param matB
	 * @return
	 */
	public static ColumnVector subtract(ColumnVector cvA, ColumnVector cvB) {
		float[] c = new float[cvA.rows()];
		for (int i = 0; i < cvA.rows(); i++)
			c[i] = cvA.get(i, 0) - cvB.get(i, 0);				
		return new ColumnVector(c);
	}

	/**
	 * Multiplies two matrices.
	 * 
	 * @param matA
	 * @param matB
	 * @return
	 */
	public static Matrix multiply(Matrix matA, Matrix matB) {
		float[][] c = new float[matA.rows()][matB.cols()];
		for (int i = 0; i < matA.rows(); i++) {
			for (int j = 0; j < matB.cols(); j++) {
				c[i][j] = 0;
				for (int k = 0; k < matA.cols(); k++) {
                   c[i][j] = c[i][j] + matA.get(i, k) * matB.get(k, j);
                }				
			}
		}
		return new Matrix(c);
	}

	/**
	 * Multiplies two matrices.
	 * 
	 * @param matA
	 * @param matB
	 * @return
	 */
	public static ColumnVector multiply(Matrix matA, ColumnVector cvB) {
		Matrix matB = new Matrix(cvB.getMatrix());
		return new ColumnVector(Matrix.multiply(matA, matB).getMatrix());
	}

	/**
	 * Multiplies two matrices.
	 * 
	 * @param matA
	 * @param matB
	 * @return
	 */
	public static ColumnVector multiply(ColumnVector cvA, ColumnVector cvB) {
		Matrix matA = new Matrix(cvA.getMatrix());
		Matrix matB = new Matrix(cvB.getMatrix());
		return new ColumnVector(Matrix.multiply(matA, matB).getMatrix());
	}

	/**
	 * Multiplies two matrices.
	 * 
	 * @param matA
	 * @param matB
	 * @return
	 */
	public static int[][] multiply(int[][] matA, int[][] matB) {
		int rows = matA.length;
		int cols = matA[0].length;
		int[][] c = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				c[i][j] = 0;
				for (int k = 0; k < cols; k++) {
                   c[i][j] = c[i][j] + matA[i][k] * matB[k][j];
                }
			}
		}
		return c;
	}
	
	/**
	 * Transposes this matrix
	 */
	public void transpose() {
		float[][] c = new float[this.cols()][this.rows()];
		for (int i = 0; i < this.cols(); i++) {
			for (int j = 0; j < this.rows(); j++) {
				c[i][j] = this.get(j, i);				
			}
		}
		this.a = c;
	}
	
	/**
	 * Returns a new matrix, the transpose of the one received as parameter
	 * 
	 * @param matA
	 * @return
	 */
	public static Matrix transpose(Matrix matA) {
		float[][] c = new float[matA.cols()][matA.rows()];
		for (int i = 0; i < matA.cols(); i++) {
			for (int j = 0; j < matA.rows(); j++) {
				c[i][j] = matA.get(j, i);				
			}
		}
		return new Matrix(c);
	}
	
	/**
	 * Returns a new matrix, the transpose of the one received as parameter
	 * 
	 * @param matA
	 * @return
	 */
	public static Matrix transpose(ColumnVector cvA) {
		Matrix matA = new Matrix(cvA.getMatrix());
		return Matrix.transpose(matA);
	}
	
	/**
	 * Returns a new matrix, the transpose of the one received as parameter
	 * 
	 * @param matA
	 * @return int[][]
	 */
	public static int[][] transpose(int[][] matA) {
		int rows = matA.length;
		int cols = matA[0].length;
		int[][] c = new int[cols][rows];
		for (int i = 0; i < cols; i++)
			for (int j = 0; j < rows; j++)
				c[i][j] = matA[j][i];	
		return c;
	}
	
	/**
	 * Computes the outer product of two column vectors
	 * 
	 * @param u
	 * @param v
	 * @return the matrix computed by the outer product u . v^T (u multiplied by transpose of v)
	 */
	public static Matrix outerProduct(ColumnVector u, ColumnVector v) {
		float[][] outer = new float[u.rows()][1];
		for (int k = 0; k < u.rows(); k++) {
			outer[k][0] = (u.get(k, 0) * v.get(k, 0));
		}
		return new Matrix(outer);
 	}
	
	/**
	 * Computes the "dot product" of two column vectors (with the same dimensions)
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	public static float dotProduct(ColumnVector u, ColumnVector v) {
		float dot = 0f;
		for (int k = 0; k < u.rows(); k++) {
			dot += (u.get(k, 0) * v.get(k, 0));
		}
		return dot;
	}
	
	/**
	 * Returns the number of rows of this matrix
	 * 
	 * @return int
	 */
	public int rows() {
		return this.a.length;
	}
	
	/**
	 * Returns the number of columns of this matrix
	 * 
	 * @return int
	 */
	public int cols() {
		return this.a.length > 0 ? this.a[0].length : 0;
	}
	
	/**
	 * Returns the value of the norm 1 for this matrix
	 * 
	 * @return
	 */
	public float norm1() {
		float maxNorm1 = 0f;
		for (int j = 0; j < this.cols(); j++) {
			float norm1 = 0f;			
			for (int i = 0; i < this.rows(); i++) {
				norm1 += Math.abs(this.get(i, j));				
			}
			if (j == 0)
				maxNorm1 = norm1;
			else if(norm1 > maxNorm1)
				maxNorm1 = norm1;
		}
		return maxNorm1;
	}
	
	/**
	 * Returns the value of the norm infinity for this matrix
	 * 
	 * @return
	 */
	public float normInf() {
		float maxNorm1 = 0f;
		for (int i = 0; i < this.rows(); i++) {
			float norm1 = 0f;			
			for (int j = 0; j < this.cols(); j++) {
				norm1 += Math.abs(this.get(i, j));				
			}
			if (i == 0)
				maxNorm1 = norm1;
			else if(norm1 > maxNorm1)
				maxNorm1 = norm1;
		}
		return maxNorm1;
	}
	
	/**
	 * Returns the value of the element at the position specified
	 * 
	 * @param theRow
	 * @param theCol
	 * @return
	 */
	public float get(int theRow, int theCol) {
		if (this.rows() >= theRow && this.cols() >= theCol) {
			return this.a[theRow][theCol];
		} else {
			System.out.println("get(theRow, theCol) invalid input data given.");
			return 0f;
		}
	}

	/**
	 * Return a float[] of the matrix row
	 * 
	 * @param theRow
	 * @return
	 */
	public float[] getRow(int theRow) {
		if (theRow < this.rows()) {
			return this.a[theRow];
		} else {
			System.out.println("getRow(theRow) invalid input data given.");
			return new float[0];
		}
	}

	/**
	 * Return a float[] of the matrix col
	 * 
	 * @param theCol
	 * @return
	 */
	public float[] getCol(int theCol) {
		float[] col = new float[this.rows()];
		if (theCol < this.cols()) {
			for (int i = 0; i < this.rows(); i++)
				col[i] = this.get(i, theCol);
			return col;
		} else {
			System.out.println("getCol(theCol) invalid input data given.");
			return new float[0];
		}
	}
	
	/**
	 * Convert the current matrix into an array of Column Vectors
	 * 
	 * @return
	 */
	public ColumnVector[] getColumnVectors() {
		return Matrix.getColumnVectors(this);
	}
	
	/**
	 * Convert a matrix into an array of ColumnVectors
	 * 
	 * @param mat
	 * @return
	 */
	public static ColumnVector[] getColumnVectors(Matrix mat) {
		ColumnVector[] cv = new ColumnVector[mat.cols()];
		for (int j = 0; j < mat.cols(); j++)
			cv[j] = new ColumnVector(mat.getCol(j));
		return cv;
	}
	
	/**
	 * Added for compatibility with Herve's MatrixGenerator class
	 * 
	 * @return
	 */
	public float[][] getMatrix() {
		return a;
	}
	
	/**
	 * Added for Mathematica tests
	 * 
	 * @return
	 */
	public static float[][] getMatrix(Matrix m) {
		return m.a;
	}
	
	
	/**
	 * Sets the value of the element at the position specified
	 * 
	 * @param theRow
	 * @param theCol
	 * @param theVal
	 */
	public void set(int theRow, int theCol, float theVal) {
		if (this.rows() >= theRow && this.cols() >= theCol) {
			this.a[theRow][theCol] = theVal;
		} else {
			System.out.println("set(theRow, theCol, theVal) is out of bounds.");
		}
	}
	
	
	/**
	 * Sets the values of a row
	 * 
	 * @param theRow
	 * @param theValues
	 */
	public void setRow(int theRow, float[] theValues) {
		if (theRow < this.rows()) {
			for (int j = 0; j < this.cols(); j++)
				this.set(theRow, j, theValues[j]);
		} else {
			System.out.println("setRow(theRow, theValues) is out of bounds.");
		}
	}
	
	
	/**
	 * Sets the values of a column
	 * 
	 * @param theCol
	 * @param theValues
	 */
	public void setCol(int theCol, float[] theValues) {
		if (theCol < this.cols()) {
			for (int i = 0; i < this.rows(); i++)
				this.set(i, theCol, theValues[i]);
		} else {
			System.out.println("setCol(theCol, theValues) is out of bounds.");
		}
	}
	
	/**
	 * Converts the matrix into a string according to the formatting strings received as parameters. 
	 * For example,
	 *		a Mathematica-compatible output would be produced by the parameters ("[", "]", ",", false)
	 *		a formatted console output would be produced by the parameters ("{", "}", ",", true) or the parameters ("", "", " \t", true)
	 *
	 * @param theBeginArrayStr
	 * @param theEndArrayStr
	 * @param theElmtSepStr
	 * @param theEolAtEor
	 * @return
	 */
	public String toString(String theBeginArrayStr, String theEndArrayStr, String theElmtSepStr, boolean theEolAtEor) {
		String matrix = theBeginArrayStr;
		matrix = theEolAtEor ? matrix + "\n" : matrix;
		for (int i = 0; i < this.rows(); i++) {
			matrix += theBeginArrayStr;
			for (int j = 0; j < this.cols(); j++) {
//				matrix += Float.toString(this.get(i, j));
				matrix += (new BigDecimal(this.get(i, j))).toPlainString();
				matrix += (j < this.cols() - 1) ? theElmtSepStr + " " : "";
			}
			matrix += theEndArrayStr;
			matrix += (i != (this.rows() - 1)) ? theElmtSepStr : ""; 
			matrix += theEolAtEor ? "\n" : "";
		}
		matrix += theEndArrayStr;
		matrix += theEolAtEor ? "\n" : "";		
		return matrix;
	}
	
	/**
	 * Converts the matrix into a string according to the formatting strings received as parameters. 
	 * For example,
	 *		a Mathematica-compatible output would be produced by the parameters ("[", "]", ",", false)
	 *		a formatted console output would be produced by the parameters ("{", "}", ",", true) or the parameters ("", "", " \t", true)
	 *
	 * @param theBeginArrayStr
	 * @param theEndArrayStr
	 * @param theElmtSepStr
	 * @param theEolAtEor
	 * @return
	 */
	public static String toString(Matrix s, String theBeginArrayStr, String theEndArrayStr, String theElmtSepStr, boolean theEolAtEor) {
		String matrix = theBeginArrayStr;
		matrix = theEolAtEor ? matrix + "\n" : matrix;
		for (int i = 0; i < s.rows(); i++) {
			matrix += theBeginArrayStr;
			for (int j = 0; j < s.cols(); j++) {
//				matrix += Float.toString(this.get(i, j));
				matrix += (new BigDecimal(s.get(i, j))).toPlainString();
				matrix += (j < s.cols() - 1) ? theElmtSepStr + " " : "";
			}
			matrix += theEndArrayStr;
			matrix += (i != (s.rows() - 1)) ? theElmtSepStr : ""; 
			matrix += theEolAtEor ? "\n" : "";
		}
		matrix += theEndArrayStr;
		matrix += theEolAtEor ? "\n" : "";		
		return matrix;
	}
	
	/**
	 * Get a copy of the matrix.
	 * 
	 * @param a
	 * @return
	 */
	public static Matrix copyMatrix(Matrix m) {
		float input[][] = new float[m.rows()][m.cols()];
		for(int i = 0; i < m.rows(); i++) {
			input[i] = m.getRow(i);
		}
		return new Matrix(input);
	}

	public int test() {
		return 123456;
	}
	
	/**
	 * Calculate the average matrix from an array of matrices
	 */
	public static float[][] average(float[][][] arrayOfMatrices) {
		int numMatrices = arrayOfMatrices.length;
		int height = arrayOfMatrices[0].length;
		int width = arrayOfMatrices[0][0].length;
		float[][] avg = new float[width][height];
		for (int i = 0; i < numMatrices; i++) {
			for (int y = 0; y < height; y ++) {
				for (int x = 0; x < width; x++) {
					if (i == 0) 
						avg[x][y] = arrayOfMatrices[i][x][y];
					else if (i == (numMatrices - 1)) {
						avg[x][y] += arrayOfMatrices[i][x][y];
						avg[x][y] /= (float) numMatrices;
					} else
						avg[x][y] += arrayOfMatrices[i][x][y];
				}
			}
		}
		return avg;
	}
		
	/**
	 * Calculate the average of the training
	 */
	public static int[][] average(int[][][] arrayOfMatrices) {
		int numMatrices = arrayOfMatrices.length;
		int rows = arrayOfMatrices[0].length;
		int cols = arrayOfMatrices[0][0].length;
		int[][] avg = new int[rows][cols];
		for (int i = 0; i < numMatrices; i++) {
			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < cols; y ++) {
					if (i == 0) 
						avg[x][y] = arrayOfMatrices[i][x][y];
					else if (i == (numMatrices - 1)) {
						avg[x][y] += arrayOfMatrices[i][x][y];
						avg[x][y] /= numMatrices;
					} else
						avg[x][y] += arrayOfMatrices[i][x][y];
				}
			}
		}
		return avg;
	}
	
	/**
	 * Calculate the covariance of a set of column vectors.
	 */
	public static Matrix covariance(Matrix A) {
		Matrix tranA = Matrix.copyMatrix(A);
		tranA.transpose();
		Matrix covariance = Matrix.multiply(tranA, A);
		return covariance;
	}
	
	/**
	 * Calculate the covariance of a set of column vectors.
	 */
	public static float[][] covariance(float[][] floatA) {
		return Matrix.covariance(new Matrix(floatA)).getMatrix();
	}
		
	/**
	 * Matrix to Column Vector, each row is transposed and appended.
	 * 
	 * @param mat
	 * @return
	 */
	public static int[] matrixToCV(int[][] mat) {
		int rows = mat.length;
		int cols = mat[0].length;
		int[] cv = new int[rows*cols];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y ++) {
				cv[y+(x*rows)] = mat[x][y];
			}
		}
		return cv;
	}
	
	public static ColumnVector[] getEigenVectors(KernelLink ml, Matrix L) {
		try {
			/**
			 * Get the Eigen Vectors
			 */
			ml.discardAnswer();
			ml.evaluate("Eigenvectors[" + (L.toString("{", "}", ",", false)) + "]");
			ml.waitForAnswer();
			Matrix matV = new Matrix (ml.getFloatArray2());
			ml.close();
			return matV.getColumnVectors();						
		} catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		} finally {
			ml.close();
		}
		return null;
	}
	
	/**
	 * Split a Matrix up into an array of Column Vectors
	 * 
	 * @param m
	 * @return ColumnVector[]
	 */
	public static ColumnVector[] split(Matrix m) {
		ColumnVector[] cvArray = new ColumnVector[m.cols()];
		for (int j = 0; j < m.cols(); j++)
			cvArray[j] = new ColumnVector(m.getCol(j));
		return cvArray;
	}
	
	/**
	 * Get the absolute value of all the elements.
	 * 
	 * @param m
	 * @return
	 */
	public static Matrix abs(Matrix m) {
		Matrix copy = Matrix.copyMatrix(m);
		for (int i = 0; i < m.rows(); i++)
			for (int j = 0; j < m.cols(); j ++)
				copy.set(i, j, Math.abs(copy.get(i, j)));
		return copy;
	}
            
}
