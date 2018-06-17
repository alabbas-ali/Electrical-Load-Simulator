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

/**
 * This class holds a few constants and variables that are used in many
 * different places throughout the program and therefore declared globally.
 * 
 * @author lukas
 *
 */
public class Input {

	public static final String TITLE = "jdm";

	public static final int DEFAULT_MONTH = 3;
	public static final int DEFAULT_RESIDENT_COUNT = 2;

	private static int residentCount = DEFAULT_RESIDENT_COUNT;
	private static boolean weekend = false;
	private static int month = DEFAULT_MONTH;

	/**
	 * @return the number of residents (0-5).
	 */
	public static int getResidentCount() {
		return residentCount;
	}

	/**
	 * @return true, if weekend is set true, otherwise false.
	 */
	public static boolean isWeekend() {
		return weekend;
	}

	/**
	 * @return the month value (1-12).
	 */
	public static int getMonth() {
		return month;
	}

	/**
	 * Sets the number of residents.
	 * 
	 * @param residentCount
	 *            an integer between 0 and 5 inclusive that represents the
	 *            number of residents
	 * @throws IllegalArgumentException
	 *             if the parameter is outside this range
	 */
	public static void setResidentCount(int residentCount) {
		if (residentCount < 0 || residentCount > 5)
			throw new IllegalArgumentException("Number of residents must be an integer between 0 and 5 inclusive.");
		Input.residentCount = residentCount;
	}

	/**
	 * Sets the weekend flag for the simulation.
	 * 
	 * @param weekend
	 *            true, if statistical data for weekends should be used, else
	 *            false
	 */
	public static void setWeekend(boolean weekend) {
		Input.weekend = weekend;
	}

	/**
	 * Sets the month.
	 * 
	 * @param month
	 *            an integer between 1 (january) and 12 (december)
	 * @throws IllegalArgumentException
	 *             if the parameter is outside this range
	 */
	public static void setMonth(int month) {
		if (month < 1 || month > 12)
			throw new IllegalArgumentException(
					"The month value must be an integer between 1 (january) and 12 (december).");
		Input.month = month;
	}

	// other constants
	public static final int MAX_TIME_INTERVAL_COUNT = 1440;
	public static final int CREST_TIME_INTERVAL_COUNT = 144;
	public static final String DIR = "data/input/";
	public static final int POSSIBLE_ACTIVE_OCCUPANCY_COUNT = 6;
}
