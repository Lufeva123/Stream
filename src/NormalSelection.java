/**
 * Class that implements the Selection interface
 * Performs the normal selection by receiving the user requirements and finding the records in the
 * file that meet those requirements
 * If the target header is the empty String ("") the filter outputs all the properly formed records
 * @author Luiz do Valle
 *
 */
public class NormalSelection implements Selection {

	/**
	 * The data column with which the file should be filtered
	 */
	private int targetColumn = -1;
	/**
	 * The header with which the file should be filtered
	 */
	private String targetHeader;
	/**
	 * The field that the user wants all the output data to have
	 */
	private String targetField;
	
	/**
	 * Constructor of the class
	 * @param targetHeader The header with which the file should be filtered
	 * @param targetField The field that the user wants all the output data to have
	 * @param targetColumn The data column with which the file should be filtered
	 */
	public NormalSelection(String targetHeader, String targetField, int targetColumn) {
		
		this.targetHeader = targetHeader;
		this.targetColumn = targetColumn;
		this.targetField = targetField;
		
	}
	
	
	@Override
	public boolean isRecordWanted(String record) {
		
		if(targetHeader.equals("")) {
			
			return true;
		}
		
		if(targetColumn == -1 || targetField == null) {
			
			return false;
		
		}
		
		String[] recordFields = record.split("\t");
		
		return targetField.equals(recordFields[targetColumn]);
		
	}

	
	
}
