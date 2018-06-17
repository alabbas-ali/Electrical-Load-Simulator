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

package de.frauas.jdemandmodel.simulator;

import de.frauas.jdemandmodel.appliance.ApplianceManager;
import de.frauas.jdemandmodel.appliance.Device;
import de.frauas.jdemandmodel.occupancy.CSVMarkovReader;
import de.frauas.jdemandmodel.occupancy.OccupancyModeler;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.Matrix;

public class ExampleSimulator {

	/**
	 * A quick example of a simulation run with specific devices. The result is printed on the console.
	 *
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {

		Input.setResidentCount(5);
		Input.setWeekend(true);
		
		OccupancyModeler occupancyModeler = new OccupancyModeler(new CSVMarkovReader());
		ApplianceManager applianceManager = new ApplianceManager(Device.REFRIGERATOR, Device.TV1, Device.KETTLE);

		Simulator simulator = new Simulator(occupancyModeler.getOccupancyValues(true),
				applianceManager.getAppliances());
		Matrix result = simulator.runDemandModelSimulation(0);

		System.out.println("OCC\tTOT\tLIG\tREF\tTV1\tKET");
		System.out.println(result.toString());
	}
}