package de.frauas.jdemandmodel.reader;

import java.util.HashMap;

import de.frauas.jdemandmodel.appliance.ApplianceData;
import de.frauas.jdemandmodel.appliance.Device;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.RandomUtil;

/**
 * This class reads the ApplianceData from the file "input/appliances.csv" which
 * then can be accessed using the "get" method.
 * 
 * @author lukas
 */
public class ApplianceReader extends CSVReader {

	private static final String APPLIANCES_FILE_LOCATION = Input.DIR + "appliances.csv";
	private static final String APPLIANCE_SEPARATOR = "\t";
	private static final int POWER_ON_COL = 6;
	private static final int POWER_OFF_COL = 7;
	private static final int CYCLE_TIME_COL = 5;
	private static final int RESTART_DELAY_COL = 8;
	private static final int SCALE_FACTOR_COL = 20;
	private static final double POWER_ON_DEVIATION = 0.1;

	private HashMap<Device, ApplianceData> applianceData;

	/**
	 * Creates a new ApplianceReader. The data is read in.
	 */
	public ApplianceReader() {
		read(APPLIANCES_FILE_LOCATION, APPLIANCE_SEPARATOR);
		applianceData = new HashMap<Device, ApplianceData>();
		fill();
	}

	/**
	 * In this method an ApplianceData hash map is filled, that is later used to
	 * return the data ("get"). As this class extends a CSVReader, the
	 * "getDouble" method can be used to access the read-in data. The method
	 * iterates through all devices and for each reads the single variables of
	 * {@link ApplianceData} from the row specified in in the {@link Device}
	 * class. Note that for TVs the length of the power cycle varies.
	 */
	private void fill() {
		int row;
		for (Device device : Device.values()) {
			row = device.getRow();
			int cycleTime = (int) getDouble(row, CYCLE_TIME_COL);
			if (device == Device.TV1 || device == Device.TV2 || device == Device.TV3) {
				cycleTime = (int) (70 * (Math.pow(0 - Math.log(1 - RandomUtil.nextDouble()), 1.1)));
			}
			ApplianceData tmp = new ApplianceData((int) getDouble(row, POWER_ON_COL),
					(int) getDouble(row, POWER_OFF_COL), cycleTime, (int) getDouble(row, RESTART_DELAY_COL),
					getDouble(row, SCALE_FACTOR_COL),
					RandomUtil.nextDouble(-1 * POWER_ON_DEVIATION, POWER_ON_DEVIATION));
			applianceData.put(device, tmp);
		}
	}

	/**
	 * @param device
	 *            the device of which to return the ApplianceData
	 * @return the ApplianceData of the specified device.
	 */
	public ApplianceData get(Device device) {
		return applianceData.get(device);
	}
}