package his.loadprofile.job;

import java.util.ArrayList;
import java.util.List;

import his.loadprofile.model.SimConfig;
import his.loadprofile.core.HouseHoldType;
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
		house.setSimName(simConfig.getName());
		house.setType(this.gerRandomHouseHoldType());
		house.setAppliances(this.getRandomAppliancesList());
		house.setAvailabilities(this.gerRandomAvailabilitiesList());
		
		//@Todo set any other Household property randomly
		
		return house;
	}
	
	private HouseHoldType gerRandomHouseHoldType() {
		
		//@Todo use simConfig to chose HouseholdType
		
		return HouseHoldType.HOUSEHOLD_FAMILE;
	}

	private List<Availability> gerRandomAvailabilitiesList() {
		
		//@Todo use simConfig to create Availabilities in Household
		simConfig.getMaxNumberOfPeople();
		List<Availability> availabilities = new ArrayList<Availability>();
		Availability av = new Availability();
		av.setName("Test_av");
		availabilities.add(av);
		
		return availabilities;
	}

	private List<Appliance> getRandomAppliancesList(){
		
		//@Todo use simConfig to create Appliances in Household
		List<Appliance> appliances = new ArrayList<Appliance>();
		Appliance ap = new Appliance();
		ap.setName("Test_ap");
		appliances.add(ap);
		return appliances;
	}
	
}
