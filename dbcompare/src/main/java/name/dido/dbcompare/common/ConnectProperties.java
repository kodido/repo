package name.dido.dbcompare.common;

public interface ConnectProperties {
	public static final String propSourceUrl = "src.url";
	public static final String propSourceDriver = "src.driver";
	public static final String propSourceUser = "src.user";
	public static final String propSourcePasswd = "src.pwd";

	public static final String propTargetUrl = "tgt.url";
	public static final String propTargetDriver = "tgt.driver";
	public static final String propTargetUser = "tgt.user";
	public static final String propTargetPasswd = "tgt.pwd";
	
	public static final String propTablesListMethod = "tableList.method";
	public static final String propTableListSelectTable = "tableList.select.table";
	public static final String propTableListSelectColumn = "tableList.select.column";
	
	
	public static final String propTableName = "tableList.single.table";
	public static final String propTableOrderBy = "tableList.single.orderby";
	
	public static final String propTableInsert = "table.insertStatement";
	public static final String propTableInsertCount = "table.insertCount";
	public static final String propNumberOfTasks = "numberOfTasks";
	public static final String propNumberOfExecutors = "numberOfExecutors";
	
	public static final String propOracleDBLinkName = "oracle.dblink.name";
}
