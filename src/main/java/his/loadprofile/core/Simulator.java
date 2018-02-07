package his.loadprofile.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import his.loadprofile.core.Importers.LightingImporter;
import his.loadprofile.job.JobRunner;
import his.loadprofile.model.Activity;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.Availability;
import his.loadprofile.model.Household;
import his.loadprofile.model.Measurement;
import his.loadprofile.model.OperationalMode;

public class Simulator implements SimulatorInterface{
	
	private static final int NUMBER_OF_MINUTES = 1440;
	Random rand = new Random();
	private HashMap<ApplianceType, Integer> applianceOP = new HashMap<ApplianceType, Integer>();
	
	// this parameters are important to check the simulation progress
	private JobRunner jobRunner;
	
	// this static parameters for Lighting calculation  
	private static final double CALIBRATION_SCALAR = 0.00815369;
	private static final double IRRADIANCE_IRRELEVANT_PROBABILITY = 0.5;
	private static final double[] EFFECTIVE_OCCUPANCY = { 0, 1, 1.528, 1.694, 1.983, 2.094 };
	private static final int[] LOWER_DURATION = { 1, 2, 3, 5, 9, 17, 28, 50, 92 };
	private static final int[] UPPER_DURATION = { 1, 2, 4, 8, 16, 27, 49, 91, 259 };
	private static final int HOUSE_COUNT = 100;
	private static final int irradianceTreshold = 60;
	int[] bulbArray;
	int[] irradiance;
	
	
	public Simulator(LightingImporter lightingImporter) {
		// this is for lighting Simulation
		bulbArray = lightingImporter.getBulbs(rand.nextInt(HOUSE_COUNT));
		irradiance = lightingImporter.getIrradiance();
	}
	
	public List<Measurement> simulate(Household house, int timeStep) {
		// Send WS HOUSEBIGEN status to front end
		jobRunner.state = "HOUSEBIGEN";
		jobRunner.sendProgress();
		
		List<Measurement> measurements =new ArrayList<Measurement>();
		Measurement measur;
		OperationalMode op;
		
		
		int fridgeWorkDuration = 0;
		boolean fridgeOn = true;
		int fridgeFreezerWorkDuration = 0;
		boolean fridgeFreezerOn = true;
		int chestFreezerWorkDuration = 0;
		boolean chestFreezerOn = true;
		
		for (int i = 1; i <= NUMBER_OF_MINUTES; i += timeStep) {
			measur = new Measurement();
			measur.setTime(i);
			
			Float loadValue = (float) 0.00;
			Calendar calendar = Calendar.getInstance();
		    
			//Active Occupancy "the number of people in Household which are not asleep"
			int activeOccupancy = 0;
			
			for (Availability availability : house.getAvailabilities()) 
			{
				for (Activity activity : availability.getActivities()) 
				{
					List<ApplianceType> applianceTypeList = activity.getType().asApplianceType();
					calendar.setTime(activity.getStart());
					int activity_start = (calendar.get(Calendar.HOUR_OF_DAY)*60) + calendar.get(Calendar.MINUTE);
					calendar.setTime(activity.getEnd());
					int activity_end = (calendar.get(Calendar.HOUR_OF_DAY)*60) + calendar.get(Calendar.MINUTE);
					
					for (Appliance appliance : house.getAppliances()) {
						
						op = this.getApplianceChaosenOP(appliance);
						
						// add activity appliances load
						if(
							applianceTypeList.contains(appliance.getType()) &&
							activity_start <= i && activity_end > i
						) {
							// other calculation in case the op has Load Curve should be done here
							// foe now we will suppose that all the appliance OP has no Load Curve
							
							loadValue += op.getPowerInputOn();
						}
						
						// add special appliances load
						switch (appliance.getType()) {
							case APPLIANCE_FRIDGE:
								if(fridgeWorkDuration > op.getDuration()) {
									fridgeWorkDuration = 0;
								}
								if(fridgeOn) {
									loadValue += op.getPowerInputOn();
								}else {
									loadValue += op.getPowerInputOff();
								}
								fridgeWorkDuration ++;
								break;
							case APPLIANCE_FRIDGE_FREEZER:
								if(fridgeFreezerWorkDuration > op.getDuration()) {
									fridgeFreezerWorkDuration = 0;
								}
								if(fridgeFreezerOn) {
									loadValue += op.getPowerInputOn();
								}else {
									loadValue += op.getPowerInputOff();
								}
								fridgeFreezerWorkDuration ++;
								break;
							case APPLIANCE_CHEST_FREEZER:
								if(chestFreezerWorkDuration > op.getDuration()) {
									chestFreezerWorkDuration = 0;
								}
								if(chestFreezerOn) {
									loadValue += op.getPowerInputOn();
								}else {
									loadValue += op.getPowerInputOff();
								}
								chestFreezerWorkDuration ++;
								break;
							case APPLIANCE_HEATING:
								chestFreezerWorkDuration ++;
								loadValue += op.getPowerInputOn();
								break;
							default:
								break;
						}
						
						// calculate Active Occupancy for time i
						//if( ) {
							
						///}
					}
				}
			}
			
			loadValue += (float) lightingLoad(i, activeOccupancy);
			
			measur.setValue(loadValue);
			measurements.add(measur);
			
			// Send WS HOUSESTATE status to front end
			jobRunner.state = "HOUSESTATE";
			jobRunner.progress.set((i/NUMBER_OF_MINUTES)*100);
			jobRunner.sendProgress();
		}
		
		// Send WS HOUSEFINISH status to front end
		jobRunner.state = "HOUSEFINISH";
		jobRunner.sendProgress();
		return measurements;
	}

