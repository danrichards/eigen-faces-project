package previous;

import java.util.Arrays;

import csc450Lib.calc.base.Function1D;
import csc450Lib.calc.snle.DeflatedFunction1D;
import csc450Lib.calc.snle.NonLinearSolver_bisection;
import csc450Lib.calc.snle.NonLinearSolver_hybrid;
import csc450Lib.calc.snle.NonLinearSolver_newton;
import csc450Lib.calc.snle.NonLinearSolver_secant;
import csc450Lib.calc.snle.SolutionNLE;

/**
 * Assignment 02 - NLE and the Bouncing Ball
 * 
 * 
 * Assignment Goals:
 * 
 * 		- Follow the spec provided at 
 * 		  https://dl.dropboxusercontent.com/u/6267156/CSC450/Assignments/Prog02/prog02_specs.xhtml
 * 
 * 		- To implement the bisection, Newton-Raphson, and secant algorithms for the numerical resolution
 * 		  of nonlinear equations
 * 
 * 		- To learn about deflation methods
 * 
 * 		- To apply these techniques to the resolution of a simple practical problem (The Bouncing Ball)
 * 		  Run BouncingBall.java application to execute BouncingBall.
 * 		
 * 
 * @author Dan Richards
 * @submission 3/16/2015, Herve, CSC 450
 */
public class Assignment02 {
	
	public boolean debug = false;

	/**
	 * Constructor
	 */
	public Assignment02() {
		System.out.println("Testing SNLE Libraries with Assignment 02 f1 and f2...");

		System.out.println("\n");
		
		/* Output test for f1(x) = xe^x - 2ex^(x/2) = 0 */
		this.testBiSectionOnF1();
		
		System.out.println("\n\n");
		
		/* Output test for f2(x) = x^2 * ln(cosx + 2) - xcosx - 1 = 0 */
		this.testBiSectionOnF2();
		
		System.out.println("\n\n");

		this.testNewtonOnF1();
		
		System.out.println("\n\n");
		
		/* Output test for f2(x) = x^2 * ln(cosx + 2) - xcosx - 1 = 0 */
		this.testNewtonOnF2();
		
		System.out.println("\n\n");

		this.testSecantOnF1();
		
		System.out.println("\n\n");
		
		/* Output test for f2(x) = x^2 * ln(cosx + 2) - xcosx - 1 = 0 */
		this.testSecantOnF2();
		
		System.out.println("\n\n");
		
		/* Output deflation for f2(x) = x^2 * ln(cosx + 2) - xcosx - 1 = 0 */
		
		/**
		 * Initialize our DeflatedFunction1D class with the function and an empty list of solutions
		 */
		DeflatedFunction1D f2 = new DeflatedFunction1D(new A02_F2(), new float[] {});
		
		/**
		 * I built a deflate method into the hybrid solver which optimizes the use of hybrid solver.
		 * 
		 * The solve() method is still available for finding one solution, but deflate will attempt
		 * to find them all.
		 */
		NonLinearSolver_hybrid hybrid = new NonLinearSolver_hybrid();
		float[] results = hybrid.deflate(f2, -17f, 2f, 0.0001f, 500, 8);
		
		/* Continue to get more solution until we've received some abnormal status */
		System.out.println(Integer.toString(results.length) + " solutions discovered in " + Integer.toString(hybrid.lastNumberOfAttempts()) + " attempts.");
		
		System.out.println("\t" + Arrays.toString(results));
		
	}
	
