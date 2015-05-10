package previous;
import csc450Lib.linalg.base.ColumnVector;
import csc450Lib.linalg.base.Matrix;
import csc450Lib.linalg.sle.LinearSolver_LU;
import csc450Lib.linalg.sle.LinearSystemRecord;

/**
 * Play nice with Mathematica and keep the java code in Java.
 *
 * This adapter was needs because the API provided returns proprietary objects 
 * instead of primitives (which obviously play nicer with Mathematica code).
 *
 * I made this class to lighten the load (of Java code) inside Mathematica. It 
 * basically sets up all our instantiations with primitives and returns primitives 
 * for Mathematica.
 */
public class TestsForMathematica {

	public TestsForMathematica() {
		
	}
	
	public int test() {
		return 12345;
	}
	
	public float[][] testMatrixAddition(float[][] af, float[][] bf) {
		Matrix a = new Matrix(af);
		Matrix b = new Matrix(bf);
		
		Matrix c = Matrix.add(a, b);
		return c.getMatrix();
	}
	
	public float[][] testMatrixSubtraction(float[][] af, float[][] bf) {
		Matrix a = new Matrix(new float[][] { {1f,4f,2f}, {0f,3f,4f}, {0f,0f,1f} });
		Matrix b = new Matrix(new float[][] { {1f,3f,1f}, {0f,6f,1f}, {0f,0f,7f} });
		
		Matrix c = Matrix.subtract(a,b);
		return c.getMatrix();
	}
	
	public float[][] testMatrixMultiplication(float[][] af, float[][] bf) {
		Matrix a = new Matrix(new float[][] { {1f,4f,2f}, {0f,3f,4f}, {0f,0f,1f} });
		Matrix b = new Matrix(new float[][] { {1f,3f,1f}, {0f,6f,1f}, {0f,0f,7f} });
		
		Matrix c = Matrix.multiply(a,b);
		return c.getMatrix();
	}
	
	public float[][] testMatrixTranspose(float[][] af) {
		Matrix a = new Matrix(new float[][] { {1f,4f,2f}, {0f,3f,4f}, {0f,0f,1f} });
		
		Matrix c = Matrix.transpose(a);
		return c.getMatrix();
	}
	
	public float testMatrixNorm1(float[][] af) {
		Matrix a = new Matrix(new float[][] { {1f,4f,2f}, {0f,3f,4f}, {0f,0f,1f} });
		return a.norm1();	
	}
	
	public float testMatrixNormInf(float[][] af) {
		Matrix a = new Matrix(new float[][] { {1f,4f,2f}, {0f,3f,4f}, {0f,0f,1f} });
		
		return a.normInf();		
	}
	
	public float[][] testColumnVectorOuterProduct(float[] uf, float[] vf) {
		ColumnVector u = new ColumnVector(uf);
		ColumnVector v = new ColumnVector(vf);	
		
		Matrix o = Matrix.outerProduct(u, v);
		return o.getMatrix();
	}
	
	public float testColumnVectorDotProduct(float[] uf, float[] vf) {
		ColumnVector u = new ColumnVector(uf);
		ColumnVector v = new ColumnVector(vf);	
		
		return Matrix.dotProduct(u, v);
	}
	
	public float[] testBackwardSubstitution(float[][] af, float[] bf) {		
		Matrix a = new Matrix(af);
		ColumnVector b = new ColumnVector(bf);
		LinearSolver_LU slec = new LinearSolver_LU();
		
		return slec.backwardSub(a, b).getCol(0);
	}
	
	public float[] testForwardSubstitution(float [][] af, float [] bf) {
		Matrix L = new Matrix(af);
		ColumnVector b = new ColumnVector(bf);
		LinearSolver_LU slec = new LinearSolver_LU();		
		return slec.forwardSub(L, b).getCol(0);
	}
	
	public float[][] testCrout(float[][] af) { //
		LinearSolver_LU slelu = new LinearSolver_LU();
		Matrix m = new Matrix(af);		
		return Matrix.getMatrix(slelu.crout(m)); 
	}
	
	public float[][] testCroutWithPivoting(float[][] af) {
		LinearSolver_LU slelu = new LinearSolver_LU();		
		Matrix A = new Matrix(af);		
		slelu.solve(A, new ColumnVector(A.getCol(0)));
		return slelu.LU().getMatrix();
	}
	
	public float[][] testLUSolverWithoutPivoting(float[][] af, float[] bf) {
		LinearSolver_LU slelu = new LinearSolver_LU();
		ColumnVector c = new ColumnVector(bf);
		slelu.setSLE(new Matrix(af), new Matrix(c.getMatrix()));
		LinearSystemRecord lsr = slelu.solveWithoutPivoting();
		return lsr.getSolution().getMatrix();
	}
	
	public float[][] testLUSolverPivoting(float[][] af, float[] bf) {
		LinearSolver_LU slelu = new LinearSolver_LU();		
		Matrix A = new Matrix(af);
		ColumnVector b = new ColumnVector(bf); /* Pick an arbitrary b */		
		LinearSystemRecord lsr = slelu.solve(A, b);
		return lsr.getSolution().getMatrix();
	}
	
	public float testUseLUToFindDeterminant(float[][] af) {
		LinearSolver_LU slelu = new LinearSolver_LU();
		
		Matrix A = new Matrix(af);
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
		
		return determinant;
	}	
}
