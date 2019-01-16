/**
 * Class that implements the Computation interface and simply performs the MIN terminal computation
 * Finds the minimum value of the desired header in the output stream
 * If the value is a String, the minimum value is the one that comes before in a lexicographical order
 * If the value is a long, the minimum is simply the one that comes before in the number line
 * 
 * @author Luiz do Valle
 *
 */
public class MinComputation implements Computation{

	/**
	 * The minimum value found so far
	 */
	private String minValue = null;
	/**
	 * Helper method that stores the minimum value of a long
	 */
	private long longMinValue = Long.MAX_VALUE;
	
	/**
	 * The column being analyzed
	 */
	private int column;
	
	/**
	 * Constructor that initializes the column being analyzed
	 * @param column the column being analyzed
	 */
	public MinComputation(int column) {
		
		this.column = column;
	}
	
	@Override
	public void compute(String record) {
		
		String[] recordFields = record.split("\t");
		
		
		if(column == -1) {
			
			minValue = "";
			return;
		
		} 
		
		String targetField = recordFields[column];
		
		if(minValue == null) {
			
			minValue = targetField;
		
		} else if(isValidLong(targetField)) {
			
			long field = getLongValue(targetField);
			long currentMax = getLongValue(minValue);
			
			if(field < currentMax) {
				
				minValue = String.valueOf(field);
			}
		
		} else {
			
			if(targetField.compareToIgnoreCase(minValue) < 0) {
				
				minValue = targetField;
			}
		}
		
	}

	@Override
	public String result() {
		
		return minValue;
	}
	
	/**
	 * Method that checks whether the given field is a long
	 * @param field the field to be checked
	 * @return true if a field is a long, false otherwise
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
