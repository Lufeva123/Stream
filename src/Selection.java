/**
 * Interface that serves as the blueprint for the Select algorithms
 * @author Luiz do Valle
 *
 */
public interface Selection {

	/**
	 * Method that determines whether the record meets the filter requirements specified by the user
	 * @param record the record to be checked
	 * @return true is the record passes the user's filter, false otherwise
	 */
	public abstract boolean isRecordWanted(String record);
}
