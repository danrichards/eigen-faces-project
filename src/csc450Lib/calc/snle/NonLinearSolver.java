package csc450Lib.calc.snle;

import csc450Lib.calc.base.Function1D;

public abstract class NonLinearSolver {

	public NonLinearSolver() {

	}
	
	abstract public SolutionNLE solve(Function1D f, float a, float b, float tol);

}
