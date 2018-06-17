package de.frauas.jdemandmodel.reader;

import java.util.Arrays;

import de.frauas.jdemandmodel.appliance.ApplianceData;
import de.frauas.jdemandmodel.appliance.powercycle.PowerCycle;
import de.frauas.jdemandmodel.appliance.powercycle.StandardPowerCycle;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.Matrix;

/**
 * This class is used for testing.
 * 
 * @author lukas
 */
public class TestData {

	public static final ApplianceData APPL_DATA = new ApplianceData(100, 5, 50, 20, 1.0, 0.05);

	public static Matrix getUseProfile(int[] length, double values) {

		double[][] result = new double[length.length][];

		for (int i = 0; i < length.length; i++) {
			double[] tmp = new double[length[i]];
			for (int j = 0; j < length[i]; j++) {
				tmp[j] = values;
			}
			result[i] = tmp;
		}

		return new Matrix(result);
	}

	public static final int TEST_POWER_ON = 105;
	public static final PowerCycle POWER_CYCLE = new StandardPowerCycle(TEST_POWER_ON);

	public static int[] getExampleOccupancy(int value) {
		int[] result = new int[Input.CREST_TIME_INTERVAL_COUNT];
		Arrays.fill(result, value);
		return result;
	}

	public static int[] getExampleOccupancy(int length, int value) {
		int[] result = new int[length];
		Arrays.fill(result, value);
		return result;
	}
}