package his.loadprofile.core.Importers;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;


public class LightingImporter extends CSVReader{

	private static final String BULBS_FILE_LOCATION = "input/lighting/bulbs.csv";
	private static final String IRRADIANCE_FILE_LOCATION = "input/lighting/irradianceInt.csv";
	private static final int MAX_TIME_INTERVAL_COUNT = 1440;
	private static final int HOUSE_COUNT = 100;
	private ArrayList<int[]> bulbArrays;
	int[] irradiance;
	
	private Random rand = new Random();

	public LightingImporter() {
		bulbArrays = this.readBulbArrays();
		irradiance = this.readIrradianceArray();
	}
	
	
	/**
	 * Reads in all bulb arrays using a {@link CSVReader}.
	 * 
	 * @return an array list containing all bulb arrays from the file "input/lighting/bulbs.csv".
	 */
	private ArrayList<int[]> readBulbArrays() 
	{
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(BULBS_FILE_LOCATION).getFile());
		ArrayList<int[]> result = new ArrayList<int[]>();
		if(this.read(file)) {
			int length;
			int[] row;
			for (int i = 0; i < HOUSE_COUNT - 1; i++) {
				length = (int) this.getDouble(i, 1);
				row = new int[length];
				for (int j = 0; j < length; j++)
					row[j] = (int) this.getDouble(i, j + 2);
				result.add(row);
			}
		}
		return result;
	}

	/**
	 * Reads in the irradiance for the currently specified month using a {@link CSVReader}.
	 * 
	 * @return the irradiance array from the file "input/lighting/irrandiance.csv" for the current month.
	 */
	private int[] readIrradianceArray() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(IRRADIANCE_FILE_LOCATION).getFile());
		int[] result = new int[MAX_TIME_INTERVAL_COUNT];
		if(this.read(file)) {
			for (int i = 0; i < MAX_TIME_INTERVAL_COUNT; i++)
				result[i] = Integer.parseInt(this.getString(5, i));
		}
		return result;
	}
	
	/**
	 * @param houseNumber the number of the house and corresponding bulb configuration
	 *        to be used for simulation
	 * @return the bulb array that corresponds to the currently specified house number.
	 */
	public int[] getBulbs(int houseNumber) {
		if (houseNumber == 0)
			return new int[0];
		else if (houseNumber == -1)
			houseNumber = rand.nextInt(HOUSE_COUNT);
		else
			houseNumber--;
		return bulbArrays.get(houseNumber);
	}
	
	
	public int[] getIrradiance() {
		return this.irradiance;
	}

}
