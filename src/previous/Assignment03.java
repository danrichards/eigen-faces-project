package previous;

import csc450Lib.linalg.base.ColumnVector;
import csc450Lib.linalg.base.Matrix;
import csc450Lib.linalg.sle.LinearSolver_LU;
import csc450Lib.linalg.sle.LinearSystemRecord;

/**
 * Assignment 03 - SLE and Finding an Equation
 * 
 * Assignment Goals:
 * 
 * 		- Follow the spec provided at 
 * 		  https://dl.dropboxusercontent.com/u/6267156/CSC450/Assignments/Prog03/specs_03/index.html
 * 
 * Assignment Successes ():
 * 		- In addition to the Mathematica notebook, you may run Assignment03 to see tests as console output.
 * 		- From the tests here and the Mathematica notebook, the solvers work pretty good.
 * 		- I spent A LOT of time getting my solvers to work popularly, I didn't get to Part 5 until 4/11.
 * 		
 * @author Dan Richards
 * @submission 3/16/2015, Herve, CSC 450
 */
public class Assignment03 {

	private float[][] af = { {1f,4f,2f}, {0f,3f,4f}, {0f,0f,1f} };
	private float[][] bf = { {1f,3f,1f}, {0f,6f,1f}, {0f,0f,7f} };
	private Matrix a, b;
	private ColumnVector u, v;
	
	private TestsForMathematica t;
	
	public Assignment03() {
		
		/* Not going to rew-write the tes */
		t = new TestsForMathematica();
		
		/* GENERAL MATRIX FUNCTIONS */		
		System.out.println("Testing SLE Matrix functions.\n");		
		this.a = new Matrix(this.af);
		this.b = new Matrix(this.bf);
		this.u = new ColumnVector(new float[] {1, 2, 3});
		this.v = new ColumnVector(new float[] {4, 5, 6});		
		System.out.println("Input Matrix A (" + Integer.toString(a.rows()) + " x "+ Integer.toString(a.cols()) + ")");
		System.out.println(a.toString("{", "}", ",", true));
		System.out.println("Input Matrix B (" + Integer.toString(b.rows()) + " x "+ Integer.toString(b.cols()) + ")");
		System.out.println(b.toString("{", "}", ",", true));
		this.testMatrixAddition();
		this.testMatrixSubtraction();
		this.testMatrixMultiplication();
		this.testMatrixTranspose();
		this.testMatrixNorm1();
		this.testMatrixNormInf();		
		this.testColumnVectorOuterProduct();
		this.testColumnVectorDotProduct();
		System.out.println("\n----------------------------------------------------------\n----------------------------------------------------------\n");
		
		/* TEST CASES FOR SUBSTITUTION */
		this.testBackwardSubstitution();
		this.testForwardSubstitution();
		System.out.println("\n\n----------------------------------------------------------\n----------------------------------------------------------\n");
		
		/* TEST CASES FOR LU DECOMPOSITION AND SUBSTITUTION */
		this.testCroutCase1();		
		this.testCroutCase2();
		System.out.println("\n\n----------------------------------------------------------\n----------------------------------------------------------\n");

		/* TEST CASES FOR LU DECOMPOSITION WITH PIVOTING */
		this.testCroutWithPivotingCase1();
		this.testCroutWithPivotingCase2();		
		System.out.println("\n\n----------------------------------------------------------\n----------------------------------------------------------\n");

		this.testUseLUToFindDeterminantCase1();
		this.testUseLUToFindDeterminantCase2();
		System.out.println("\n\n----------------------------------------------------------\n----------------------------------------------------------\n");

		this.testTaylorFit();
		
//		this.testLUSolverWithoutPivoting();
	}
	
	/**
	 * Test Matrix Addition
	 */
	public void testMatrixAddition() {
		System.out.println("A + B = ");
		Matrix c = Matrix.add(a,b);
		System.out.println(c.toString("{", "}", ",", true));		
	}
	
	/**
	 * Test Matrix Subtraction
	 */
	public void testMatrixSubtraction() {
		System.out.println("A - B = ");
		Matrix c = Matrix.subtract(a,b);
		System.out.println(c.toString("{", "}", ",", true));		
	}
	
