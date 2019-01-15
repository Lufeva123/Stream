import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Service provider class that converts the representation from compressed to uncompressed and vice versa
 * Static because it simply acts as a helper class and does not need its own fields
 * @author Luiz do Valle (lld2131)
 *
 */
public class RLEConverter {

	/**
	 * Method that converts a compressed sequence into an uncompressed one
	 * 
	 * Decompression works by looking at the even indices of the array (0, 2, 4, 6...) for the count
	 * and at the odd indices following it for the value being counted.
	 * This way, even if [4 4 3] becomes [1 4 1 4 1 3] instead
	 * of [2 4 1 3], the decompression will work correctly and both compressions will be equivalent
	 * 
	 * @param compressedSequence the vararg of ints representing the sequence to be uncompressed
	 * @return the uncompressed sequence as an array of ints
	 */
	public static int[] toAPI(int...compressedSequence) {
		
		int size = compressedSequence.length;
		
		int sizeOfUncompressedSequence = predictLengthOfUncompressedSequence(compressedSequence);
		
		int[] uncompressedSequence = new int[sizeOfUncompressedSequence];
		
		int uncompressedIndex = 0;
		int compressedIndex = 0;
		
		while(compressedIndex < size) {
			
			int count = compressedSequence[compressedIndex];
			compressedIndex++;
			
			for(int i = 0; i < count; i++) {
				
				uncompressedSequence[uncompressedIndex] = compressedSequence[compressedIndex];
				uncompressedIndex++;
			}
			
			compressedIndex++;
		}
		
		return uncompressedSequence;
	}
	
	/**
	 * Method that converts an uncompressed sequence to a compressed one
	 * @param uncompressedSequence the vararg of ints representing the uncompressed sequence
	 * @return compressed sequence as an array of ints
	 */
	public static int[] toSpace(int...uncompressedSequence) {
		
		ArrayList<Integer> counts = extractCounts(uncompressedSequence);
		int totalNumValues = predictLengthOfCompressedSequence(counts);
		
		int[] compressedSequence = new int[totalNumValues];
		
		int uncompressedIndex = 0;
		int compressedIndex = 0;
		
		for(int prefix : counts) {
			
			compressedSequence[compressedIndex] = prefix;
			compressedIndex++;
			
				
			compressedSequence[compressedIndex] = uncompressedSequence[uncompressedIndex];
			uncompressedIndex += prefix;
			compressedIndex++;
			
		}
		
		return compressedSequence;
	}
	
	/**
	 * Method for debugging purposes that prints the sequence exactly as it is represented internally
	 * This would be removed in the final version of the product so the user cannot access the internal
	 * representation of the sequence
	 * @param sequence the sequence to be represented
	 * @return a String in the format [...]
	 */
	public static String dumpGutsOf(RLESequence sequence) {
		
		int[] sequenceAsArray = sequence.asArray();
		
		String guts = "[";
		
		for(int element : sequenceAsArray) {
			
			guts += element + " ";
			
		}
		
		guts = guts.trim();
		
		guts+= "]";
		
		return guts;
	}
	
	private static ArrayList<Integer> extractCounts(int[] uncompressedSequence) {
		
		ArrayList<Integer> counts = new ArrayList<>();
		
		int numberOfRepetitions = 1;
		
		int size = uncompressedSequence.length;
		
		for(int i = 0; i < size; i++) {
			
			int currentValue = uncompressedSequence[i];
			
			while(i < size - 1 && uncompressedSequence[i + 1] == currentValue) {
				
				i+=1;
				
				numberOfRepetitions += 1;
				
			}
			
			counts.add(numberOfRepetitions);
			numberOfRepetitions = 1;

			
		}
		
		return counts;
		
	}
	
	private static int predictLengthOfCompressedSequence(ArrayList<Integer> prefixes) {
		
		int totalNumValues = 0;
		
		int numPrefixes = prefixes.size();
		
		totalNumValues = numPrefixes * 2;
		
		return totalNumValues;
	}
	
	private static int predictLengthOfUncompressedSequence(int[] compressedSequence) {
		
		int compressedSize = compressedSequence.length;
		int uncompressedSize = 0;
		
		int compressedIndex = 0;
		
		while(compressedIndex < compressedSize) {
			
			int prefix = compressedSequence[compressedIndex];
			
			uncompressedSize += Math.abs(prefix);
				
			compressedIndex += 2;
		}		
		
		return uncompressedSize;
	}
}