	/**
	 * Output BiSection test for A02_F1
	 */
	private void testBiSectionOnF1() {
		/**
		 * Let's get f1 so we can start playing with our BiSection
		 */
		A02_F1 f1 = new A02_F1();
		System.out.println("Assignment 02: f1(x) = xe^x - 2ex^(x/2) has been loaded.");
		
		/**
		 * Let's give BiSection a try
		 */
		NonLinearSolver_bisection bs = new NonLinearSolver_bisection();		bs.debug = this.debug;
		System.out.println("Using Bisection...");
		
		/**
		 * Solve xe^x - 2ex^(x/2) = 0 using bisection with tolerance of 0.000001 and range [0,5]
		 * 
		 * Solution should be near 1.1343~
		 */
		SolutionNLE solutionF1 = bs.solve(f1, 0.0f, 5.0f, 0.000001f);
		
		/**
		 * Our solution is formalized by SolutionStatus, consider all statuses
		 */
		switch (solutionF1.getStatus()) {
			case SEARCH_SUCCESSFUL :
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF1.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF1.getValueAtSolution()));
				String toleranceNote = solutionF1.getNumberOfIterations() >= bs.maxIterations
						? " and max iterations was exceeded."
						: "";
				System.out.println("\tusing " + Integer.toString(solutionF1.getNumberOfIterations()) + " iterations " + toleranceNote);
				
