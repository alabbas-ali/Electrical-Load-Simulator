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

import de.frauas.jdemandmodel.occupancy.CSVMarkovReader;
import de.frauas.jdemandmodel.occupancy.OccupancyModeler;

public class ExampleLighting {

	private static final int BULB_CONFIG = 24;

	/**
	 * Example {@link Lighting} run with bulb configuration
	 * {@value #BULB_CONFIG}.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {

		OccupancyModeler occupancyModel = new OccupancyModeler(new CSVMarkovReader());
		int[] occValues = occupancyModel.getOccupancyValues(true);

		Lighting lightingModel = new Lighting(occValues);
		lightingModel.setHouseNumber(BULB_CONFIG);

		int[] result = lightingModel.getLightingValues();

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < result.length; i++)
			stringBuilder.append(i + "\t" + occValues[i] + "\t" + result[i] + " W\n");
		System.out.println(stringBuilder.toString());
	}
}