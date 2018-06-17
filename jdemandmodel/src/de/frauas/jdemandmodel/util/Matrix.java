package de.frauas.jdemandmodel.util;

/**
 * This is a utilizing class for handling two-dimensional arrays more easily.
 * 
 * @author lukas
 */
public class Matrix {

	private double[][] data;
	private int size;

	/**
	 * Create a new Matrix from the given double array.
	 * 
	 * @param data
	 *            a two-dimensional double array
	 */
	public Matrix(double[][] data) {
		checkMatrix(data);
		this.size = data[0].length;
		this.data = data;
	}

	/**
	 * @param row
	 *            row of the desired value
	 * @param col
	 *            column of the desired value
	 * @return the value at specified row and column in the matrix.
	 */
	public double get(int row, int col) {
		return data[row][col];
	}

	/**
	 * This method sets the field at given row and column to the specified
	 * value.
	 * 
	 * @param row
	 *            row to be set
	 * @param col
	 *            column to be set
	 * @param value
	 *            the value this field should be set to
	 */
	public void set(int row, int col, double value) {
		data[row][col] = value;
	}

	/**
	 * @param row
	 *            row to be returned
	 * @return the specified row.
	 */
	public double[] getRow(int row) {
		return data[row];
	}

	/**
	 * @param col
	 *            column to be returned
	 * @return the specified column.
	 */
	public double[] getCol(int col) {
		double[] result = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			result[i] = data[i][col];
		}
		return result;
	}

	/**
	 * @return the length of the matrix as in number of rows.
	 */
	public int getLength() {
		return data.length;
	}

	/**
	 * @return the size of the matrix as in number of columns.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the two-dimensional array representing the matrix.
	 */
	public double[][] getData() {
		return data;
	}

	/**
	 * Creates a string representation of the matrix that can be read easily by
	 * the user (table format).
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < getLength(); i++) {
			for (int j = 0; j < size; j++) {
				stringBuilder.append((int) get(i, j));
				if (j != (size - 1))
					stringBuilder.append("\t");
				else
					stringBuilder.append("\n");
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * Checks whether all the lines of the matrix have the same length.
	 * 
	 * @param data
	 *            the data passed to construct a matrix
	 * @throws IllegalArgumentException
	 *             if the lines of the passed data have different lengths
	 */
	private void checkMatrix(double[][] data) {
		int expected = data[0].length;
		for (int i = 0; i < data.length; i++) {
			int actual = data[i].length;
			if (actual != expected)
				throw new IllegalArgumentException("Lines don't contain the same number of fields. Expected " + expected
						+ ", but was " + actual + ".");
		}
	}
}