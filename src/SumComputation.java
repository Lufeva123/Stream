/**
 * Class that implements the Computation interface and simply performs the SUM terminal computation
 * Gets the total sum of the long values under the header specified by the user
 * If the values are Strings, nothing is added and the class returns 0
 * If the header does not exist, nothing is added and the class returns 0
 * @author Luiz do Valle
 *
 */
public class SumComputation implements Computation {

	private long currentSum  = 0;

	private int targetColumn;
	
	public SumComputation(int column) {
		
		this.targetColumn = column;
	}
	
	@Override
	public void compute(String record) {
		
		if(targetColumn == -1) {
			
			return;
		}
		String[] recordFields = record.split("\t");
		String targetRecord = recordFields[targetColumn];
		
		if(isValidLong(targetRecord)) {
			
			currentSum += getLongValue(targetRecord);
		}
		
	}

	@Override
	public String result() {
		
		return String.valueOf(currentSum);
		
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
