/**
 * Class that implements the Computation interface and simply performs the NULL terminal computation
 * NullObject of the Computation interface, that does not perform any operation
 * @author Luiz do Valle
 *
 */
public class NullComputation implements Computation {

	@Override
	public void compute(String record) {
		
		
	}

	@Override
	public String result() {
		
		return "No terminal operations specified";
	}

	
}