	/**
	 * Test Matrix Multiplication
	 */
	public void testMatrixMultiplication() {
		System.out.println("A * B = ");
		Matrix c = Matrix.multiply(a,b);
		System.out.println(c.toString("{", "}", ",", true));		
	}
	
	/**
	 * Test Matrix Transpose
	 */
	public void testMatrixTranspose() {
		System.out.println("A Transposed = ");
		Matrix c = Matrix.transpose(a);
		System.out.println(c.toString("{", "}", ",", true));		
	}
	
	/**
	 * Test Matrix Norm1
	 */
	public void testMatrixNorm1() {
		System.out.println("Norm1(A) = " + Float.toString(this.a.norm1()));	
	}
	
	/**
	 * Test Matrix NormInf
	 */
	public void testMatrixNormInf() {
		System.out.println("NormInf(A) = " + Float.toString(this.a.normInf()));		
	}
	
	/**
	 * Test ColumnVector outerProduct
	 */
	public void testColumnVectorOuterProduct() {
		System.out.println("ColumnVector u (" + Integer.toString(u.rows()) + " x "+ Integer.toString(u.cols()) + ") " + u.toString("{", "}", ",", false));
		System.out.println("ColumnVector v (" + Integer.toString(u.rows()) + " x "+ Integer.toString(u.cols()) + ") " + v.toString("{", "}", ",", false));
		System.out.println("\nouterProduct(u, v) = " + Matrix.toString(Matrix.outerProduct(u, v), "{", "}", ",", false) + "\n");	
	}
	
	/**
	 * Test ColumnVector dotProduct
	 */
	public void testColumnVectorDotProduct() {
		System.out.println("dotProduct(u, v) = " + Float.toString(Matrix.dotProduct(u, v)) + "\n");
	}
	
	public void testBackwardSubstitution() {
		System.out.println("\nBackward Substitution");
		
		Matrix a = new Matrix(new float[][] { {1f, 4f, 2f}, {0f, 3f, 4f}, {0f, 0f, 1f} });
		ColumnVector b = new ColumnVector(new float[] {15, 30, 3});
		LinearSolver_LU slec = new LinearSolver_LU();
		
		System.out.println("U (" + Integer.toString(a.rows()) + " x "+ Integer.toString(a.cols()) + "): " + a.toString("{", "}", ",", true));
		ColumnVector x = slec.backwardSub(this.a, b);
		System.out.println("* x (" + Integer.toString(x.rows()) + " x "+ Integer.toString(x.cols()) + "): "+ x.toString("{", "}", ",", false));
		System.out.println("= b (" + Integer.toString(b.rows()) + " x "+ Integer.toString(b.cols()) + "): " + b.toString("{", "}", ",", false));
	}
	
	/**
	 * Test Forward Substitution
	 */
	public void testForwardSubstitution() {
		System.out.println("\nForward Substitution");
		
		Matrix L = new Matrix(new float[][] { {1,0,0}, {2,1,0}, {4,9,1} });
		ColumnVector b = new ColumnVector(new float[] {1, 10, 90});
		LinearSolver_LU slec = new LinearSolver_LU();
		
		System.out.println("L (" + Integer.toString(L.rows()) + " x "+ Integer.toString(L.cols()) + "): " + L.toString("{", "}", ",", true));
		ColumnVector x = slec.forwardSub(L, b);
		System.out.println("* x (" + Integer.toString(x.rows()) + " x "+ Integer.toString(x.cols()) + "): "+ x.toString("{", "}", ",", false));
		System.out.println("= d (" + Integer.toString(b.rows()) + " x "+ Integer.toString(b.cols()) + "): " + b.toString("{", "}", ",", false));
	}
	
