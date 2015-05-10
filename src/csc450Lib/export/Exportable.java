package csc450Lib.export;

import java.io.PrintWriter;

public interface Exportable {
	
	/**
	 * All Exportable types should have and export method.
	 * 
	 * @return String[]
	 */
	public void export(PrintWriter writer);
	
	/**
	 * A delimiter should default to a space.
	 * @return
	 */
	public String delimiter();
	
	/**
	 * A delimiter(String delimiter) method should return this
	 * 
	 * @param delimeter
	 * @return
	 */
	public Exportable delimiter(String delimiter);
	
	/**
	 * Test if our data has a NaN value
	 * 
	 * @return
	 */
	public boolean hasNaN();
	
	/**
	 * Test if our data has a null value
	 */
	public boolean hasNull();
	
	
}
