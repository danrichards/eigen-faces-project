package csc450Lib.linalg.base;

public class RowVector extends Matrix {

	/**
	 * Creates and initializes a new column vector
	 * 
	 * @param v
	 */
	public RowVector(float[] v) {
		super(convertToMatrix(v));
	}

	/**
	 * Added for compatibility with MatrixGenerator
	 * 
	 * @param v
	 */
	public RowVector(float[][] elements) {
		super(elements);
	}
	
	/**
	 * Creates a new row vector (initialized to zero values)
	 * 
	 * @param nbRows
	 */
	public RowVector(int nbCols) {
		super(1, nbCols);
	}
	
	/**
	 * Convert a vector into a matrix for instantiation by the super, Matrix(float[][] a).
	 * 
	 * @param v
	 * @return
	 */
	private static float[][] convertToMatrix (float[] v) {
		float[][] mat = new float[1][v.length];
		int i = 0;
		for(int j = 0; j < v.length; j++)
			mat[i][j] = v[j];
		return mat;
	}

}
