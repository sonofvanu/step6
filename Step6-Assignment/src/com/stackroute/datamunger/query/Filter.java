package com.stackroute.datamunger.query;

import java.util.List;

import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;

//this class contains methods to evaluate expressions
public class Filter {
	// method to evaluate expression for eg: salary>20000
		public boolean expressionEvaluator(String singleRow, Header header, RowDataTypeDefinitions rowDataTypeDefinitions,
				QueryParameter queryParameter) {
			boolean finalState = true;
			List<Restriction> restrictions = queryParameter.getRestrictions();
			if (!queryParameter.getLogicalOperators().isEmpty()) {
				int totalOperators = queryParameter.getLogicalOperators().size();
				finalState = this.whereQueryEvaluator(singleRow, header, rowDataTypeDefinitions, restrictions.get(0));

				for (int point = 1; point < totalOperators; point++) {
					if (queryParameter.getLogicalOperators().get(point-1).equals("and")) {
						finalState = finalState
								&& this.whereQueryEvaluator(singleRow, header, rowDataTypeDefinitions, restrictions.get(point));
					} else if (queryParameter.getLogicalOperators().get(point-1).equals("or")) {
						finalState = finalState
								|| this.whereQueryEvaluator(singleRow, header, rowDataTypeDefinitions, restrictions.get(point));
					}

				}

			} else {
				finalState = this.whereQueryEvaluator(singleRow, header, rowDataTypeDefinitions, restrictions.get(0));
			}
			return finalState;
		}

		public boolean whereQueryEvaluator(String singleRow, Header header, RowDataTypeDefinitions rowDataTypeDefinitions,
				Restriction eachCondition) {
			boolean expression = true;
				if (eachCondition.getCondition().equals(">=")) {
					expression = this.greaterThanEqualToEvaluator(eachCondition, singleRow, header, rowDataTypeDefinitions);
				} else if (eachCondition.getCondition().equals("<=")) {
					expression = this.lessThanEqualToEvaluator(eachCondition, singleRow, header, rowDataTypeDefinitions);
				} else if (eachCondition.getCondition().equals("!=")) {
					expression = this.notEqualToEvaluator(eachCondition, singleRow, header, rowDataTypeDefinitions);
				} else if (eachCondition.getCondition().equals("=")) {
					expression = this.equalToEvaluator(eachCondition, singleRow, header, rowDataTypeDefinitions);
				}
				if (eachCondition.getCondition().equals(">")) {
					expression = this.greaterThanEvaluator(eachCondition, singleRow, header, rowDataTypeDefinitions);
				} else if (eachCondition.getCondition().equals("<")) {
					expression = this.lessThanEvaluator(eachCondition, singleRow, header, rowDataTypeDefinitions);
				}

			
			return expression;
		}

		// method containing implementation of equalTo operator
		public boolean equalToEvaluator(Restriction restriction, String singleRow, Header header,
				RowDataTypeDefinitions rowDataTypeDefinitions) {
			boolean result = true;
			String[] lineOfData = singleRow.split(",");
			if (rowDataTypeDefinitions.get(header.get(restriction.getPropertyName().toLowerCase()))
					.equals(String.class.getName())) {
				String actualValue = lineOfData[header.get(restriction.getPropertyName().toLowerCase())];

				String ValueToCheck = restriction.getPropertyValue().toLowerCase();
				if (actualValue.equals(ValueToCheck)) {
					result = true;
				} else {
					result = false;
				}
			} else {
				double actualValue = Integer.parseInt(lineOfData[header.get(restriction.getPropertyName().toLowerCase())]);
				double ValueToCheck = Integer.parseInt(restriction.getPropertyValue());
				if (actualValue == ValueToCheck) {
					result = true;
				} else {
					result = false;
				}
			}
			return result;

		}

		// method containing implementation of greaterThan operator
		public boolean greaterThanEvaluator(Restriction restriction, String singleRow, Header header,
				RowDataTypeDefinitions rowDataTypeDefinitions) {
			String[] lineOfData = singleRow.split(",");
			double actualValue = Integer.parseInt(lineOfData[header.get(restriction.getPropertyName().toLowerCase())]);
			double ValueToCheck = Integer.parseInt(restriction.getPropertyValue());
			if (actualValue > ValueToCheck) {

				return true;
			} else {

				return false;
			}
		}

		// method containing implementation of greaterThanOrEqualTo operator
		public boolean greaterThanEqualToEvaluator(Restriction restriction, String singleRow, Header header,
				RowDataTypeDefinitions rowDataTypeDefinitions) {
			String[] lineOfData = singleRow.split(",");
			double actualValue = Integer.parseInt(lineOfData[header.get(restriction.getPropertyName().toLowerCase())]);
			double ValueToCheck = Integer.parseInt(restriction.getPropertyValue());
			if (actualValue >= ValueToCheck) {
				return true;
			} else {
				return false;
			}
		}

		// method containing implementation of lessThan operator

		public boolean lessThanEvaluator(Restriction restriction, String singleRow, Header header,
				RowDataTypeDefinitions rowDataTypeDefinitions) {
			String[] lineOfData = singleRow.split(",");
			double actualValue = Integer.parseInt(lineOfData[header.get(restriction.getPropertyName().toLowerCase())]);
			double ValueToCheck = Integer.parseInt(restriction.getPropertyValue());
			if (actualValue < ValueToCheck) {
				return true;
			} else {
				return false;
			}
		}

		// method containing implementation of lessThanOrEqualTo operator
		public boolean lessThanEqualToEvaluator(Restriction restriction, String singleRow, Header header,
				RowDataTypeDefinitions rowDataTypeDefinitions) {
			String[] lineOfData = singleRow.split(",");
			double actualValue = Integer.parseInt(lineOfData[header.get(restriction.getPropertyName().toLowerCase())]);
			double ValueToCheck = Integer.parseInt(restriction.getPropertyValue());
			if (actualValue <= ValueToCheck) {
				return true;
			} else {
				return false;
			}
		}

		public boolean notEqualToEvaluator(Restriction restriction, String singleRow, Header header,
				RowDataTypeDefinitions rowDataTypeDefinitions) {
			boolean result = true;
			String[] lineOfData = singleRow.split(",");

			if (rowDataTypeDefinitions.get(header.get(restriction.getPropertyName().toLowerCase()))
					.equals(String.class.getName())) {
				String actualValue = lineOfData[header.get(restriction.getPropertyName().toLowerCase())];

				String ValueToCheck = restriction.getPropertyValue().toLowerCase();
				if (!actualValue.equals(ValueToCheck)) {
					result = true;
				} else {
					result = false;
				}
			} else {
				double actualValue = Integer.parseInt(lineOfData[header.get(restriction.getPropertyName().toLowerCase())]);
				double ValueToCheck = Integer.parseInt(restriction.getPropertyValue());
				if (actualValue != ValueToCheck) {
					result = true;
				} else {
					result = false;
				}
			}
			return result;
		}

	}
