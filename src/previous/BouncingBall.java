package previous;
import csc450Lib.calc.base.PolyFunction1D;
import csc450Lib.calc.snle.NonLinearSolver_bisection;

/**
 * Assignment 02 - Bouncing Ball
 * 
 * 
 * Bouncing Ball Goals:
 * 
 * 		- To apply these techniques to the resolution of a simple practical problem (The Bouncing Ball)
 * 		  Run BouncingBall.java application to execute BouncingBall.
 * 
 * 		- Note: Assignment02.java provide tests for all of the SNLE solvers.
 * 
 * @author Dan Richards
 * @submission 3/16/2015, Herve, CSC 450
 */
public class BouncingBall {

	public BouncingBall() {
		
		// Initialize a ball with (x0 = 0, z0 = 0, Vx = 1, Vz = 0)
		Ball ball = new Ball(new PolyFunction1D(new float[] {-10}), 0f, 0f, 1f, 0f);
		
		// Pass the Ball to NonLinearSolver to find t
		
		float x0 = 0;
		float y0 = 0;
		float a = 0f;
		float b = 1f;
		
		NonLinearSolver_bisection bs = new NonLinearSolver_bisection();
		
		while (SameSign(ball.func(a), ball.func(b))) {
			
			/* Move our range to check forward */
			a = b;
			b = b + 1;
			
			
			
		}
		
		// Find t so we can now...
		
			// Review final values from ball
		
			// Construct a new Ball based on these final values.
		
				// Run the ball.dfunc to get the tangential vector (one method)
		
				// Use the tangential vector to get the normal vector and determine the next direction / velocity
			
			// Setup our next Non Linear Solver. Look at the sign Vx, set a & b appropriately
		
		
		
		/* Other Notes:
		 * 
		 * Start simple with one impact
		 * Then get a sequence of impacts working (for loop)
		 * Then put it in a while loop until the ball stops.
		 */
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

	public static void main(String[] args) {

	}

}
