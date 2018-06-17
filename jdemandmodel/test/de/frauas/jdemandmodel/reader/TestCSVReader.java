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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.frauas.jdemandmodel.util.Matrix;

public class TestCSVReader {

	private static final String TESTFILE = "test/de/frauas/jdemandmodel/reader/testCSVReader.csv";

	/**
	 * Tests whether the correct data is read from a test file.
	 */
	@Test
	public void resultTest() {

		CSVReader csvReader = new CSVReader();
		csvReader.read(TESTFILE, "\t");

		Matrix result = csvReader.getMatrix(1, 5, 2, 5);
		assertEquals(new Double(0.994), result.get(0, 0), 0);
	}

	/**
	 * Tests whether an exception is thrown, if the reader reads data that
	 * cannot be parsed to doubles.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void parseTest() {
		CSVReader csvReader = new CSVReader();
		csvReader.read(TESTFILE, "\t");
		csvReader.getMatrix(0, 2, 0, 3);
	}

	/**
	 * Tests the return value of the "read" method. It should return false, if
	 * the file cannot be found.
	 */
	@Test
	public void fileFoundTest() {
		CSVReader csvReader = new CSVReader();
		boolean result1 = csvReader.read(TESTFILE, "\t");

		assertEquals(true, result1);
	}
}