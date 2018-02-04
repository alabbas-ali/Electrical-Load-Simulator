package his.loadprofile.core.Importers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Locale;


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
	 * Returns a specific value from the CSV file data at given row and column.
	 * 
	 * @param row	Row number of desired value.
	 * @param col	Column number of desired value.
	 * @return desired double value from CSV file.
	 */
	public double getDouble(int row, int col) {
		return parseDecimal(lines.get(row)[col]);
	}
	
	/**
	 * Returns a specific value from the CSV file data at given row and column.
	 * 
	 * @param row	Row number of desired value.
	 * @param col	Column number of desired value.
	 * @return desired String value from CSV file.
	 */
	public String getString(int row, int col) {
		return lines.get(row)[col];
	}
	
	public int getLineNymber() {
		return lines.size();
	}

	/**
	 * Reads the data from CSV files.
	 * @return false, if the reading failed, otherwise true.
	 */
	public boolean read(File filename) {

		lines.clear();
		boolean result = false;
		BufferedReader bufferedReader = null;

		String currentLine;

		try {
			bufferedReader = new BufferedReader(new FileReader(filename));
			while ((currentLine = bufferedReader.readLine()) != null) {
				lines.add(currentLine.split(INPUT_FILE_SEPARATOR));
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
	 * @param input					the string to be parsed to a double
	 * @return Double 				representing the passed string.
	 * @throws ParseExceptionif 	the string cannot be parsed to a double.
	 */
	private Double parseDecimal(String input) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		ParsePosition parsePosition = new ParsePosition(0);
		Number number = numberFormat.parse(input, parsePosition);

		if (parsePosition.getIndex() != input.length()) {
			throw new IllegalArgumentException("Invalid input, input cannot be parsed to double."); 
			// parsePosition.getIndex());
		}
		return number.doubleValue();
	}
}