package csc450Lib.calc.snle;

import csc450Lib.calc.base.Function1D;

/**
 * Leverage Newton-Raphson formula to solve Non Linear Equations
 * 
 * @author Danimal
 *
 */
public class NonLinearSolver_newton extends NonLinearSolver {

	public int maxIterations = 100;
	public boolean debug = false;

	public NonLinearSolver_newton() {

	}
	
	/**
	 * Find solution using Newton-Raphson algorithm
	 */
	public SolutionNLE solve(Function1D f, float a, float b, float tol) {
		SolutionNLE sol = new SolutionNLE();
		
		/* Where xkp1 = "x sub k + 1" */
		float x, xk, xkp1, xkBest, fxk, dfxk;
		/* Our xk which will iterate through the rate by the increment we set */
		x = a;
		
		/* eq8: initialize xk at the beginning of our range */
		fxk = f.func(x);
		dfxk = f.dfunc(x);
		xk =  x - (fxk / dfxk);
		xkBest = xk;

		/* force a to be lower than b */
		if (a > b) {
			if (debug) System.out.println("SEARCH_FAILED_OUT_OF_RANGE");
			return sol.value(xkBest).solution(f.func(xkBest)).status(SolutionStatus.SEARCH_FAILED_OUT_OF_RANGE);
		}
		
		/* Iterate within our range, continue to use the slope from
		 * the previous iteration to guess xStar for the next iteration. Stop when f(xStar)
		 * is (very) close to zero. */
		while (sol.getNumberOfIterations() < this.maxIterations) {
			
			/* prepare for our next xk test */
			fxk = f.func(xk);
			dfxk = f.dfunc(xk);
			sol.iterate();
			
			if (debug) System.out.print("f(xk+1) = " + String.format("%f", xk) + " - (" + 
					String.format("%f", fxk) + " / " + String.format("%f", dfxk) + ")");
			
			/* division by zero is bad, return xkBest if that happens */
			if (dfxk == 0.0f) {
				if (debug) System.out.println("Division by Zero");
				return sol.value(xkBest).solution(f.func(xkBest)).status(SolutionStatus.SEARCH_FAILED_NUMERICAL_ERROR);
			}
			
			/* if f(xk) is close enough to tolerance (almost zero), we're done */
			if (fxk <= tol && fxk >= (-1 * tol)) {
				if (debug) System.out.println("tolerance breached.");
				return sol.value(xk).solution(fxk).status(SolutionStatus.SEARCH_SUCCESSFUL);
			}
						
			/* eq9: approximate our next xk with the previous */
			xkp1 = xk - (fxk / dfxk);
			
			if (debug) System.out.println(" = " + String.format("%f", xkp1));
			
			
			/* if xkPlus1 is closer to zero, save it as the better xStar for our solution */
			xkBest = (f.func(xkp1) < f.func(xkBest)) ? xkp1 : xkBest;
			
			xk  = xkp1;
		}
		
		/* If we never come within our tolerance, at least we can checkout what the best
		 * solution for our range and incrementer would have been. */
		return sol.value(xkBest).solution(f.func(xkBest)).status(SolutionStatus.SEARCH_FAILED_TOO_MANY_ITERATIONS);			
	}

}
