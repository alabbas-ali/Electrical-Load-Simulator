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

package de.frauas.jdemandmodel.seed;

import java.util.Arrays;

import de.frauas.jdemandmodel.occupancy.CSVMarkovReader;
import de.frauas.jdemandmodel.occupancy.OccupancyModeler;

public class ExampleOccupancySeed {

	private static final String FILENAME = "../examples/de/frauas/jdemandmodel/seed/exampleSeed";
	
	/**
	 * An example of using a seed for modeling occupancy values.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {

		Seed.setActive(false);
		OccupancyModeler occModeler = new OccupancyModeler(new CSVMarkovReader());
		int[] occValues = occModeler.getOccupancyValues(true);
		System.out.println("original run:\t" + Arrays.toString(occValues));

		Seed.writeToFile(FILENAME, new String[0]);

		Seed.setActive(true);
		Seed.loadSeeds(FILENAME);
		occValues = occModeler.getOccupancyValues(true);
		System.out.println("using seed:\t" + Arrays.toString(occValues));
	}
}