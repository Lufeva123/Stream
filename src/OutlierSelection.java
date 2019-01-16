/**
 * Class that implements the Selection interface
 * Performs the outlier selection by checking whether the long value specified by the user 
 * of the current record is more than plus or minus N (error) from the previous record
 * If the values under the specified header are not longs, the class does not send anything to the output
 * @author Luiz do Valle
 *
 */
public class OutlierSelection implements Selection {

	/**
	 * The previous field seen
	 */
	private long previousField;
	/**
	 * The allowed error
	 */
	private long error;
	/**
	 * The column being analyzed
	 */
	private int targetColumn;
	/**
	 * Whether the first record has already been seen
	 */
	private boolean isFirstRecord = true;
	
	/**
	 * Constructor for the class
	 * @param targetColumn the column being analyzed
	 * @param error
	 */
	public OutlierSelection(int targetColumn, long error) {
		
		this.targetColumn = targetColumn;
		this.error = error;
		
	}
	
	@Override
	public boolean isRecordWanted(String record) {
		
		String[] recordFields = record.split("\t");
		String targetField = recordFields[targetColumn];
		boolean wanted = false;
		
		
		if(isValidLong(targetField)) {
			
			long currentField = getLongValue(targetField);
			
			if(isFirstRecord) {
				
				previousField = currentField;
				wanted = false;
				isFirstRecord = false;
			
			} else {
				
				if(Math.abs(currentField - previousField) > error) {
					
					wanted = true;;
				}
			}
			
			previousField = currentField;
			return wanted;
		}
		
		
		
		
		return false;
	}

	private boolean isValidLong(String field) {
		
		try {
			
			Long.parseLong(field);
		
		} catch (NumberFormatException e){
			
			return false;
		}
		
		return true;
	}
	
	private long getLongValue(String field) {
		
		return Long.parseLong(field);
	}
	
}
