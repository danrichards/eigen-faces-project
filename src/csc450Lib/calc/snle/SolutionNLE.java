package csc450Lib.calc.snle;

/**
 * Provide a conventional object for solutions to Non Linear Equations
 * 
 * @author Dan Richards
 */
public class SolutionNLE {

	protected SolutionStatus status;
	protected float xStar;
	protected float fxStar;
	protected int iterations;
	
	public SolutionNLE() {
		this.iterations = 0;
	}
	
	/**
	 * Set the status of our solution search
	 * 
	 * @param status
	 * @return this
	 */
	public SolutionNLE status(SolutionStatus status) {
		this.status = status;
		return this;
	}
	
	/**
	 * Return the status of our solution search
	 * 
	 * @return SolutionStatus
	 */
	public SolutionStatus getStatus() {
		// This method returns the search's status.
		SolutionStatus status = SolutionStatus.SEARCH_SUCCESSFUL;
		return status;
	}
	
	/**
	 * Set the value of fxStar
	 * 
	 * @param xStar
	 * @return SolutionNLE
	 */
	public SolutionNLE solution(float fxStar) {
		this.fxStar = fxStar;
		return this;
	}
	
	/**
	 * Return f(xStar)
	 * 
	 * @return float
	 */
	public float getSolution() {
		// This method returns the value of the solution estimate computed. This value makes 
		// sense when the search has been successful and also to some extent for a search that 
		// stopped after too many iterations.
		return this.fxStar;
	}
	
	/**
	 * Set the value our solution search found to solve f(x)
	 * 
	 * @param xStar
	 * @return this
	 */
	public SolutionNLE value(float xStar) {
		this.xStar = xStar;
		return this;
	}
	
	/**
	 * Return xStar 
	 * 
	 * @return float
	 */
	public float getValueAtSolution() {
		// This method returns the value of the function at the solution estimate computed.
		return this.xStar;
	}
	
	/**
	 * Increase # of iterations
	 * 
	 * @return this
	 */
	public SolutionNLE iterate() {
		this.iterations = this.iterations + 1;
		return this;
	}
	
	/**
	 * Return the number of iterations
	 * 
	 * @return int
	 */
	public int getNumberOfIterations() {
		// This method returns the number of iterations when the search stopped.
		return this.iterations;
	}

}
