package csc450Lib.linalg.sle;

import csc450Lib.linalg.base.Matrix;

abstract public class LinearSolver {
	
	public Matrix a;
	public Matrix b;

	public LinearSolver() {

	}
	
	/**
	 * Sets a new matrix and right side term for the SLE
	 * 
	 * @param a
	 * @param b
	 */
	public void setSLE(Matrix a, Matrix b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Sets a system matrix to the SLE
	 * 
	 * @param a
	 */
	public void setMatrix(Matrix a) {
		this.a = a;
	}
	
	/**
	 * Sets a new right-side term to the SLE
	 * 
	 * @param b
	 */
	public void setRightSideTerm(Matrix b) {
		this.b = b;			
	}
	
	abstract public LinearSystemRecord solve(Matrix a, Matrix b);
	
	/**
	 * Solves the SLE (with the preset system matrix), using the implementation of the child class
	 * 
	 * @param b
	 * @return
	 */
	public LinearSystemRecord solve(Matrix b) {
		return this.solve(b);
	}
	
	/**
	 * Solves the SLE (with the preset system matrix and right-side term), using the implementation of the child class
	 * 
	 * @return
	 */
	public LinearSystemRecord solve() {
		return this.solve();
	}

}
