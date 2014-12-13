package name.dido.dbcompare.compare;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface ITableComparator {
	public TableCompareResult compareTable(String tableName, String orderBy, Connection sourceConnection,
			Connection targetConnection) throws SQLException, IOException;
}
