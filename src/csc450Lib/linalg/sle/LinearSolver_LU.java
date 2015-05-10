package csc450Lib.linalg.sle;

import csc450Lib.linalg.base.ColumnVector;
import csc450Lib.linalg.base.Matrix;

public class LinearSolver_LU extends LinearSolver {	
	
	/* Use when pulling out hair. */
	public boolean debug = false;
	
	/* So we don't overwrite a */
	private Matrix LU;
	
	/* Our permutation Matrix */
	private int[] p;
	
	/* Last known Status given the usage of our LinearSolver_LU instance */
	private LinearSystemStatus status;
	
	/**
	 * Constructor
	 */
	public LinearSolver_LU() {
		/* Do nothing. */
	}
	
	/**
	 * Solves the SLE (with the preset system matrix and right-side term)
	 * 
	 * @return A LinearSystemRecord which houses our solution and status.
	 */
	public LinearSystemRecord solve() {
		this.status = LinearSystemStatus.LINEAR_SOLVER_SUCCEDED;
		LU = Matrix.copyMatrix(super.a);
		
		/* Run Crout with pivoting on our copy. */
		croutWithPivoting(this.LU);
		
		/* Run forwardSubstitution on our LU matrix, this vector will serve as the input for our backwardSubstitution */
		ColumnVector xForward = forwardSub(this.LU, new ColumnVector(super.b.getCol(0)), p);
		ColumnVector xBackward = backwardSub(this.LU, xForward, p);
		
		if (debug) {
			System.out.println("backwardSubstitution (" + Integer.toString(xBackward.rows()) + " x "+ Integer.toString(xBackward.cols()) + "): " + xBackward.toString("{", "}", ",", true));
			System.out.println("forwardSubstitution (" + Integer.toString(xForward.rows()) + " x "+ Integer.toString(xForward.cols()) + "): " + xForward.toString("{", "}", ",", true));	
		}
		
		return new LinearSystemRecord(status, xBackward);
	}
	
	/**
	 * Solves the SLE (with the preset system matrix and right-side term)
	 * 
	 * @return A LinearSystemRecord which houses our solution and status.
	 */
	public LinearSystemRecord solveWithoutPivoting() {
		LU = Matrix.copyMatrix(super.a);
		
		/* Run Crout with pivoting on our copy. */
		this.LU = crout(this.LU);
		
		/* Run forwardSubstitution on our LU matrix, this vector will serve as the input for our backwardSubstitution */
		ColumnVector xForward = forwardSub(this.LU, new ColumnVector(super.b.getCol(0)));
		ColumnVector xBackward = backwardSub(this.LU, xForward);
		
		if (debug) {
			System.out.println("backwardSubstitution (" + Integer.toString(xBackward.rows()) + " x "+ Integer.toString(xBackward.cols()) + "): " + xBackward.toString("{", "}", ",", true));
			System.out.println("forwardSubstitution (" + Integer.toString(xForward.rows()) + " x "+ Integer.toString(xForward.cols()) + "): " + xForward.toString("{", "}", ",", true));	
		}
		
		return new LinearSystemRecord(LinearSystemStatus.LINEAR_SOLVER_SUCCEDED, xBackward);
	}


	/**
	 * Solves the SLE
	 */
	@Override
	public LinearSystemRecord solve(Matrix a, Matrix b) {
		this.a = a;
		this.b = b;		
		return this.solve();
	}


	/**
	 * Solves the SLE
	 */
	public LinearSystemRecord solve(Matrix a, ColumnVector b) {
		this.a = a;
		
		/* Setup a Matrix that complements our ColumnVector */
		float bm[][] = new float[b.rows()][1];
		int j = 0;
		for(int i = 0; i < b.rows(); i++)
			bm[i][j] = b.get(i, j);		
		this.b = new Matrix(bm);
		
		return this.solve();
	}
	
