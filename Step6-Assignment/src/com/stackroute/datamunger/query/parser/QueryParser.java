package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();
	//this method will parse the queryString and will return the object of QueryParameter
	//class
	public QueryParameter parseQuery(String queryString) {
	
		// this method will parse the queryString and will return the object of
				// QueryParameter
				// class
				queryParameter.setFile(this.getFile(queryString));
				queryParameter.setBaseQuery(this.getBaseQuery(queryString));
				queryParameter.setQUERY_TYPE(this.getQueryType(queryString));
				queryParameter.setAggregateFunctions(new ArrayList<>());
				queryParameter.setFields(this.getFields(queryString));
				queryParameter.setRestrictions(this.getWhereConditions(queryString));
				queryParameter.setGroupByFields(this.getGroupByFields(queryString));
				queryParameter.setOrderByFields(this.getOrderByFields(queryString));
				queryParameter.setAggregateFunctions(this.getAggregateFunctions(queryString));
				queryParameter.setLogicalOperators(this.getLogicalOperators(queryString));
				return queryParameter;
			}

			// get and display the filename
			private String getFile(String queryString) {

				String fileName = (queryString.split("from")[1].trim()).split("where")[0].trim().split(" ")[0].trim();
				return fileName;
			}

			// getting the baseQuery and display
			private String getBaseQuery(String queryString) {
				String baseQuery = "";
				if (queryString.contains("where")) {
					baseQuery = (queryString.split("where")[0].trim());
				} else if (queryString.contains("group by") || queryString.contains("order by")) {
					baseQuery = (queryString.split("order by|group by")[0].trim());
				} else {
					baseQuery = queryString;
				}
				return baseQuery;
			}

			/* get the fields from the select clause */
			public List<String> getFields(String queryString) {
				queryParameter.getFields().clear();
				String[] fields = (queryString.split("from")[0].trim()).split("select")[1].trim().split(",");
				for (String eachField : fields) {
					queryParameter.getFields().add(eachField.trim());
				}
				return queryParameter.getFields();

			}

			private List<Restriction> getWhereConditions(String queryString) {
				queryParameter.getRestrictions().clear();
				if (queryString.contains("where")) {
					String[] conditions = (((queryString.split("where")[1].trim()).split("order by|group by")[0].trim()
							.toLowerCase()).split(" and | or "));
					for (String eachCondition : conditions) {
						Restriction restriction = new Restriction();
						restriction.setPropertyName(eachCondition.split(">=|<=|!=|<|>|=")[0].trim());
						restriction.setCondition(eachCondition.split(eachCondition.split(">=|<=|!=|<|>|=")[1].trim())[0].trim()
								.split(eachCondition.split(">=|<=|!=|<|>|=")[0].trim())[1].trim());
						restriction.setPropertyValue(eachCondition.split(">=|<=|!=|<|>|=")[1].trim());
						queryParameter.getRestrictions().add(restriction);

					}
				}
				return queryParameter.getRestrictions();
			}

			private List<String> getOrderByFields(String queryString) {
				queryParameter.getOrderByFields().clear();
				if (queryString.contains("order by")) {
					String[] orderByField = queryString.split("order by")[1].split("group by")[0].split(",");
					for (String eachOrderByField : orderByField) {
						queryParameter.getOrderByFields().add(eachOrderByField.trim());
					}
				}
				return queryParameter.getOrderByFields();
			}

			private List<String> getGroupByFields(String queryString) {
				queryParameter.getGroupByFields().clear();
				if (queryString.contains("group by")) {
					String[] groupByFields = queryString.split("group by")[1].split("order by")[0].split(",");
					for (String eachGroupByField : groupByFields) {
						queryParameter.getGroupByFields().add(eachGroupByField.trim());
					}
				}
				return queryParameter.getGroupByFields();
			}

			// parse and display aggregate functions(if applicable)
			private List<AggregateFunction> getAggregateFunctions(String queryString) {
				queryParameter.getAggregateFunctions().clear();
				for (String aggregate : this.getFields(queryString)) {
					if (aggregate.contains("(") && aggregate.contains(")")) {
						AggregateFunction aggregateFunction = new AggregateFunction();
						aggregateFunction.setField(aggregate.split("\\(")[1].trim().split("\\)")[0]);
						aggregateFunction.setFunction(aggregate.split("\\(")[0]);
						queryParameter.getAggregateFunctions().add(aggregateFunction);
					}
				}

				return queryParameter.getAggregateFunctions();
			}

			private String getQueryType(String queryString) {
				if (queryString.contains("where")) {
					queryParameter.setQUERY_TYPE("WHERE");
					if (queryString.contains("order by") || queryString.contains("group by")) {
						queryParameter.setQUERY_TYPE("WHERE ORDER GROUP");
					}
				} else if (queryString.contains("order by") || queryString.contains("group by")) {
					queryParameter.setQUERY_TYPE("ORDER GROUP");
				} else {
					queryParameter.setQUERY_TYPE("SIMPLE");
				}
				return queryParameter.getQUERY_TYPE();
			}

			private List<String> getLogicalOperators(String queryString) {
				queryParameter.getLogicalOperators().clear();
				if (queryString.contains("where")) {
					String[] conditionsQuery = ((queryString.split("where")[1].trim()).split("order by|group by")[0].trim()
							.toLowerCase()).split(" ");
					for (String eachCondition : conditionsQuery) {
						if (eachCondition.equals("and") || eachCondition.equals("or")) {
							queryParameter.getLogicalOperators().add(eachCondition);
						}

					}
				}

				return queryParameter.getLogicalOperators();

			}

		}