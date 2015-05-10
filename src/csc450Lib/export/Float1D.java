package csc450Lib.export;

import java.io.PrintWriter;

public class Float1D implements Exportable {

	protected float[] args;
	public String delimiter;
	
	/**
	 * Pass as many float arguments as you like.
	 * 
	 * @param float...args
	 */
	public Float1D(float...args) {
		this.delimiter = " ";
		this.args = args;
	}
	
	/**
	 * We'll need one of these
	 * 
	 * @param PrinterWriter writer
	 */
	public void export(PrintWriter writer) {
		String string = new String();
		for (int i = 0; i < args.length; i++) {
			string += Float.toString(this.args[i]) + this.delimiter();
		}
		writer.println(string);
	}

	/**
	 * Return the delimiter
	 * 
	 * return String this.delimiter
	 */
	public String delimiter() {
		return this.delimiter;
	}
	
	/**
	 * Set the delimiter on the fly
	 * 
	 * @param delimiter
	 */
	public Exportable delimiter(String delimiter) {
		this.delimiter = delimiter;
		return this;
	}

	/**
	 * We may want to check for NaN instead of using exceptions
	 */
	public boolean hasNaN() {
//		for (float arg : this.args) {
//			
//		}
		return false;
	}

	/**
	 * We may want to check for null instead of using exceptions
	 */
	public boolean hasNull() {
		return false;
	}

}
