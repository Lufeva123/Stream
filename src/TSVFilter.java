import java.awt.GraphicsDevice.WindowTranslucency;
import java.io.File;
/**
 * Class that records the user's requirements
 * Default value for selection is the "normal selection", with no headers or fields (no filter)
 * Default value for terminal computation is the NULL operation, which does nothing
 * @author Luiz do Valle
 *
 */
public class TSVFilter {

	/**
	 * The file to be read
	 */
	private final File file;
	
	/**
	 * The header to be used for the selection algorithm
	 */
	private final String selectHeader;
	/**
	 * The record field to be used by the selection algorithm
	 */
	private final String recordField;
	/**
	 * The type of selection being done
	 */
	private final SelectionType selection;
	/**
	 * The error allowed in case the user selects the "outlier" selection algorithm
	 */
	private final long error;
	/**
	 * The header to be used for the terminal computation
	 */
	private final String computeHeader;
	/**
	 * The type of terminal computation to be performed
	 */
	private final Terminal computation;
	
	/**
	 * Inner static class that serves as the builder for the TSVFilter class
	 * @author Luiz do Valle
	 *
	 */
	public static class WhichFile {
		
		private File file;
		
		private String selectHeader = "";
		private String recordField = null;
		private SelectionType selection = SelectionType.NORMAL;
		private long error;
		
		private String computeHeader = "";
		private Terminal computation = Terminal.NULL;
		
		public WhichFile (String fileName) {
			
			this.file = new File (fileName);
			
		}
		
		/**
		 * Method that records the filters the user wants to filter the data with
		 * Selection based on String value
		 * Allows longs to be passed as String if the user wants
		 * @param columnHeader the header to filter by
		 * @param field the field that must be presents under the specified header
		 * @return a reference to the current WhichFile Builder
		 */
		public WhichFile select (String columnHeader, String field) {
			
			this.selectHeader = columnHeader;
			this.recordField = field;
			this.selection = SelectionType.NORMAL;
			
			return this;
		}
		
		/**
		 * Method that records the filters the user wants to filter the data with
		 * Selection based on long value
		 * @param columnHeader the header to filter by
		 * @param field the field that must be presents under the specified header
		 * @return a reference to the current WhichFile Builder
		 */
		public WhichFile select (String columnHeader, long field) {
			
			this.selectHeader = columnHeader;
			this.recordField = String.valueOf(field);
			this.selection = SelectionType.NORMAL;
			
			return this;
		}
		
		/**
		 * Method that records the filters the user wants to filter the data with
		 * Selection based on outlier value
		 * @param header the header to filter by
		 * @param error the margin of error that the values must exceed the previous record by to be selected by the filter
		 * @return a reference to the current WhichFile Builder
		 */
		public WhichFile outlier (String header, long error) {
			
			this.selectHeader = header;
			this.selection = SelectionType.OUTLIER;
			this.error = error;
			
			return this;
			
		}
		
		/**
		 * Method that records the terminal computation the user wants to perform and on which header value
		 * @param columnHeader the header to which the terminal compuatation should be applied
		 * @param computation the type of Computation to be performed
		 * @return a reference to the current WhichFile builder
		 */
		public WhichFile compute(String columnHeader, Terminal computation) {
			
			this.computeHeader = columnHeader;
			this.computation = computation;
			
			return this;
		}
		
		/**
		 * Method that creates a TSVFilter from the WhichFile builder
		 * @return a new TSVFilter with the user filters stored in the WhichFile builder
		 */
		public TSVFilter done() {
			
			return new TSVFilter(this);
		}
	}
	
	/**
	 * Private constructor of the TSVFilter that is only to be used by the Builder class WhichFile
	 * @param whichFile
	 */
	private TSVFilter(WhichFile whichFile) {
		
		this.file = whichFile.file;
		
		this.selectHeader = whichFile.selectHeader;
		this.recordField = whichFile.recordField;
		this.selection = whichFile.selection;
		this.error = whichFile.error;
		
		this.computeHeader = whichFile.computeHeader;
		this.computation = whichFile.computation;
		
	}

	/**
	 * Accessor method for the file
	 * @return the file to be read
	 */
	public File getFile() {
		
		return file;
	}

	/**
	 * Accessor method for the reocord
	 * @return the record field to be used as a filter
	 */
	public String getRecordField() {
		return recordField;
	}
	
	/**
	 * Accessor method for the Select header
	 * @return the header to be used as a filter
	 */
	public String getSelectHeader() {
		return selectHeader;
	}
	
	/**
	 * Accessor method for the type of selection algorithm to be used
	 * @return the selection algorithm to be used as an Enum
	 */
	public SelectionType getSelection() {
		return selection;
	}
	
	/**
	 * Accessor method for the error to be used in the outlier algorithm
	 * @return the margin of error of the outlier algorithm
	 */
	public long getError() {
		return error;
	}
	
	/**
	 * Acessor method for the header to be used in the terminal computation
	 * @return the header to be used in the computation
	 */
	public String getComputeHeader() {
		return computeHeader;
	}

	/**
	 * Accessor method for the type of terminal computation to be performed
	 * @return the type of Computation to be performed as an Enum
	 */
	public Terminal getComputation() {
		return computation;
	}

	/**
	 * Overriden toString() implementation that prints the filters used in the streaming
	 * if no filters were used, a message saying this to the user is displayed instead
	 */
	@Override
	public String toString() {
		
		StringBuilder filters = new StringBuilder();
		
		if(!selectHeader.equals("") && recordField != null) {
			
			filters.append(selectHeader + " = " + recordField + "\n");
		}
		
		if(!computeHeader.equals("") && computation != Terminal.NULL) {
			
			filters.append(computation.name() + " = " + computeHeader);
		}
		
		if(filters.length() == 0) {
			
			filters.append("No filters selected");
		}
		
		return filters.toString();
	}
}
