import java.util.LinkedList;

/**
 * Class that represents version 2 of the RLESequence in which the internal representation of the sequence is compressed
 * using the [count1 value1 count2 value2 ...] scheme so that every count is in an even position in the data structure,
 * while every value is in an odd position, which makes manipulating the sequence simpler.
 * Compressed sequence is converted to uncompressed and passed to a helper RLESequenceV1, that does the 
 * operations. After that the uncompressed sequence is compressed again and stored in the LinkedList "sequence" as
 * a compressed version 
 *  
 * @author Luiz do Valle (lld2131)
 *
 */
public class RLESequenceV2 implements RLESequence {
	
	/*LinkedList over ArrayList because it is more efficient to add and remove nodes and each element
	 * points to the location of the next element in the sequence
	 * Dynamic data structure unlike arrays, means that elements can be added or removed
	 * 
	 * Integer was chosen because it has a positive range of 10^9 (giga pixel), which should be more than enough for 
	 * the number of "pixels" (each count) in each image considering that 8K images have around 10^7 pixels.
	 */
	private LinkedList<Integer> sequence = new LinkedList<>();
	
	/*
	 * A helper RLESequenceV1 that performs operations after this version has been uncompressed by RLEConverter
	 */
	private RLESequenceV1 helperSequence;
	
	/**
	 * Default constructor
	 * Default length chosen to zero as number of pixels cannot be assumed unless specified by users.
	 * There is no default, non-zero, size for pictures
	 * 
	 */
	public RLESequenceV2() {
		
		this(0);
	}
	
	/**
	 * Constructor that creates a sequence of the given length with default values of zero
	 * Zero because it represents pure black and it is sensible to start with no image being shown
	 * (all pixels off)
	 * The absolute value of the length is taken because a minus sign can only be a typo, so it is ignored
	 * @param length of sequence
	 */
	public RLESequenceV2(int length) {
		
		int size = Math.abs(length);
		
		int[] array = new int[size];
		
		initializeSequence(array);
	}
	
	/**
	 * Constructor that accepts a varag of ints as values
	 * @param entireSequence values pixel values for the sequence
	 */
	public RLESequenceV2(int...entireSequence) {
		
		initializeSequence(entireSequence);
	}
	
	private void initializeSequence(int[] values) {
		
		if(!SequenceValidator.isSequenceValid(values)) {
			
			respondToInvalidInput("value");
			return;
			
		} 
		sequence.clear();
		
		int[] compressedValues = RLEConverter.toSpace(values);
		
		for(int value : compressedValues) {
			
			sequence.addLast(value);
		}
	}
	@Override
	public int length() {
		
		int[] compressedSequence = this.asArray();
		int[] uncompressedSequence = RLEConverter.toAPI(compressedSequence);
		
		return uncompressedSequence.length;
	}

	@Override
	public void addElementToPosition(int element, int position) {
		
		helperSequence = initializeHelper();
		
		helperSequence.addElementToPosition(element, position);
		
		int[] uncompressedSequence = helperSequence.asArray();
		
		this.initializeSequence(uncompressedSequence);
		
	}

	@Override
	public void removeElementAt(int position) {
		
		
		helperSequence = initializeHelper();
		
		helperSequence.removeElementAt(position);
		
		int[] uncompressedSequence = helperSequence.asArray();
		
		this.initializeSequence(uncompressedSequence);
		
	}
	
	@Override
	public void modifyElementToAt(int newValue, int position) {
		
		helperSequence = initializeHelper();
		
		helperSequence.modifyElementToAt(newValue, position);
		
		int[] uncompressedSequence = helperSequence.asArray();
		
		this.initializeSequence(uncompressedSequence);
		
	}

	@Override
	public int getElementAt(int position) throws IndexOutOfBoundsException{
		
		helperSequence = initializeHelper();
		
		int element = helperSequence.getElementAt(position);
		
		return element;
	}

	@Override
	public void addToHead(int... sequence) {
		
		helperSequence = initializeHelper();
		
		helperSequence.addToHead(sequence);
		
		int[] uncompressedSequence = helperSequence.asArray();
		
		
		this.initializeSequence(uncompressedSequence);
		
	}

	@Override
	public void addToTail(int... sequence) {
		
		helperSequence = initializeHelper();
		
		helperSequence.addToTail(sequence);
		
		int[] uncompressedSequence = helperSequence.asArray();
		
		this.initializeSequence(uncompressedSequence);
		
	}
	
	@Override
	public int[] asArray() {
		
		int size = sequence.size();
		int[] compressedArray = new int[size];
		
		for(int i = 0; i < size; i++) {
			
			compressedArray[i] = sequence.get(i);
		}
		
		return compressedArray;
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
	
	private RLESequenceV1 initializeHelper() {
		
		int[] compressedSequence = this.asArray();
		int[] uncompressedSequence = RLEConverter.toAPI(compressedSequence);
		
		RLESequenceV1 rle1 = new RLESequenceV1(uncompressedSequence);
		
		return rle1;
				
	}
	
	private void respondToInvalidInput(String type) {
		
		if(type.equals("value")) {
			
			System.out.println("The values in the sequence must be between 0 and 255 inclusive");
		
		} else if (type.equals("position")) {
			
			int length = this.length();
			
			System.out.printf("The position given does not exist in this sequence of length: %d", length);
		}
	}
}
