package com.stackroute.datamunger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Filter;
import com.stackroute.datamunger.query.GenericComparator;
import com.stackroute.datamunger.query.Header;
import com.stackroute.datamunger.query.Query;
import com.stackroute.datamunger.query.Row;
import com.stackroute.datamunger.query.RowDataTypeDefinitions;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.QueryParser;
import com.stackroute.datamunger.writer.JsonWriter;


public class DataMunger {
	
	public static void main(String[] args){
		
		//read the query from the user
		
		
		//instantiate Query class
		
		
		//call executeQuery() method to get the resultSet and write to JSON file
		String queryString = "select city,winner,team1,team2,player_of_match from data/ipl.csv where season >= 2013 or toss_decision != bat and city = Bangalore group by team1 order by win_by_runs";
		QueryParameter queryParameter;
		QueryParser qp = new QueryParser();
		queryParameter = qp.parseQuery(queryString);
		DataSet dataSet = new DataSet();
		HashMap<Long,String> orderingData=new HashMap<>();
		DataSet orderByDataSet=new DataSet();
		Filter filter = new Filter();
		BufferedReader bufferedReader;
		try {
			dataSet.clear();
			bufferedReader = new BufferedReader(new FileReader(queryParameter.getFile()));
			// read the first line which contains the header
			String Header[] = bufferedReader.readLine().split(",");
			int index = 0, headerCount = Header.length;
			Header header = new Header();
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
			System.out.println(rowDataTypeDefinitions);
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
			orderByDataSet.clear();
			orderingData.clear();
			if (queryParameter.getFields().get(0).equals("*")) {
				if (!queryParameter.getRestrictions().isEmpty()) {
					singleRow = bufferedReader.readLine();
					while (singleRow != null) {
						Row row = new Row();
						Row orderBy=new Row();
						row.clear();
						singleRowData = singleRow.split(",");
						int indexPoint = 0;
						while (indexPoint < tempheaderCollectionSize) {
							if (filter.expressionEvaluator(singleRow, header, rowDataTypeDefinitions, queryParameter)) {
								row.put(Header[indexPoint], singleRowData[indexPoint]);
								indexPoint++;

								if (indexPoint == tempheaderCollectionSize - 1) {
									orderBy.put(queryParameter.getOrderByFields().get(0), singleRowData[header.get(queryParameter.getOrderByFields().get(0))]);
									orderingData.put(locationPoint, singleRowData[header.get(queryParameter.getOrderByFields().get(0))]);
									break;
								}
							} /*
								 * else { break; }
								 */

						}
						dataSet.put(locationPoint, row);
						orderByDataSet.put(locationPoint, orderBy);
						locationPoint++;
						singleRow = bufferedReader.readLine();
					}
					
					if(!queryParameter.getOrderByFields().isEmpty())
					{
						System.out.println("hello1");
					}
				} else {
					bufferedReader.readLine();
					while (singleRow != null) {
						Row row = new Row();
						Row orderBy=new Row();
						orderBy.clear();
						row.clear();
					
						singleRowData = singleRow.split(",");
						int indexPoint = 0;
						while (indexPoint < tempheaderCollectionSize) {
							row.put(Header[indexPoint], singleRowData[indexPoint]);
							indexPoint++;
							if (indexPoint == tempheaderCollectionSize - 1) {
								orderBy.put(queryParameter.getOrderByFields().get(0), singleRowData[header.get(queryParameter.getOrderByFields().get(0))]);
								orderingData.put(locationPoint, singleRowData[header.get(queryParameter.getOrderByFields().get(0))]);
								break;
							}
						}
						dataSet.put(locationPoint, row);
						orderByDataSet.put(locationPoint, orderBy);
						locationPoint++;
						singleRow = bufferedReader.readLine();
					}
					
					if(!queryParameter.getOrderByFields().isEmpty())
					{
						System.out.println("hello2");
					}
					
					
					
					
					
				}
			} else {
				if (!queryParameter.getRestrictions().isEmpty()) {
					singleRow = bufferedReader.readLine();
					
					while (singleRow != null) {
						Row row = new Row();
						Row orderBy=new Row();
						orderBy.clear();
						row.clear();
						singleRowData = singleRow.split(",");
						int indexPoint = 1;
						
							if (filter.expressionEvaluator(singleRow, header, rowDataTypeDefinitions, queryParameter)) {
								for (String eachColumn : queryParameter.getFields()) {
									row.put(eachColumn, singleRowData[header.get(eachColumn)]);
								}

								/*if (indexPoint == tempheaderCollectionSize - 1) {
									break;
								}*/
								orderBy.put(queryParameter.getOrderByFields().get(0), singleRowData[header.get(queryParameter.getOrderByFields().get(0))]);
								orderingData.put(locationPoint, singleRowData[header.get(queryParameter.getOrderByFields().get(0))]);
							} 

						
						dataSet.put(locationPoint, row);
						orderByDataSet.put(locationPoint, orderBy);
						locationPoint++;
						singleRow = bufferedReader.readLine();
						
					}
					System.out.println("ola");
					
					

				} else {
					bufferedReader.readLine();
					while (singleRow != null) {
						Row row = new Row();
						Row orderBy=new Row();
						orderBy.clear();
						row.clear();
						singleRowData = singleRow.split(",");
						for (String eachColumn : queryParameter.getFields()) {
							row.put(eachColumn, singleRowData[header.get(eachColumn)]);
						}
						orderBy.put(queryParameter.getOrderByFields().get(0), singleRowData[header.get(queryParameter.getOrderByFields().get(0))]);
						orderingData.put(locationPoint, singleRowData[header.get(queryParameter.getOrderByFields().get(0))]);
						dataSet.put(locationPoint, row);
						orderByDataSet.put(locationPoint, orderBy);
						locationPoint++;
						singleRow = bufferedReader.readLine();
					}
					if(!queryParameter.getOrderByFields().isEmpty())
					{
						System.out.println("hello4");
					}
				}
			}
		} catch (Exception e) {
		}		int size=dataSet.size();
		for(Long i=(long) 0;i<size;i++)
		{
			if(dataSet.get(i).isEmpty())
			{
				dataSet.remove(i);
			}
			if(orderByDataSet.get(i).isEmpty())
			{
				orderByDataSet.remove(i);
			}
		}
		
	/*	if(!queryParameter.getOrderByFields().isEmpty())
		{
			List<Row> afterOrdered=new ArrayList<>(dataSet.values());
			List finalOrder=new ArrayList<>();
			GenericComparator genericComparator=new GenericComparator();
			genericComparator.setSortingIndex(queryParameter.getOrderByFields().get(0).toLowerCase());
			//Collections.sort(afterOrdered,(Comparator<? super Row>) finalOrder);
			System.out.println("afterordered");
			//afterOrdered.forEach(System.out::println);
			
			System.out.println(afterOrdered.get(0).get("team1"));
		}*/
	//	System.out.println("after final"+dataSet);
		
		
		
		
		List<Row> list=new ArrayList(orderByDataSet.values());
		List<Row> listempty=new ArrayList<>();
		listempty=list;
		List locationList=new ArrayList<>(orderByDataSet.entrySet());
		System.out.println(list.size());
		if(!queryParameter.getOrderByFields().isEmpty())
		{
			GenericComparator genericComparator=new GenericComparator();
			genericComparator.setSortingIndex(queryParameter.getOrderByFields().get(0));
			Collections.sort(list,genericComparator);
			
		}
		DataSet ds=new DataSet();
		List<Long> orderedLocation=new ArrayList<>();
		long sizeOfList=(long) list.size();
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				if(list.get(i).toString().equals(listempty.get(j).toString()))
				{
					System.out.println("hi");
				}
			}
			
		}
		
		

		System.out.println("hehehe");
		orderedLocation.forEach(System.out::println);
		
		
	}
}