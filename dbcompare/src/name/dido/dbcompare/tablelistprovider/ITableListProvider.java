package name.dido.dbcompare.tablelistprovider;

import java.sql.SQLException;
import java.util.List;

public interface ITableListProvider {
	public List<String> getTableNames() throws SQLException;
}
