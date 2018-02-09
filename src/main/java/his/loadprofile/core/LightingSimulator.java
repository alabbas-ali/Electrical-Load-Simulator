package his.loadprofile.core;

import java.util.Random;

import his.loadprofile.core.Importers.LightingImporter;

public class LightingSimulator {
	
	private static final int NUMBER_OF_MINUTES = 1440;
	
	// this static parameters for Lighting calculation  
	private static final double CALIBRATION_SCALAR = 0.00815369;
	private static final double IRRADIANCE_IRRELEVANT_PROBABILITY = 0.5;
	private static final double[] EFFECTIVE_OCCUPANCY = { 0, 1, 1.528, 1.694, 1.983, 2.094 };
	private static final int[] LOWER_DURATION = { 1, 2, 3, 5, 9, 17, 28, 50, 92 };
	private static final int[] UPPER_DURATION = { 1, 2, 4, 8, 16, 27, 49, 91, 259 };
	private static final int HOUSE_COUNT = 100;
	private int[] bulbArray;
	private int[] irradiance;
	
	private Random rand = new Random();
	
	public LightingSimulator(LightingImporter lightingImporter) {
		bulbArray = lightingImporter.getBulbs(rand.nextInt(HOUSE_COUNT));
		irradiance = lightingImporter.getIrradiance();
	}
	
	
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
	public int[] simulateLightingLoad(int[] activeOccupancy) {
		
		int[] result = new int[NUMBER_OF_MINUTES];
		int irradianceTreshold = 60;

		for (int bulb = 0; bulb < bulbArray.length; bulb++) {
			int time = 0;
			while (time < NUMBER_OF_MINUTES) {

				// bulb switch on event
				boolean lowIrradiance = ((irradiance[time] < irradianceTreshold)
						|| (rand.nextDouble() < IRRADIANCE_IRRELEVANT_PROBABILITY));
				double effectiveOccupancy = EFFECTIVE_OCCUPANCY[activeOccupancy[time]];
				double relativeUseWeighting = -CALIBRATION_SCALAR * Math.log(rand.nextDouble());
				if (lowIrradiance && (rand.nextDouble() < (effectiveOccupancy * relativeUseWeighting))) {

					int lightDuration = determineDuration();

					// fill result
					for (int i = 0; i < lightDuration; i++) {
						if (time > NUMBER_OF_MINUTES - 1 || activeOccupancy[time] == 0)
							break;
						result[time] += bulbArray[bulb];
						time++;
					}
				} else // bulb remains off
					time++;
			}
		}
		return result;
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
		for (int j = 1; j <= 9; j++) {
			cumulative = j / 9;
			if (random1 < cumulative)
				return  (int) rand.nextDouble() * (UPPER_DURATION[j - 1] - LOWER_DURATION[j - 1]);
		}
		return (int) rand.nextDouble() *(UPPER_DURATION[0] - LOWER_DURATION[0]);
	}
}
