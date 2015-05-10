package csc450Lib.calc.snle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import csc450Lib.calc.base.Function1D;

/**
 * Hybrid solver picks a random solve method that hasn't been picked yet. If it fails, it'll try the 
 * others until there are no methods left to try in which case null is return. When using hybrid 
 * Solver, you have to check for null. If null is returned, there is no point checking for more 
 * solutions.
 * 
 * See Assignment02.testDeflationOnF2() for an implementation of hybrid solver.
 * 
 * Issues...when used in tandem with the DeflationFunction1D, once one poor solution gets mixed into
 * your deflation strategy (divisor), you'll start to derive other bogus solutions. For more accurate
 * solution, set originalFunction() (before starting deflation). If this is set, the validation
 * function will test against the original function instead.
 * 
 * @author Dan Richards
 */
public class NonLinearSolver_hybrid extends NonLinearSolver {
	
	public boolean debug = false;
	public boolean scrambleBounds = false;
	public Function1D fAlpha;
	public float a, b, tol;
	public int attempts;
	
	/**
	 * Constructor
	 */
	public NonLinearSolver_hybrid() {
		this.fAlpha = null;
	}
	
	/**
	 * Choose a random solver and solve.
	 */
	public SolutionNLE solve(Function1D f, float a, float b, float tol) {
		this.a = a;				this.b = b;			this.tol = tol;
		SolutionNLE sol;
        
        Random rnd = new Random();
        a = this.scrambleBounds ? rnd.nextFloat() * (b - a) + a : a;
		
        ArrayList<Integer> chosen = new ArrayList<Integer>();
        rnd = new Random();
        
        /* Choose a Random solution until we get a valid one or we've exhausted all options */
        int index = rnd.nextInt(3) + 1;
		while (chosen.size() < 3) {
			if ( !chosen.contains(new Integer(index))) {
				sol = this.chooseSolution(index, f, a, b, tol);
				if (this.validSolution(f, sol.getValueAtSolution(), tol) && sol.getStatus() == SolutionStatus.SEARCH_SUCCESSFUL)
					return sol;
				chosen.add(new Integer(index));
			}				
			index = rnd.nextInt(3) + 1;
		}
		
		return null;
	}
	
	/**
	 * Choose a solution based on an integer index
	 * 
	 * @param solverIndex
	 * @param f
	 * @param a
	 * @param b
	 * @param tol
	 * @return
	 */
	public SolutionNLE chooseSolution(int solverIndex, Function1D f, float a, float b, float tol) {
		switch (solverIndex) {
			case 1:
				NonLinearSolver_newton newton = new NonLinearSolver_newton();				newton.debug = this.debug;
				return newton.solve(f, a, b, tol);
			case 2: 
				NonLinearSolver_secant secant = new NonLinearSolver_secant();				secant.debug = this.debug;
				return secant.solve(f, a, b, tol);
			default:
				NonLinearSolver_bisection bisection = new NonLinearSolver_bisection();		bisection.debug = this.debug;
				return bisection.solve(f, a, b, tol);
		}
	}
	
	/**
	 * Provide the original Function1D for validation purposes.
	 * 
	 * @return this
	 */
	public NonLinearSolver_hybrid originalFunction(Function1D alpha) {
		this.fAlpha = alpha;
		return this;
	}
	
	/**
	 * Run deflation using hybrid Solver
	 * 
	 * Some of these tactics are somewhat brute force, but that's why we have really fast computers.
	 * 
	 * @param f
	 * @param a
	 * @param b
	 * @param attemptsToMake
	 * @param solutionsToFind
	 * @return float[]
	 */
	public float[] deflate(Function1D f, float a, float b, float tol, int attemptsToMake, int solutionsToFind) {
		
		/* Of course we start with no solutions */
		float[] predecessors = new float[] {};
		this.attempts = 0;
		
		/* Initialize our DeflatedFunction1D */
		DeflatedFunction1D df = new DeflatedFunction1D(f, predecessors);
		
		/**
		 * Our best chance is using the hybrid solver. Set it up to validate solutions that are out 
		 * of bounds and always use the originalFunction to validate.
		 */
		this.scrambleBounds(true);
		this.originalFunction(f);
		
		float[] results = new float[] {};
		
		/* Retrieve solutions using any method until no method works or we've found enough. */
		while (this.attempts < attemptsToMake && results.length < solutionsToFind) {			
			/* start over after a few attempts, help hybrid's randomness prevail */
			if (this.attempts % 10 == 0) {
				for(int i = 0; i < predecessors.length; i++) {
					if (!this.existsInArray(results, predecessors[i], tol * 10))
						results = this.addToArray(results, predecessors[i]);
				}
				predecessors = new float[] {};
				df = new DeflatedFunction1D(f, predecessors);				
			} else {
				df = new DeflatedFunction1D(df, predecessors);				
			}
			
			SolutionNLE lastSolution = this.solve(df, a, b, tol);

			/* If it's a good solution, remove it from future searches. */
			if (lastSolution != null && !existsInArray(predecessors, lastSolution.getValueAtSolution(), tol))
				predecessors = addToArray(predecessors, lastSolution.getValueAtSolution());
			
			attempts ++;
		}
		/* Continue to get more solution until we've received some abnormal status */
		if (debug) System.out.println(Integer.toString(results.length) + " solutions discovered in " + Integer.toString(attempts) + " attempts.");
		
		Arrays.sort(results);
				
		return results;
	}
	
	/**
	 * Sure wish we were using and abstract data structure
	 */
	private float[] addToArray(float[] array, float value) {
		float[] arrayUpdate = new float[array.length + 1];
		for (int i = 0; i < array.length; i++)
			arrayUpdate[i] = array[i];
		arrayUpdate[array.length] = value;
		return arrayUpdate;
	}
	
	/**
	 * Check if a float exists in an array of floats
	 * 
	 * @param array
	 * @param value
	 * @return
	 */
	private boolean existsInArray(float[] array, float value, float tolerance) {
		if (array.length == 0)
			return false;
		for (int i = 0; i < array.length; i++) {
			if (value == array[i] || Math.abs(array[i]-value) <= tolerance)
				return true;
		}
		return false;
	}
	
	/**
	 * Check if a float exists in an array with zero tolerance
	 * @param array
	 * @param value
	 * @param tolerance
	 * @return
	 */
	private boolean existsInArray(float[] array, float value) { 
		return existsInArray(array, value, 0);
	}
	
	/**
	 * For Information purposes
	 * 
	 * @return int
	 */
	public int lastNumberOfAttempts() {
		return this.attempts;
	}

	/**
	 * Check if a solution is valid ~ f(x) is very close to zero
	 * 
	 * @param f
	 * @param value
	 * @param tolerance
	 * @return
	 */
	private boolean validSolution(Function1D f, float value, float tolerance) {
		float solution = this.fAlpha == null 
				? f.func(value)
				: this.fAlpha.func(value);
		
		if (solution == 0f)
			return true;
		
		if (value >= this.a && value <= this.b)
			return Math.abs(solution) <= tolerance;
		
		return false;
	}
	
	/**
	 * Pick a random value for 'a' when before searching for a solution. The motivation here is to again
	 * make our picks a bit more random so our algorithms have a better chance (when implemented within a
	 * loop)
	 * 
	 * b > random value > a
	 * 
	 * @return
	 */
	public NonLinearSolver_hybrid scrambleBounds(boolean tf) {
		this.scrambleBounds = tf;
		return this;
	}
}
