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

package de.frauas.jdemandmodel.reader;

import java.util.Arrays;

import de.frauas.jdemandmodel.util.Matrix;

public class ExampleCSVReader {

	/**
	 * Example of using the CSVReader to read from a CSV file, parsing the
	 * result to double values and printing them.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {

		CSVReader csvReader = new CSVReader();
		csvReader.read("test/de/frauas/jdemandmodel/reader/testCSVReader.csv", "\t");

		Matrix result = csvReader.getMatrix(1, 5, 2, 5);

		System.out.println(Arrays.deepToString(result.getData()));
	}
}