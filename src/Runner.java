/**
 * Class used for testing
 * 
 * System Design: The "select" and "compute" behaviors were abstracted following the Strategy Pattern
 * to interfaces (Selection and Computation) that are implemented by concrete behaviors (in the case of Select: NormalSelection 
 * and OutlierSelection) (in the case of Compute: NullComputation, MaxComputation, CountComputation, etc)
 * These classes are used as plugins in the TSVPipeline class, which makes the system flexible and expandable, but 
 * increases the number of classes in the program
 * 
 * NOTE: The user interface in part 2.1 was different than that for the following parts. It required the user to
 * specify the file on the console.
 * 
 * NOTE: The toString() method of TSVFilter did not include the Terminal computation type until the tests 
 * in part 2.4 when I realized it was missing and added it.
 * 
 * TESTING:
 * 
 * Part 2.1
 * 
 * Test Case ID: Invalid Record Field
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can detect malformed records
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: Input data with a String where a long should be found and missing a field. 
 * Expected Result: The system will tell the user it found a problem with a the malformed lines, output
 * these lines to the invalidOutput.tsv file and continue analyzing the other records
 * Actual Result: The results matched expectations
 * Post-condition: The output files are populated correctly
 * 
 * Test Case ID: Inexistent File
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can identify a file that does not exist and act elegantly in response
 * Pre-conditions: None
 * Test Data: Inexistent File
 * Expected Result: The system should tell the user that the file does not exist
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Malformed Type Line
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can detect malformed type lines
 * Pre-conditions: The file exists and the header is properly formed
 * Test Data: File with type line missing a field
 * Expected Result: The system will tell the user it found a problem with a the type line and
 * stop the program
 * Actual Result: The results matched expectations
 * Post-condition: The program stopped
 * 
 * Test Case ID: Missing Header
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can detect absent headers
 * Pre-conditions: The file exists
 * Test Data: Perfect file except with missing header line 
 * Expected Result: The system will tell the user it found a problem with the header and
 * stop the program
 * Actual Result: The results matched expectations
 * Post-condition: The program stopped
 * 
 * Test Case ID: Missing Record Field
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can detect missing fields in the records
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: Input data with fields in different records
 * Expected Result: The system will tell the user it found a problem with a record line, output
 * these lines to the invalidOutput.tsv file and continue analyzing the other records
 * Actual Result: The results matched expectations
 * Post-condition: The output files are populated correctly
 * 
 * Test Case ID: Missing Type Line
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can detect absent type lines
 * Pre-conditions: The file exists
 * Test Data: Perfect file except with missing type line 
 * Expected Result: The system will tell the user it found a problem with the type line and
 * stop the program
 * Actual Result: The results matched expectations
 * Post-condition: The program stopped
 * 
 * Test Case ID: Only New Lines Record
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can detect records that are empty
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: Empty or tab only records
 * Expected Result: The system will tell the user it found a problem with a record line, output
 * these lines to the invalidOutput.tsv file and continue analyzing the other records
 * Actual Result: The results matched expectations
 * Post-condition: The output files are populated correctly
 * 
 * Test Case ID: Perfect Input 1
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can parse a properly formed file correctly
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: Propely formed file
 * Expected Result: The output file will match the input
 * Actual Result: The results matched expectations
 * Post-condition: The output files are populated correctly
 * 
 * Test Case ID: Perfect Input 2
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can parse a different, but properly formed file correctly
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: Propely formed file
 * Expected Result: The output file will match the input
 * Actual Result: The results matched expectations
 * Post-condition: The output files are populated correctly
 * 
 * Test Case ID: Tabs In a Row
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can detect records that are properly formed, but with one field consisting of two tabs in a row empty
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: Two tabs in a row
 * Expected Result: The system will tell the user it found a problem with a record line, output
 * these lines to the invalidOutput.tsv file and continue analyzing the other records
 * Actual Result: The results matched expectations
 * Post-condition: The output files are populated correctly
 * 
 * Part 2.2
 * 
 * Test Case ID: Select With Inexistent Filter
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system detects that the filter does no match any record in the file
 * and therefore outputs no record to the output.tsv file, but still performs the normal validity checks
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: File that contains a properly formed header and type line and mostly properly formed records
 * Expected Result: The system will tell the user that no records matched the filter query and output only the
 * header and type line to the output file (no records). The system will identify and act as in the tests above 
 * when it finds a malformed record.
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Select With Invalid Filter
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system detects that the filter's header does no match any header in the file
 * and therefore outputs no record to the output.tsv file, but still performs the normal validity checks
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: File that contains a properly formed header and type line and mostly properly formed records
 * Expected Result: The system will tell the user that no records matched the filter query and output only the
 * header and type line to the output file (no records). The system will identify and act as in the tests above 
 * when it finds a malformed record.
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Select With Long Filter
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can correctly stream the records in the file that match the filters and
 * still perform the normal validity checks. The field filter is a long field in this case
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: File that contains a properly formed header and type line and mostly properly formed records
 * Expected Result: The system will stream only the records that match the user's query and warn the user if it
 * finds any invalid records
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * 
 * Test Case ID: Select With String Filter
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system can correctly stream the records in the file that match the filters and
 * still perform the normal validity checks. The field filter is a String field in this case
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: File that contains a properly formed header and type line and mostly properly formed records
 * Expected Result: The system will stream only the records that match the user's query and warn the user if it
 * finds any invalid records
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Select With No Filters
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the system identifies that no filters were specified and acts exactly like part 2.1,
 * simply performing the normal validity checks, and telling the user what types of fields it found.
 * Pre-conditions: The file exists and the header and type lines are properly formed
 * Test Data: File that contains a properly formed header and type line and mostly properly formed records
 * Expected Result: The system will only stream the records that are valid and warn the user if it
 * finds any invalid records, outputing those to the invalid_output.tsv 
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * 
 * Part 2.3
 * 
 * Test Case ID: Terminal ALLSAME1
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the ALLSAME terminal computation can identify whether all the records in
 * the output file are exactly the same.
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains element names with their symbol, group number, and atomic number
 * Expected Result: The system should print that the result of the computation is false as none of the 
 * records in the output file are exactly the same
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal ALLSAME2
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the ALLSAME terminal computation can identify whether all the records in
 * the output file are exactly the same.
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains element names with their symbol, group number, and atomic number. The file has some invalid records
 * Expected Result: The system should print that the result of the computation is true as there should
 * be only one record in the output and that record is equal to itself
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal COUNT
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the COUNT terminal computation can count properly
 * how many records are in the output file
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains person names, with their phone numbers and zip codes. The file has some invalid records
 * Expected Result: The system should print that the result of the computation is 2 as there are only two records with 
 * the zip code specified in the filter
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal MAX Long
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the MAX terminal computation can identify the the record with the greatest
 * numerical value
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains element names with their symbol, group number, and atomic number. The file has some invalid records
 * Expected Result: The system should print that the result of the computation is 46 as the greatest atomic number in the output
 * is 46
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal MAX String
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the MAX terminal computation can identify the the record with the greatest
 * String value
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains element names with their symbol, group number, and atomic number. The file has some invalid records
 * Expected Result: The system should print that the result of the computation is "Sulfur" as this word is the last one in
 * lexicographical order in the output
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal MIN Long
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the MIN terminal computation can identify the the record with the smallest
 * numerical value
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains element names with their symbol, group number, and atomic number. The file has some invalid records
 * Expected Result: The system should print that the result of the computation is 1 as the smallest atomic number in the output
 * is 1
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal MIN String
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the MIN terminal computation can identify the the record with the smallest
 * String value
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains person names, with their phone numbers and zip codes. The file has some invalid records
 * Expected Result: The system should print that the result of the computation is Antonio as this is the name field
 * that comes before lexicographically in the output file.
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal SUM Long
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the SUM terminal computation performs correctly on long fields, adding all of them
 * and returning the sum
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains person names, with their phone numbers and zip codes. The file has some invalid records
 * Expected Result: The system should print that the result of the computation is 131 as the some of all the ages in the output
 * file is 131
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal SUM String
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the SUM terminal computation performs correctly on String fields, essentially just
 * returning zero
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains person names, with their phone numbers and zip codes. The file has some invalid records
 * Expected Result: The system should print that the result of the computation is 0 because there is nothing to add. "Names" cannot be added like numbers
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal Header Inexistent
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the terminal computations (in this test, MAX) are able to recognize
 * when a header passed to the compute() method does not exist. No computation should be performed
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity and filter checks work
 * as specified in the use case
 * Test Data: File that contains element names with their symbol, group number, and atomic number. The file has some invalid records
 * Expected Result: The system should print that the result of the computation "" as the Header "Pizza" passed to compute("Pizza", Terminal.MAX)
 * does not exist in the file
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * 
 * Part 2.4
 * 
 * Test Case ID: Outlier Long
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the outlier filter works correctly on long fields
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity checks work
 * as specified in the use case
 * Test Data: File that contains person names, with their phone numbers and zip codes. The file has some invalid records
 * Expected Result: The system should output only the records containing the ages 50 and 14 as those are the only ones
 * that differ from the previous record by the error specified in the outlier() function
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Outlier String
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the outlier filter responds correctly to String fields being passed as headers
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity checks work
 * as specified in the use case
 * Test Data: File that contains element names with their symbol, group number, and atomic number. The file has some invalid records
 * Expected Result: The system should stream no records to the output file as outlier is not defined for String fields
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal FIRSTDIFF
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the FIRSTDIFF terminal computation works correctly on String fields
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity checks work
 * as specified in the use case
 * Test Data: File that contains element names with their symbol, group number, and atomic number. The file has some invalid records
 * Expected Result: The system should print the whole record containing the Magnesium information as it is the first that differs
 * from the previous one by its "Group Number" field. The console should also display the counter as 0 as there are no other Magnesium
 * records
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 * Test Case ID: Terminal FIRSTDIFF2
 * Test Designed By: Luiz do Valle
 * Test Summary: This tests whether the FIRSTDIFF terminal computation works correctly on long fields
 * Pre-conditions: The file exists and the header and type lines are properly formed. The validity checks work
 * as specified in the use case
 * Test Data: File that contains person names, with their phone numbers and zip codes. An extra record containing
 * the "Name" Bob was inserted for the purpose of this testing. The file has some invalid records
 * Expected Result: The system should print the whole record containing the Bob information as it is the first that differs
 * from the previous one by its "Name" field. The console should also display the counter as 1 as there is one
 * other Bob record
 * Actual Result: The results matched expectations
 * Post-condition: None
 * 
 *@author Luiz do Valle
 *
 *
 */
