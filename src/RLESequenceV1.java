import java.util.LinkedList;

/**
 * Class that represents version 1 of the RLESequence (no compression)
 * 
 * @author Luiz do Valle (lld2131)
 *
 */
public class RLESequenceV1 implements RLESequence {

	/*LinkedList because it is more efficient to add and remove nodes and each element
	 * points to the location of the next element in the sequence
	 * Dynamic data structure
	 */
	private LinkedList<Integer> sequence = new LinkedList<>();

	/**
	 * Default constructor
	 * Default length chosen to zero as number of pixels cannot be assumed unless specified by users.
	 * There is no default, non-zero, size for pictures
	 */
	public RLESequenceV1() {
		
		this(0);
	}
	
	/**
	 * Constructor that creates a sequence of the given length with default values of zero
	 * Zero because it represents pure black and it is sensible to start with no image being shown
	 * (all pixels off)
	 * The absolute value of the length is taken because a minus sign can only be a typo, so it is ignored
	 * @param length of sequence
	 */
	public RLESequenceV1(int length) {
		
		int size = Math.abs(length);
		
		int[] array = new int[size];
		
		initializeSequence(array);
	}
	
	/**
	 * Constructor that accepts a varag of ints as values
	 * @param entireSequence values pixel values for the sequence
	 */
	public RLESequenceV1(int...entireSequence) {
		
		
		initializeSequence(entireSequence);
	}
	
	private void initializeSequence(int[] values) {
		
		if(!SequenceValidator.isSequenceValid(values)) {
			
			respondToInvalidInput("value");
			return;
			
		} 
		
		sequence.clear();
		
		for(int value : values) {
			
			sequence.addLast(value);
		}
	}
	
	@Override
	public int length() {
		
		return sequence.size();
	}

	@Override
	public void addElementToPosition(int element, int position) {

		if (!SequenceValidator.isElementValid(element)) {
			
			respondToInvalidInput("value");
			return;
			
		}
		
		try {
			
			sequence.add(position, element);
		
		} catch (IndexOutOfBoundsException e) {
			
			respondToInvalidInput("position");
			return;
		}
		
		
	}

	@Override
	public void removeElementAt(int position) {
				
		try {
			
			sequence.remove(position);
		
		} catch (IndexOutOfBoundsException e) {
			
			respondToInvalidInput("position");
		}
		
		
	}
	
	@Override
	public void modifyElementToAt(int newValue, int position) {
		
		if (!SequenceValidator.isElementValid(newValue)) {
			
			respondToInvalidInput("value");
			return;
			
		} 
		
		try {
			
			sequence.set(position, newValue);
		
		} catch (IndexOutOfBoundsException e) {
			
			respondToInvalidInput("position");
		}
		
		
	}

	@Override
	public int getElementAt(int position) {
		
		int element = -1;
		
		try {
			
			element = sequence.get(position);
		
		} catch (IndexOutOfBoundsException e) {
			
			respondToInvalidInput("position");
		
		}
			
		return element;
	}

	@Override
	public void addToHead(int... sequence) {
		
		if(!SequenceValidator.isSequenceValid(sequence)) {
			
			respondToInvalidInput("value");
			return;
		}
		
		for (int element : sequence) {
			
			this.sequence.addLast(element);
		}
		
	}

	@Override
	public void addToTail(int... sequence) {
		
		if(!SequenceValidator.isSequenceValid(sequence)) {
			
			respondToInvalidInput("value");
			return;
		}
		
		int size = sequence.length;
		
		for (int i = size - 1; i >= 0; i--) {
			
			this.sequence.addFirst(sequence[i]);
		}
		
	}

	@Override
	public int[] asArray() {
		
		int size = length();
		
		int[] compressedArray = new int[size];
		
		for(int i = 0; i < size; i++) {
			
			compressedArray[i] = sequence.get(i);
		}
		
		return compressedArray;
	}

	@Override
	public String asString() {
		
		int[] uncompressedSequence = asArray();
		
		String sequenceAsString = "[";
		
		for (int element : uncompressedSequence) {
			
			sequenceAsString += element + " ";
		}
		
		sequenceAsString = sequenceAsString.trim();
		
		sequenceAsString += "]";
		
		return sequenceAsString;
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
