package previous;
import csc450Lib.calc.base.PolyFunction1D;
import csc450Lib.linalg.base.ColumnVector;
import csc450Lib.linalg.base.Matrix;
import csc450Lib.linalg.sle.LinearSolver_LU;

/**
 * Accessors left public for debugging
 * 
 * @author Danimal
 *
 */
public class TaylorFit {
	
	public int size;
	public PolyFunction1D fit;
	
	/* An array of [x,y] arrays */
	public float[][] points;
	
	/* We may want access to this in Mathematica */
	public float[] coeff;
	
	/* Our Taylor Expansion (less the derivatives) */
	public Matrix expanded;
	
	/**
	 * Build our f[x]
	 * 
	 * @param points
	 */
	public TaylorFit(float[][] points) {
		this.points = points;
		this.size = points.length;
		
		float[] yb = new float[size];
		for (int i = 0; i < size; i++) {
			yb[i] = points[i][1];
		}
		
		ColumnVector b = new ColumnVector(yb);
		expanded = new Matrix(size, size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				expanded.set(i, j, (float) Math.pow(points[i][0], j));
			}
		}
		
		LinearSolver_LU slelu = new LinearSolver_LU();
		this.coeff = slelu.solve(expanded, b).getSolution().getCol(0);
		this.fit = new PolyFunction1D(this.coeff);
	}
	
	/**
	 * Return our trivial Taylor Expansion
	 * 
	 * @return
	 */
	public Matrix expanded() {
		return expanded;
	}
	
	/**
	 * Return our polynomial
	 * 
	 * @return
	 */
	public PolyFunction1D polynomial() {
		return new PolyFunction1D(this.coeff);
	}
	
	/**
	 * Return our coefficients
	 * 
	 * @return
	 */
	public float[] coeff() {
		return coeff;
	}
	
	/**
	 * Resolve our polynomial
	 * 
	 * @param x
	 * @return
	 */
	public float func(float x) {
		return this.fit.func(x);
	}
	
	/**
	 * Return our polynomial as a string
	 * @return 
	 */
	public String toString() {
		String p = "";
		for (int i = 0; i < this.size; i++) {
			if (i == 0)
				p = " " + Float.toString(this.coeff[i]) + " + ";
			else if (i < this.size - 1)
				p += Float.toString(this.coeff[i]) + "x^" + Integer.toString(i) + " + ";
			else
				p += Float.toString(this.coeff[i]) + "x^" + Integer.toString(i);
		}
		return p;
	}

}
