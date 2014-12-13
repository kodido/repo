package name.dido.dbcompare.generator.parallel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import java.util.logging.Logger;

import name.dido.dbcompare.common.ConnectionData;
import name.dido.dbcompare.common.DBConnector;
import name.dido.dbcompare.common.LogUtils;
import name.dido.dbcompare.common.metadata.ColumnMetaData;
import name.dido.dbcompare.generator.TableDataGenerator;
import name.dido.dbcompare.generator.field.ParameterGenerator;

public class ParallelDataGenerator {

	private static Logger getLogger() {
		return LogUtils.LOGGER;
	}
	
	private ConnectionData connectionData;

	public ParallelDataGenerator(ConnectionData connectionData) {
		this.connectionData = connectionData;
	}

	public void generateData(String tableName, 
			long numberOfRows, int numberOfTasks, int numberOfExecutors) throws SQLException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException, ClassNotFoundException {		
	    ExecutorService executor = Executors.newFixedThreadPool(numberOfExecutors);
	    CompletionService<DataGenerationResult> compService = new ExecutorCompletionService<DataGenerationResult>(executor);
	    
		Connection connection = DBConnector.connect(connectionData.getDriver(),
				connectionData.getUrl(), connectionData.getUser(),
				connectionData.getPasswd());	   
		String insertSQL = null;
		ParameterGenerator[] parameterGenerators = null;
		
	    try {
	    	List<ColumnMetaData> columnsMetadata = ColumnMetaData.getColumnsMetadata(tableName, connection);
			insertSQL = TableDataGenerator.generateInsertSQL(tableName, columnsMetadata);
			
			parameterGenerators = TableDataGenerator.createParmeterGenerators(columnsMetadata);
	    } finally {
	    	connection.close();
	    }
	    
	    for (int i = 0; i < numberOfTasks; ++i)
	    {	    		    	
	    	Callable<DataGenerationResult> task = new DataGenerationTask(insertSQL, connectionData, numberOfRows/numberOfTasks, parameterGenerators);
			compService.submit(task );
	    }
	    
	    long generated = 0;
	    for (int i = 0; i < numberOfTasks; ++i) {
	    	Future<DataGenerationResult> future = compService.take();
	    	DataGenerationResult result = future.get();
	    	generated += result.getGenerated();
	    	getLogger().info("Task generated " + result.getGenerated() + " rows; total rows generated:" + generated);
	    }

	    executor.shutdown(); 
	}
	
}
