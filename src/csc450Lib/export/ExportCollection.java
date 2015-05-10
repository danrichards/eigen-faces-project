package csc450Lib.export;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Set up some conventions for exporting some basic mathematically data structures to text files for Mathematica.
 * 
 * @author Dan Richards
 *
 */
public class ExportCollection {
	
	public String filename;
	public String directory;
	public ArrayList<Exportable> col;
	
	public void export() {		
		try {
			System.setProperty("line.separator", "\n");
			PrintWriter writer = new PrintWriter(this.filename, "UTF-8");
			Iterator<Exportable> it = this.col.iterator();
			while(it.hasNext()) {
				it.next().export(writer);
			}
			writer.close();
		} catch(FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch(Exception e) {
			System.out.println("Export Error");
		}
	}
	
	public ExportCollection withFilename(String filename) {
		this.filename = filename;
		return this;
	}
	
	public ExportCollection inDirectory(String directory) {
		this.directory = directory;
		return this;
	}
	
	
}
