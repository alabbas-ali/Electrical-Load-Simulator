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

import java.util.ArrayList;

import de.frauas.jdemandmodel.reader.CSVReader;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.Matrix;

/**
 * This class is used to read the data needed for creating a Markov chain. This
 * data was copied from the CREST Domestic Electricity Demand Model
 * (http://hdl.handle.net/2134/5786) to files that can be found at "input/".
 * 
 * @author lukas
 */
public class CSVMarkovReader extends CSVReader implements MarkovReader {

	private static final String START_WD_FILE_LOCATION = Input.DIR + "start_wd.csv";
	private static final String START_WE_FILE_LOCATION = Input.DIR + "start_we.csv";
	private static final String MARKOV_SEPARATOR = "\t";

	/**
	 * Sorts the Markov data from a csv file and returns a list of matrices
	 * organized like this:
	 * <p>
	 * 0: { { p1, p2, p3 }, { p4, p5, p6 }, { p7, p8, p9 } }
	 * </p>
	 * <p>
	 * 1: { { p10, p11 ...
	 * </p>
	 * <p>
	 * ...
	 * </p>
	 * The position in the list represents the time interval and each list entry
	 * consists of arrays of probabilities for each current occupancy state,
	 * e.g. p6 would be the probability that in time interval one (0:), if there
	 * was already one person at home (array 1), that another person appears
	 * (position 2 in array), and so on.
	 * 
	 * @return a list of transition matrices.
	 */
	public ArrayList<Matrix> readTransitionMatrices() {

		ArrayList<Matrix> result = new ArrayList<Matrix>();

		read(getPath(), MARKOV_SEPARATOR);

		Matrix oneTimeIntervalMatrix;
		int tmp = Input.POSSIBLE_ACTIVE_OCCUPANCY_COUNT;
		for (int i = 0; i < Input.CREST_TIME_INTERVAL_COUNT; i++) {
			oneTimeIntervalMatrix = getMatrix(i * (tmp + 1), i * (tmp + 1) + tmp, 2, tmp + 1);
			result.add(oneTimeIntervalMatrix);
		}

		return result;
	}

	/**
	 * Reads the start probability data from the files mentioned above,
	 * rearranges it and returns the occupancy probabilities.
	 * 
	 * @return an array of occupancy probabilities for the first time interval.
	 */
	public double[] readInitialProbabilities() {

		double[] result = new double[Input.POSSIBLE_ACTIVE_OCCUPANCY_COUNT];

		String pathString = START_WD_FILE_LOCATION;
		if (Input.isWeekend())
			pathString = START_WE_FILE_LOCATION;
		read(pathString, MARKOV_SEPARATOR);

		for (int i = 0; i < result.length; i++)
			result[i] = getDouble(i, Input.getResidentCount());

		return result;
	}

	/**
	 * Depending on the given values of weekend and chosen residentCount,
	 * returns the correct file path.
	 * 
	 * @return "input/{1-5}_w{d/e}.csv".
	 */
	private String getPath() {
		String weekdayString = "d";
		if (Input.isWeekend())
			weekdayString = "e";
		String occupancyString = String.valueOf(Input.getResidentCount());
		return Input.DIR + occupancyString + "_w" + weekdayString + ".csv";
	}
}