	/**
	 * Run Crout's algorithm and return an LU matrix
	 * 
	 * Matrix a is preserved.
	 * 
	 * @param a
	 * 
	 * @return an LU matrix
	 */
	public Matrix crout(Matrix a) {
		int i, j, k, n;
		n = a.cols();
		Matrix lu = new Matrix(n, n);
		Matrix l = new Matrix(n, n);
		Matrix u = new Matrix(n, n);
		float s = 0f;
		float sum = 0f;
		
		/* For all columns */ 
		for (j = 0; j < n; j++) {
			
			/* For all elements above the diagonal */
			for (i = 0; i < j; i++) {
				sum = 0f;
				for (k = 0; k <= (i - 1); k++) {
					sum += l.get(i, k) * u.get(k, j);
				}
				if (debug) System.out.println("U[" + Integer.toString(i) + "][" + Integer.toString(j) + "] = " + Float.toString(lu.get(i, j) - sum));
				u.set(i, j, a.get(i, j) - sum);
			}
			
			/* For all elements below the diagonal */
			for (i = j; i < n; i++) {
				sum = 0f;
				for (k = 0; k <= (j - 1); k++) {
					sum += l.get(i, k) * u.get(k, j);
				}
				if (debug) System.out.println("LU[" + Integer.toString(i) + "][" + Integer.toString(j) + "] = " + Float.toString(lu.get(i, j) - sum));
				lu.set(i, j, a.get(i, j) - sum);
			}
			
			// PIVOTING HERE LATER
			
			/* Our l diagonal: only needed for debugging since we only return one matrix with u diagonal */
			l.set(j, j, 1f);
			
			/* Our u diagonal */
			u.set(j, j, lu.get(j, j));
			
			if (u.get(j, j) == 0f) {
				s = 0f ;
				if (debug) System.out.println("ujj = 0, problem!");
			} else {
				s = 1 / u.get(j, j);
			}
			
			/* For all elements below the diagonal */
			for (i = j + 1; i < n; i++) {
				l.set(i, j, s * lu.get(i, j));
			}
		}
		
		/* Return on LU matrix as one matrix */
		for (j = 0; j < n; j++) {
			for (i = 0; i < n; i++) {
				if (i <= j)
					lu.set(i, j, u.get(i, j));
				else
					lu.set(i, j, l.get(i, j));
			}
		}
		
		if (debug) System.out.println("L: " + l.toString("{", "}", ",", true));
		if (debug) System.out.println("U: " + u.toString("{", "}", ",", true));
		
		return lu;
	}
	
	/**
	 * Run Crout's algorithm with Pivoting.
	 * 
	 * Matrix a is overwritten with LU matrix.
	 * 
	 * @return int[n] pivot array
	 */
	public LinearSolver_LU croutWithPivoting(Matrix a) {
		int i, j, k, n;
		n = a.cols();
		Matrix lu = a;
		Matrix l = a;
		Matrix u = a;
		float s = 0f;
		float sum = 0f;
		float v[] = new float[n];
		p = new int[n];
		
		/* Initialize our pivot array */
		for (i = 0; i < n; i ++)
			p[i] = i;
		
		/* Max coefficient in each row */
		for (i = 0; i < n; i++)
			v[i] = absMaxInArray(a.getRow(i));
		
		if (debug) {
			System.out.print("v: ");
			for (i = 0; i < v.length; i++)
				System.out.print(v[i] + "  ");
		}
		
		/* For all columns */ 
		for (j = 0; j < n; j++) {
			
			/* For all elements above the diagonal */
			for (i = 0; i < j; i++) {
				sum = 0f;
				for (k = 0; k < i; k++) {
					sum += l.get(p[i], k) * u.get(p[k], j);
				}
				if (debug) {
					System.out.println("u[" + Integer.toString(p[i]) + "][" + Integer.toString(j) + "] = " + 
						Float.toString(u.get(p[i], j)) + " - " + Float.toString(sum) + " = " + 
						Float.toString(u.get(p[i], j) - s)
					);
				}
				u.set(p[i], j, u.get(p[i], j) - sum);
			}
			
			/* For all elements below the diagonal */
			for (i = j; i < n; i++) {
				sum = 0f;
				for (k = 0; k < j; k++) {
					sum += l.get(p[i], k) * u.get(p[k], j);
				}
				if (debug) {
					System.out.println("a[" + Integer.toString(p[i]) + "][" + Integer.toString(j) + "] = " + 
						Float.toString(a.get(p[i], j)) + " - " + Float.toString(sum) + " = " + 
						Float.toString(a.get(p[i], j) - s)
					);
				}
				a.set(p[i], j, a.get(p[i], j) - sum);
			}
			
			/* Initialize the pivot choice on the diagonal. Don't divide by zero. */
			float division = v[p[j]] > 0.0f ? Math.abs(lu.get(p[j], j) / v[p[j]]) : 0f;
			float max = division;
			int iStar = j;
			
			/* Starting one right of the diagonal, find the best pivot */
			for (i = j + 1; i < n; i++) {
				division = Math.abs(lu.get(p[i], j) / v[p[i]]);
				if (division >= max) {
					max = division;
					iStar = i;
				}
			}
			
			/* Swap */
			int temp = p[j];
            p[j] = p[iStar];
            p[iStar] = temp;
            System.out.println("pivot: " + Integer.toString(temp) + " <-> " + Integer.toString(p[j])); //if (debug) 
			
			/* Scale */
			s = Math.abs(u.get(p[j], j)) > 0f ? (1.0f / u.get(p[j], j)) : 0f;
			for (i = j + 1; i < n; i++) {
				if (debug) {
					System.out.println("scale a[" + Integer.toString(p[i]) + "][" + Integer.toString(j) + "] = " + 
						Float.toString(a.get(p[i], j)) + " * " + Float.toString(s) + " = " + 
						Float.toString(a.get(p[i], j) * s)
					);
				}
                a.set(p[i], j , a.get(p[i],  j) * s);
			}
		}
		
		/* In case we call croutWithoutPivoting() outside of solve() */
		LU = Matrix.copyMatrix(a);
		
		if (debug) {
			System.out.println("LU: " + a.toString("{", "}", ",", true));
			System.out.print("p: ");
			for (i = 0; i < p.length; i++)
				System.out.print(p[i] + "  ");
			System.out.println("");
		}
		
		return this;
	}
	
