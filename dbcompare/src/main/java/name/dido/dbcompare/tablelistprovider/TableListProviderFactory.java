package name.dido.dbcompare.tablelistprovider;

import static name.dido.dbcompare.common.ConnectProperties.propTableListSelectColumn;
import static name.dido.dbcompare.common.ConnectProperties.propTableListSelectTable;
import static name.dido.dbcompare.common.ConnectProperties.propTableName;
import static name.dido.dbcompare.common.ConnectProperties.propTablesListMethod;

import java.sql.Connection;
import java.util.Properties;

import name.dido.dbcompare.compare.cmd.TableCompareException;

public class TableListProviderFactory {

	public static ITableListProvider createTableListProvider(
			Connection connection, Properties props) throws TableCompareException {
		ITableListProvider tableListProvider = null;
		String tableListMethod = props.getProperty(propTablesListMethod);
		if (tableListMethod.equalsIgnoreCase("single")) {
		    tableListProvider = new SingleTableProvider(props.getProperty(propTableName));
		}
		else if (tableListMethod.equalsIgnoreCase("select")) {
			tableListProvider = new SelectTableListProvider(connection, props.getProperty(propTableListSelectTable), props.getProperty(propTableListSelectColumn));
		}
		else throw new TableCompareException("Table list provider method unknown: " + tableListMethod);
		return tableListProvider;
	}	
	
}
