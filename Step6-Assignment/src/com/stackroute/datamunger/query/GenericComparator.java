package com.stackroute.datamunger.query;

import java.util.Comparator;

/* this class contains the implementation of the custom comparator 
 * which can work for all data types*/
public class GenericComparator implements Comparator<Row> {
	private String sortingIndex;

	public String getSortingIndex() {
		return sortingIndex;
	}

	public void setSortingIndex(String sortingIndex) {
		this.sortingIndex = sortingIndex;
	}

	@Override
	public int compare(Row o1, Row o2) {
		// TODO Auto-generated method stub
		return o1.get(sortingIndex).compareTo(o2.get(sortingIndex));
	}
	
		
}
