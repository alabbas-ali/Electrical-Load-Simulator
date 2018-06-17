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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Locale;

import de.frauas.jdemandmodel.util.Matrix;

public class CSVReader {

	public static final String INPUT_FILE_SEPARATOR = "\t";

	private ArrayList<String[]> lines;

	/**
	 * Creates a new CSVReader.
	 */
	public CSVReader() {
		lines = new ArrayList<String[]>();
	}

	/**
	 * Parses all String values to doubles.
	 * 
	 * @param fromRow
	 *            first row to be parsed
	 * @param toRow
	 *            last row to be parsed
	 * @param fromCol
	 *            first column to be parsed
	 * @param toCol
	 *            last column to be parsed
	 * @return a Matrix of all values within the specified boundaries.
	 */
	public Matrix getMatrix(int fromRow, int toRow, int fromCol, int toCol) {

		double[][] result = new double[toRow - fromRow + 1][toCol - fromCol + 1];
		for (int i = fromRow; i <= toRow; i++) {
			for (int j = fromCol; j <= toCol; j++) {

				result[i - fromRow][j - fromCol] = parseDecimal(lines.get(i)[j]);

			}
		}
		return new Matrix(result);
	}

	/**
	 * Returns a specific value from the CSV file data at given row and column.
	 * 
	 * @param row
	 *            Row number of desired value.
	 * @param col
	 *            Column number of desired value.
	 * @return desired double value from CSV file.
	 */
	public double getDouble(int row, int col) {
		return parseDecimal(lines.get(row)[col]);
	}

	/**
	 * Reads the data from CSV files.
	 * 
	 * @return false, if the reading failed, otherwise true.
	 */
	public boolean read(String filename, String separator) {

		lines.clear();
		boolean result = false;
		BufferedReader bufferedReader = null;

		String currentLine;

		try {

			bufferedReader = new BufferedReader(new FileReader(filename));
			while ((currentLine = bufferedReader.readLine()) != null) {
				lines.add(currentLine.split(separator));
			}
			result = true;
			if (bufferedReader != null)
				bufferedReader.close();

		} catch (IOException e) {
			System.err.println("File \"" + filename + "\" not found.");
			System.exit(0);
		}

		return result;
	}

	/**
	 * Copied from
	 * http://stackoverflow.com/questions/4323599/best-way-to-parsedouble-with-comma-as-decimal-separator
	 * answer12 [16/10/26], slightly modified
	 * 
	 * @param input
	 *            the string to be parsed to a double
	 * @return a Double representing the passed string.
	 * @throws ParseException
	 *             if the string cannot be parsed to a double.
	 */
	private Double parseDecimal(String input) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		ParsePosition parsePosition = new ParsePosition(0);
		Number number = numberFormat.parse(input, parsePosition);

		if (parsePosition.getIndex() != input.length()) {
			throw new IllegalArgumentException("Invalid input, input cannot be parsed to double."); // parsePosition.getIndex());
		}
		return number.doubleValue();
	}
}