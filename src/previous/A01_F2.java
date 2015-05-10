package previous;

import csc450Lib.calc.base.Function1D;


public class A01_F2 extends Function1D {

	public A01_F2() {

	}
	
	/**
	 * All child-classes shall implement a func() method
	 * @param x
	 * @return
	 */
	public float func(float x) {
		return (float) ( 
				Math.cos(x) * 
					( 
						( 1 - Math.cos(2*x) ) / 
						( 2 + Math.pow( Math.sin(x), 2) )
					)
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
