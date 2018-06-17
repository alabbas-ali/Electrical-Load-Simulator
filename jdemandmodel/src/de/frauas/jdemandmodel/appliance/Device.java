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

package de.frauas.jdemandmodel.appliance;

import java.util.ArrayList;
import java.util.Arrays;

import de.frauas.jdemandmodel.cli.Parser;
import de.frauas.jdemandmodel.reader.ActivityReader;
import de.frauas.jdemandmodel.reader.ApplianceReader;

/**
 * This enum type lists the names, activity value and row of all appliances.
 * The activity value is used to retrieve statistical data of when this 
 * appliance is used from a file (see {@link ActivityReader}. The row 
 * indicates where to find the appliance's other data in another file (see 
 * {@link ApplianceReader}). There is a static method to translate device
 * strings into an array of devices.
 * 
 * @author lukas
 */
public enum Device {
	
	CHEST_FREEZER(-2,0), 
	FRIDGE_FREEZER(-2,1), 
	REFRIGERATOR(-2,2), 
	UPRIGHT_FREEZER(-2,3),
	
	TV1(Activity.TV, 14),
	TV2(Activity.TV, 15),
	TV3(Activity.TV, 16),
	DVD_PLAYER(Activity.TV, 17),
	RECEIVER(Activity.TV, 18),
	
	HOB(Activity.COOKING, 19),
	OVEN(Activity.COOKING, 20),
	MICROWAVE(Activity.COOKING, 21),
	SMALL_COOKING(Activity.COOKING, 23),
	DISH_WASHER(Activity.COOKING, 24),
	
	TUMBLE_DRYER(Activity.LAUNDRY, 25),
	WASHING_MACHINE(Activity.LAUNDRY, 26),
	WASHER_DRYER(Activity.LAUNDRY, 27),

	ELECTRIC_SHOWER(Activity.WASHDRESS, 30),
	IRON(Activity.IRON, 9),
	VACUUM(Activity.HOUSECLEAN, 10),
	
//	ANSWER_MACHINE(-1,4),
	CD_PLAYER(-1,5), 
//	CLOCK(-1,6), 
//	PHONE(-1,7), 
	HIFI(-1,8), 
	FAX(-1,11), 
	PC(-1,12), 
	PRINTER(-1,13),
	KETTLE(-1,22),
	DESWH(-1,28),
	E_INST(-1,29),
	
	ELEC_SPACE_HEATING(-2, 32),
	STORAGE_HEATER(-2, 31);
	
	private int activity;
	private int row;
	
	/**
	 * The constructor of a new device.
	 * @param activity an integer representing the device's activity
	 * (see {@link Activity}), -1 for active occupancy dependent devices,
	 * -2 for independent devices
	 * @param row the row of where to find the appliance's entry at 
	 * "input/appliances.csv"
	 */
	Device(int activity, int row){
		this.activity = activity;
		this.row = row;
	}
	
	/**
	 * @return the row of where to find the appliance's entry at 
	 * "input/appliances.csv".
	 */
	public int getRow(){
		return row;
	}

	/**
	 * @return an integer representing the device's activity (see 
	 * {@link Activity}), -1 for active occupancy dependent devices, -2 for
	 * independent devices.
	 */
	public int getActivity(){
		return activity;
	}
	
	/**
	 * <p>
	 * This method is designed especially for being used by the
	 * {@link Parser} and the {@link Main}, that will call the 
	 * {@link ApplianceManager} constructor with the result. If any of
	 * the strings equals "none", a null pointer will be returned and an
	 * empty string array will result in an empty device array.
	 * </p>
	 * Any invalid strings will result in a warning and add a trailing
	 * null pointer to the returned device array (this is handled in the
	 * ApplianceManager constructor, but must be taken care of when using
	 * the method in any other context!). Finally, if only invalid strings
	 * were passed (excluding "none"), an exception is thrown.  
	 * @param stringArray
	 * 		strings representing {@link Device}s, that appear in the result
	 * @return an array of the devices represented by the parameter strings.
	 * @throws NullPointerException
	 * 					if only invalid strings were passed.
	 */
	public static Device[] getDevices(String[] stringArray) {
		ArrayList<String> strings = new ArrayList<String>(Arrays.asList(stringArray));
		if(strings.contains("none"))
			return null;
		else if(stringArray.length==0) return new Device[0];

		Device[] devices = new Device[strings.size()];
		for (int i = 0; i < strings.size(); i++) {
			try {
				devices[i] = Device.valueOf(strings.get(i));
			} catch (IllegalArgumentException e) {
				System.err.println("Warning: Appliance " + strings.get(i) + " doesn't exist and will be ignored.");
				strings.remove(i);
				i--;
			}
		}
		if (strings.isEmpty())
			throw new NullPointerException("No valid appliances have been chosen.");
		return devices;
	}
}
