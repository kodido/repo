package name.dido.dbcompare.compare.cmd;

import java.sql.SQLException;

public interface IOrderByClauseProvider {
	public String getOrderByClause(String tableName) throws MetadataOrderByProviderException, SQLException;
}
