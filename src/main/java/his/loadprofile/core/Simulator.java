package his.loadprofile.core;


import his.loadprofile.model.Household;
import his.loadprofile.model.SimConfig;
import his.loadprofile.model.SimResult;

public class Simulator {

	private SimConfig simConfig;

	public Simulator(SimConfig config) {
		this.simConfig = config;
	}

	
	public SimResult simulate(Household house) {
		
		// calculations go here
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SimResult();
	}

}
