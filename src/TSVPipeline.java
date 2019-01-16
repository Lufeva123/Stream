import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class that streams the records in the file specified in the TSVFilter to an output file if they are valid
 * and match the filters specified by the user in the TSVFilter.
 * It also uses the Computation algorithms to perform the required computations on the streamed data
 * 
 * The class does not have "natural" bounds for fields like Age or Weight because:
 * 	1-) There is an infinite number of possible headers and just implementing a few of them is not good as most
 * 	of the possible headers would not have this functionality and it is impossible to extend it for even most of the headers.
 * 	2-) There is no natural or elegant way of saying that Age, for example, can only be between 0 and 127 (unless you restrict it to a byte)
 * 	or that a phone number is restricted to 10 digits because, if you are referring to a rock's age, it will exceed 127 or if you
 * 	are talking about international phone numbers, the number of digits vary widely, for instance.
 * 	3-) "Reasonableness" is context-dependent, so it is up to the user to determine what is reasonable. If he/she wants to find outliers, like a human being with more than 127 years,
 * 	in his/her data, the user can simply use the "outlier()" filter (outlier("Age", 127), for example).
 * 
 * @author Luiz do Valle
 *
 */
public class TSVPipeline {

	/**
	 * The TSVFilter containing the filters to be used in the pipeline
	 */
	private TSVFilter tsvFilter;
	/**
	 * The data types (String and long) found in the file in the order in whicg they were found
	 * 0 for long and 1 for String
	 * byte used because only the values of 0 and 1 are required and byte occupies the lowest memoru fo the
	 * primitive numerical types
	 */
	private byte[] dataTypes;
	
	/**
	 * The header fields in the order in which they were found
	 */
	private String[] headerFields;
	
	/**
	 * The Scanner used to stream the data
	 */
	private Scanner scanner;
	/**
	 * The PrintWriter used to stream records to the valid output file
	 */
	private PrintWriter wantedOutput;
	
	/**
	 * The PrintWriter used to stream records to the invalid output file
	 */
	private PrintWriter invalidOutput;
	
	/**
	 * The reference to the computation algorithm that is used as a plugin
	 */
	private Computation computer;
	
	/**
	 * The reference to the selection algorithm that is used as a plugin
	 */
	private Selection selector;
	
	/**
	 * The path for the valid outputs
	 */
	private final String OUTPUT_PATH = "output.tsv";
	/**
	 * The path for the invalid outputs
	 */
	private final String INVALID_OUTPUT_PATH = "invalid_output.tsv";
	
	/**
	 * Constructor for the class
	 * @param tsvFilter
	 */
	public TSVPipeline(TSVFilter tsvFilter) {
		
		this.tsvFilter = tsvFilter;
		
	}
	
