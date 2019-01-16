/**
 * Class that implements the Computation interface and simply performs the COUNT terminal computation
 * Counts how many records have been output
 * @author Luiz do Valle
 *
 */
public class CountComputation implements Computation {

	private int count;
	
	@Override
	public void compute(String record) {
		
		count++;
	}

	@Override
	public String result() {
		
		return String.valueOf(count);
	}
	
	

}
