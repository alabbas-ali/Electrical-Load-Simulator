package his.loadprofile.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import his.loadprofile.model.SimConfig;
import his.loadprofile.repo.ApplianceRepository;
import his.loadprofile.repo.AvailabilityRepository;
import his.loadprofile.core.ApplianceType;
import his.loadprofile.core.AvailabilityType;
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
			Availability v = availabilityRepository.findOneRandomlyByType(AvailabilityType.AVAILABILITY_WORKER);
			if(v != null)
				availabilities.add(v);
			v = availabilityRepository.findOneRandomlyByType(AvailabilityType.AVAILABILITY_UNWORKER);
			if(v != null)
				availabilities.add(v);
			
			for(int i = minimum; i < randomNumber; i ++) 
			{
				v = availabilityRepository.findOneRandomlyByType(AvailabilityType.AVAILABILITY_CHILD);
				if(v != null)
					availabilities.add(v);
				
			}
		} else {
			Availability v = availabilityRepository.findOneRandomlyByType(AvailabilityType.AVAILABILITY_WORKER);
			if(v != null)
				availabilities.add(v);
		}
		
		return availabilities;
	}

	private List<Appliance> getRandomAppliancesList()
	{	
		List<Appliance> appliances = new ArrayList<Appliance>();
		for (ApplianceType apType : ApplianceType.values()) {
			Appliance a = applianceRepository.findOneRandomlyByType(apType);
			if(a != null)
				appliances.add(a);
		}
		return appliances;
	}
	
	
}