	/**
	 * test Crout (without pivoting) Case 1
	 */
	public void testCroutCase1() {
		System.out.println("\n3.1 & 3.2 LU Decomposition using Crout Test Case 1");
		LinearSolver_LU slelu = new LinearSolver_LU();
		
		Matrix A = new Matrix(new float[][] { {8,2,9}, {4,9,4}, {6,7,9} });
		System.out.println("A (" + Integer.toString(A.rows()) + " x "+ Integer.toString(A.cols()) + "): " + A.toString("{", "}", ",", true));
		Matrix LU = slelu.crout(A);
		System.out.println("LU (" + Integer.toString(LU.rows()) + " x "+ Integer.toString(LU.cols()) + "): " + LU.toString("{", "}", ",", true));

		b = new ColumnVector(new float[] {1, 2, 3}); /* Pick an arbitrary b */
		LinearSystemRecord lsr1 = slelu.solve(A, b);
		System.out.println("x1, x2...xn: " + lsr1.getSolution().toString("{", "}", ",", false));
	}
	
	/**
	 * Test Crout (without pivoting) Case 2
	 */
	public void testCroutCase2() {
		System.out.println("\n3.1 & 3.2 LU Decomposition using Crout Test Case 2");
		LinearSolver_LU slelu = new LinearSolver_LU();
		
		Matrix A = new Matrix(new float[][] {{175, 25, 5, 1}, {512, 64, 8, 1}, {1728, 144, 12, 1}, {8, 4, 2, 1}});
		System.out.println("A (" + Integer.toString(A.rows()) + " x "+ Integer.toString(A.cols()) + "): " + A.toString("{", "}", ",", true));
		Matrix LU = slelu.crout(A);
		System.out.println("LU (" + Integer.toString(LU.rows()) + " x "+ Integer.toString(LU.cols()) + "): " + LU.toString("{", "}", ",", true));

		b = new ColumnVector(new float[] {1, 2, 3, 4}); /* Pick an arbitrary b */
		LinearSystemRecord lsr2 = slelu.solve(A, b);
		System.out.println("x1, x2...xn: " + lsr2.getSolution().toString("{", "}", ",", false));
	}
	
	/**
	 * Test Crout with Pivoting Case 1
	 */
	public void testCroutWithPivotingCase1() {
		System.out.println("\n4.1, 4.2 & 4.3 LU Decomposition using Crout with Pivoting Test Case 1");
		LinearSolver_LU slelu = new LinearSolver_LU();
		
		Matrix A = new Matrix(new float[][] { {8,2,9}, {4,9,4}, {6,7,9} });
		ColumnVector b = new ColumnVector(new float[] {1, 2, 3}); /* Pick an arbitrary b */
		System.out.println("A (" + Integer.toString(A.rows()) + " x "+ Integer.toString(A.cols()) + "): " + A.toString("{", "}", ",", true));
		
		LinearSystemRecord lsr = slelu.solve(A, b);
		System.out.println("LU Crout w/Pivot (" + Integer.toString(slelu.LU().rows()) + " x "+ Integer.toString(slelu.LU().cols()) + "): " + slelu.LU().toString("{", "}", ",", true));
		
		System.out.println("x1, x2...xn = (" + Integer.toString(lsr.getSolution().rows()) + " x "+ Integer.toString(lsr.getSolution().cols()) + "): " + lsr.getSolution().toString("{", "}", ",", true));
	}
	
	/**
	 * Test Crout with Pivoting Case 2
	 */
	public void testCroutWithPivotingCase2() {
		System.out.println("\n4.1, 4.2 & 4.3 LU Decomposition using Crout with Pivoting Test Case 2");
		LinearSolver_LU slelu = new LinearSolver_LU();
		
		Matrix A = new Matrix(new float[][] {{175, 25, 5, 1}, {512, 64, 8, 1}, {1728, 144, 12, 1}, {8, 4, 2, 1}});
		ColumnVector b = new ColumnVector(new float[] {1, 2, 3, 4}); /* Pick an arbitrary b */
		System.out.println("A (" + Integer.toString(A.rows()) + " x "+ Integer.toString(A.cols()) + "): " + A.toString("{", "}", ",", true));
		
		LinearSystemRecord lsr = slelu.solve(A, b);
		System.out.println("LU Crout w/Pivot (" + Integer.toString(slelu.LU().rows()) + " x "+ Integer.toString(slelu.LU().cols()) + "): " + slelu.LU().toString("{", "}", ",", true));
		
		System.out.println("x1, x2...xn = (" + Integer.toString(lsr.getSolution().rows()) + " x "+ Integer.toString(lsr.getSolution().cols()) + "): " + lsr.getSolution().toString("{", "}", ",", true));
	}
	
