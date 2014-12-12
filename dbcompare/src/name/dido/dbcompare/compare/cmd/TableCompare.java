package name.dido.dbcompare.compare.cmd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.dido.dbcompare.common.ConnectionData;
import name.dido.dbcompare.common.DBConnector;
import name.dido.dbcompare.common.LogUtils;
import name.dido.dbcompare.common.TableUtils;
import name.dido.dbcompare.common.Utils;
import name.dido.dbcompare.compare.ITableComparator;
import name.dido.dbcompare.compare.TableCompareResult;
import name.dido.dbcompare.compare.TraversalTableComparator;
import name.dido.dbcompare.tablelistprovider.ITableListProvider;
import name.dido.dbcompare.tablelistprovider.TableListProviderFactory;
import static name.dido.dbcompare.common.ConnectProperties.*;

public class TableCompare {
	
	private static Logger getLogger() {
		return LogUtils.LOGGER;
	}

	
	public static void main(String[] args) {
		Connection sourceConnection = null, targetConnection = null;
		try {
			LogUtils.startConsoleLogging();
			
			Properties props = Utils.loadProperties("connect.properties");
						
			ConnectionData srcConnectionData = new ConnectionData(
					props.getProperty(propSourceDriver),
					props.getProperty(propSourceUrl),
					props.getProperty(propSourceUser),
					props.getProperty(propSourcePasswd));			
			getLogger().info("Connecting to source DB");
			sourceConnection = DBConnector.connect(srcConnectionData);
			
			ConnectionData tgtConnectionData = new ConnectionData(props.getProperty(propTargetDriver),
					props.getProperty(propTargetUrl), props.getProperty(propTargetUser), props.getProperty(propTargetPasswd));
			getLogger().info("Connecting to target DB");
			targetConnection = DBConnector.connect(tgtConnectionData);
			
			Map<String, String> orderByMap = createOrderByMap();
						
			IOrderByClauseProvider orderByProvider = getOrderByProvider(sourceConnection, orderByMap);
			ITableComparator tableComparator = getTableComparator();

			ITableListProvider tableListProvider = TableListProviderFactory.createTableListProvider(
					sourceConnection, props);
			
			getLogger().info("Comparing tables");	
						
			Map<String, String> errors = compareTablesParallel(sourceConnection, targetConnection, srcConnectionData, tgtConnectionData, orderByProvider,
					tableComparator, tableListProvider);
			
			if (errors.size() > 0) {
				getLogger().severe(
						"Errors found while comparing tables: \n" + toString(errors));
				System.exit(-1);
			}
						
			getLogger().info("Finished.");			
		} catch (Exception e) {			
			getLogger().log(Level.SEVERE, "Exception caught: " + e.getMessage(), e);
			System.exit(1);
		} finally {
			DBConnector.closeConnection(targetConnection);
			DBConnector.closeConnection(sourceConnection);
		}
		
	}

	private static String toString(Map<String, String> errors) {
		StringBuffer sb = new StringBuffer("");
		Iterator<Map.Entry<String, String>> it = errors.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it
					.next();
			sb.append(pairs.getKey()).append(" : ").append(pairs.getValue()).append("\n");

		}
		return sb.toString();
	}


	private static Map<String, String> compareTablesParallel(Connection sourceConnection,
			Connection targetConnection, ConnectionData sourceConnectionData, ConnectionData targetConnectionData, 
			IOrderByClauseProvider orderByProvider,
			ITableComparator tableComparator,
			ITableListProvider tableListProvider) throws SQLException,
			IOException, InterruptedException, ExecutionException {
		Map<String, String> errors = new HashMap<String, String>();

		int numberOfExecutors = 8;
	    ExecutorService executor = Executors.newFixedThreadPool(numberOfExecutors);
	    CompletionService<TableCompareResult> compService = new ExecutorCompletionService<TableCompareResult>(executor);
		
		int numberOfPooled = 0;
		HashSet<String> pooled = new HashSet<String>();
	    
		for (String tableName : tableListProvider.getTableNames()) {
			if (TableUtils.isEmpty(sourceConnection, tableName)) {
				//getLogger().info("Table " + tableName + " is empty on source, nothing to compare");
			} else {
				getLogger().info("Comparing table " + tableName);
				
				String orderByClause;
				try {
					orderByClause = orderByProvider
							.getOrderByClause(tableName);
				} catch (MetadataOrderByProviderException e) {
					errors.put("MetadataOrderByProvider", e.getMessage());
					continue;
				}
				
				/* todo: replace with new connections */				
		    	Callable<TableCompareResult> task = new TableCompareTask(tableComparator, tableName, orderByClause, sourceConnectionData, targetConnectionData);
				compService.submit(task );
				pooled.add(tableName);
				numberOfPooled++;										
			}
		}
		
		/* collect results */
	    for (int i = 0; i < numberOfPooled; ++i) {
	    	Future<TableCompareResult> future = compService.take();
	    	TableCompareResult result = future.get();
			if (result.getErrorMessage() != null) {
				errors.put(result.getTableName(), result.getErrorMessage());				
			}	    	
			
			/* some logging of the results  */
			List<name.dido.dbcompare.compare.TableCompareResult.TableDifference> differences = result.getDifferences();						
			getLogger().info(result.getTableName() + ", differences: " + differences.size()+ " : compared " + result.getTotalRows() + " rows in " + result.getTotalTimeMsec() + " msec");
			int runningCount = (numberOfPooled - i);
			getLogger().info("Still waiting for " + runningCount + " tables to finish");
			pooled.remove(result.getTableName());
			if (runningCount < 20)
				getLogger().info("Running tables: " + pooled.toString());
	    }

	    executor.shutdown(); 		
		
		return errors;
	}
	
	

	private static Map<String, String> createOrderByMap() throws IOException {
		Map<String, String> result = new HashMap<String, String>();			
		Properties orderByProps = Utils.loadProperties("orderby.properties");
        for(java.util.Map.Entry<Object, Object> e : orderByProps.entrySet()) {
            result.put((String) e.getKey(), "ORDER BY " + (String) e.getValue());
        }
		return result;
	}

	private static IOrderByClauseProvider getOrderByProvider(Connection connection,
			Map<String, String> tableNameToOrderByMap) { 
		return new MetaDataOrderByProvider(connection, tableNameToOrderByMap);
	}


	private static ITableComparator getTableComparator() {
		return new TraversalTableComparator();
	}


}
