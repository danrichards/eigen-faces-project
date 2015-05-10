package csc450Lib.linalg.base;

public class ColumnVector extends Matrix {

	/**
	 * Creates and initializes a new column vector
	 * 
	 * @param v
	 */
	public ColumnVector(float[] v) {
		super(convertToMatrix(v));
	}

	/**
	 * Added for compatibility with MatrixGenerator
	 * 
	 * @param v
	 */
	public ColumnVector(float[][] matrix) {
		super(matrix);
	}
	
	/**
	 * Creates a new column vector (initialized to zero values)
	 * 
	 * @param nbRows
	 */
	public ColumnVector(int nbRows) {
		super(nbRows, 1);
	}
	
	/**
	 * Creates a new column vector (initialized to fill values)
	 * 
	 * @param nbRows
	 */
	public ColumnVector(int nbRows, float fill) {
		super(nbRows, 1, fill);
	}
	
	/**
	 * Convert a vector into a matrix for instantiation by the super, Matrix(float[][] a).
	 * 
	 * @param v
	 * @return
	 */
	private static float[][] convertToMatrix (float[] v) {
		float[][] mat = new float[v.length][1];
		int j = 0;
		for(int i = 0; i < v.length; i++)
			mat[i][j] = v[i];
		return mat;
	}
	
	/**
	 * Get a copy of the matrix.
	 * 
	 * @param a
	 * @return
	 */
	public static ColumnVector copyColumnVector(ColumnVector cv) {
		return new ColumnVector(cv.getCol(0));
	}
	
	/**
	 * Calculate the average of a set of column vectors
	 */
	public static float[] averageCV(float[][] cvArray) {
		int numCV = cvArray.length;
		int lenCV = cvArray[0].length;
		float[] avg = new float[lenCV];
		for (int j = 0; j < lenCV; j++) {
			avg[j] = 0;
			for (int i = 0; i < numCV; i++) {
				avg[j] += cvArray[i][j];	
			}
			avg[j] /= numCV;			
		}
		return avg;
	}
	
	/**
	 * Calculate the average Column Vector from and array of Column Vectors
	 */
	public static ColumnVector average(ColumnVector[] cv) {
		int numCvs = cv.length;
		int rows = cv[0].rows();
		float[] avg = new float[rows];
		for (int i = 0; i < numCvs; i++) {
			for (int j = 0; j < rows; j ++) {
				if (i == 0) 
					avg[j] = cv[i].get(j, 0);
				else if (i == (numCvs - 1)) {
					avg[j] += cv[i].get(j, 0);
					avg[j] /= (float) numCvs;
				} else
					avg[j] += cv[i].get(j, 0);
			}
		}
		return new ColumnVector(avg);
	}
	
	/**
	 * Split a long Column Vector into a Matrix
	 * 
	 * @param cv
	 * @return
	 */
	public static ColumnVector[] split(ColumnVector cv, int rows) {
		int cols = cv.rows() / rows;
		ColumnVector[] cvArray = new ColumnVector[rows];
		float[][] f = new float[rows][cols];		
		
		for (int i = 0; i < cv.rows(); i++)
			f[i / rows][i % rows] = cv.get(i, 0);
		
		return Matrix.split(new Matrix(f));
	}
	
	/**
	 * Get the absolute value of all the elements.
	 * 
	 * @param m
	 * @return
	 */
	public static ColumnVector abs(ColumnVector cv) {
		ColumnVector copy = ColumnVector.copyColumnVector(cv);
		for (int i = 0; i < cv.rows(); i++)
			copy.set(i, 0, Math.abs(copy.get(i, 0)));
		return copy;
	}
	
	/**
	 * Find the norm of this vector
	 * 
	 * @return
	 */
	public float norm() {
		return ColumnVector.norm(this);
	}
	
	/**
	 * Find the norm of a vector
	 * 
	 * @param cv
	 * @return
	 */
	public static float norm(ColumnVector cv) {
		float sqSum = 0f;
		for (int i = 0; i < cv.rows(); i++)
			sqSum += Math.pow(cv.get(i,0), 2);
		return (float) Math.sqrt(sqSum);
	}

}