	/**
	 * Test computing a determinant with an LU decomposition
	 */
	public void testUseLUToFindDeterminantCase1() {
		System.out.println("\n4.4 Find Determinant using LU Decomposed Matrix Test Case 1");
		LinearSolver_LU slelu = new LinearSolver_LU();
		
		Matrix A = new Matrix(new float[][] { {8,2,9}, {4,9,4}, {6,7,9} });
		Matrix lu = slelu.croutWithPivoting(A).LU();
		
		int swaps = 0;
		float determinant = 1f;
		
		/* Get the product of the U diagonal */
		for (int i = 0; i < A.rows(); i++) {
			determinant *= lu.get(slelu.p(i), i);
			swaps += (slelu.p(i) != i) ? 1 : 0;
		}

		/* Switch sign for every two swaps */
		determinant *= ((swaps / 2) % 2 != 0) ? -1f : 1f;
		
		System.out.println("LU Crout w/Pivot (" + Integer.toString(lu.rows()) + " x "+ Integer.toString(lu.cols()) + "): " + lu.toString("{", "}", ",", true));
		System.out.println("det(A) = det(LU) = " + Float.toString(determinant));		
	}
	
	/**
	 * Test computing a determinant with an LU decomposition
	 */
	public String testUseLUToFindDeterminantCase2() {
		System.out.println("\n4.4 Find Determinant using LU Decomposed Matrix Test Case 2");
		LinearSolver_LU slelu = new LinearSolver_LU();

		Matrix A = new Matrix(new float[][] {{175, 25, 5, 1}, {512, 64, 8, 1}, {1728, 144, 12, 1}, {8, 4, 2, 1}});
		Matrix lu = slelu.croutWithPivoting(A).LU();
		
		int swaps = 0;
		float determinant = 1f;
		
		/* Get the product of the U diagonal */
		for (int i = 0; i < A.rows(); i++) {
			determinant *= lu.get(slelu.p(i), i);
			System.out.println("d[" + Integer.toString(slelu.p(i)) + "][" + Integer.toString(i) + "] = " + Float.toString(lu.get(slelu.p(i), i)));
			swaps += (slelu.p(i) != i) ? 1 : 0;
		}

		determinant *= ((swaps / 2) % 2 != 0) ? -1f : 1f;
		
		System.out.println("LU Crout w/Pivot (" + Integer.toString(lu.rows()) + " x "+ Integer.toString(lu.cols()) + "): " + lu.toString("{", "}", ",", true));
		System.out.println("det(A) = det(LU) = " + Float.toString(determinant));
		return "det(A) = det(LU) = " + Float.toString(determinant);
	}

	public int test() {
		return 1234;
	}
	
	public void testLUSolverWithoutPivoting() {
		float[][] f = t.testLUSolverWithoutPivoting(
				new float[][] {{175, 25, 5, 1}, {512, 64, 8, 1}, {1728, 144, 12, 1}, {8, 4, 2, 1}}, 
				new float[] {1, 2, 3, 4}
		);
		Matrix m = new Matrix(f);
		System.out.println(m.toString("{", "}", ",", true));
	}
	
	public void testTaylorFit() {
		TaylorFit tf = new TaylorFit(new float[][] {{0f, 0f}, {0.48f, 0.447469f}, {0.96f, 0.818588f}, 
				{1.44f, 0.799018f}, {1.92f, -0.182628f}, {2.4f, -0.659417f}, {2.88f, 0.167005f}, 
				{3.36f, 0.016369f}, {3.84f, 0.0577753f}, {4.32f, 0.188307f}, {4.8f, -0.816758f}}
		);
		
		System.out.println("Taylor Fit Expansion:" + tf.expanded().toString("{", "}", ",", true));
		System.out.println("Taylor Fit Polynomial:" + tf.toString());
	}

	
	/**
	 * Run our demo 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Assignment03();
	}

}
