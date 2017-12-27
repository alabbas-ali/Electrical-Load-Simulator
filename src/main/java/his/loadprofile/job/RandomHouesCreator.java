package his.loadprofile.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import his.loadprofile.model.SimConfig;
import his.loadprofile.repo.ApplianceRepository;
import his.loadprofile.repo.AvailabilityRepository;
import his.loadprofile.core.ApplianceType;
import his.loadprofile.core.HouseHoldType;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.Availability;
import his.loadprofile.model.Household;

public class RandomHouesCreator implements HouesCreator {

	private SimConfig simConfig;
	
	@Autowired
	ApplianceRepository applianceRepository;
	
	@Autowired
	AvailabilityRepository availabilityRepository;

	public void setSimConfig(SimConfig config) {
		this.simConfig = config;
	}

	public Household getHousehold() 
	{	
		Household house = new Household();
		house.setSimName(simConfig.getName());
		house.setType(this.gerRandomHouseHoldType());
		house.setAppliances(this.getRandomAppliancesList());
		house.setAvailabilities(this.gerRandomAvailabilitiesList(house.getType()));
		return house;
	}
	
	private HouseHoldType gerRandomHouseHoldType() 
	{
		Random r = new Random();
		int randomNumber = r.nextInt(100);
		if(randomNumber <= simConfig.getSinglesPercentage()) {
			return HouseHoldType.HOUSEHOLD_SINGLE;
		}
		return HouseHoldType.HOUSEHOLD_FAMILE;
	}

	private List<Availability> gerRandomAvailabilitiesList(HouseHoldType hType) 
	{	
		List<Availability> availabilities = new ArrayList<Availability>();
		
		if(hType == HouseHoldType.HOUSEHOLD_FAMILE) {
			int minimum = simConfig.getMinNumberOfPeople();
			int maximum = simConfig.getMaxNumberOfPeople();
			Random r = new Random();
			int n = maximum - minimum + 1;
			int k = r.nextInt() % n;
			int randomNumber = minimum + k;
			for(int i = minimum; i < randomNumber; i ++) {
				availabilities.add(availabilityRepository.findOne("Create Find Random"));
			}
		} else {
			availabilities.add(availabilityRepository.findOne("Create Find Random"));
		}
		
		return availabilities;
	}

	private List<Appliance> getRandomAppliancesList()
	{	
		List<Appliance> appliances = new ArrayList<Appliance>();
		for (ApplianceType apType : ApplianceType.values()) {
			appliances.add(applianceRepository.findCustomByType(apType));
		}
		return appliances;
	}
	
//	private String getSaltString() 
//	{
//		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
//		StringBuilder salt = new StringBuilder();
//		Random rnd = new Random();
//        while (salt.length() < 18) { // length of the random string.
//            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
//            salt.append(SALTCHARS.charAt(index));
//        }
//        String saltStr = salt.toString();
//        return saltStr;
//    }
	
}

