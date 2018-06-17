/**
 * Copyright (C) 2016 Lukas Wiederhold
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE@.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/. 
 **/

package de.frauas.jdemandmodel.lighting;

import java.util.ArrayList;

import de.frauas.jdemandmodel.occupancy.OccupancyModeler;
import de.frauas.jdemandmodel.reader.CSVReader;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.Matrix;
import de.frauas.jdemandmodel.util.RandomUtil;

/**
 * The class Lighting can be used to simulate the lighting of a dwelling for
 * 1440 1-minute time intervals of a day. The only data that need to be provided
 * is the occupancy of the dwelling ({@link OccupancyModeler}). Also, an
 * optional bulb configuration can be set by choosing a house number between 1
 * and 100 (representing 100 sample households). Choosing -1 will result in a
 * random configuration and 0 turns the light off (returns array of length 1440
 * filled with zeros).
 * <p>
 * The content is a direct translation of the "RunLightingSimulation" macro of
 * the Domestic Lighting Demand Model (2008) by I. Richardson and M. Thomson
 * from Loughborough University (http://hdl.handle.net/2134/4065).
 * </p>
 * 
 * @author lukas
 *
 */
public class Lighting {

	private static final String BULBS_FILE_LOCATION = Input.DIR + "lighting/bulbs.csv";
	private static final String IRRADIANCE_FILE_LOCATION = Input.DIR + "lighting/irradianceInt.csv";
	private static final String LIGHTING_SEPARATOR = "\t";
	private static final double CALIBRATION_SCALAR = 0.00815369;
	private static final int HOUSE_COUNT = 100;
	public static final int DEFAULT_BULB_CONFIG = -1;
	private static final double IRRADIANCE_IRRELEVANT_PROBABILITY = 0.5;

	private static final double[] EFFECTIVE_OCCUPANCY = { 0, 1, 1.528, 1.694, 1.983, 2.094 };
	private static final int[] LOWER_DURATION = { 1, 2, 3, 5, 9, 17, 28, 50, 92 };
	private static final int[] UPPER_DURATION = { 1, 2, 4, 8, 16, 27, 49, 91, 259 };

	private ArrayList<int[]> bulbArrays;
	private int[] irradiance;
	private int[] activeOccupancy;
	private int houseNumber = DEFAULT_BULB_CONFIG;

	/**
	 * Creates new Lighting. All necessary files are read in in the constructor.
	 * 
	 * @param activeOccupancy
	 *            the active occupancy values to be used for simulating the
	 *            lighting demand of a dwelling
	 * @throws IllegalArgumentException
	 *             if the length of the passed array does not equal 1440
	 */
	public Lighting(int[] activeOccupancy) {
		if (activeOccupancy.length != Input.MAX_TIME_INTERVAL_COUNT)
			throw new IllegalArgumentException(
					"The length of the passed integer array must be " + Input.MAX_TIME_INTERVAL_COUNT + ".");
		this.activeOccupancy = activeOccupancy;
		bulbArrays = getBulbArrays();
		irradiance = getIrradianceArray();
	}

	/**
	 * This is for deciding which house (bulb configuration) should be used when
	 * simulating light. Passing -1 will result in a random configuration while
	 * 0 turns the light off ("getLightingValues" will return an array of
	 * zeros).
	 * 
	 * @param houseNumber
	 *            the number of the house and corresponding bulb configuration
	 *            to be used for simulation
	 * @throws IllegalArgumentException
	 *             if the passed number is less than -1 or grater than 100
	 */
	public void setHouseNumber(int houseNumber) {
		if (houseNumber < -1 || houseNumber > 100)
			throw new IllegalArgumentException("The lighting value must be an integer between -1 and 100 inclusive.");
		this.houseNumber = houseNumber;
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
	public int[] getLightingValues() {

		int[] result = new int[Input.MAX_TIME_INTERVAL_COUNT];
		int irradianceTreshold = 60;
		int[] bulbArray = getBulbArray();

		for (int bulb = 0; bulb < bulbArray.length; bulb++) {

			int time = 0;

			while (time < Input.MAX_TIME_INTERVAL_COUNT) {

				// bulb switch on event
				boolean lowIrradiance = ((irradiance[time] < irradianceTreshold)
						|| (RandomUtil.nextDouble() < IRRADIANCE_IRRELEVANT_PROBABILITY));
				double effectiveOccupancy = EFFECTIVE_OCCUPANCY[activeOccupancy[time]];
				double relativeUseWeighting = -CALIBRATION_SCALAR * Math.log(RandomUtil.nextDouble());
				if (lowIrradiance && (RandomUtil.nextDouble() < (effectiveOccupancy * relativeUseWeighting))) {

					int lightDuration = determineDuration();

					// fill result
					for (int i = 0; i < lightDuration; i++) {
						if (time > Input.MAX_TIME_INTERVAL_COUNT - 1 || activeOccupancy[time] == 0)
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
	 * @return the bulb array that corresponds to the currently specified house
	 *         number.
	 */
	private int[] getBulbArray() {
		if (houseNumber == 0)
			return new int[0];
		else if (houseNumber == -1)
			houseNumber = RandomUtil.nextInt(HOUSE_COUNT);
		else
			houseNumber--;
		return bulbArrays.get(houseNumber);
	}

	/**
	 * Reads in all bulb arrays using a {@link CSVReader}.
	 * 
	 * @return an array list containing all bulb arrays from the file
	 *         "input/lighting/bulbs.csv".
	 */
	private ArrayList<int[]> getBulbArrays() {
		CSVReader reader = new CSVReader();
		reader.read(BULBS_FILE_LOCATION, LIGHTING_SEPARATOR);
		ArrayList<int[]> result = new ArrayList<int[]>();
		int length;
		int[] row;
		for (int i = 0; i < HOUSE_COUNT - 1; i++) {
			length = (int) reader.getDouble(i, 1);
			row = new int[length];
			for (int j = 0; j < length; j++)
				row[j] = (int) reader.getDouble(i, j + 2);
			result.add(row);
		}
		return result;
	}

	/**
	 * Reads in the irradiance for the currently specified month using a
	 * {@link CSVReader}.
	 * 
	 * @return the irradiance array from the file
	 *         "input/lighting/irrandiance.csv" for the current month.
	 */
	private int[] getIrradianceArray() {
		CSVReader reader = new CSVReader();
		reader.read(IRRADIANCE_FILE_LOCATION, LIGHTING_SEPARATOR);
		Matrix matrix = reader.getMatrix(0, Input.MAX_TIME_INTERVAL_COUNT - 1, 0, 11);
		double[] tmp = matrix.getCol(Input.getMonth());
		int[] result = new int[tmp.length];
		for (int i = 0; i < tmp.length; i++)
			result[i] = (int) tmp[i];
		return result;
	}

	/**
	 * Method to terminate the duration of a bulb staying turned on. For further
	 * detail see https://dspace.lboro.ac.uk/2134/4759.
	 * 
	 * @return and integer representing the time [min] a bulb should be turned
	 *         on for.
	 */
	private int determineDuration() {
		double random1 = RandomUtil.nextDouble();
		double cumulative = 0;
		for (int i = 1; i <= 9; i++) {

			cumulative = i / 9;

			if (random1 < cumulative)
				return (int) RandomUtil.nextDouble(LOWER_DURATION[i - 1], UPPER_DURATION[i - 1]);
		}
		return (int) RandomUtil.nextDouble(LOWER_DURATION[0], UPPER_DURATION[0]);
	}
}
