package csc450Lib.calc.snle;

import csc450Lib.calc.base.Function1D;

/**
 * Leverage deflation to find all zeros within a range
 * 
 * @author Dan Richards
 *
 */
public class DeflatedFunction1D extends Function1D{
	
	public Function1D f;
	public float[] xStar;
	public float denominator;

	/**
	 * Call Function1D with previous occurrences of xStar discovered
	 * 
	 * @param f
	 * @param xStar
	 */
	public DeflatedFunction1D(Function1D f, float[] xStar) {
		/* Our modified func() will call the original */
		this.f = f;
		
		/* What have we already found? */
		this.xStar = xStar;
	}

	/**
	 * Add consideration of previously determined zeros of our f(x)
	 */
	@Override
	public float func(float x) {
		/* We recalculate the denominator so previous xStars are taken out of the equation.
		 * 
		 * Just like our formula x -> f(x) / ( (x -x1Star) (x - x2Star) (x-xnStar) )
		 */
		if (this.xStar != null && this.xStar.length > 0) {
			this.denominator = 1.0f;
			for (int i = 0; i < this.xStar.length; i++) {
				this.denominator *= (x - this.xStar[i]);
			}
//			System.out.println("f(" + String.format("%f", x) + ") = " + String.format("%f", f.func(x)) + " / " + String.format("%f", this.denominator));
			return f.func(x) / (this.denominator);	
		} else {
//			System.out.println("func = " + String.format("%f", f.func(x)) + " / 1");
			return f.func(x);	
		}
	}

	/**
	 * Nope.
	 */
	@Override
	public boolean isExactDerivativeDefined() {
		return false;
	}

}
