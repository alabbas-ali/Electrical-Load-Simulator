package de.frauas.jdemandmodel.reader;

import java.util.HashMap;

import de.frauas.jdemandmodel.appliance.Activity;
import de.frauas.jdemandmodel.appliance.Device;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.Matrix;

/**
 * An ActivityReader reads from the file "input/activityStats.csv" and saves for
 * each device (that has an {@link Activity} representation!) a {@link Matrix}
 * containing the probabilities of them being used at each time interval for all
 * possible active occupancies. To access these matrices use the "get" method.
 * 
 * @author lukas
 */
public class ActivityReader extends CSVReader {

	private static final String ACTIVITY_STATS_FILE_LOCATION = Input.DIR + "activityStats.csv";
	private static final String ACTIVITY_SEPARATOR = "\t";

	private HashMap<Device, Matrix> useProfile;

	/**
	 * Creates a new ActivityReader that reads from the file and fills a hash
	 * map with the data.
	 */
	public ActivityReader() {
		read(ACTIVITY_STATS_FILE_LOCATION, ACTIVITY_SEPARATOR);
		useProfile = new HashMap<Device, Matrix>();
		fill();
	}

	/**
	 * In this method the hash map is filled with the activity data. In the file
	 * "activityStats.csv" the data is organized in a table with the following
	 * columns:
	 * <p>
	 * weekend flag | active occupancy | activity | p1 | p2 | ... | p144
	 * </p>
	 * <p>
	 * It is ordered first by weekend flag, then active occupancy and finally
	 * the activity. The result should be a matrix containing all probabilities
	 * for all active occupancies but for a known weekend flag and activity.
	 * Therefore, weekend flag and activity are used to calculate the row, where
	 * to find the next corresponding probabilities, iterating through all
	 * active occupancy values.
	 * </p>
	 * Note that devices with an activity value less than 0 don't have those
	 * probability arrays and are left out.
	 */
	private void fill() {
		int activity;
		for (Device device : Device.values()) {
			if (device.getActivity() < 0)
				continue;
			activity = device.getActivity();

			int weekendInt = 0;
			if (Input.isWeekend())
				weekendInt = 1;

			int occCount = Input.POSSIBLE_ACTIVE_OCCUPANCY_COUNT;
			double[][] result = new double[occCount][];
			int row;
			for (int i = 0; i < occCount; i++) {
				row = (weekendInt * occCount * Activity.class.getFields().length) + (i * occCount) + activity;

				Matrix matrix = getMatrix(row, row, 3, Input.CREST_TIME_INTERVAL_COUNT + 2);
				result[i] = matrix.getRow(0);
			}
			useProfile.put(device, new Matrix(result));
		}
	}

	/**
	 * @param device
	 *            the device of which to return a use profile
	 * @return the use profile matrix (activity probability for each time
	 *         interval) of an appliance.
	 * @throws IllegalArgumentException
	 *             if the device has an activity value less than 0
	 */
	public Matrix get(Device device) {
		if (device.getActivity() < 0)
			throw new IllegalArgumentException(
					"There are no activity probabilities for the device " + device.name() + ".");
		return useProfile.get(device);
	}
}