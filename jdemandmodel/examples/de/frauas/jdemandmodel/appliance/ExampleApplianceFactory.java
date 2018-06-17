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

public class ExampleApplianceFactory {

	/**
	 * This example can be used to receive some information about a specified
	 * appliance. It shows how the factory works.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {

		CSVApplianceFactory factory = CSVApplianceFactory.getInstance();

		Appliance appliance = factory.createAppliance(Device.RECEIVER);

		System.out.println("device:\t\t" + appliance.getDevice());
		System.out.println("activity:\t" + appliance.getDevice().getActivity());
		System.out.print("useProfile:\t");
		if (appliance.getUseProfile() == null)
			System.out.print("null\n");
		else
			System.out.print("size " + appliance.getUseProfile().getSize() + "\n");
		System.out.println("power cycle:\t" + appliance.getPowerCycle().getClass().getSimpleName());
		System.out.println();
		appliance.getApplianceData().print();
	}
}