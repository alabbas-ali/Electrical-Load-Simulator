package his.loadprofile.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import his.loadprofile.model.SimConfig;
import his.loadprofile.core.HouseHoldType;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.Availability;
import his.loadprofile.model.Household;

public class RandomHouesCreator implements HouesCreator {

	private SimConfig simConfig;

	public RandomHouesCreator(SimConfig config) {
		this.simConfig = config;
	}

	public Household getHousehold() {
		
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
		av.setName(this.getSaltString());
		availabilities.add(av);
		
		return availabilities;
	}

	private List<Appliance> getRandomAppliancesList(){
		
		//@Todo use simConfig to create Appliances in Household
		List<Appliance> appliances = new ArrayList<Appliance>();
		Appliance ap = new Appliance();
		ap.setName(this.getSaltString());
		appliances.add(ap);
		return appliances;
	}
	
	private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
}
