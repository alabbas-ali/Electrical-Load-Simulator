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

import java.util.Arrays;

public class ExampleApplianceManager {

	/**
	 * This example shows how the constructor of the {@link ApplianceManager}
	 * works. Passing the devices from a string array is how it is done in the
	 * parser, so this example can be used for testing the behavior for
	 * different inputs.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {

		/**
		 * try putting none / "none" / "TV1", "invalid" / "invalid" / ...
		 */
		String[] devices = { "REFRIGERATOR", "invalid", "TV3" };

		ApplianceManager applianceManager = new ApplianceManager(Device.getDevices(devices));

		System.out.println(Arrays.toString((applianceManager.getDevices())));
	}
}