	/**
	 * In case we want to run croutWithPivoting, backwardSub, forwardSub outside this class.
	 * 
	 * @return Our pivoting
	 */
	public int[] p() {
		return p;
	}
	
	/**
	 * In case we want to run croutWithPivoting, backwardSub, forwardSub outside this class.
	 * 
	 * @return Our pivoting
	 */
	public int p(int i) {
		return p[i];
	}
	
	/**
	 * In case we want to run croutWithPivoting, backwardSub, forwardSub outside this class.
	 * 
	 * @return Our pivoting
	 */
	public Matrix LU() {
		return LU;
	}
	
	/**
	 * backwardSub
	 * 
	 * @param U
	 * @param b
	 * @return
	 */
	public ColumnVector backwardSub(Matrix U, ColumnVector b) {
		float sum = 0f;
		int n = b.rows();
		float[] x = new float[b.rows()];
		/* Initialize the last element */
		x[n-1] = b.get(n-1, 0) / U.get(n-1, n-1);
		/* Iterate backwards through the remaining elements. */
		for (int i = (n-2); i >= 0; i--) {
			sum = 0f;
			for (int j = i+1; j <= n-1; j++) {
				sum += (U.get(i, j) * x[j]);
			}
			x[i] = (1 / U.get(i, i)) * (b.get(i, 0) - sum);
			/* System.out.println("xi  = 1/uii  *  (bi  -  sum)"); */
			/* System.out.println(Float.toString(x[i]) + " = " + Float.toString(1 / U.get(i, i)) + " * (" + 
					Float.toString(b.get(i,0)) + " - " + Float.toString(sum) + ")"); */
		}
		return new ColumnVector(x);
	}
	
	/**
	 * backwardSub
	 * 
	 * @param U
	 * @param b
	 * @return
	 */
	public ColumnVector backwardSub(Matrix U, Matrix b) {
		/* Setup a ColumnVector that complements our Matrix */
		float bv[] = new float[b.rows()];
		int j = 0;
		for(int i = 0; i < b.rows(); i++)
			bv[i] = b.get(i, j);
		
		return backwardSub(U, new ColumnVector(bv));
	}
	
