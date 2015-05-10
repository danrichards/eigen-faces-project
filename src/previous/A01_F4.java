package previous;

import csc450Lib.calc.base.Function1D;


public class A01_F4 extends Function1D {

	public A01_F4() {

	}
	
	/**
	 * All child-classes shall implement a func() method
	 * @param x
	 * @return
	 */
	public float func(float x) {
		return (float) Math.cos(
				( Math.pow(x,5) - 1 ) / 
				(1 + Math.pow(x, 3))
		);
	}
	
	/**
	 * All child-classes shall implement an isExactDerivativeDefined() method
	 * @return
	 */
	public boolean isExactDerivativeDefined() {
		return false;
	}

}
