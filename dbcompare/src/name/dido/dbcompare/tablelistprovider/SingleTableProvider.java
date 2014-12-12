package name.dido.dbcompare.tablelistprovider;

import java.util.Collections;
import java.util.List;

public class SingleTableProvider implements ITableListProvider {

	private String tableName;

	public SingleTableProvider(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public List<String> getTableNames() {
		return Collections.singletonList(tableName);
	}

}
