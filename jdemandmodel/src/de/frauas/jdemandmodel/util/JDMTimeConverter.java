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

package de.frauas.jdemandmodel.util;

import de.frauas.jdemandmodel.occupancy.OccupancyModeler;
import de.frauas.jdemandmodel.simulator.Simulator;

/**
 * This class is for converting data produced by the {@link OccupancyModeler}
 * and {@link Simulator} to the specified time interval length.
 * 
 * @author lukas
 *
 */
public class JDMTimeConverter {

	public static final int CREST_TIME_INTERVAL_LENGTH = 10;
	private static int timeIntervalLength = CREST_TIME_INTERVAL_LENGTH;

	/**
	 * @return the time interval length / time resolution.
	 */
	public static int getTimeIntervalLength() {
		return timeIntervalLength;
	}

	/**
	 * Sets the time interval length / time resolution.
	 * 
	 * @param timeIntervalLength
	 *            an integer between 1 and 1440 inclusive.
	 * @throws IllegalArgumentException
	 *             if the parameter is outside this range
	 */
	public static void setTimeIntervalLength(int timeIntervalLength) {
		if (timeIntervalLength < 1 || timeIntervalLength > 1440)
			throw new IllegalArgumentException(
					"The time resolution value must be an integer between 1 and 1440 inclusive.");
		JDMTimeConverter.timeIntervalLength = timeIntervalLength;
	}

	/**
	 * This method can be used to expand an integer array of length 144 to an
	 * integer array of length 1440. The method will simply turn one field into
	 * ten fields holding the same value.
	 * 
	 * @param data
	 *            the data to be expanded (length must be 144)
	 * @return an integer array of length 1440 including the same data as in the
	 *         original array.
	 */
	public static int[] expandToMAX_TIME_INTERVAL_COUNT(int[] data) {
		int crestCount = Input.CREST_TIME_INTERVAL_COUNT;
		if (data.length == crestCount) {
			int[] result = new int[Input.MAX_TIME_INTERVAL_COUNT];
			for (int i = 0; i < result.length; i++) {
				result[i] = data[i / CREST_TIME_INTERVAL_LENGTH];
			}
			return result;
		} else
			throw new IllegalArgumentException("Only data of length " + crestCount + " can be expanded.");
	}

	/**
	 * This method converts a whole matrix of length 1440 to another length. As
	 * the matrix is expected to hold electricity demand data for 1440 1-minute
	 * time intervals in each column, each column is saved in a new array and
	 * then passed to the private "convert" method that only takes one array
	 * instead of matrix. This method does the actual conversion. Finally, the
	 * new arrays are put together to a matrix again.
	 * 
	 * @param matrix
	 *            electricity demand data such as produced by a
	 *            {@link Simulator}
	 * @return a converted matrix with the average demand of each newly sized
	 *         time interval.
	 * @throws IllegalArgumentException
	 *             if the passed matrix has any length other than 1440
	 */
	public static Matrix convert(Matrix matrix) {

		int maxCount = Input.MAX_TIME_INTERVAL_COUNT;

		if (matrix.getLength() == maxCount) {
			if (timeIntervalLength == 1)
				return matrix;
			else {

				int[][] pivot1 = new int[matrix.getSize()][];

				for (int i = 0; i < matrix.getSize(); i++) {
					for (int j = 0; j < matrix.getLength(); j++) {
						if (j == 0)
							pivot1[i] = new int[matrix.getLength()];
						pivot1[i][j] = (int) matrix.get(j, i);
					}
				}

				int[][] tmp = new int[pivot1.length][];
				for (int i = 0; i < pivot1.length; i++) {
					tmp[i] = (int[]) convert(pivot1[i], timeIntervalLength);
				}

				double[][] pivot2 = new double[tmp[0].length][];

				for (int i = 0; i < tmp[0].length; i++) {
					for (int j = 0; j < tmp.length; j++) {
						if (j == 0)
							pivot2[i] = new double[tmp.length];
						pivot2[i][j] = tmp[j][i];
					}
				}

				return new Matrix(pivot2);
			}
		} else
			throw new IllegalArgumentException("Can only convert data of length " + maxCount);
	}

	/**
	 * This method does the actual conversion, as it adds up all values that
	 * will be summarized in a new time interval and divides the result by the
	 * time resolution. This way, the result will contain the average
	 * electricity demand in each time interval.
	 * 
	 * @param data
	 *            the integer array to be converted
	 * @param timeResolution
	 *            the new time interval length the array should be converted to
	 * @return a converted integer array.
	 * @throws IllegalArgumentException
	 *             if the passed array has any length other than 1440
	 */
	private static int[] convert(int[] data, int timeResolution) {
		int maxCount = Input.MAX_TIME_INTERVAL_COUNT;
		if (data.length == maxCount) {
			if (timeResolution == 1)
				return data;
			else {
				int newLength = maxCount / timeResolution;
				int[] result = new int[newLength];

				int subTotal = 0;
				for (int i = 1; i <= maxCount; i++) {
					subTotal += data[i - 1];
					if (i % timeResolution == 0) {
						result[(i / timeResolution) - 1] = (int) Math
								.round((double) subTotal / (double) timeResolution);
						subTotal = 0;
					}
				}
				return result;
			}
		} else
			throw new IllegalArgumentException("Can only convert data of length " + maxCount + ".");
	}
}