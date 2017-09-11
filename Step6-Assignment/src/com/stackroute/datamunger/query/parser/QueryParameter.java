package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//this class contains the parameters and accessor/mutator methods of QueryParameter
public class QueryParameter {
	private String File, QUERY_TYPE, BaseQuery;
	private List<String> orderByFields = new ArrayList<>(), groupByFields = new ArrayList<>(),
			fields = new ArrayList<>(), logicalOperators = new ArrayList<>();
	private List<Restriction> restrictions = new ArrayList<>();
	private List<AggregateFunction> aggregateFunctions = new ArrayList<>();

	public String getFile() {
		return File;
	}

	public void setFile(String file) {
		File = file;
	}

	public String getQUERY_TYPE() {
		return QUERY_TYPE;
	}

	public void setQUERY_TYPE(String qUERY_TYPE) {
		QUERY_TYPE = qUERY_TYPE;
	}

	public String getBaseQuery() {
		return BaseQuery;
	}

	public void setBaseQuery(String baseQuery) {
		BaseQuery = baseQuery;
	}

	public List<String> getOrderByFields() {
		return orderByFields;
	}

	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}

	public List<String> getGroupByFields() {
		return groupByFields;
	}

	public void setGroupByFields(List<String> groupByFields) {
		this.groupByFields = groupByFields;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public List<AggregateFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}

	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}

	public List<String> getLogicalOperators() {
		return logicalOperators;
	}

	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}

}

	

