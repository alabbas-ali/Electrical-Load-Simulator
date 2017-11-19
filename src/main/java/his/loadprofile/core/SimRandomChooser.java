package his.loadprofile.core;

import java.util.ArrayList;
import java.util.List;

import his.loadprofile.model.SimConfig;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.Availability;
import his.loadprofile.model.Household;

public class SimRandomChooser {

	private SimConfig simConfig;

	public SimRandomChooser(SimConfig config) {
		this.simConfig = config;
	}

	public Household getRandomHousehold() {
		
		Household house = new Household();
		house.setType(this.gerRandomHouseHoldType());
		house.setAppliances(this.getRandomAppliancesList());
		house.setAvailabilities(this.gerRandomAvailabilitiesList());
		// set any other Household property randomly
		return house;
	}
	
	private HouseHoldType gerRandomHouseHoldType() {
		
		// use simConfig to chose HouseholdType
		
		return HouseHoldType.HOUSEHOLD_FAMILE;
	}

	private List<Availability> gerRandomAvailabilitiesList() {
		
		// use simConfig to create Availabilities in Household
		return new ArrayList<Availability>();
	}

	private List<Appliance> getRandomAppliancesList(){
		
		// use simConfig to create Appliances in Household
		
		return new ArrayList<Appliance>();
	}
	

}
