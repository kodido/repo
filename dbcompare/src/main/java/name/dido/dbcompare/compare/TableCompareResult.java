package name.dido.dbcompare.compare;

import java.util.ArrayList;
import java.util.List;

public class TableCompareResult {
	
	public TableCompareResult (String tableName) {
		this.tableName = tableName;
	}
	
	private final String tableName; 
	public String getTableName() {
		return tableName;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public long getTotalTimeMsec() {
		return totalTimeMsec;
	}

	private List<TableDifference> differences = new ArrayList<TableCompareResult.TableDifference>();	
	private long totalRows;
	private long totalTimeMsec;
	private String errorMessage;	
	
	public List<TableDifference> getDifferences() {
		return differences;
	}		
	
	public class TableDifference {

		private String message;

		public TableDifference(String message) {
			this.message = message;
		}
		
	}

	public void setTotalRows(long rowCount) {
		this.totalRows = rowCount;
		
	}

	public void setTotalTimeMsec(long msecs) {
		this.totalTimeMsec = totalTimeMsec;
		
	}

	public void setErrorMessage(String message) {
		this.errorMessage = message;		
	}
	
	public String getErrorMessage() {
		return errorMessage;		
	}
}