			break;
			case SEARCH_FAILED_NUMERICAL_ERROR:
				System.out.println("Numerical Error solving xe^x - 2ex^(x/2)");
				break;
			case SEARCH_FAILED_OTHER_REASON:
				System.out.println("Unknown failure solving xe^x - 2ex^(x/2)");
				break;
			case SEARCH_FAILED_OUT_OF_RANGE:
				System.out.println("Range invalid for solving xe^x - 2ex^(x/2)");
				break;
			case SEARCH_FAILED_TOO_MANY_ITERATIONS:
				System.out.println("Too many iterations solving xe^x - 2ex^(x/2)");
				break;
		}
	}
	
	/**
	 * Output BiSection test for A02_F2
	 */
	private void testBiSectionOnF2() {
		
		/**
		 * Let's get f2 so we can start playing with our BiSection
		 */
		A02_F2 f2 = new A02_F2();
		System.out.println("Assignment 02: f2(x) = x^2 * ln(cosx + 2) - xcosx - 1 has been loaded.");
		
		/**
		 * Let's give BiSection a try
		 */
		NonLinearSolver_bisection bs = new NonLinearSolver_bisection();		bs.debug = this.debug;
		System.out.println("Using Bisection...");
		
		/**
		 * Solve x^2 * ln(cosx + 2) - xcosx - 1 = 0 using bisection with tolerance of 0.000001 and range [0,5]
		 * 
		 * Solution should be near 
		 */
		SolutionNLE solutionF2 = bs.solve(f2, 0.0f, 5.0f, 0.000001f);
		
		/**
		 * Our solution is formalized by SolutionStatus, consider all statuses
		 */
		switch (solutionF2.getStatus()) {
			case SEARCH_SUCCESSFUL :
				System.out.println("\tf2(xStar) = " + String.format("%f", solutionF2.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF2.getValueAtSolution()));
				String toleranceNote = solutionF2.getNumberOfIterations() >= bs.maxIterations
						? " and max iterations was exceeded."
						: "";
				System.out.println("\tusing " + Integer.toString(solutionF2.getNumberOfIterations()) + " iterations " + toleranceNote);
				
			break;
			case SEARCH_FAILED_NUMERICAL_ERROR:
				System.out.println("Numerical Error solving x^2 * ln(cosx + 2) - xcosx - 1");
				break;
			case SEARCH_FAILED_OTHER_REASON:
				System.out.println("Unknown failure solving x^2 * ln(cosx + 2) - xcosx - 1");
				break;
			case SEARCH_FAILED_OUT_OF_RANGE:
				System.out.println("Range invalid for solving x^2 * ln(cosx + 2) - xcosx - 1");
				break;
			case SEARCH_FAILED_TOO_MANY_ITERATIONS:
				System.out.println("Too many iterations solving x^2 * ln(cosx + 2) - xcosx - 1");
				break;
		}
	}
	
	/**
	 * Output Newton-Raphson test for A02_F1
	 */
	private void testNewtonOnF1() {
		/**
		 * Let's get f1 so we can start playing with our Newton
		 */
		A02_F1 f1 = new A02_F1();
		System.out.println("Assignment 02: f1(x) = xe^x - 2ex^(x/2) has been loaded.");
		
		/**
		 * Let's give Newton a try
		 */
		NonLinearSolver_newton newton = new NonLinearSolver_newton();	newton.debug = this.debug;
		System.out.println("Using Newton-Raphson...");
		
		/**
		 * Solve xe^x - 2ex^(x/2) = 0 using Newton-Raphson with tolerance of 0.000001 and range [0.3,3]
		 * 
		 * Solution should be near 1.1343~
		 * 
		 * a must be chosen carefully, if we choose an a value that is too low (before f1''[x] is positive)
		 * then our solution will be different than expected or possibly not even determined.
		 */
		SolutionNLE solutionF1 = newton.solve(f1, 0.3f, 3.0f, 0.000001f);
		
		/**
		 * Our solution is formalized by SolutionStatus, consider all statuses
		 */
		switch (solutionF1.getStatus()) {
			case SEARCH_SUCCESSFUL :
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF1.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF1.getValueAtSolution()));
				System.out.println("\tusing " + Integer.toString(solutionF1.getNumberOfIterations()) + " iterations ");
			break;
			case SEARCH_FAILED_NUMERICAL_ERROR:
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF1.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF1.getValueAtSolution()));
				System.out.println("\tusing " + Integer.toString(solutionF1.getNumberOfIterations()) + " iterations ");
				System.out.println("\tuntil division by zero, D[f(xk)], occurred");
				break;
			case SEARCH_FAILED_OTHER_REASON:
				System.out.println("Unknown failure solving xe^x - 2ex^(x/2)");
				break;
			case SEARCH_FAILED_OUT_OF_RANGE:
				System.out.println("Range invalid for solving xe^x - 2ex^(x/2)");
				break;
			case SEARCH_FAILED_TOO_MANY_ITERATIONS:
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF1.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF1.getValueAtSolution()));
				System.out.println("\tusing 1000 (maximum) iterations, consider adjust your range and/or tolerance. ");
				break;
		}
	}
	
	/**
	 * Output Newton-Raphson test for A02_F2
	 */
	private void testNewtonOnF2() {
		/**
		 * Let's get f2 so we can start playing with our Newton
		 */
		A02_F2 f2 = new A02_F2();
		System.out.println("Assignment 02: f2(x) = x^2 * ln(cosx + 2) - xcosx - 1 has been loaded.");
		
		/**
		 * Let's give Newton a try
		 */
		NonLinearSolver_newton newton = new NonLinearSolver_newton();	newton.debug = this.debug;
		System.out.println("Using Newton-Raphson...");
		
		/**
		 * Solve xe^x - 2ex^(x/2) = 0 using Newton-Raphson with tolerance of 0.000001 and range [0.7,3]
		 * 
		 * Solution should be near 1.1343~
		 * 
		 * a must be chosen carefully, if we choose an a value that is too low (before f1''[x] is positive)
		 * then our solution will be different than expected or possibly not even determined.
		 */
		SolutionNLE solutionF2 = newton.solve(f2, 0.7f, 3f, 0.000001f);
		
		/**
		 * Our solution is formalized by SolutionStatus, consider all statuses
		 */
		switch (solutionF2.getStatus()) {
			case SEARCH_SUCCESSFUL :
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF2.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF2.getValueAtSolution()));
				System.out.println("\tusing " + Integer.toString(solutionF2.getNumberOfIterations()) + " iterations ");
			break;
			case SEARCH_FAILED_NUMERICAL_ERROR:
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF2.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF2.getValueAtSolution()));
				System.out.println("\tusing " + Integer.toString(solutionF2.getNumberOfIterations()) + " iterations");
				System.out.println("\tuntil division by zero, D[f(xk)], occurred");
				break;
			case SEARCH_FAILED_OTHER_REASON:
				System.out.println("Unknown failure solving x^2 * ln(cosx + 2) - xcosx - 1");
				break;
			case SEARCH_FAILED_OUT_OF_RANGE:
				System.out.println("Range invalid for solving x^2 * ln(cosx + 2) - xcosx - 1");
				break;
			case SEARCH_FAILED_TOO_MANY_ITERATIONS:
				System.out.println("\tf1(xBest) = " + String.format("%f", solutionF2.getSolution()));
				System.out.println("\twhere xBest = " + String.format("%f", solutionF2.getValueAtSolution()));
				System.out.println("\tusing 1000 (maximum) iterations, consider adjust your range and/or tolerance. ");
				break;
		}
	}
	
	/**
	 * Output Secant test for A02_F1
	 */
	private void testSecantOnF1() {
		/**
		 * Let's get f1 so we can start playing with our secant
		 */
		A02_F1 f1 = new A02_F1();
		System.out.println("Assignment 02: f1(x) = xe^x - 2ex^(x/2) has been loaded.");
		
		/**
		 * Let's give Newton a try
		 */
		NonLinearSolver_secant secant = new NonLinearSolver_secant();	secant.debug = this.debug;
		System.out.println("Using Secant Method...");
		
		/**
		 * Solve xe^x - 2ex^(x/2) = 0 using the secant method with tolerance of 0.000001 and range [0.3,3]
		 * 
		 * Solution should be near 1.1343~
		 * 
		 * a must be chosen carefully, if we choose an a value that is too low (before f1''[x] is positive)
		 * then our solution will be different than expected or possibly not even determined.
		 */
		SolutionNLE solutionF1 = secant.solve(f1, 0.3f, 3f, 0.000001f);
		
		/**
		 * Our solution is formalized by SolutionStatus, consider all statuses
		 */
		switch (solutionF1.getStatus()) {
			case SEARCH_SUCCESSFUL :
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF1.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF1.getValueAtSolution()));
				System.out.println("\tusing " + Integer.toString(solutionF1.getNumberOfIterations()) + " iterations ");
			break;
			case SEARCH_FAILED_NUMERICAL_ERROR:
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF1.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF1.getValueAtSolution()));
				System.out.println("\tusing " + Integer.toString(solutionF1.getNumberOfIterations()) + " iterations ");
				System.out.println("\tuntil division by zero, D[f(xk)], occurred");
				break;
			case SEARCH_FAILED_OTHER_REASON:
				System.out.println("Unknown failure solving xe^x - 2ex^(x/2)");
				break;
			case SEARCH_FAILED_OUT_OF_RANGE:
				System.out.println("Range invalid for solving xe^x - 2ex^(x/2)");
				break;
			case SEARCH_FAILED_TOO_MANY_ITERATIONS:
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF1.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF1.getValueAtSolution()));
				System.out.println("\tusing 1000 (maximum) iterations, consider adjust your range and/or tolerance. ");
				break;
		}
	}
	
	/**
	 * Output Secant test for A02_F2
	 */
	private void testSecantOnF2() {
		/**
		 * Let's get f2 so we can start playing with our secant
		 */
		A02_F2 f2 = new A02_F2();
		System.out.println("Assignment 02: f2(x) = xe^x - 2ex^(x/2) has been loaded.");
		
		/**
		 * Let's give Newton a try
		 */
		NonLinearSolver_secant secant = new NonLinearSolver_secant();	secant.debug = this.debug;
		System.out.println("Using Secant Method...");
		
		/**
		 * Solve xe^x - 2ex^(x/2) = 0 using the secant method with tolerance of 0.000001 and range [0.5,5]
		 * 
		 * Solution should be near 1.1343~
		 * 
		 * a must be chosen carefully, if we choose an a value that is too low (before f1''[x] is positive)
		 * then our solution will be different than expected or possibly not even determined.
		 */
		SolutionNLE solutionF2 = secant.solve(f2, 0.5f, 5.0f, 0.000001f);
		
		/**
		 * Our solution is formalized by SolutionStatus, consider all statuses
		 */
		switch (solutionF2.getStatus()) {
			case SEARCH_SUCCESSFUL :
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF2.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF2.getValueAtSolution()));
				System.out.println("\tusing " + Integer.toString(solutionF2.getNumberOfIterations()) + " iterations ");
			break;
			case SEARCH_FAILED_NUMERICAL_ERROR:
				System.out.println("\tf1(xStar) = " + String.format("%f", solutionF2.getSolution()));
				System.out.println("\twhere xStar = " + String.format("%f", solutionF2.getValueAtSolution()));
				System.out.println("\tusing " + Integer.toString(solutionF2.getNumberOfIterations()) + " iterations");
				System.out.println("\tuntil division by zero, D[f(xk)], occurred");
				break;
			case SEARCH_FAILED_OTHER_REASON:
				System.out.println("Unknown failure solving x^2 * ln(cosx + 2) - xcosx - 1");
				break;
			case SEARCH_FAILED_OUT_OF_RANGE:
				System.out.println("Range invalid for solving x^2 * ln(cosx + 2) - xcosx - 1");
				break;
			case SEARCH_FAILED_TOO_MANY_ITERATIONS:
				System.out.println("\tf1(xBest) = " + String.format("%f", solutionF2.getSolution()));
				System.out.println("\twhere xBest = " + String.format("%f", solutionF2.getValueAtSolution()));
				System.out.println("\tusing 1000 (maximum) iterations, consider adjust your range and/or tolerance. ");
				break;
		}
	}
	
	/**
	 * Output Secant test for A02_F2
	 */
	private void testDeflationOnF2() {
		/* Of course we start with no solutions */
		Function1D originalFunction = new A02_F2();
		Function1D df2 = new A02_F2();
		float[] predecessors = new float[0];
		
		/* Some constraints */
		int attempts = 0;
		int maxAttempts = 500;
		int discover = 8;
		float lowerbound = -17.0f;
		float upperbound = 2.0f;
		
		/**
		 * Initialize our DeflatedFunction1D
		 */
		DeflatedFunction1D f2 = new DeflatedFunction1D(originalFunction, predecessors);
		System.out.println("Assignment 02: f2(x) = x^2 * ln(cosx + 2) - xcosx - 1 has been loaded.");
		
		/**
		 * Our best chance is using the hybrid solver. Set it up to validate solutions that are out 
		 * of bounds and always use the originalFunction to validate.
		 */
		NonLinearSolver_hybrid hybrid = new NonLinearSolver_hybrid();
		hybrid.scrambleBounds(true);
		hybrid.originalFunction(originalFunction);
		hybrid.debug = this.debug;
		
		float[] results = new float[] {};
		
		/* Retrieve solutions using any method until no method works or we've found enough. */
		while (attempts < maxAttempts && results.length < discover) {
			
			/* start over after a few attempts, help hybrid's randomness prevail */
			if (attempts % 10 == 0) {
				for(int i = 0; i < predecessors.length; i++) {
					if (!this.existsInArray(results, predecessors[i], 0.001f))
						results = this.addToArray(results, predecessors[i]);
				}
				predecessors = new float[] {};
				df2 = new DeflatedFunction1D(originalFunction, predecessors);				
			} else {
				df2 = new DeflatedFunction1D(df2, predecessors);				
			}
			
			SolutionNLE lastSolution = hybrid.solve(df2, lowerbound, upperbound, 0.0001f);

			/* If it's a good solution, remove it from future searches. */
			if (lastSolution != null && !existsInArray(predecessors, lastSolution.getValueAtSolution(),0.001f))
				predecessors = addToArray(predecessors, lastSolution.getValueAtSolution());
			
			/* Provide some info on the solution */
			if (debug) {
				System.out.println("\tf2(" + String.format("%f", lastSolution.getValueAtSolution()) + ") = " + 
						String.format("%f", lastSolution.getSolution()) + "\tusing " + 
						Integer.toString(lastSolution.getNumberOfIterations()) + " iterations\n"
				);
			}
			
			attempts ++;
		}
		/* Continue to get more solution until we've received some abnormal status */
		System.out.println(Integer.toString(results.length) + " solutions discovered in " + Integer.toString(attempts) + " attempts.");
		
		Arrays.sort(results);
		System.out.println("\t" + Arrays.toString(results));
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
	 * Run our demo 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Assignment02();
	}
	
}
