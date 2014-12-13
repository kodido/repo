package name.dido.dbcompare.generator.parallel;

import java.util.concurrent.Callable;

import name.dido.dbcompare.common.ConnectionData;
import name.dido.dbcompare.generator.TableDataGenerator;
import name.dido.dbcompare.generator.field.ParameterGenerator;

public class DataGenerationTask implements Callable<DataGenerationResult> {

	private String insertSQL;
	private ConnectionData connectionData;
	private ParameterGenerator[] parameterGenerators;
	
	public DataGenerationTask(String insertSQL, ConnectionData connectionData,
			long numberOfRecords, ParameterGenerator[] parameterGenerators) {
		super();
		this.insertSQL = insertSQL;
		this.connectionData = connectionData;
		this.numberOfRecords = numberOfRecords;
		this.parameterGenerators = parameterGenerators;
	}

	private long numberOfRecords;

	@Override
	public DataGenerationResult call() throws Exception {
		TableDataGenerator dataGenerator = new TableDataGenerator();	
		long generated = dataGenerator.generateData(connectionData, numberOfRecords, insertSQL, parameterGenerators);
		return new DataGenerationResult(generated);
	}

}
