/**
 * Class that implements the Computation interface and simply performs the FIRSTDIFF terminal computation
 * Finds the first record that differs from the one prior to it and counts how many of the following records
 * are the same as this one
 * If the target column does not exist, it simply returns an empty String
 * The first record of a file does not count as a first difference
 * @author Luiz do Valle
 *
 */
public class FirstDiffComputation implements Computation{
	
	/**
	 * The number of different records
	 */
	private int counter;
	/**
	 * The first different field found
	 */
	private String firstDifferentField;
	
	/**
	 * The record to which the first different fields belongs to
	 */
	private String differentRecord = null;
	/**
	 * The previous target field seen
	 */
	private String previousField = null;
	/**
	 * Whether a different record was found
	 */
	private boolean differentFound = false;
	/**
	 * Whether the the first record has already been read
	 */
	private boolean isFirstRecord = true;
	/**
	 * The column of the data on which the computation is to be performed
	 */
	private int targetColumn;
	
	/**
	 * Constructor of the class
	 * @param targetColumn the column of the data on which the compuation is to be performed
	 */
	public FirstDiffComputation(int targetColumn) {
		
		this.targetColumn = targetColumn;
		
	}
	
	@Override
	public void compute(String record) {
		
		if(targetColumn == -1) {
			
			return;
		}
		
		String[] recordFields = record.split("\t");
		String targetField = recordFields[targetColumn];
		
		if(isFirstRecord) {
			
			previousField = targetField;
			isFirstRecord = false;
		}
		
		
		if(differentFound && targetField.equals(firstDifferentField)) {
			
			counter++;
		
		} else if(!differentFound && !targetField.equals(previousField)) {
			
			firstDifferentField = targetField;
			differentRecord = record;
			differentFound = true;
			
		} else {
			
			previousField = targetField;
			
		}
		
	}

	@Override
	public String result() {
		
		return "Record: " + differentRecord + " Counter: " + counter;
	}

	
}