	/**
	 * backwardSub
	 * 
	 * @param U
	 * @param b
	 * @param p
	 * @return
	 */
	public ColumnVector backwardSub(Matrix U, ColumnVector b, int[] p) {
		float sum = 0f;
		int m = b.rows();
		float[] x = new float[m];
		/* Initialize the last element, use p for U only (forward sub already switched b) */
		x[m-1] = b.get(m-1, 0) / U.get(p[m-1], m-1);
		/* Iterate backwards through the remaining elements, use p for U only (forward sub already switched b) */
		for (int i = (m-2); i >= 0; i--) {
			sum = 0f;
			for (int j = i+1; j <= m-1; j++) {
				sum += (U.get(p[i], j) * x[j]);
			}
			x[i] = (1 / U.get(p[i], i)) * (b.get(i, 0) - sum);
			/* System.out.println("xi  = 1/uii  *  (bi  -  sum)"); */
			/* System.out.println(Float.toString(x[i]) + " = " + Float.toString(1 / U.get(p[i], i)) + " * (" + 
					Float.toString(b.get(p[i],0)) + " - " + Float.toString(sum) + ")"); */
		}
		return new ColumnVector(x);
	}
	
	/**
	 * backwardSub with b as float[]
	 * 
	 * @param U
	 * @param b
	 * @return
	 */
	public ColumnVector backwardSub(Matrix U, float[] b) {
		return backwardSub(U, new ColumnVector(b));
	}
	
	/**
	 * forwardSub
	 * 
	 * @param L
	 * @param d
	 * @return
	 */
	public ColumnVector forwardSub(Matrix L, ColumnVector d, int[] p) {
		int n = d.rows();
		float sum = 0f;
		float[] x = new float[n];
		/* Initialize the first element, use p for L and d */
		x[0] = d.get(p[0], 0);
		/* Iterate through the rest, use p for L and d */
		for (int i = 1; i < n; i++) {
			sum = 0f;
			for (int j = 0; j <= (i-1); j++) {
				sum += (L.get(p[i], j) * x[j]);
				/* System.out.println("i(" + Integer.toString(i) + "): sum (" + Float.toString(sum) 
						+ ") = Li(" + Float.toString(L.get(p[i],j)) + ") * xj("+ Float.toString(x[j])+ ")"); */
			}
			x[i] = d.get(p[i], 0) - sum;
			/* System.out.println("xi = di - sum");
			System.out.println(Float.toString(x[i]) + " = " + Float.toString(d.get(p[i], 0)) + " - " + Float.toString(sum)); */
		}
		return new ColumnVector(x);
	}
	
	/**
	 * forwardSub
	 * 
	 * @param L
	 * @param d
	 * @return
	 */
	public ColumnVector forwardSub(Matrix L, ColumnVector d) {
		float sum = 0f;
		float[] x = new float[d.rows()];
		/* Initialize the first element*/
		x[0] = d.get(0, 0);
		/* Iterate through the rest */
		for (int i = 1; i < d.rows(); i++) {
			sum = 0f;
			for (int j = 0; j <= (i-1); j++) {
				sum += (L.get(i, j) * x[j]);
				/* System.out.println("i(" + Integer.toString(i) + "): sum (" + Float.toString(sum) 
						+ ") = Li(" + Float.toString(L.get(i,j)) + ") * xj("+ Float.toString(x[j])+ ")"); */
			}
			x[i] = d.get(i, 0) - sum;
			/* System.out.println("xi = di - sum");
			System.out.println(Float.toString(x[i]) + " = " + Float.toString(d.get(i, 0)) + " - " + Float.toString(sum)); */
		}
		return new ColumnVector(x);
	}
	
	/**
	 * forwardSub
	 * 
	 * @param U
	 * @param b
	 * @return
	 */
	public ColumnVector forwardSub(Matrix L, Matrix d) {
		/* Setup a ColumnVector that complements our Matrix */
		float dv[] = new float[d.rows()];
		int j = 0;
		for(int i = 0; i < d.rows(); i++)
			dv[i] = d.get(i, j);
		return forwardSub(L, new ColumnVector(dv));
	}
	
	/**
	 * forwardSub with d as float[]
	 * @param L
	 * @param d
	 * @return
	 */
	public ColumnVector forwardSub(Matrix L, float[] d) {
		return forwardSub(L, new ColumnVector(d));
	}
	
	/**
	 * Iterate through and array and find the absolute value maximum
	 * 
	 * @param array
	 * @return
	 */
	public float absMaxInArray(float[] array) {		
		if (array.length == 0)
			return 0f;
		float max = array[0];
		for (int i = 1; i < array.length; i++)
			max = array[i] > max ? array[i] : max;
		return max;
	}

}
