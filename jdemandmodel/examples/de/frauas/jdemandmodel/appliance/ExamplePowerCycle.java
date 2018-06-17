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

import de.frauas.jdemandmodel.appliance.powercycle.WashingCycle;

public class ExamplePowerCycle {

	/**
	 * This demonstrates the behavior of the "getPowerUsage" method of a {@link WashingCycle}.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		WashingCycle wmc = new WashingCycle(1,138);
		
		for(int i=138; i > 0; i--) {
			System.out.println(wmc.getPowerUsage(i));
		}
	}
}
