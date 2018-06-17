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

public class ExampleApplianceRun {
	
	private static final Device DEVICE = Device.WASHING_MACHINE;
	private static final int OCCUPANCY = 5;
	
	/**
	 * An example run of a {@value #DEVICE} for a constant occupancy of {@value #OCCUPANCY}.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		
		CSVApplianceFactory factory = CSVApplianceFactory.getInstance();
		
		Appliance appliance = factory.createAppliance(DEVICE);
		
		StringBuilder sb = new StringBuilder();
		int timeInterval = 0;
		while(Appliance.hasNextTimeInterval()) {
			appliance.nextWattage(OCCUPANCY);
			sb.append((timeInterval+1));
			sb.append(":\t");
			sb.append(appliance.getPower());
			sb.append("W\n");
			timeInterval++;
		}
		
		System.out.println(sb.toString());
	}
}