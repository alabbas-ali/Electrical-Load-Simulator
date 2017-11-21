package his.loadprofile.core;


import his.loadprofile.model.Household;
import his.loadprofile.model.SimResult;

public class Simulator {

	
	public SimResult simulate(Household house) {
		
		//@Todo calculations go here , the calculation should conseder all the configuration and the house
		
		try {
			Thread.sleep(400000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SimResult();
	}

}