	/**
	 * Method that reads the .tsv file specified in the tsvFilter TSVFilter, checks if it exists and whether it is formated correctly.
	 * If either of the above conditions is not fulfilled, a message is displayed to the user indicating
	 * this and the method stops.
	 * If the file exists and the first two lines are valid, the method checks whether each subsequent record is valid.
	 * If the record is not valid, the method outputs this record to the invalidOutput.tsv and informs the user at the end of execution
	 * of the method. A record is not valid if a field is supposed to be a long, but cannot be converted to a long or if it is missing
	 * a field.
	 * If the record is valid, the method checks whether the record is wanted by the user. If it is, the record
	 * is streamed to the output.tsv file.
	 * A record is wanted if the target field specified in the TSVFilter matches the record field under the 
	 * TSVFilter's target header.
	 * If the TSVFilter's specified header is the default value (empty String, ""), the method streams the whole
	 * input file to the output file.
	 * If the user specified a header, but no field in the TSVFilter, nothing is sent to the output file as there is no
	 * way to determine a default field for an arbitrary header.
	 * If no match between the TSVFilter's requirements and the records in the file are found, a message
	 * telling this to the user is displayed.
	 * 
	 * At the end of execution the method also tells the user which data types (String and/or long) were found
	 */
	public void doit() {
		
		
		File file = tsvFilter.getFile();
		
		if(!file.exists()) {
			
			System.out.println("The file does not exist in the given directory.");
			return;
		
		} 
			
		initializeScanner(file);
		initializePrintWriters();
		
		//Header line validation
		if(!scanner.hasNextLine()) {
			
			System.out.println("The file cannot be read becuase the header line is missing");
			return;
		}
		
		String headerLine = scanner.nextLine();
		
		if(!isHeaderValid(headerLine)) {
			
			System.out.println("The file cannot be read because the header line is malformed");
			return;
		}
		
		headerFields = extractFields(headerLine);
		wantedOutput.println(headerLine);
		
		//Type line validation
		if(!scanner.hasNextLine()) {
			
			System.out.println("The file cannot be read because the type line is missing");
			return;
		}
		
		String typeLine = scanner.nextLine();
		
		if(!isTypeLineValid(typeLine)) {
			
			System.out.println("The file cannot be read because the type line is malformed");
			return;
		}
		
		dataTypes = extractTypes(typeLine);
		wantedOutput.println(typeLine);
		
		boolean foundSomething = false;
		boolean invalidRecordFound = false;
		
		initializeComputer();
		initializeSelector();
		
		
		//Record validation
		while(scanner.hasNextLine()) {
			
			String record = scanner.nextLine();
			
			if(!isRecordValid(record)) {
				//Send to malformed file
				invalidOutput.println(record);
				invalidRecordFound = true;
				continue;
			}
			
			if(selector.isRecordWanted(record)) {
				//Stream to file
				foundSomething = true;
				computer.compute(record);
				wantedOutput.println(record);
			}
			
		}
		
		//Display types found
		displayTypesFound();
		
		if(invalidRecordFound) {
			
			System.out.println("Invalid records found. See invalid_out.tsv file");
		}
		
		if(!foundSomething) {
			
			System.out.println("No records matched your query.");
		}
		
		//Free resources
		scanner.close();
		wantedOutput.close();
		invalidOutput.close();
		System.out.println("Done!");
		System.out.println("Computation Result: " + computer.result());
	}
	
	/**
	 * Method that initializes the file scanner
	 * @param file file to be streamed
	 */
	private void initializeScanner(File file) {
		
		try {
			
			scanner = new Scanner(file);
		
		} catch (FileNotFoundException e) {
			
			scanner.close();
		}
	}
	
	/**
	 * Method that initializes the PrintWriters to be used to stream the data to the valid and invalid 
	 * output files
	 */
	private void initializePrintWriters() {
		
		try {
			
			wantedOutput = new PrintWriter(OUTPUT_PATH);
			invalidOutput = new PrintWriter(INVALID_OUTPUT_PATH);
			
		} catch (FileNotFoundException e) {
			
			System.out.println("Problem creating printwriter");
			wantedOutput.close();
			invalidOutput.close();
		}
	}
	
