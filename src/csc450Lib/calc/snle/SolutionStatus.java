package csc450Lib.calc.snle;

/**
 * Provide a convention for Solution Statuses
 * 
 * @author Dan Richards
 */
public enum SolutionStatus {
	SEARCH_SUCCESSFUL					(0, "search successful"),
	SEARCH_FAILED_TOO_MANY_ITERATIONS   (1, "search failed, too many iterations"),
	SEARCH_FAILED_OUT_OF_RANGE 			(2, "search failed, out of range"),
	SEARCH_FAILED_NUMERICAL_ERROR 		(3, "search failed, numerical error"),
	SEARCH_FAILED_OTHER_REASON 			(4, "search failed, other reason");
	
	private final int code;
	private final String statusStr;
	
	SolutionStatus(int code, String statusStr) {
		this.code = code;
		this.statusStr = statusStr;
	}
	
	 public int getCode() {
		 return this.code;
	 }
	 
	 public String getStatusStr() {
		 return this.statusStr;
	 }
	
}
