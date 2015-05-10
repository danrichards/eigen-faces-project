package csc450Lib.linalg.sle;
import csc450Lib.linalg.base.Matrix;

public class LinearSystemRecord {

	private Matrix solution;
	private LinearSystemStatus status;
	
	public LinearSystemRecord(LinearSystemStatus theStatus, Matrix theSol) {
		this.solution = theSol;
		this.status = theStatus;
	}
	
	/**
	 * Gives the status of an attempt to solve an SLE
	 * 
	 * @return
	 */
	public LinearSystemStatus getStatus() {
		return this.status;
	}
	
	/**
	 * Gives the solution to an SLE (a null reference if the solution failed)
	 * 
	 * @return
	 */
	public Matrix getSolution() {
		return this.solution;
	}

}
