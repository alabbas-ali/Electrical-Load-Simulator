package his.loadprofile.core;

import java.util.ArrayList;
import java.util.List;

import his.loadprofile.model.Household;
import his.loadprofile.model.SimConfig;
import his.loadprofile.model.SimResult;

public class Simulator {

	private SimConfig simConfig;

	public Simulator(SimConfig config) {
		this.simConfig = config;
	}

	public List<SimResult> simulate() {
		SimRandomChooser randomchouser = new SimRandomChooser(this.simConfig);
		Household household;
		List<SimResult> simResult = new ArrayList<SimResult>();
		
		for (int i = 0; i < this.simConfig.getNumberOfHouses(); i++) {
			household = randomchouser.getRandomHousehold();
			simResult.add(this.simulate(household));
		}
		return simResult;
	}
	
	private SimResult simulate(Household house) {
		
		// calculations go here
		
		return new SimResult();
	}

}
