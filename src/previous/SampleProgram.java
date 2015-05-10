package previous;
import com.wolfram.jlink.*;

/**
 * SampleProgram.java
 * 
 * Code for the simple J/Link example program presented in section 2.3 of
 * the J/Link User Guide.
 * To compile or run this program, you need to make sure that JLink.jar is in the
 * class path. You can do this by setting the CLASSPATH environment variable or
 * by specifying the path to JLink.jar using the -classpath option on the command
 * line. The examples below use the -classpath option. You can leave this out if
 * your CLASSPATH environment variable includes the full path to JLink.jar.
 * Consult your Java documentation or the J/Link User Guide for more information.
 * To run this program, go to a shell or DOS window and change to the directory in
 * which SampleProgram.class resides. 
 * 
 * Then use a line like:
 * 
 * (Windows)
 * java -classpath ".;\path\to\JLink.jar" SampleProgram -linkmode launch -linkname "c:/program files/wolfram
 * research/mathematica/5.2/mathkernel.exe"
 * 
 * (Unix)
 * java -classpath .:/path/to/JLink.jar SampleProgram -linkmode launch -linkname 'math -mathlink'
 * 
 * (Mac OS X from a terminal window)
 * java -classpath .:/path/to/JLink.jar SampleProgram -linkmode launch -linkname '"/Applications/Mathematica
 * 5.2.app/Contents/MacOS/MathKernel" -mathlink'
 * 
 * If you wish to compile this program, use a line like this:
 * 
 * (Windows)
 * javac -classpath ".;\path\to\JLink.jar" SampleProgram.java
 * (Unix, or Mac OS X from a terminal window)
 * javac -classpath .:/path/to/JLink.jar SampleProgram.java
 */

public class SampleProgram {
		
	static KernelLink ml = null;
	
	public static void main(String[] argv) {
		
		//KernelLink ml = null;
		argv = new String[5];
		argv[0] = "./MathLinkTest";
		argv[1] = "-linkmode";
		argv[2] = "launch";
		argv[3] = "-linkname";
		argv[4] = "\"C:/Program Files\\Wolfram Research\\Mathematica\\10.0\\MathKernel.exe\" -mathlink";
		
		try {
			ml = MathLinkFactory.createKernelLink(argv);
		} catch (MathLinkException e) {
			System.out.println("Fatal error opening link: " + e.getMessage());
			return;
		}
		
		try {
			// Get rid of the initial InputNamePacket the kernel will send
			// when it is launched.
			ml.discardAnswer();
			ml.evaluate("<<MyPackage.m");
			ml.discardAnswer();
			System.out.println("Simple Additions");
			ml.evaluate("2+2");
			ml.waitForAnswer();
			int result = ml.getInteger();
			System.out.println("2 + 2 = " + result);
			// Here ís how to send the same input, but not as a string:
			ml.putFunction("EvaluatePacket", 1);
			ml.putFunction("Plus", 2);
			ml.put(3);
			ml.put(3);
			ml.endPacket();
			ml.waitForAnswer();
			result = ml.getInteger();
			System.out.println("3 + 3 = " + result);
			// If you want the result back as a string, use evaluateToInputForm or
			// evaluateToOutputForm. The second arg for either is the requested page
			// width for formatting the string. Pass 0 for PageWidth->Infinity.
			// These methods get the result in one step--no need to call waitForAnswer.
			String strResult = ml.evaluateToOutputForm("4+4", 0);
			System.out.println("4 + 4 = " + strResult);
			System.out.println();
			System.out.println();
			System.out.println("Integrate[5 x^n a^x, x]");
			String output = ml.evaluateToOutputForm("Integrate[5 x^n a^x, x]", 0);
			System.out.println(output);
			System.out.println();
			System.out.println();
			System.out.println("LeastSquares[{{1, 1}, {1, 2}, {1, 3}}, {7, 7, 8}]");
			output = ml.evaluateToOutputForm("LeastSquares[{{1, 1}, {1, 2}, {1, 3}}, {7, 7, 8}]",-1);
			System.out.println(output);
		} catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		} finally {
			ml.close();
		}
	}
}
