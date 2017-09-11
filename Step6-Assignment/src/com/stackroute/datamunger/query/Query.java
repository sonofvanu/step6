package com.stackroute.datamunger.query;

import java.util.HashMap;

import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.QueryParser;
import com.stackroute.datamunger.reader.CsvAggregateQueryProcessor;
import com.stackroute.datamunger.reader.CsvGroupByAggregateQueryProcessor;
import com.stackroute.datamunger.reader.CsvGroupByQueryProcessor;
import com.stackroute.datamunger.reader.CsvQueryProcessor;
import com.stackroute.datamunger.reader.QueryProcessingEngine;

public class Query {

	public HashMap executeQuery(String queryString) {
		QueryProcessingEngine queryProcessingEngine;
		QueryParameter queryParameter = new QueryParameter();
		QueryParser queryParser = new QueryParser();
		queryProcessingEngine=new CsvQueryProcessor();
	
		queryParameter = queryParser.parseQuery(queryString);

		// checking type of Query

		// queries without aggregate functions, order by clause or group by
		// clause

		DataSet resultSet=new DataSet();
		//resultSet= 
		resultSet=(DataSet) queryProcessingEngine.getResultSet(queryParameter);
		if(!resultSet.isEmpty())
		{
		return resultSet;
		}
		else
		{
			return null;
		}
	}

}
