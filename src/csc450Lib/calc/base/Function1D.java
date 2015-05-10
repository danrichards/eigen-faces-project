package csc450Lib.calc.base;

public abstract class Function1D {

	public Function1D() {

	}

	/**
	 * All child-classes shall implement a func() method
	 * @param x
	 * @return
	 */
	public abstract float func(float x);
	
	/**
	 * All child-classes shall implement an isExactDerivativeDefined() method
	 * @return
	 */
	public abstract boolean isExactDerivativeDefined();
	
	/**
	 * Richardson's Approximation in case our child-class does not have a exact derivative
	 * @param x
	 * @return
	 */
	public float dfunc(float x) {
		// TODO: write Richardson's approximation 
		return x;
	}
	
}
