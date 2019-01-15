/**
 * Class that contains that contains scripts (methods) to test the different versions of RLESequence
 * @author Luiz do Valle (lld2131)
 *
 */
public class RLESequenceTester {

	/**
	 * Method that tests all methods and constructors in RLESequenceV1
	 */
	public static void testRLE1() {
		
		RLESequence s1 = new RLESequenceV1();
		
		RLESequence s2 = new RLESequenceV1(10);
		
		RLESequence s3 = null;
		
		s3 = new RLESequenceV1(200, 255, 100, 0, 1);
		
		System.out.printf("Default (empty) sequence (s1): %s\n", s1.asString());
		
		System.out.printf("Default length %d sequence(s2): %s\n", s2.length(), s2.asString());
		
		System.out.printf("Constructor initialized sequence (s3): %s\n", s3.asString());
		
			
		s1.addElementToPosition(187, 0);
		System.out.printf("\ns1 after call to addElementToPosition(187, 0): %s\n", s1.asString());
			
		s2.modifyElementToAt(190, 5);
		System.out.printf("s2 after call to modifyElementToAt(190, 5): %s\n", s2.asString());
			
		s3.addToHead(245, 198, 188, 187);
		System.out.printf("s3 after call to addToHead(245, 198, 188, 187): %s\n", s3.asString());
			
		s1.addToTail(200, 255, 100, 0, 1, 245, 198, 188);
		System.out.printf("\ns1 after call to addToTail(200, 255, 100, 0, 1, 245, 198, 188): %s\n", s1.asString());
			
		boolean isEqual = s1.isEqualTo(s2);
		System.out.printf("\nEquality comparison between s1 and s2 yields: %s\n", isEqual);
		
		isEqual = s1.isEqualTo(s3);
		System.out.printf("Equality comparison between s1 and s3 yields: %s\n", isEqual);
			
		isEqual = s2.isEqualTo(s2);
		System.out.printf("Equality comparison between s2 and s2 yields: %s\n", isEqual);
			
		int element = s3.getElementAt(8);
		System.out.printf("\nResult of call to s3.getElementAt(9): %d\n", element);
		
		s3.removeElementAt(8);
		System.out.printf("\nResult of call to s3.removeElement(8): %s\n", s3.asString());
		
		System.out.print("\nResult of calling new RLESequenceV1(-7, 3, -2, 9): ");
		
		RLESequence s4 = null;
		
		s4 = new RLESequenceV1(-7, 3, -2, 9);
		
		
		System.out.print("\nResult of calling new RLESequenceV2(256, 100, 9): ");
		
		s4 = new RLESequenceV2(256, 100, 9);
		
		System.out.print("\n\nResult of calling s1.addElementToPosition(-1, 0): ");
		
		s1.addElementToPosition(-1, 0);
		
		System.out.print("\nResult of calling s1.addElementToPosition(1, 10): ");
		
		s1.addElementToPosition(1, 10);
			
		System.out.print("\n\nResult of calling s2.modifyElementToAt(-1, 0): ");
		
		s2.modifyElementToAt(-1, 0);
		
		System.out.print("\nResult of calling s2.modifyElementToAt(1, 10): ");
		
		s2.modifyElementToAt(1, 10);
		
		System.out.print("\n\nResult of calling s3.getElementAt(100): ");
		
		s3.getElementAt(100);
		
		System.out.print("\nResult of calling s3.removeElemetAt(18): ");
		
		s3.removeElementAt(18);
		
		System.out.print("\nResult of calling s1.addToHead(-1, 255): ");
		
		s1.addToHead(-1, 255);
		
		System.out.print("\nResult of calling s1.addToHead(1, 256): ");
		
		s1.addToHead(1, 256);
		
		System.out.print("\n\nResult of calling s1.addToTail(-1, 255): ");
		
		s1.addToTail(-1, 255);
		
		System.out.print("\nResult of calling s1.addToHead(1, 256): ");
		
		s1.addToTail(1, 256);
		
	}
	
