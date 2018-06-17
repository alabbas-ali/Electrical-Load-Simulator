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

package de.frauas.jdemandmodel.occupancy;

import de.frauas.jdemandmodel.util.Input;

public class ExampleOccupancy {

	/**
	 * An example of modeling occupancy data.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {

		Input.setResidentCount(5);

		OccupancyModeler occupancyModeler = new OccupancyModeler(new CSVMarkovReader());
		int[] occupancyValues = occupancyModeler.getOccupancyValues(false);

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < occupancyValues.length; i++) {
			String pluralS = null;
			int occ = occupancyValues[i];
			if (occ == 1)
				pluralS = "";
			else
				pluralS = "s";
			stringBuilder.append((i + 1) + "\t" + occ + " occupant" + pluralS + "\n");
		}

		System.out.println(stringBuilder.toString());
	}
}