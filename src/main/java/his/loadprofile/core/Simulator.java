package his.loadprofile.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import his.loadprofile.job.JobRunner;
import his.loadprofile.model.Activity;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.Availability;
import his.loadprofile.model.Household;
import his.loadprofile.model.Measurement;
import his.loadprofile.model.OperationalMode;

public class Simulator implements SimulatorInterface{
	
	private static final int NUMBER_OF_MINUTES = 1440;
	
	private HashMap<ApplianceType, Integer> applianceOP = new HashMap<ApplianceType, Integer>();
	
	// this parameters are important to check the simulation progress
	private JobRunner jobRunner;
	
	//Active Occupancy "the number of people in Household which are not asleep"
	private int[] activeOccupancy;
	private int[] occupancy;
	
	private LightingSimulator lightingSimulator;
	
	//
	private int fridgeWorkDuration = 0;
	private boolean fridgeOn = true;
	private int fridgeFreezerWorkDuration = 0;
	private boolean fridgeFreezerOn = true;
	private int chestFreezerWorkDuration = 0;
	private boolean chestFreezerOn = true;
	
	public Simulator(LightingSimulator lightingSimulator) {
		// this is for lighting Simulation
		this.lightingSimulator = lightingSimulator;
	}
	
	public List<Measurement> simulate(Household house, int timeStep) {
		
		// Send WS HOUSEBIGEN status to front end
		this.sendMessageToForntend("HOUSEBIGEN",0);
		
		//full the activeOccupancy array
		creatOccupancyArrays(house);
		
		//full the lighting load array
		int[] lightingValues = lightingSimulator.simulateLightingLoad(activeOccupancy);
		
		List<Measurement> measurements = new ArrayList<Measurement>();
		Measurement measur;
		float loadValue = 0;
		OperationalMode op;
		
		for (int i = 0; i < NUMBER_OF_MINUTES; i += timeStep) 
		{
			measur = new Measurement();
			measur.setTime(i);
			
			// add activity appliances load
			loadValue = activityAppliancesLoad(house , i);
			
			// add special appliances load
			for (Appliance appliance : house.getAppliances()) {
				
				op = this.getApplianceChaosenOP(appliance);
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
			}
			
			loadValue += lightingValues[i];
			
			measur.setValue(loadValue);
			measur.setLightingValue(lightingValues[i]);
			measur.setActiveOccupancy(activeOccupancy[i]);
			measur.setOccupancy(occupancy[i]);
			measurements.add(measur);
			
			// Send WS HOUSESTATE status to front end
			this.sendMessageToForntend("HOUSESTATE", (i/NUMBER_OF_MINUTES)*100);
		}
		
		// Send WS HOUSEFINISH status to front end
		this.sendMessageToForntend("HOUSEFINISH", 100);
		return measurements;
	}

	public void setJobRunner(JobRunner jobRunner) {
		this.jobRunner = jobRunner;
	}
	
	private OperationalMode getApplianceChaosenOP(Appliance appliance) {
		int index;
		Random rand = new Random();
		if(applianceOP.get(appliance.getType()) != null) {
			index = applianceOP.get(appliance.getType());
		}else {
			index = rand.nextInt(appliance.getOperationalModes().size());
			applianceOP.put(appliance.getType(), index);
		}
		return appliance.getOperationalModes().get(index);
	}
	
	private void sendMessageToForntend(String message, int progress) {
		jobRunner.state = "HOUSEBIGEN";
		jobRunner.progress.set(progress);
		jobRunner.sendProgress();
	}
	
	private void creatOccupancyArrays(Household house) 
	{
		this.activeOccupancy = new int[NUMBER_OF_MINUTES];
		this.occupancy = new int[NUMBER_OF_MINUTES];
		
		for(int i = 0; i < activeOccupancy.length; i++) {
			this.activeOccupancy[i] = 0;
			this.occupancy[i] = 0;
		}
		
		Calendar calendar = Calendar.getInstance();
		for (Availability availability : house.getAvailabilities()) 
		{
			
			int availability_Wakup_time = -1;
			int availability_Sleep_time = -1;
			int[] availability_Out_times = {-1,-1,-1,-1,-1,-1,-1,-1};
			int[] availability_back_times = {-1,-1,-1,-1,-1,-1,-1,-1};
			int outIndex = 0;
			int inIndex = 0;
			
			for (Activity activity : availability.getActivities()) 
			{
				calendar.setTime(activity.getStart());
				int activity_start = (calendar.get(Calendar.HOUR_OF_DAY)*60) + calendar.get(Calendar.MINUTE);
				
				switch (activity.getType()) {
					case ACTIVITY_WAKUP:
						availability_Wakup_time = activity_start;
						break;
					case ACTIVITY_SLEEP:
						availability_Sleep_time = activity_start;
						break;
					case ACTIVITY_GOOUT:
						availability_Out_times[outIndex] = activity_start;
						outIndex ++;
						break;
					case ACTIVITY_BACK_HOME:
						availability_back_times[inIndex] = activity_start;
						inIndex++;
						break;
					default:
						break;
				}
			}

			for (int i = 0; i < NUMBER_OF_MINUTES; i ++) {
				activeOccupancy[i] = 0;
				boolean athome = true;
				for(int j=0; j < availability_Out_times.length; j++ ) {
					if(
						(availability_Out_times[j] != -1 && availability_Out_times[j] < i ) && 
						(availability_back_times[j] != -1  && i < availability_back_times[j])
					) {
						athome = false;
					}
				}

				if(
					availability_Wakup_time != -1 && availability_Wakup_time < i && 
					availability_Sleep_time != -1 && i < availability_Sleep_time &&
					athome
				) {
					
					activeOccupancy[i] ++;
				}
				
				if(athome) {
					occupancy[i] ++;
				}
			}
			
			System.out.println("Availability " + availability.getType());
			for(int i = 0; i < activeOccupancy.length; i++) {
				System.out.print(activeOccupancy[i] + " ,");
			}
		}
	}
	
	
	private float activityAppliancesLoad(Household house, int time) {
		
		OperationalMode op;
		float loadValue = 0;
		Calendar calendar = Calendar.getInstance();
		for (Availability availability : house.getAvailabilities()) {
			for (Activity activity : availability.getActivities()) {
				List<ApplianceType> applianceTypeList = activity.getType().asApplianceType();
				calendar.setTime(activity.getStart());
				int activity_start = (calendar.get(Calendar.HOUR_OF_DAY)*60) + calendar.get(Calendar.MINUTE);
				calendar.setTime(activity.getEnd());
				int activity_end = (calendar.get(Calendar.HOUR_OF_DAY)*60) + calendar.get(Calendar.MINUTE);
				
				for (Appliance appliance : house.getAppliances()) {
					op = this.getApplianceChaosenOP(appliance);
					
					if(
						applianceTypeList != null &&
						applianceTypeList.contains(appliance.getType()) &&
						activity_start <= time && activity_end > time
					) {
						// other calculation in case the op has Load Curve should be done here
						// foe now we will suppose that all the appliance OP has no Load Curve
						loadValue += op.getPowerInputOn();
					}
				}
			}
		}
		
		return loadValue;
	}

}
