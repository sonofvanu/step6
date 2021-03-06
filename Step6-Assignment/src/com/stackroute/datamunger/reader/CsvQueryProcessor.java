package com.stackroute.datamunger.reader;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Filter;
import com.stackroute.datamunger.query.GenericComparator;
import com.stackroute.datamunger.query.Header;
import com.stackroute.datamunger.query.Row;
import com.stackroute.datamunger.query.RowDataTypeDefinitions;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;


//this class will read from CSV file and process and return the resultSet
public class CsvQueryProcessor implements QueryProcessingEngine {

	public DataSet getResultSet(QueryParameter queryParameter) {
		

		// initialize BufferedReader
		/*
		 * String queryString="select * from data/ipl.csv"; QueryParameter
		 * queryParameter; QueryParser qp=new QueryParser();
		 * queryParameter=qp.parseQuery(queryString);
		 */
		DataSet dataSet = new DataSet();
		Filter filter = new Filter();
		Header header = new Header();
		BufferedReader bufferedReader;
		try {
			dataSet.clear();
			bufferedReader = new BufferedReader(new FileReader(queryParameter.getFile()));
			// read the first line which contains the header
			String Header[] = bufferedReader.readLine().split(",");
			int index = 0, headerCount = Header.length;
			
			// populate the header Map object from the header array
			for (String eachHeader : Header) {
				header.put(eachHeader.toLowerCase(), index);
				index++;
			}
			// read the next line which contains the first row of data
			String[] firstRowData = bufferedReader.readLine().split(",");
			DataTypeDefinitions dataTypeDefinitions = new DataTypeDefinitions();
			RowDataTypeDefinitions rowDataTypeDefinitions = new RowDataTypeDefinitions();
			int point = 0;
			for (String eachFirstRowData : firstRowData) {
				rowDataTypeDefinitions.put(point,
						dataTypeDefinitions.getDataType(eachFirstRowData).getClass().getName());
				point++;
			}
			// reset the buffered reader so that it can start reading from
			// the first
			// line
			bufferedReader = new BufferedReader(new FileReader(queryParameter.getFile()));
			int increment = 0;
			String singleRow = bufferedReader.readLine();
			Set<String> tempHeaderCollection = header.keySet();
			Long locationPoint = (long) 0;
			int tempheaderCollectionSize = tempHeaderCollection.size();
			String[] singleRowData;
			dataSet.clear();
			if (queryParameter.getFields().get(0).equals("*")) {
				if (!queryParameter.getRestrictions().isEmpty()) {
					singleRow = bufferedReader.readLine();
					while (singleRow != null) {
						Row row = new Row();
						row.clear();
						singleRowData = singleRow.split(",");
						int indexPoint = 0;
						while (indexPoint < tempheaderCollectionSize) {
							if (filter.expressionEvaluator(singleRow, header, rowDataTypeDefinitions, queryParameter)) {
								row.put(Header[indexPoint], singleRowData[indexPoint]);
								indexPoint++;

								if (indexPoint == tempheaderCollectionSize - 1) {
									break;
								}
							} 
						}
						dataSet.put(locationPoint, row);
						locationPoint++;
						singleRow = bufferedReader.readLine();
					}
				} else {
					bufferedReader.readLine();
					while (singleRow != null) {
						Row row = new Row();
						row.clear();
						singleRowData = singleRow.split(",");
						int indexPoint = 0;
						while (indexPoint < tempheaderCollectionSize) {
							row.put(Header[indexPoint], singleRowData[indexPoint]);
							indexPoint++;
							if (indexPoint == tempheaderCollectionSize - 1) {
								break;
							}
						}
						dataSet.put(locationPoint, row);
						locationPoint++;
						singleRow = bufferedReader.readLine();
					}
				}
			} else {
				if (!queryParameter.getRestrictions().isEmpty()) {
					singleRow = bufferedReader.readLine();
					
					while (singleRow != null) {
						Row row = new Row();
						row.clear();
						singleRowData = singleRow.split(",");
						
							if (filter.expressionEvaluator(singleRow, header, rowDataTypeDefinitions, queryParameter)) {
								for (String eachColumn : queryParameter.getFields()) {
									row.put(eachColumn, singleRowData[header.get(eachColumn)]);
								}

							} 

						
						dataSet.put(locationPoint, row);
						locationPoint++;
						singleRow = bufferedReader.readLine();
						
					}

				} else {
					bufferedReader.readLine();
					while (singleRow != null) {
						Row row = new Row();
						row.clear();
						singleRowData = singleRow.split(",");
						for (String eachColumn : queryParameter.getFields()) {
							row.put(eachColumn, singleRowData[header.get(eachColumn)]);
						}
						dataSet.put(locationPoint, row);
						locationPoint++;
						singleRow = bufferedReader.readLine();
					}
				}
			}
		} catch (Exception e) {
		}
		int size = dataSet.size();
		for (Long i = (long) 0; i < size; i++) {
			if (dataSet.get(i).isEmpty()) {
				dataSet.remove(i);
			}
		}
		
		if(!queryParameter.getOrderByFields().isEmpty())
		{
			List<Row> afterOrdered=new ArrayList<>(dataSet.values());
			GenericComparator genericComparator=new GenericComparator();
			genericComparator.setSortingIndex(queryParameter.getOrderByFields().get(0).toLowerCase());
		}
		
		return dataSet;
	}

}
