package his.loadprofile.core;

import java.util.ArrayList;
import java.util.List;

import his.loadprofile.model.SimConfig;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.Availability;
import his.loadprofile.model.Household;

public class SimRandomChoser {

	private SimConfig simConfig;

	public SimRandomChoser(SimConfig config) {
		this.simConfig = config;
	}

	public Household getRandomHousehold() {
		
		Household h = new Household();
		//h.
		return new Household();
	}
	

	private List<Availability> gerRandomAvailabilitiesList() {
		return new ArrayList<Availability>();
	}

	private List<Appliance> getRandomAppliancesList(){
		return new ArrayList<Appliance>();
	}
	

}
