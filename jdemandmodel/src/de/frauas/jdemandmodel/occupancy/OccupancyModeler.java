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

import de.frauas.jdemandmodel.util.JDMTimeConverter;

/**
 * This class is used to model the occupancy, i.e. produce an array of integers
 * representing the active occupancy (residents that are present and awake)
 * throughout one day.
 * 
 * @author lukas
 */
public class OccupancyModeler {

	private MarkovChain occupancyMarkovChain;

	/**
	 * Constructor for a new OccupancyModeler. As the modeler needs a
	 * MarkovChain to produce values, an instance of a class implementing a
	 * MarkovReader needs to be passed.
	 * 
	 * @param markovReader
	 *            a reader that implements MarkovReader to define the
	 *            MarkovChain
	 */
	public OccupancyModeler(MarkovReader markovReader) {
		occupancyMarkovChain = new MarkovChain(markovReader.readInitialProbabilities(),
				markovReader.readTransitionMatrices());
	}

	/**
	 * Models occupancy values for 144 10-minute time intervals using a
	 * {@link MarkovChain} read in by the passed {@link MarkovReader}. The
	 * result can be converted to an array of size 1440 (time resolution 1
	 * minute).
	 * 
	 * @param convert
	 *            true to return 1440 values, false for 144 values
	 * @return active occupancy values for one day.
	 */
	public int[] getOccupancyValues(boolean convert) {

		int[] result = new int[occupancyMarkovChain.getSize()];

		result[0] = occupancyMarkovChain.getInitialState();

		for (int i = 1; i < occupancyMarkovChain.getSize(); i++) {
			result[i] = occupancyMarkovChain.getStateAt((i - 1), result[i - 1]);
		}

		if (convert)
			return JDMTimeConverter.expandToMAX_TIME_INTERVAL_COUNT(result);
		else
			return result;
	}
}