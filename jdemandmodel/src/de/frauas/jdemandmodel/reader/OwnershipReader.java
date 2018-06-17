package de.frauas.jdemandmodel.reader;

import java.util.HashMap;

import de.frauas.jdemandmodel.appliance.Device;
import de.frauas.jdemandmodel.util.Input;

/**
 * This class reads for each appliance a probability of that appliance being
 * installed in a dwelling. The reading will take place during the constructor
 * call. To access the probabilities use the method "get".
 * 
 * @author lukas
 */
public class OwnershipReader extends CSVReader {

	private static final String APP_OWNERSHIP_PROB_FILE_LOCATION = Input.DIR + "appOwnershipProb.csv";
	private static final String OWNERSHIP_SEPARATOR = "\t";

	private HashMap<Device, Double> possibilities;

	/**
	 * Creates a new OwnershipReader. The data is read in.
	 */
	public OwnershipReader() {
		read(APP_OWNERSHIP_PROB_FILE_LOCATION, OWNERSHIP_SEPARATOR);
		fill();
	}

	/**
	 * In this method the possibilities array is filled. As this class extends a
	 * CSVReader, the "getDouble" method can be used to retrieve the data.
	 */
	private void fill() {
		possibilities = new HashMap<Device, Double>();
		for (Device device : Device.values())
			possibilities.put(device, getDouble(device.getRow(), 0));
	}

	/**
	 * @param device the device you want to read the ownership possibility of
	 * @return the ownership possibility of this device.
	 */
	public double get(Device device) {
		return possibilities.get(device);
	}
}