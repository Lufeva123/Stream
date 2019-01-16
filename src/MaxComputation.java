/**
 * Class that implements the Computation interface and simply performs the MAX terminal computation
 * Finds the maximum value of the desired header in the output stream
 * If the value is a String, the maximum value is the one that comes after in a lexicographical order
 * If the value is a long, the maximum is simply the one that comes after in the number line
 * @author Luiz do Valle
 *
 */
public class MaxComputation implements Computation {

	/**
	 * The maximum value found
	 */
	private String maxValue = null;
	/**
	 * Helper variable that stores the maximum long value
	 */
	private long longMaxValue = Long.MIN_VALUE;
	/**
	 * The column being analyzed
	 */
	private int column;
	
	/**
	 * Constructor for the class that initializes the column being analyzed
	 * @param column
	 */
	public MaxComputation(int column) {
		
		this.column = column;
	}
	
	@Override
	public void compute(String record) {
		
		String[] recordFields = record.split("\t");
		
		
		if(column == -1) {
			
			maxValue = "";
			return;
		
		} 
		
		String targetField = recordFields[column];
		
		if(maxValue == null) {
			
			maxValue = targetField;
		
		} else if(isValidLong(targetField)) {
			
			long field = getLongValue(targetField);
			long currentMax = getLongValue(maxValue);
			
			if(field > currentMax) {
				
				maxValue = String.valueOf(field);
			}
		
		} else {
			
			if(targetField.compareToIgnoreCase(maxValue) > 0) {
				
				maxValue = targetField;
			}
		}
		
	}

	@Override
	public String result() {
		
		return maxValue;
	}
	
	/**
	 * Method that checks whether the given field is a long
	 * @param field the field to be checked
	 * @return true if the field is a long, false otherwise
	 */
	private boolean isValidLong(String field) {
		
		try {
			
			Long.parseLong(field);
		
		} catch (NumberFormatException e){
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method that converts the given field to a long
	 * @param field the field to be converted to a long
	 * @return the field as long
	 */
	private long getLongValue(String field) {
		
		return Long.parseLong(field);
	}
	
}
