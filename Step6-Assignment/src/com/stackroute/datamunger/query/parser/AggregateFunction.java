package com.stackroute.datamunger.query.parser;

public class AggregateFunction {
	
	private String Field;
	private String result="0";
	private String Function;
	private int aggregateFieldIndex;
	private int count;
	private double sum;
	public int getAggregateFieldIndex() {
		return aggregateFieldIndex;
	}
	public void setAggregateFieldIndex(int aggregateFieldIndex) {
		this.aggregateFieldIndex = aggregateFieldIndex;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getField() {
		return Field;
	}
	public void setField(String field) {
		Field = field;
	}
	public String getFunction() {
		return Function;
	}
	public void setFunction(String function) {
		Function = function;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	
	
	

}
