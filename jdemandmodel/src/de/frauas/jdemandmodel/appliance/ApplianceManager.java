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

import de.frauas.jdemandmodel.lighting.Lighting;
import de.frauas.jdemandmodel.reader.OwnershipReader;
import de.frauas.jdemandmodel.util.RandomUtil;

/**
 * This class is used for managing the appliances that will be used for the
 * simulation. Either a list of devices is passed to the constructor or the
 * manager will use an {@link OwnershipReader} to create a random list using
 * statistical data. Note that the {@link Lighting} has a class of its own and
 * is always included in the result.
 * 
 * @author lukas
 */
public class ApplianceManager {

	private CSVApplianceFactory factory;
	private ArrayList<Appliance> applianceList;

	/**
	 * Creates a new ApplianceManager and at the same time decides, which
	 * appliances will be used. If a null pointer is passed, no appliances are
	 * added to the list. If no parameters (or a device array of length 0) are
	 * passed, a random list of appliances will be generated. Else the specified
	 * devices will be added to the list. Note that duplicates are deleted.
	 * Also, from the method "getDevice" at {@link Device}, it could happen that
	 * trailing null pointer exist in the passed array from invalid input. Those
	 * will be cut off.
	 * 
	 * @param devices
	 *            the devices to be used in the simulation, null pointer results
	 *            in no devices, an empty array or no parameter in a random list
	 *            of devices (see method "randomList")
	 */
	public ApplianceManager(Device... devices) {
		factory = CSVApplianceFactory.getInstance();
		applianceList = new ArrayList<Appliance>();

		if (devices == null)
			;
		else if (devices.length == 0)
			randomList();
		else {
			for (Device device : devices) {
				if (device == null)
					break;
				Appliance a = factory.createAppliance(device);
				if (!applianceList.contains(a))
					applianceList.add(factory.createAppliance(device));
			}
		}
	}

	/**
	 * This method uses an {@link OwnershipReader} to get statistical data about
	 * how likely it is for an appliance to be installed in a dwelling. Using
	 * this data, it creates a random list of appliances to be used for the next
	 * simulation.
	 */
	private void randomList() {
		OwnershipReader ownershipReader = new OwnershipReader();
		Device[] allDevices = Device.values();
		applianceList.clear();
		for (int i = 0; i < allDevices.length; i++) {
			Device device = allDevices[i];
			if (RandomUtil.nextBoolean(ownershipReader.get(device))) {
				applianceList.add(factory.createAppliance(device));
			}
		}
	}

	/**
	 * @return the current list of appliances that the manager holds.
	 */
	public ArrayList<Appliance> getAppliances() {
		return applianceList;
	}

	/**
	 * @return a list of devices that the manager holds.
	 */
	public Device[] getDevices() {
		Device[] result = new Device[applianceList.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = applianceList.get(i).getDevice();
		}
		return result;
	}
}