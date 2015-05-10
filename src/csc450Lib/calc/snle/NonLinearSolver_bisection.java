package csc450Lib.calc.snle;

import csc450Lib.calc.base.Function1D;

/**
 * Leverage BiSection to solve Non Linear Equations
 * 
 * @author Danimal
 *
 */
public class NonLinearSolver_bisection extends NonLinearSolver {
	
	public int maxIterations = 100;
	public boolean debug = false;

	public NonLinearSolver_bisection() {
		// do nothing
	}
	
	public SolutionNLE solve(Function1D f, float a, float b, float tol) {
		SolutionNLE sol = new SolutionNLE();
		
		float fa, fb, fc, c;
		fa = f.func(a);
		fb = f.func(b);
		c = (a + b) / 2.0f;
		fc = f.func(c);
		
		/**
		 * Solve at midpoint, until we've reached our tolerance or one of our bounds
		 */
		while ( (b-a > 2 * tol) && c != a && c != b) {
			fc = f.func(c);
			sol.iterate();

			/* debugger */
			if (this.debug) System.out.println("fa = f(" + Float.toString(a) + ") = " + 
					Float.toString(fa) + ", fb = f(" + Float.toString(b) + ") = " + 
					Float.toString(fb) + ", fc = f(" + Float.toString(c) + ") = " + Float.toString(fc));
			
			/**
			 * To be safe, we need to give up at some point.
			 */
			if (sol.getNumberOfIterations() > this.maxIterations)
				return sol.value(c).solution(fc).status(SolutionStatus.SEARCH_FAILED_TOO_MANY_ITERATIONS);
			
			/* If our midpoint has the same sign as a, make a the new lower bound */
			if (SameSign(fc, fa)) {
				a = c;
				fa = fc;
			/* Otherwise, so long as we're not at a solution, make c the new upper bound */
			} else if (fc != 0.0f) {
				b = c;
				fb = fc;
			/* We arrived at a solution early, celebrate and return */
			} else {
				return sol.value(c).solution(fc).status(SolutionStatus.SEARCH_SUCCESSFUL);
			}
			
			c = (float) (a + b) / 2.0f;
		}
		
		return sol.value(c).solution(fc).status(SolutionStatus.SEARCH_SUCCESSFUL);
	}
	
	/**
	 * Do two numbers have the same sign?
	 * 
	 * @param number
	 * @return boolean
	 */
	private boolean SameSign(float num1, float num2) {
		return (num1 == num2) ||
			(num1 > 0 && num2 > 0) ||
			(num1 < 0 && num2 < 0);
	}

}
