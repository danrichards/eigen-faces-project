package previous;
/**
 * Assignment 01 - Build a package structure for our csc450Lib Java Libraries.
 * 
 * 
 * Assignment Goals:
 * 
 * 		- Stub a sequence of math packages (skeleton of our csc450Lib) leveraging the conventions defined in the 
 * 		  prog01_specs. https://dl.dropboxusercontent.com/u/6267156/CSC450/Assignments/Prog01/prog01_specs.xhtml
 * 
 * 		- Build an abstract Function1D class with abstract func() and isExactDerivativeDefined() methods and a
 * 		  general purpose dfunc() method which implements Richardson's approximation.
 * 
 * 		- Build a child class Polynomial1D with a constructor which accepts an array of floats for the coefficients.
 * 
 * 		- Important: Please use the Tests class in the csc450Lib main package to test the PolyFunction1D and 
 * 		  Function1D classes.
 * 		
 * 
 * Supplementary Classes:
 * 
 * 		A few extras because I was a little confused by the assignment at first.
 * 			F1, F2, F3, F4 (please ignore these), csc450Lib/Tests (use to test), csc450Lib.export/Export (a little 
 * 			something I'm working on to use to generate Mathematica input throughout the semester)
 * 
 * 	    I considered doing dfunc(Richardsons) for the extra credit, but I'm not sure yet how do to do this quite
 * 		yet without imported third-party classes, for a explicit derivative function (don't know if that is allowed).
 *      I wait until we talk about it in class and do it right.
 * 
 * @author Dan Richards
 * @submission 2/27/2015, Herve, CSC 450
 *
 */
public class Assignment01 {

	public Assignment01() {
		System.out.println("Please run the csc450Lib Tests main() to check assignment goals.");
	}

	public static void main(String[] args) {
		new Assignment01();
	}
	
}
