package com.stackroute.datamunger.query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//this class contains methods to find the column data types
public class DataTypeDefinitions {

	public static Object getDataType(String input) {
		Object object;
		Integer integertype;
		double doubletype;
		try {
			if (input.contains("\\d+\\-\\d+\\-\\d+") || input.contains("\\d+\\/\\d+\\/\\d+")) {
				object = new Date();
			} else {
				try {
					if (input.matches("\\d+\\.\\d+")) {
						doubletype = Double.parseDouble(input);
						object = new Double(0);
					} else {
						integertype = Integer.parseInt(input);
						object = new Integer(0);
					}
				} catch (Exception e) {
					object = new String();
				}
			}
		} catch (Exception e) {
			object = Object.class.getName();
		}
		return object;

	}
	
	
}
