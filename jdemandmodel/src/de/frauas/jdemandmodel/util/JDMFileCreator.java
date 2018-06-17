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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import de.frauas.jdemandmodel.appliance.Device;

/**
 * This class creates a file from simulated data.
 * 
 * @author lukas
 */
public class JDMFileCreator {

	public static final String UNIT_STRING = "[W]";
	public static final String FIRST_HEADER_STRING = "OCCUPANCY";
	public static final String SECOND_HEADER_STRING = "TOTAL_DEMAND" + UNIT_STRING;
	public static final String THIRD_HEADER_STRING = "LIGHT" + UNIT_STRING;

	private String headerString;
	private String dataString;

	private static final String DIR = "data/output";
	private static final String EXT = ".csv";
	private static final String SEP = ";";

	/**
	 * Creates a new JDMFileCreator. The compliance of the data's size with the
	 * number of devices is checked, then header string and data string are set
	 * internally.
	 * 
	 * @param data
	 *            the simulation data
	 * @param devices
	 *            the devices used for the simulation
	 */
	public JDMFileCreator(Matrix data, Device[] devices) {
		checkCompliance(data.getSize(), devices.length);
		this.headerString = getHeaderString(devices);
		dataString = data.toString().replaceAll("\t", SEP);
	}

	/**
	 * Builds a header string from the devices used.
	 * 
	 * @param devices
	 *            device to appear in the header
	 * @return one string representing the header of the CSV file including a
	 *         line break.
	 */
	private String getHeaderString(Device[] devices) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(FIRST_HEADER_STRING + SEP);
		stringBuilder.append(SECOND_HEADER_STRING + SEP);
		stringBuilder.append(THIRD_HEADER_STRING);
		for (Device device : devices) {
			stringBuilder.append(SEP + device.toString() + UNIT_STRING);
		}
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}

	/**
	 * This method creates a new file. If the file already exists, the program
	 * will terminate.
	 * 
	 * @param filename
	 *            the name under which the file should be saved.
	 */
	public void createNewFile(String filename) {
		File file = new File(DIR + "/" + filename + EXT);
		if (file.exists())
			System.err.println("Warning: File \"" + file.getName() + "\" is overwritten.");
		file.getParentFile().mkdirs();
		writeHeaderAndDataTo(file);
	}

	/**
	 * Writes the header and data string to the specified file. If the file (and
	 * directory) doesn't exist, creates a new one.
	 * 
	 * @param file
	 *            file to write the data to
	 * @throws FileNotFoundException
	 *             if the passed file holds an invalid filename, e. g.
	 *             containing ?, | or \.
	 */
	private void writeHeaderAndDataTo(File file) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			System.err.println("Invalid filename: \"" + file.getName() + "\".");
			System.exit(1);
		}
		printWriter.print(headerString);
		printWriter.print(dataString);
		printWriter.close();
	}

	/**
	 * This method checks whether the size of the given data fits the number of
	 * devices that were specified. The data size must equal the number of
	 * device plus three, because the data should contain columns for occupancy,
	 * total demand and lighting.
	 * 
	 * @param dataSize
	 *            size of the data matrix
	 * @param devicesCount
	 *            number of devices to be included in the header
	 * @throws IllegalArgumentException
	 *             if data size and device count do not comply
	 */
	private void checkCompliance(int dataSize, int devicesCount) {
		if (dataSize != devicesCount + 3) {
			throw new IllegalArgumentException("Number of headings doesn't comply with data.");
		}
	}
}