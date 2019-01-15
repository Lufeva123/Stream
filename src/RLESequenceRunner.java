/**
 * Class that executes the tests
 * 
 * Test case ID: RLE_V1
 * Test case designed by: Luiz do Valle
 * Test Summary: Assert whether the methods in the first version of the RLESequence work properly and only accept
 * valid values as inputs.
 * Pre-Conditions: No pre-conditions necessary
 * Test Data: All methods were tested using pixel values on the left of the allowed range (less than 0)
 * on the range (between 0 and 255 inclusive) and to the right of the range (more than 255). In addition, invalid positions 
 * in the sequence were chosen to test the behavior of the index out of bound exception.
 * Expected Result: The methods were expected to perform their respective operations correctly and print
 * a message on the screen when an invalid value or position was passed as a parameter
 * Actual Result: The results matched expectations
 * Post-condition: No post-condition required
 * 
 * Test case ID: RLE_V2
 * Test case designed by: Luiz do Valle
 * Test Summary: Assert that the methods in RLESequenceV2 and RLEConverter work properly
 * Pre-conditions: RLESequenceV1 works correctly
 * Test Data: All methods were tested using pixel values on the left of the allowed range (less than 0)
 * on the range (between 0 and 255 inclusive) and to the right of the range (more than 255). In addition, invalid positions 
 * in the sequence were chosen to test the behavior of the index out of bound exception. The isEqualTo method
 * of the RLESequence interface was tested also with a version 1 and version 2 sequences compared to each other
 * Expected Result: RLESequence2 was expected to work exactly as RLESequence 1, except with a different internal
 * representation and RLEConverter changing one sequence into the other.
 * Actual Result: The results matched expectations
 * Post-Condition: No post-condition required
 * 
 * Test case ID: RLE_V3
 * Test case designed by: Luiz do Valle
 * Test Summary: Assert that the methods in RLESequenceV3 work properly and have the same
 * functionality as those in V1 and V2 without needing to uncompress the sequence between each step.
 * Pre-conditions: No pre-condition required
 * Test Data: Same data used in the previous test. This makes it simpler to check the accuracy of the outputs
 * Expected Result: RLESequence V3 was expected to work correctly even by performing the operations
 * in the compressed sequence and produce the same outputs as previous versions
 * Actual Result: The results matched expectations
 * Post-Condition: No post-condition required
 * 
 * Test case ID: RLE_Change_Values
 * Test case designed by: Luiz do Valle
 * Test Summary: Assert that the changeValues(int byWhat) method in RLESequenceV3 works properly
 * Pre-conditions: The RLESequenceV3 was initialized correctly
 * Test Data: The sequence was changed by a value that saturated (more than 255) some pixels but not others,
 * a value that did not saturate any pixel, and a value that unsaturated (less than 0) some pixels.
 * Expected Result: The individual pixel values in the sequence were expected to change by the specified amount
 * and be capped at 255 and 0 if they fell above or below this range
 * Actual Result: The results matched expectations
 * Post-Condition: No post-condition required
 * 
 * @author Luiz do Valle (lld2131)
 *
 */
public class RLESequenceRunner {

	public static void main(String[] args) {
	
		RLESequenceTester.testRLE3();
		
	}
}
