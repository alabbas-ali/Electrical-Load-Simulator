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

import de.frauas.jdemandmodel.appliance.powercycle.PowerCycle;
import de.frauas.jdemandmodel.appliance.powercycle.StandardPowerCycle;
import de.frauas.jdemandmodel.appliance.powercycle.WashingCycle;
import de.frauas.jdemandmodel.reader.ActivityReader;
import de.frauas.jdemandmodel.reader.ApplianceReader;

/**
 * This class is for creating appliances. As it is a singleton, the
 * "getInstance" method has to be used to construct the factory. For further
 * detail on the different types of appliances see {@link Appliance}.
 * 
 * @author lukas
 */
public class CSVApplianceFactory {

	private ApplianceReader applianceReader;
	private ActivityReader activityReader;

	private static CSVApplianceFactory uniqueFactory = new CSVApplianceFactory();

	/**
	 * Here the readers are instantiated. Note that all the reading-in happens
	 * in the constructors, rather than later in the "createAppliance" method
	 * when calling the "get" methods.
	 */
	private CSVApplianceFactory() {
		applianceReader = new ApplianceReader();
		activityReader = new ActivityReader();
	}

	/**
	 * @return a unique CSVApplianceFactory.
	 */
	public static CSVApplianceFactory getInstance() {
		return uniqueFactory;
	}

	/**
	 * This method is the core of the CSVApplianceFactory as it creates an
	 * {@link Appliance}. The standard appliance has a corresponding device,
	 * {@link ApplianceData}, a use profile ({@link ActivityReader}) and a
	 * {@link StandardPowerCycle}. However, some appliances have an activity
	 * value below 0 or they behave in a specific way and therefore have their
	 * own class ({@link ElectricSpaceHeating}, {@link StorageHeater}). For
	 * further detail on the different types of appliances see
	 * {@link Appliance}.
	 * 
	 * @return an appliance containing all the data belonging to the specified
	 *         device.
	 */
	public Appliance createAppliance(Device device) {

		ApplianceData data = applianceReader.get(device);

		if (device == Device.ELEC_SPACE_HEATING)
			return new ElectricSpaceHeating(data, getPowerCycle(device, data));
		else if (device == Device.STORAGE_HEATER)
			return new StorageHeater(data, getPowerCycle(device, data));
		else if (device.getActivity() < 0)
			return new Appliance(device, data, getPowerCycle(device, data));
		else
			return new Appliance(device, data, activityReader.get(device), getPowerCycle(device, data));
	}

	/**
	 * Some appliances have a specific power cycle.
	 * 
	 * @param device
	 *            the device of which to return the corresponding power cycle.
	 * @param data
	 *            the appliance's data needs to be passed to retrieve the on and
	 *            off power values.
	 * @return the power cycle that corresponds to device.
	 */
	private PowerCycle getPowerCycle(Device device, ApplianceData data) {
		if (device == Device.WASHING_MACHINE || device == Device.WASHER_DRYER)
			return new WashingCycle(data.getOffPower(), data.getCycleTime());
		else
			return new StandardPowerCycle(data.getOnPower());
	}
}