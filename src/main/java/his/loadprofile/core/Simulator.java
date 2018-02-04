package his.loadprofile.core;

import java.util.ArrayList;
import java.util.List;

import his.loadprofile.job.JobRunner;
import his.loadprofile.model.Household;
import his.loadprofile.model.Measurement;

public class Simulator implements SimulatorInterface{

	// this parameters are important to check the simulation progress
	private static final int NUMBER_OF_SECOUNDS = 86400;
	private JobRunner jobRunner;

	public List<Measurement> simulate(Household house, int timeStep) {
		// Send WS HOUSEBIGEN status to front end
		jobRunner.state = "HOUSEBIGEN";
		jobRunner.sendProgress();
		
		List<Measurement> measurements =new ArrayList<Measurement>();
		Measurement measur;		
		
		for (int i = 1; i <= NUMBER_OF_SECOUNDS; i += timeStep * 60) {
					
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			measur = new Measurement();
			measur.setTime(i);
			
			Float loadValue = (float) Math.sin(i);
				
			// @Todo calculations go here , the calculation should consider all the
			// configuration and the house
			// assign the value to loadValue 
			
			measur.setValue(loadValue);
			measurements.add(measur);
				
			// Send WS HOUSESTATE status to front end
			jobRunner.state = "HOUSESTATE";
			jobRunner.progress.set((i*100)/NUMBER_OF_SECOUNDS);
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
	
	
//	Matrix powerMatrix = new Matrix(new double[Input.MAX_TIME_INTERVAL_COUNT][appList.size() + 3]);
//
//	Lighting light = new Lighting(occupancyValues);
//	light.setHouseNumber(bulbConfig);
//	int[] lightValues = light.getLightingValues();
//
//	int occupancy = 0;
//	int lightSum = 0;
//	int powerSum = 0;
//	int i = 0;
//	while (Appliance.hasNextTimeInterval()) {
//		occupancy = occupancyValues[i];
//		lightSum = lightValues[i];
//		powerMatrix.set(i, 0, occupancy);
//		for (int j = 0; j < appList.size(); j++) {
//			Appliance a = appList.get(j);
//			a.nextWattage(occupancy);
//			powerMatrix.set(i, j + 3, a.getPower());
//			powerSum += a.getPower();
//		}
//		powerMatrix.set(i, 1, powerSum + lightSum);
//		powerMatrix.set(i, 2, lightSum);
//		powerSum = 0;
//		i++;
//	}

}
