/**
 * Interface that serves as a blueprint for the the classes that perform a computation
 * @author Luiz do Valle
 *
 */
public interface Computation {

	/**
	 * Method that performs a computation
	 * @param record
	 */
	public abstract void compute(String record);
	
	/**
	 * Method that returns the result of the computation
	 * @return the result of the computation
	 */
	public abstract String result();
	
}
