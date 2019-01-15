import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class that stores the sequence passed by the user using the same compressed format as RLESequenceV2 ([count1 value1 count2 value2 ...]),
 * but that performs the requested operations without uncompressing the sequence. The user is not aware of this difference as the functions
 * produce the same outputs as for the other two versions
 * 
 * @author Luiz do Valle (lld2131)
 *
 */
public class RLESequenceV3 implements RLESequence {

	/*LinkedList over ArrayList because it is more efficient to add and remove nodes and each element
	 * points to the location of the next element in the sequence
	 * Dynamic data structure unlike arrays, means that elements can be added or removed
	 * 
	 * Integer was chosen because it has a positive range of 10^9 (giga pixel), which should be more than enough for 
	 * the number of "pixels" (total count) in each image considering that 8K images have around 10^7 pixels.
	 */
	private LinkedList<Integer> sequence = new LinkedList<>();
	
	/**
	 * Default constructor
	 * Default length chosen to zero as number of pixels cannot be assumed unless specified by users.
	 * There is no default, non-zero, size for pictures
	 */
	public RLESequenceV3() {
		
		this(0);
	}
	
	/**
	 * Constructor that creates a sequence of the given length with default values of zero
	 * Zero because it represents pure black and it is sensible to start with no image being shown
	 * (all pixels off)
	 * The absolute value of the length is taken because a minus sign can only be a typo, so it is ignored
	 * @param length of sequence
	 */
	public RLESequenceV3(int length) {
		
		int size = Math.abs(length);
		
		int[] array = new int[size];
		
		initializeSequence(array);
	}
	
	/**
	 * Constructor that accepts a varag of ints as values
	 * @param entireSequence values for the sequence
	 */
	public RLESequenceV3(int...entireSequence) {
		
		initializeSequence(entireSequence);
	}
	
	
	@Override
	public int length() {
		
		int length = 0;
		
		for(int i = 0; i < sequence.size(); i+=2) {
			
			int count = sequence.get(i);
			
			length += count;
		}
		
		return length;
	}

	private void initializeSequence(int[] values) {
		
		if(!SequenceValidator.isSequenceValid(values)) {
			
			respondToInvalidInput("value");
			return;
			
		} 
		
		this.sequence.clear();
		
		int[] compressedSequence = compress(values);
		
		for(int value : compressedSequence) {
			
			this.sequence.addLast(value);
		}
	}
	
	@Override
	public void addElementToPosition(int element, int position) {
		
		if (!SequenceValidator.isElementValid(element)) {
			
			respondToInvalidInput("value");
			return;
			
		}
		
		int size = sequence.size();
		
		/*If the sequence is empty and the user wants to add a value to index 0, this block does it and skips all the
		 * processing below by calling return;
		 */
		if(position == 0 && size == 0) {
			
			sequence.add(0, 1);
			sequence.add(1, element);
			return;
		}
		
		int[] countAndTotal = null;
		
		try {
			
			countAndTotal = getCountAndTotalPosition(position);
		
		} catch (IndexOutOfBoundsException e) {
			
			respondToInvalidInput("position");
			return; 
		}
		
		
		int countIndexBefore = countAndTotal[0];
		int totalUpToPosition = countAndTotal[1];
		
		int countBefore = sequence.get(countIndexBefore);
		int elementBefore = sequence.get(countIndexBefore + 1);
		
		/*
		 * First scenario is that the new element should be between two different numbers 
		 * Compressed: [5 4 3 2] add x to index 5. In which case the totalUpToPosition is going to equal the index 
		 * to be added to
		 */
		if(totalUpToPosition == position) {
			
			int countIndexAfter = countIndexBefore + 2;
			
			int countAfter = countBefore;
			int elementAfter = elementBefore;
			
			/*
			 * If the position to be added is not at the end of the sequence
			 */
			if(countIndexAfter < size) {
				
				countAfter = sequence.get(countIndexAfter);
				elementAfter = sequence.get(countIndexAfter + 1);
			}
			
			/*
			 * If the element before if equal to the new element
			 * Example: Compressed: [5 4 3 2] add 4 to position 5
			 */
			if(elementBefore == element) {
				
				sequence.set(countIndexBefore, countBefore + 1);
				
			} else if(elementAfter == element) {
				
				sequence.set(countIndexAfter, countAfter + 1);
				
			} else {
				
				int positionOfNewElement = countIndexBefore + 2;
				sequence.add(positionOfNewElement, 1);
				sequence.add(positionOfNewElement + 1, element);
			}
		
			/*
			 * Second scenario is that the new element should be in the middle of an existing interval
			 * Compressed: [5 4 3 2] add x to index 2. Which would require either breaking the interval into two with
			 * the new element in the middle or just incrementing the count of the interval
			 */
		} else {
			
			if(elementBefore == element) {
				
				sequence.set(countIndexBefore, countBefore + 1);
			
			} else {
				
				int newCountAfter = totalUpToPosition - position;
				
				int newCountBefore = countBefore - newCountAfter;
				
				sequence.set(countIndexBefore, newCountBefore);
				
				sequence.add(countIndexBefore + 2, 1);
				sequence.add(countIndexBefore + 3, element);
				
				sequence.add(countIndexBefore + 4, newCountAfter);
				sequence.add(countIndexBefore + 5, elementBefore);
			}
		}
	}

	@Override
	public void modifyElementToAt(int newValue, int position) {
		
		if (!SequenceValidator.isElementValid(newValue)) {
			
			respondToInvalidInput("value");
			return;
			
		}
		
		try {
			
			removeElementAt(position);
		
			addElementToPosition(newValue, position);
		
		} catch (IndexOutOfBoundsException e) {
			
			respondToInvalidInput("position");
		}
		
	}

