package assignment04;

import csc450Lib.linalg.base.ColumnVector;
import csc450Lib.linalg.base.Matrix;

public class Tests {

	public Tests() {
		/**
		 * Test averageCV
		 */
		System.out.println("Test averageCV");
		float in[][] = new float[][]{ {0f, 0f, 0f}, {4f, 3f, 2f,}, {8f, 6f, 4f}};
		System.out.println("in: " + (new Matrix(in)).toString("{", "} ", ", ", false));
		float out[] = ColumnVector.averageCV(in);
		System.out.print("out: {");
		for (int x = 0; x < 3; x++) {
			System.out.print(Float.toString(out[x]) + ", ");
		}
		System.out.println("}\n\n");
		
		/**
		 * Test Covariance(Matrix)
		 */
		System.out.println("Test Covariance(Matrix)");
		in = new float[][]{{1f,2f,3f,4f}, {5f,6f,7f,8f}, {9f,10f,11f,12f}};
		System.out.println("in: " + (new Matrix(in)).toString("{", "} ", ", ", false));
		System.out.println(Matrix.covariance(new Matrix(in)).toString("{", "} ", ", ", false));
		System.out.println("\n\n");
		
		/**
		 * Test Covariance(float[][])
		 */
		System.out.println("Test Covariance(float[][])");
		in = new float[][]{{1f,2f,3f,4f}, {5f,6f,7f,8f}, {9f,10f,11f,12f}};
		System.out.println("in: " + (new Matrix(in)).toString("{", "} ", ", ", false));
		float floatOut[][] = Matrix.covariance(in);
		System.out.println((new Matrix(floatOut)).toString("{", "} ", ", ", false));
		System.out.println("\n\n");
		
		/**
		 * Combine an array of column vectors into a Matrix
		 */
		System.out.println("Test Combine ColumnVectors into Matrix");
		ColumnVector[] abc = new ColumnVector[3];
		abc[0] = new ColumnVector(new float[]{1,2,3});
		abc[1] = new ColumnVector(new float[]{4,5,6});
		abc[2] = new ColumnVector(new float[]{7,8,9});
		
		Matrix abcMat = new Matrix(abc);
		System.out.println(abcMat.toString("{", "}", ",", true));
		
		/**
		 * Subtract two ColumnVectors
		 */
		System.out.println("Test Subtract to Column Vectors");
		System.out.println(Matrix.subtract(abc[1], abc[0]).toString("{", "}", ",", true));
		
		/**
		 * Average an array of ColumnVectors
		 */
		System.out.println("Test Average an array of Column Vectors");
		System.out.println(ColumnVector.average(abc).toString("{", "}", ",", true));

		/**
		 * Get an array of ColumnVectors from a Matrix
		 */
		System.out.println("Test Convert Matrix to Column Vectors");
		ColumnVector[] cvs = (new Matrix(in)).getColumnVectors();
		for (int i = 0; i < cvs.length; i++)
			System.out.println(cvs[i].toString("{", "}", ",", true));
	}
	

	/**
	 * Run it
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		new Tests();
	}

}
