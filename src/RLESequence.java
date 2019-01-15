import java.util.Arrays;

/**
 * Interface that contains the blueprint for an RLESequence and a default method to determine equality across all versions
 * @author Luiz do Valle (lld2131)
 *
 */
public interface RLESequence {

	/**
	 * Method that returns the length of the sequence
	 * @return dimensions of sequence
	 */
	public abstract int length();
	
	/**
	 * Method that adds the given element to the given position 
	 * If the position or element is not valid, a message indicating this to the user is displayed and the
	 * operation is not completed 
	 * @param element a value from 0 to 255 inclusive
	 * @param position integer (or integers in case for two dimension) that represent the position to be added to
	 */
	public abstract void addElementToPosition(int element, int position);
	
	/**
	 * Method that removes the element at the position specified
	 * If the position is not valid, a message indicating this to the user is displayed and the operation
	 * is not completed
	 * @param position where the element to be removed is
	 */
	public abstract void removeElementAt(int position);
	
	/**
	 * Method that modifies the element in the given position to the a desired value
	 * If the position or newElement is not valid, a message indicating this to the user is displayed and
	 * the operation is not completed 
	 * @param newValue the value to replace the old one
	 * @param position integer (or integers in case for two dimension) that represent the position to be modified
	 */
	public abstract void modifyElementToAt(int newValue, int position);
	
	/**
	 * Method that return the element at the given position
	 * If the position not valid, a message indicating this to the user is displayed and -1 is returned
	 * @param position integer (or integers in case for two dimension) that represent the desired elemnt's position
	 * @return the element at the given position. -1 if no element in that position exists
	 */
	public abstract int getElementAt(int position);
	
	/**
	 * Method that adds a sequence to the end of the already existing sequence
	 * If an element is not valid, a message indicating this to the user is displayed and the operation is not completed 
	 * @param sequence the sequence to be added
	 */
	public abstract void addToHead(int...sequence);
	
	/**
	 * Method that adds a sequence to the beginning of the already existing sequence
	 * If an element is not valid, a message indicating this to the user is displayed and the operation is not completed 
	 * @param sequence the sequence to be added
	 */
	public abstract void addToTail(int...sequence);
	
	/**
	 * Method that returns the internal representation of the sequence as an array (debugging purposes)
	 * @return array of ints containing the internal representation of the array
	 */
	public abstract int[] asArray();
	
	/**
	 * Method that generates the uncompressed representation of the sequence as a String for the user
	 * @return String representing the uncompressed version of the sequence in the form "[1 3 4 255 ..]"
	 */
	public abstract String asString();
	
	/**
	 * Method that checks whether the contents of two sequences are the same
	 * Works by comparing the string representations of the sequences created by asString(), which 
	 * is the same independent of compression since it outputs the uncompressed version of the sequence
	 * for all versions
	 * @param otherSequence sequence to be compared to
	 * @return true if the two sequences are equivalent, false otherwise
	 */
	public default boolean isEqualTo(RLESequence otherSequence) {
		
		//If the otherSequence object is the method caller (a sequence is equal to itself)
		if (otherSequence == this) {
			
			return true;
		}
		
		String thisString = this.asString();
		String otherSequenceString = otherSequence.asString();
		
		
		return thisString.equals(otherSequenceString);
	}
}