public class Runner {

	/**
	 * The start of the application
	 * @param args
	 */
	public static void main(String[] args) {
		
		testTerminalFIRSTDIFF2();
	}
	
	public static void testSelectWithNoFilters() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testSelectWithStringFilter() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input1.tsv").select("Name", "Joao").done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testSelectWithLongFilter() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").select("Group Number", 2).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testSelectWithInvalidFilters() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").select("Address", "31 SE 5th Street").done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
		
	}
	
	public static void testSelectWithInexistentFilter() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input1.tsv").select("Zip Code", "33136").done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalALLSAME1() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").select("Group Number", 10).compute("Atomic Number", Terminal.ALLSAME).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalALLSAME2() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").select("Group Number", 18).compute("Group Number", Terminal.ALLSAME).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalCOUNT() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input1.tsv").select("Zip Code", 33131).compute("", Terminal.COUNT).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);

	}
	
	public static void testTerminalMINlong() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").select("", "").compute("Atomic Number", Terminal.MIN).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalMINstring() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input1.tsv").select("", "").compute("Name", Terminal.MIN).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalMAXlong() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").select("Group Number", 10).compute("Atomic Number", Terminal.MAX).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalMAXstring() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").compute("Element Name", Terminal.MAX).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalSUMlong() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input1.tsv").compute("Age", Terminal.SUM).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalSUMstring() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input1.tsv").select("Zip Code", 33131).compute("Name", Terminal.SUM).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testOutlierLong() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input1.tsv").outlier("Age", 10).compute("Age", Terminal.MAX).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testOutlierString() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").outlier("Symbol", 5).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalFIRSTDIFF() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").compute("Group Number", Terminal.FIRSTDIFF).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalFIRSTDIFF2() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input1.tsv").compute("Name", Terminal.FIRSTDIFF).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
	
	public static void testTerminalInexistentHeader() {
		
		TSVFilter myTSVFilter = new TSVFilter.WhichFile("input_data2/input2.tsv").select("Group Number", 10).compute("Pizza", Terminal.MAX).done();
		
		new TSVPipeline(myTSVFilter).doit();
		
		System.out.println(myTSVFilter);
	}
}
