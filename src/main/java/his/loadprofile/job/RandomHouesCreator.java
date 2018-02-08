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
import his.loadprofile.model.Activity;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.Availability;
import his.loadprofile.model.Household;

public class RandomHouesCreator implements HouesCreator {

	private SimConfig simConfig;
	
	@Autowired
	private ApplianceRepository applianceRepository;
	
	@Autowired
	private AvailabilityRepository availabilityRepository;
	
	private Household house;

	public void setSimConfig(SimConfig config) {
		this.simConfig = config;
	}

	public Household getHousehold() 
	{	
		house = new Household();
		house.setSimName(simConfig.getName());
		house.setType(this.gerRandomHouseHoldType());
		house.setAvailabilities(this.gerRandomAvailabilitiesList(house.getType()));
		house.setAppliances(this.getRandomAppliancesList());
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
		Availability v = null;
		Random r = new Random();
		
		if(hType == HouseHoldType.HOUSEHOLD_FAMILE) {
			int minimum = simConfig.getMinNumberOfPeople();
			int maximum = simConfig.getMaxNumberOfPeople();
			int n = maximum - minimum + 1;
			int k = r.nextInt() % n;
			int randomNumber = minimum + k;
			// add Father availabilities
			while (v == null) {
				v = availabilityRepository.findOneRandomlyByType(AvailabilityType.AVAILABILITY_WORKER);
			}
			availabilities.add(v);
			// add children availabilities
			v = null;
			while (v == null) {
				v = availabilityRepository.findOneRandomlyByType(AvailabilityType.AVAILABILITY_NON_WORKER);
			}
			availabilities.add(v);
			// add children availabilities
			for(int i = minimum; i < randomNumber; i ++) 
			{
				v = null;
				while (v == null) {
					v = availabilityRepository.findOneRandomlyByType(AvailabilityType.AVAILABILITY_CHILD);
				}
				availabilities.add(v);
			}
		} else {
			while (v == null) {
				v = availabilityRepository.findOneRandomlyByType(AvailabilityType.AVAILABILITY_WORKER);
			}
			availabilities.add(v);
		}
		
		return availabilities;
	}

	private List<Appliance> getRandomAppliancesList()
	{	
		List<Appliance> appliances = new ArrayList<Appliance>();
		Appliance a;
		List<ApplianceType> typeList;
		
		for (Availability availability : this.house.getAvailabilities()) 
		{
			for (Activity activity : availability.getActivities()) 
			{
				typeList = activity.getType().asApplianceType();
				if(typeList != null) {
					for (ApplianceType apType : typeList) 
					{
						a = applianceRepository.findOneRandomlyByType(apType);
						if(a != null)
							appliances.add(a);
					}
				}
			}
			
		}
		
		// the static list of Appliances which always work in the Household
		a = applianceRepository.findOneRandomlyByType(ApplianceType.APPLIANCE_FRIDGE);
		if(a != null)
			appliances.add(a);
		
		a = applianceRepository.findOneRandomlyByType(ApplianceType.APPLIANCE_HEATING);
		if(a != null)
			appliances.add(a);
		
		Random r = new Random();
		if(r.nextInt(100) < 5) {
			a = applianceRepository.findOneRandomlyByType(ApplianceType.APPLIANCE_FRIDGE_FREEZER);
			if(a != null)
				appliances.add(a);
		}
		
		if(r.nextInt(100) < 5) {
			a = applianceRepository.findOneRandomlyByType(ApplianceType.APPLIANCE_CHEST_FREEZER);
			if(a != null)
				appliances.add(a);
		}
		
		return appliances;
	}
	
}