	@Override
	public void removeElementAt(int position) {
		
		int[] countAndTotal =  null;
		
		try {
			
			countAndTotal = getCountAndTotalPosition(position);
		
		} catch (IndexOutOfBoundsException e) {
			
			respondToInvalidInput("position");
			return;
		}
		
		int countPosition = countAndTotal[0];
		
		int count = sequence.get(countPosition);
		
		int newCount = count - 1;
		
		//Removes element and its count from sequence if their new count is zero
		if(newCount == 0) {
			
			sequence.remove(countPosition);
			sequence.remove(countPosition);
			
		
		} else {
			
			sequence.set(countPosition, newCount);
		}
		
	}
	
	@Override
	public int getElementAt(int position) {
		
		int[] countAndTotal = null;
		
		try {
			
			countAndTotal = getCountAndTotalPosition(position);
		
		} catch (IndexOutOfBoundsException e) {
			
			respondToInvalidInput("position");
			return -1;
		}
		
		int countIndex = countAndTotal[0];
		int elementAtPosition = sequence.get(countIndex + 1);
		
		return elementAtPosition; 
	}

	@Override
	public void addToHead(int... sequence) {
		
		if(!SequenceValidator.isSequenceValid(sequence)) {
			
			respondToInvalidInput("value");
			return;
		}
		
		int originalSize = this.length();
		
		for(int value : sequence) {
			
			addElementToPosition(value, originalSize);
			originalSize+=1;
		}
		
	}

	@Override
	public void addToTail(int... sequence) {
		
		if(!SequenceValidator.isSequenceValid(sequence)) {
			
			respondToInvalidInput("value");
			return;
		}
		
		for(int i = sequence.length - 1; i >= 0; i--) {
			
			int newElement = sequence[i];
			
			addElementToPosition(newElement, 0);
		}
		
	}

	@Override
	public int[] asArray() {
		
		int size = sequence.size();
		int[] compressedSequence = new int[size];
		
		for(int i = 0; i < size; i++) {
			
			compressedSequence[i] = sequence.get(i);
		}
		
		return compressedSequence;
	}

	@Override
	public String asString() {
		
		int[] compressedSequence = this.asArray();
		
		int[] uncompressedSequence = RLEConverter.toAPI(compressedSequence);
		
		String sequenceAsString = "[";
		
		for (int element : uncompressedSequence) {
			
			sequenceAsString += element + " ";
		}
		
		sequenceAsString = sequenceAsString.trim();
		
		sequenceAsString += "]";
		
		return sequenceAsString;
	}

	/**
	 * Method that changes all the values in the sequence by a specific amount.
	 * 
	 * If the value of an element is too small (less than 0) or too big (more than 255) the value is capped
	 * at 0 and 255 respectively
	 * If the resulting value is too big, the value is capped at 255. Same if
	 * if it is too small.
	 * So if the sequence is [149 255 0 4] and the user calls changeValues(106), the result is
	 * [255 255 106 110].
	 * Conversely, if the user calls changeValues(-149) on [149 255 0 4], the result is
	 * [0 106 0 0]
	 * This behavior was chosen because that allows the method to be more versatile,
	 * and used to turn all the pixels to maximum or off. It is also more fluid for the user to use
	 * since it does a best guess within the constrains of the pixel value range without returning errors.
	 * 
	 * @param byWhat the amount by which the values in the sequence should be changed
	 */
	public void changeValues(int byWhat) {
		
		int size = sequence.size();
		
		for(int i = 1; i < size; i+=2) {
			
			int oldValue = sequence.get(i);
			
			int newValue = oldValue + byWhat;
			
			newValue = (newValue > 255) ? 255 : newValue;
			newValue = (newValue < 0) ? 0 : newValue;
			
			sequence.set(i, newValue);
		}
		
	}
	
	/**
	 * Method that returns an array where the first position is the index of the count of the element right
	 * before the position that should be modified and the second position is the total number of elements
	 * up to the index to be modified
	 * 
	 * Example: [5 4 3 2] modify index 5 (refers to index of theoretical uncompressed sequence) countAndTotal[0] = 0, countAndTotal[1] = 5
	 * 
	 * @param positionToBeModified index of element (uncompressed array) to be modified
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private int[] getCountAndTotalPosition(int positionToBeModified) throws IndexOutOfBoundsException{
		
		int size = sequence.size();
		int total = 0;
		int[] countAndTotal = new int[2];
		
		for(int countIndex = 0; countIndex < size; countIndex+=2) {
			
			total += sequence.get(countIndex);
			
			if(total >= positionToBeModified) {
				
				countAndTotal[0] = countIndex;
				countAndTotal[1] = total;
				
				return countAndTotal;
			}
		}
		
		throw new IndexOutOfBoundsException();
	}
	
	private void respondToInvalidInput(String type) {
		
		if(type.equals("value")) {
			
			System.out.println("The values in the sequence must be between 0 and 255 inclusive");
		
		} else if (type.equals("position")) {
			
			int length = this.length();
			
			System.out.printf("The position given does not exist in this sequence of length: %d", length);
		}
	}
	
	private int[] compress(int[] uncompressedSequence) {
		
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
	
	private static int predictLengthOfCompressedSequence(ArrayList<Integer> prefixes) {
		
		int totalNumValues = 0;
		
		int numPrefixes = prefixes.size();
		
		totalNumValues = numPrefixes * 2;
		
		
		return totalNumValues;
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
}
