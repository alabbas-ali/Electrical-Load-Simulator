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

public class ExampleMultipleAppliances {

	/**
	 * A simple example for running multiple appliances on your own.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {

		CSVApplianceFactory appFactory = CSVApplianceFactory.getInstance();

		ArrayList<Appliance> appliances = new ArrayList<Appliance>();

		appliances.add(appFactory.createAppliance(Device.DISH_WASHER));
		appliances.add(appFactory.createAppliance(Device.TV2));
		appliances.add(appFactory.createAppliance(Device.VACUUM));

		while (Appliance.hasNextTimeInterval()) {
			for (int i = 0; i < appliances.size(); i++) {
				Appliance a = appliances.get(i);
				a.nextWattage(2);
				System.out.print(a.getPower() + "W\t");
			}
			System.out.println();
		}
	}
}