	public void setJobRunner(JobRunner jobRunner) {
		this.jobRunner = jobRunner;
	}
	
	//Calculate the lighting load at current moment
	/**
	 * The main iteration goes through all bulbs that have been chosen (house
	 * number). Then, it is iterated for each bulb through time, at each minute
	 * deciding whether a switch-on event should happen using e.g. time of day
	 * (irradiance) and effective occupancy as input. See
	 * https://dspace.lboro.ac.uk/2134/4759 for further detail. If a bulb is
	 * switched on, next, the duration of the event is calculated and the power
	 * value of the current bulb added to the final result.
	 * 
	 * @return 1440 power values [W] representing the lighting demand of a
	 *         dwelling.
	 */
	private int lightingLoad(int time, int activeOccupancy) {
		int lighting = 0;
		for (int bulb = 0; bulb < bulbArray.length; bulb++) {
			// bulb switch on event
			boolean lowIrradiance = ((irradiance[time] < irradianceTreshold) || (rand.nextDouble() < IRRADIANCE_IRRELEVANT_PROBABILITY));
			double effectiveOccupancy = EFFECTIVE_OCCUPANCY[activeOccupancy];
			double relativeUseWeighting = -CALIBRATION_SCALAR * Math.log(rand.nextDouble());
			if (lowIrradiance && (rand.nextDouble() < (effectiveOccupancy * relativeUseWeighting))) {
				int lightDuration = determineDuration();
				// fill result
				for (int k = 0; k < lightDuration; k++) {
					if (time > NUMBER_OF_MINUTES - 1 || activeOccupancy == 0)
						break;
					lighting += bulbArray[bulb];
				}
			}
		}
		return lighting;
	}
	
	/**
	 * Method to terminate the duration of a bulb staying turned on. For further
	 * detail see https://dspace.lboro.ac.uk/2134/4759.
	 * 
	 * @return and integer representing the time [min] a bulb should be turned on for.
	 */
	private int determineDuration() {
		double random1 = rand.nextDouble();
		double cumulative = 0;
		for (int i = 1; i <= 9; i++) {
			cumulative = i / 9;
			if (random1 < cumulative)
				return (int) rand.nextDouble() * (UPPER_DURATION[i - 1] - LOWER_DURATION[i - 1]);
		}
		return (int) rand.nextDouble() *(UPPER_DURATION[0] - LOWER_DURATION[0]);
	}
	
	private OperationalMode getApplianceChaosenOP(Appliance appliance) {
		int index;
		if(applianceOP.get(appliance.getType()) != null) {
			index = applianceOP.get(appliance.getType());
		}else {
			index = rand.nextInt(appliance.getOperationalModes().size());
			applianceOP.put(appliance.getType(), index);
		}
		return appliance.getOperationalModes().get(index);
	}

}
