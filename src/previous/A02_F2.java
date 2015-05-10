package previous;
import csc450Lib.calc.base.Function1D;


public class A02_F2 extends Function1D {

	public A02_F2() {

	}

	/**
	 * Perform f1(x) from Assignment 02 
	 */
	@Override
	public float func(float x) {
		return (float) (
				Math.pow(x, 2) * Math.log(Math.cos(x) + 2) 
				- x * Math.cos(x) - 1				
		);
	}
	
	/**
	 * Perform D[f1(x)] from Assignment 02
	 */
	public float dfunc(float x) {
		return (float) (
				(-1 * (Math.pow(x, 2) * Math.sin(x)) / (Math.cos(x) + 2))
				+ (x * Math.sin(x))
				- Math.cos(x)
				+ (2 * x * Math.log(Math.cos(x) + 2))
		);
	}

	/**
	 * Yes, we've defined a Derivative function
	 */
	@Override
	public boolean isExactDerivativeDefined() {
		return true;
	}

}
