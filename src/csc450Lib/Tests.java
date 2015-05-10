package csc450Lib;

import csc450Lib.calc.base.PolyFunction1D;

/**
 * Some proprietary tests for our csc450Lib, I'll see about implementing JUnit later
 * 
 * @author Dan Richards
 */
public class Tests {
	
	/**
	 * Constructor
	 */
	public Tests() {
		testPolyFunction1D();
	}

	public static void main(String[] args) {
		new Tests();
	}
	
	/**
	 * Provide a test for testPolyFunction1D
	 */
	public void testPolyFunction1D() {
		
		float[] fiveXSquared = {0f, 0f, 5f};
		PolyFunction1D test = new PolyFunction1D(fiveXSquared);
		
		System.out.println("5x^2 w/x@4 = " + Float.toString(test.func(4)));
		System.out.println("Derivative of 5x^2 = 10x w/x@4 = " + Float.toString(test.dfunc(4)));
		
		float[] fourXQuadPlus3Point5XSquaredPlusX = {0f, 1.0f, -3.5f, 0f, 4.0f};
		test = new PolyFunction1D(fourXQuadPlus3Point5XSquaredPlusX);
		
		System.out.println("4x^4-3.5x^2+x w/x@5 = " + Float.toString(test.func(5)));
		System.out.println("Derivative of 4x^4-3.5x^2+x = 16x^3-7x+1 w/x@5 = " + Float.toString(test.dfunc(5)));
		
	}

}