	/**
	 * Method that tests all methods and constructors in RLESequenceV2
	 */
	public static void testRLE2() {
		
		RLESequence s1 = new RLESequenceV2();
		RLESequence s2 = new RLESequenceV2(10);
		RLESequence s3 = new RLESequenceV2(200, 255, 100, 0, 1);
		RLESequence s4 = new RLESequenceV1(0, 0, 0, 0, 0, 190, 0, 0, 0, 0);
		
		System.out.printf("Default (empty) sequence (s1): %s Internal: %s\n", s1.asString(), RLEConverter.dumpGutsOf(s1));
		
		System.out.printf("Default length %d sequence(s2): %s Internal: %s\n", s2.length(), s2.asString(), RLEConverter.dumpGutsOf(s2));
		
		System.out.printf("Constructor initialized sequence (s3): %s Internal: %s\n", s3.asString(), RLEConverter.dumpGutsOf(s3));
		
		System.out.printf("Constructor initialized sequence (s4-Version1): %s\n", s4.asString());
		
		s1.addElementToPosition(188, 0);
		System.out.printf("\ns1 after call to addElementToPosition(188, 0): %s\n", s1.asString());
			
		s2.modifyElementToAt(190, 5);
		System.out.printf("s2 after call to modifyElementToAt(190, 5): %s\n", s2.asString());
			
		s3.addToHead(245, 198, 188, 188, 188);
		System.out.printf("s3 after call to addToHead(245, 198, 188, 188, 188): %s\n", s3.asString());
			
		s1.addToTail(200, 255, 100, 0, 1, 245, 198, 188, 188);
		System.out.printf("\ns1 after call to addToTail(200, 255, 100, 0, 1, 245, 198, 188, 188): %s\n", s1.asString());
			
		boolean isEqual = s1.isEqualTo(s2);
		System.out.printf("\nEquality comparison between s1 and s2 yields: %s\n", isEqual);
			
		isEqual = s1.isEqualTo(s3);
		System.out.printf("Equality comparison between s1 and s3 yields: %s\n", isEqual);
			
		isEqual = s2.isEqualTo(s2);
		System.out.printf("Equality comparison between s2 and s2 yields: %s\n", isEqual);
			
		isEqual = s2.isEqualTo(s4);
		System.out.printf("Equality comparison between s2(Version2) and s4(Version1) : %s\n", isEqual);
			
		int element = s3.getElementAt(8);
		System.out.printf("\nResult of call to s3.getElementAt(8): %d\n", element);
		
		s3.removeElementAt(8);
		System.out.printf("\nResult of call to s3.removeElementAt(8): %s\n", s3.asString());
		
		System.out.print("\nResult of calling new RLESequenceV1(-7, 3, -2, 9): ");
		
		s4 = new RLESequenceV1(-7, 3, -2, 9);
		
		
		System.out.print("\nResult of calling new RLESequenceV1(256, 100, 9): ");
		
		s4 = new RLESequenceV1(256, 100, 9);
		
		
		System.out.print("\n\nResult of calling s1.addElementToPosition(-1, 0): ");
		
		s1.addElementToPosition(-1, 0);
		
		System.out.print("\nResult of calling s1.addElementToPosition(1, 16): ");
		
		s1.addElementToPosition(1, 16);
		
		System.out.print("\n\nResult of calling s2.modifyElementToAt(-1, 0): ");
		
		s2.modifyElementToAt(-1, 0);
		
		System.out.print("\nResult of calling s2.modifyElementToAt(1, 10): ");
		
		s2.modifyElementToAt(1, 10);
		
		System.out.print("\n\nResult of calling s3.getElementAt(100): ");
		
		s3.getElementAt(100);
		
		System.out.print("\nResult of calling s3.removeElementAt(100): ");
		
		s3.removeElementAt(100);
		
		System.out.print("\nResult of calling s1.addToHead(-1, 255): ");
		
		s1.addToHead(-1, 255);
		
		System.out.print("\nResult of calling s1.addToHead(1, 256): ");
		
		s1.addToHead(1, 256);
		
		System.out.print("\n\nResult of calling s1.addToTail(-1, 255): ");
		
		s1.addToTail(-1, 255);
		
		System.out.print("\nResult of calling s1.addToTail(1, 256): ");
		
		s1.addToTail(1, 256);
		
	}
	