	/**
	 * Method that checks whether the given header is valid
	 * A header is valid if it is not empty and contains no empty fields
	 * @param header the header to be checked
	 * @return true if the header is valid, false otherwise
	 */
	private boolean isHeaderValid(String header) {
		
		String[] headerFields = extractFields(header);
		
		if (headerFields.length < 1) {
			
			return false;
		}
		
		for(String headerField : headerFields) {
			
			if(headerField.length() < 1) {
				
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Method that checks whether the type line is valid
	 * A type line is valid if and only if it has the same number of fields as the header and
	 * all of its fields are either "String" or "long"
	 * @param typeLine the type line to be checked
	 * @return true if the type line is valid, false otherwise
	 */
	private boolean isTypeLineValid(String typeLine) {
		
		String[] typeFields = typeLine.split("\t");
		
		if (typeFields.length != headerFields.length) {
			
			return false;
		}
		
		for(String typeField : typeFields) {
			
			if (!typeField.equals("String") && !typeField.equals("long")) {
				
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Method that checks whether a given record is valid
	 * A record is valid is it contains the same number of fields as the header and
	 * the values under long columns can be represented as longs
	 * @param record
	 * @return
	 */
	private boolean isRecordValid(String record) {
		
		String[] recordFields = extractFields(record);
		
		if(recordFields.length != headerFields.length) {
			
			return false;
		}
		
		
		for (int column = 0; column < recordFields.length; column++) {
			
			String field = recordFields[column];
			byte columnType = dataTypes[column];
			
			if (columnType == 0 && !isValidLong(field)) {
				
				return false;
				
			}
			
		}
		
		return true;
	}
	
	/**
	 * Method that decides which Selector plugin to use based on what the user specified in the TSVFilter
	 * The default is the normal
	 */
	private void initializeSelector() {
		
		SelectionType selectionType = tsvFilter.getSelection();
		String targetHeader = tsvFilter.getSelectHeader();
		String targetField = tsvFilter.getRecordField();
		int targetColumn = findTargetColumn(targetHeader);
		
		switch(selectionType) {
		
			case NORMAL:
				selector = new NormalSelection(targetHeader, targetField, targetColumn);
				break;
			case OUTLIER:
				selector = new OutlierSelection(targetColumn, tsvFilter.getError());
				break;
			default:
				selector = new NormalSelection(targetHeader, targetField, targetColumn);
		}
		
	}
	
	/**
	 * Method that decides which Computer plugin to use based on what the user selected in the TSVFilter
	 */
	private void initializeComputer() {
		
		Terminal computation = tsvFilter.getComputation();
		int targetColumn = findTargetColumn(tsvFilter.getComputeHeader());
		
		switch(computation) {
		
			case ALLSAME:
				computer = new AllSameComputation();
				break;
			case COUNT:
				computer = new CountComputation();
				break;
			case MIN:
				computer = new MinComputation(targetColumn);
				break;
			case MAX:
				computer = new MaxComputation(targetColumn);
				break;
			case SUM:
				computer = new SumComputation(targetColumn);
				break;
			case FIRSTDIFF:
				computer = new FirstDiffComputation(targetColumn);
				break;
			case NULL:
				computer = new NullComputation();
		}
	}
	
	/**
	 * Method that extracts the fields from the given line based
	 * @param line the line from which the values should be extracted from
	 * @return
	 */
	private String[] extractFields (String line) {
		
		return line.split("\t");
	}
	
	/**
	 * Method that extracts the types from the type line and converts them to 0 (long) or 1 (String)
	 * @param typeLine the type line
	 * @return array of bytes containing the converted data types
	 */
	private byte[] extractTypes(String typeLine) {
		
		String[] types = typeLine.split("\t");
		
		byte[] dataTypes = new byte[types.length];
		
		for(int column = 0; column < types.length; column++) {
			
			String type = types[column];
			
			if(type.equals("long")) {
				
				dataTypes[column] = 0;
			
			} else if (type.equals("String")) {
				
				dataTypes[column] = 1;
			}
		}
		
		return dataTypes;
	}
	
	/**
	 * Method that finds the column where the given header is
	 * @param header the header from which the column is to be found
	 * @return the column where the header is or -2 if it is not found
	 */
	private int findTargetColumn(String header) {
		
		for(int column = 0; column < headerFields.length; column++) {
			
			if(header.equals(headerFields[column])) {
				
				return column;
			}
		}
		
		return -1;
	}
	
	/**
	 * Method that checks whether the given field can be represented as a long
	 * @param field the field to be checked
	 * @return true if the field can be represented as long, false otherwise
	 */
	private boolean isValidLong(String field) {
		
		try {
			
			Long.parseLong(field);
		
		} catch (NumberFormatException e) {
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method that displays the types in the dataTypes array to the user
	 */
	private void displayTypesFound() {
		
		boolean longFound = false;
		boolean stringFound = false;
		
		for(byte type : dataTypes) {
			
			if(type == 0) {
				
				longFound = true;
			
			} else if (type == 1) {
				
				stringFound = true;
			}
		}
		
		if(longFound) {
			
			System.out.print("long ");
		} 
		
		if(stringFound) {
			
			System.out.print("String ");
		}
		
		System.out.println("fields found");
	}
	
}
