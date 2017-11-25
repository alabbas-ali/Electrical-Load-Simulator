package his.loadprofile.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import his.loadprofile.job.JobRunner;
import his.loadprofile.model.Household;
import his.loadprofile.model.LoadCurve;
import his.loadprofile.model.Measurement;

public class Simulator {

	// this parameters are important to check the simulation progress
	private static final int NUMBER_OF_SECOUNDS = 86400;
	private int totalNumberOfSimulation;
	private JobRunner jobRunner;

	public Simulator(int totalNumberOfSimulation, JobRunner jobRunner) {
		this.totalNumberOfSimulation = totalNumberOfSimulation;
		this.jobRunner = jobRunner;
	}

	public LoadCurve simulate(Household house, int currentHouseNumber) {
		
		List<Measurement> measurements = new ArrayList<Measurement>();
		Measurement measur;
		try {
			
			
//			Matrix powerMatrix = new Matrix(new double[Input.MAX_TIME_INTERVAL_COUNT][appList.size() + 3]);
//
//			Lighting light = new Lighting(occupancyValues);
//			light.setHouseNumber(bulbConfig);
//			int[] lightValues = light.getLightingValues();
//
//			int occupancy = 0;
//			int lightSum = 0;
//			int powerSum = 0;
//			int i = 0;
//			while (Appliance.hasNextTimeInterval()) {
//				occupancy = occupancyValues[i];
//				lightSum = lightValues[i];
//				powerMatrix.set(i, 0, occupancy);
//				for (int j = 0; j < appList.size(); j++) {
//					Appliance a = appList.get(j);
//					a.nextWattage(occupancy);
//					powerMatrix.set(i, j + 3, a.getPower());
//					powerSum += a.getPower();
//				}
//				powerMatrix.set(i, 1, powerSum + lightSum);
//				powerMatrix.set(i, 2, lightSum);
//				powerSum = 0;
//				i++;
//			}

			
			
			for (int i = 1; i <= NUMBER_OF_SECOUNDS; i++) {
				Thread.sleep(1);
				
				Float loadValue = (float) Math.sin(i);
				
				// @Todo calculations go here , the calculation should consider all the
				// configuration and the house
				// assign the value to loadValue 
				
				
				
				measur = new Measurement();
				measur.setTime(i);
				measur.setValue(loadValue);
				measurements.add(measur);
				
				// Send the status to front end
				jobRunner.progress
						.set((int) (((currentHouseNumber * i * 100) / (NUMBER_OF_SECOUNDS * totalNumberOfSimulation))
								+ ((100 / totalNumberOfSimulation) * (currentHouseNumber-1))));
				jobRunner.sendProgress();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LoadCurve loadCurve = new LoadCurve();
		loadCurve.setMeasurements(measurements);
		loadCurve.setCreationDate(new Date());
		loadCurve.setName("Sim_" + house.getSimName() + "_" + currentHouseNumber);
		loadCurve.setDescription("This is auto created curve for simulation " + 
				house.getSimName() + 
				"and household number " + 
				currentHouseNumber
			);
		return loadCurve;
	}

}