	/**
	 * Method that tests all the methods and constructors in RLESequenceV3
	 */
	public static void testRLE3() {
		
		RLESequence s1 = new RLESequenceV3();
		RLESequence s2 = new RLESequenceV3(10);
		RLESequence s3 = new RLESequenceV3(200, 255, 100, 0, 1);
		RLESequence s4 = new RLESequenceV1(0, 0, 0, 0, 0, 190, 0, 0, 0, 0);
		
		System.out.printf("Default (empty) sequence (s1): %s Internal: %s\n", s1.asString(), RLEConverter.dumpGutsOf(s1));
		
		System.out.printf("Default length %d sequence(s2): %s Internal: %s\n", s2.length(), s2.asString(), RLEConverter.dumpGutsOf(s2));
		
		System.out.printf("Constructor initialized sequence (s3): %s Internal: %s\n", s3.asString(), RLEConverter.dumpGutsOf(s3));
		
		System.out.printf("Constructor initialized sequence (s4-Version1): %s\n", s4.asString());
		
		s1.addElementToPosition(188, 0);
		System.out.printf("\ns1 after call to addElementToPosition(188, 0): %s\n", s1.asString());
			
		s2.modifyElementToAt(190, 5);
		System.out.printf("s2 after call to modifyElementToAt(190, 5): %s\n", s2.asString());
			
		s3.addToHead(245, 198, 188, 188, 188);
		System.out.printf("s3 after call to addToHead(245, 198, 188, 188, 188): %s\n", s3.asString());
			
		s1.addToTail(200, 255, 100, 0, 1, 245, 198, 188, 188);
		System.out.printf("\ns1 after call to addToTail(200, 255, 100, 0, 1, 245, 198, 188, 188): %s\n", s1.asString());
			
		boolean isEqual = s1.isEqualTo(s2);
		System.out.printf("\nEquality comparison between s1 and s2 yields: %s\n", isEqual);
			
		isEqual = s1.isEqualTo(s3);
		System.out.printf("Equality comparison between s1 and s3 yields: %s\n", isEqual);
			
		isEqual = s2.isEqualTo(s2);
		System.out.printf("Equality comparison between s2 and s2 yields: %s\n", isEqual);
			
		isEqual = s2.isEqualTo(s4);
		System.out.printf("Equality comparison between s2(Version2) and s4(Version1): %s\n", isEqual);
			
		int element = s3.getElementAt(8);
		System.out.printf("\nResult of call to s3.getElementAt(9): %d\n", element);
		
		s3.removeElementAt(8);
		System.out.printf("\nResult of call to s3.removeElementAt(8): %s\n", s3.asString());
		
		System.out.print("\nResult of calling new RLESequenceV1(-7, 3, -2, 9): ");
		
		s4 = new RLESequenceV1(-7, 3, -2, 9);
		
		
		System.out.print("\nResult of calling new RLESequenceV1(256, 100, 9): ");
		
		s4 = new RLESequenceV1(256, 100, 9);
		
		
		System.out.print("\n\nResult of calling s1.addElementToPosition(-1, 0): ");
		
		s1.addElementToPosition(-1, 0);
		
		System.out.print("\nResult of calling s1.addElementToPosition(1, 16): ");
		
		s1.addElementToPosition(1, 16);
		
		System.out.print("\n\nResult of calling s2.modifyElementToAt(-1, 0): ");
		
		s2.modifyElementToAt(-1, 0);
		
		System.out.print("\nResult of calling s2.modifyElementToAt(1, 10): ");
		
		s2.modifyElementToAt(1, 10);
		
		System.out.print("\n\nResult of calling s3.getElementAt(100): ");
		
		s3.getElementAt(100);
		
		System.out.print("\nResult of calling s3.removeElementAt(100): ");
		
		s3.removeElementAt(100);
		
		System.out.print("\nResult of calling s1.addToHead(-1, 255): ");
		
		s1.addToHead(-1, 255);
		
		System.out.print("\nResult of calling s1.addToHead(1, 256): ");
		
		s1.addToHead(1, 256);
		
		System.out.print("\n\nResult of calling s1.addToTail(-1, 255): ");
		
		s1.addToTail(-1, 255);
		
		System.out.print("\nResult of calling s1.addToTail(1, 256): ");
		
		s1.addToTail(1, 256);
		
	}
	
	/**
	 * Method that tests the creativity section
	 */
	public static void testCreativity() {
		
		RLESequenceV3 s1 = new RLESequenceV3(255, 5, 140, 0);
		
		System.out.printf("Original s1: %s\n", s1.asString());
		
		s1.changeValues(115);
		
		System.out.printf("\ns1 after call to change(115): %s\n", s1.asString());
		
		s1.changeValues(-115);
		
		System.out.printf("\ns1 after call to change(-115): %s\n", s1.asString());
		
		s1.changeValues(-100);
		
		System.out.printf("\ns1 after call to change(-100): %s", s1.asString());
	}
}
