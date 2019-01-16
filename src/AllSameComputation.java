/**
 * Class that implements the Computation interface and simply performs the ALLSAME terminal computation
 * Discovers whether all the output records are exactly the same
 * @author Luiz do Valle
 *
 */
public class AllSameComputation implements Computation {

	private boolean allSame = true;
	private String previousRecord = null;
	
	@Override
	public void compute(String record) {
		
		if(previousRecord == null) {
			
			previousRecord = record;
		
		} 			
		
		if(!record.equals(previousRecord)) {
				
			allSame = false;
		}
		
		
	}

	@Override
	public String result() {
		
		return String.valueOf(allSame);
		
	}

	
}
