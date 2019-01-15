/**
 * Service class that validates whether a single value or sequence of values is valid
 * @author Luiz do Valle (lld2131)
 *
 */
public class SequenceValidator {

	/**
	 * Method that checks whether the given element is valid
	 * @param element value to be checked
	 * @return true if the element is valid, false otherwise
	 */
	public static boolean isElementValid(int element) {
		
		if (element >= 0 && element <= 255) {
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method that checks whether the whole sequence given is valid
	 * @param sequence numbers to be tested
	 * @return true if sequence is valid, false otherwise
	 */
	public static boolean isSequenceValid(int...sequence) {
		
		for (int element : sequence) {
			
			if (!isElementValid(element)) {
				
				return false;
			}
		}
		
		return true;
	}
}
