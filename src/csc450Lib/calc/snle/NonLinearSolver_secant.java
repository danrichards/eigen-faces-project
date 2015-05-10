package csc450Lib.calc.snle;

import csc450Lib.calc.base.Function1D;

/**
 * Leverage "the secant method" to solve Non Linear Equations
 * 
 * @author Dan Richards
 *
 */
public class NonLinearSolver_secant extends NonLinearSolver {

	public int maxIterations = 100;
	public boolean debug = true;

	public NonLinearSolver_secant() {

	}
	
	/**
	 * Find solution using "the secant method" algorithm
	 */
	public SolutionNLE solve(Function1D f, float a, float b, float tol) {
		SolutionNLE sol = new SolutionNLE();
		
		/* Where xkp1 means "x sub k + 1" and xkm1 means "x sub k - 1" */
		float x, xk, xkp1, xkm1, xkBest, fxk, fxkm1, dfxk;
		/* Our xk which will iterate through the rate by the increment we set */
		x = a;
		
		fxk = f.func(x);
		dfxk = f.dfunc(x);
		/* x0 and x1? Why not start with the slope and the first order approximation? */
		xkm1 = dfxk;
		/* Formula derived from the secant method derivative approximation */
		xk = xkm1 - (fxk / dfxk);
		xkBest = xk;

		/* force a to be lower than b */
		if (a > b) {
			if (debug) System.out.println("SEARCH_FAILED_OUT_OF_RANGE");
			return sol.value(xkBest).solution(f.func(xkBest)).status(SolutionStatus.SEARCH_FAILED_OUT_OF_RANGE);
		}
		
		/* Iterate within our range, continue to use the secant approximation for the next iteration. 
		 * Stop when f(xStar) is (very) close to zero. */
		while (sol.getNumberOfIterations() < this.maxIterations) {
			
			/* prepare for our next xk test */
			fxk = f.func(xk);
			fxkm1 = f.func(xkm1);
			sol.iterate();
			
			if (debug) {
				System.out.print("xk+1 = " + String.format("%f", xk) + " - " + 
						"( f(" + String.format("%f", xk) + ")(" + String.format("%f", xk) + " - " + String.format("%f", xkm1) +") )" + "  /  " + 
						"( f(" + String.format("%f", xk) + ") - f(" + String.format("%f", xkm1) + ") ) = ");
				System.out.print("xk+1 = " + String.format("%f", xk) + " - " + 
						"(" + String.format("%f", fxk) + " * (" + String.format("%f", xk) + " - " + String.format("%f", xkm1) +") )" + "  /  " + 
						"( " + String.format("%f", fxk) + " - " + String.format("%f", fxkm1) + " )");
			}
			
			/* division by zero is bad, return xkBest if that happens */
			if (fxk - fxkm1 == 0.0f) {
				if (debug) System.out.println("Division by Zero");
				return sol.value(xkBest).solution(f.func(xkBest)).status(SolutionStatus.SEARCH_FAILED_NUMERICAL_ERROR);
			}
			
			/* if f(xk) is close enough to tolerance (almost zero), we're done */
			if (fxk <= tol && fxk >= (-1 * tol)) {
				if (debug) System.out.println("tolerance breached.");
				return sol.value(xk).solution(fxk).status(SolutionStatus.SEARCH_SUCCESSFUL);
			}
						
			/* eq12: the secant method */
			xkp1 = xk - ( (fxk * (xk - xkm1)) / (fxk - fxkm1) );
			
			if (debug) System.out.println(" = " + String.format("%f", xkp1));
			
			/* if xkPlus1 is closer to zero, save it as the better xStar for our solution */
			xkBest = (f.func(xkp1) < f.func(xkBest)) ? xkp1 : xkBest;
			
			xkm1 = xk;
			xk  = xkp1;
		}
		
		/* If we never come within our tolerance, at least we can checkout what the best
		 * solution for our range and incrementer would have been. */
		return sol.value(xkBest).solution(f.func(xkBest)).status(SolutionStatus.SEARCH_FAILED_TOO_MANY_ITERATIONS);			
	}

}
