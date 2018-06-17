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

package de.frauas.jdemandmodel.simulator;

import java.util.ArrayList;

import de.frauas.jdemandmodel.appliance.Appliance;
import de.frauas.jdemandmodel.appliance.ApplianceManager;
import de.frauas.jdemandmodel.lighting.Lighting;
import de.frauas.jdemandmodel.occupancy.OccupancyModeler;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.Matrix;

/**
 * This class puts it all together. It needs occupancy values and a list of
 * appliances as input and from that simulates lighting (using the provided bulb
 * configuration), demand for all specified appliances and the total demand and
 * the result is written to one compact matrix.
 * 
 * @author lukas
 */
public class Simulator {

	private int[] occupancyValues;
	private ArrayList<Appliance> appList;

	/**
	 * Creates a new Simulator, that will use the given occupancy values and
	 * appliances for simulation.
	 * 
	 * @param occupancyValues
	 *            occupancy values to be used (see {@link OccupancyModeler})
	 * @param appList
	 *            list of appliances to be used (see {@link ApplianceManager})
	 */
	public Simulator(int[] occupancyValues, ArrayList<Appliance> appList) {
		this.occupancyValues = occupancyValues;
		this.appList = appList;
	}

	/**
	 * This method simulates the lighting demand and demand of all appliances
	 * from the occupancy values and data each appliance hold. It also
	 * calculates the total demand.
	 * 
	 * @see {@link Lighting}
	 * @see {@link Appliance} "hasNextTimeInterval" and "nextWattage"
	 * @param bulbConfig
	 *            the bulb configuration to be used for the simulation (see file
	 *            "input/lighting/bulbs.csv")
	 * @return a matrix holding occupancy, total demand, lighting demand and
	 *         demand of all appliances passed to the constructor for 1440
	 *         1-minute time intervals.
	 */
	public Matrix runDemandModelSimulation(int bulbConfig) {

		Matrix powerMatrix = new Matrix(new double[Input.MAX_TIME_INTERVAL_COUNT][appList.size() + 3]);

		Lighting light = new Lighting(occupancyValues);
		light.setHouseNumber(bulbConfig);
		int[] lightValues = light.getLightingValues();

		int occupancy = 0;
		int lightSum = 0;
		int powerSum = 0;
		int i = 0;
		while (Appliance.hasNextTimeInterval()) {
			occupancy = occupancyValues[i];
			lightSum = lightValues[i];
			powerMatrix.set(i, 0, occupancy);
			for (int j = 0; j < appList.size(); j++) {
				Appliance a = appList.get(j);
				a.nextWattage(occupancy);
				powerMatrix.set(i, j + 3, a.getPower());
				powerSum += a.getPower();
			}
			powerMatrix.set(i, 1, powerSum + lightSum);
			powerMatrix.set(i, 2, lightSum);
			powerSum = 0;
			i++;
		}

		return powerMatrix;
	}
}