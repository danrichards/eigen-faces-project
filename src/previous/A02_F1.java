package previous;
import csc450Lib.calc.base.Function1D;


public class A02_F1 extends Function1D {

	public A02_F1() {

	}

	/**
	 * Perform f1(x) from Assignment 02 
	 */
	@Override
	public float func(float x) {
		return (float) (x * Math.exp(x) - 2*Math.exp(x/2.0f));
	}
	
	/**
	 * Perform D[f1(x)] from Assignment 02
	 */
	public float dfunc(float x) {
		return (float) (
				(-1 * Math.exp(x / 2))
				+ (Math.exp(x))
				+ (Math.exp(x) * x)
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
