package csc450Lib.calc.base;

public class PolyFunction1D extends Function1D{

	public float[] coeff;
	
	/**
	 * Constructor accepts array of coefficients
	 * 
	 * @param coeff
	 */
	public PolyFunction1D(float[] coeff) {
		this.coeff = coeff;
	}
	
	/**
	 * Evaluate our polynomial function at x, our array index will server as the power of x
	 * 
	 * For instance: 
	 * poly = new PolyFunction1D(new float[] {5, 10, 15}
	 * float solution = poly.func(x) = 5*x^0 + 10*x^1 + 15*x^2
	 * solution@2 = 5 + 20 + 60 = 85
	 *  
	 * @param float x
	 * @return float solution
	 */
	public float func(float x) {
		float solution = 0f;
		
		for (int i = this.coeff.length - 1; i >= 0; --i)
			solution += this.coeff[i] * Math.pow(x, i);
				
		return solution;
	}
	
	/**
	 * Implement the power rule to find the derivative of a polynomial
	 * 
	 * @param float x
	 * return float derivative
	 */
	public float dfunc(float x) {
		float derivative = 0f;
		for (int i = this.coeff.length - 1; i > 0; --i) {
			System.out.print(Float.toString(this.coeff[i] * (i)) + "x^" + Integer.toString(i-1) + " + ");
			derivative += this.coeff[i] * (i) * Math.pow(x, i-1);
		}
		return derivative;
	}
	
	/**
	 * Does our polynomial have an exact derivative?
	 * 
	 * @return boolean exact
	 */
	public boolean isExactDerivativeDefined() {
		return true;
	}
	

}
