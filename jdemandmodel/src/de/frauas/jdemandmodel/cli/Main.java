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

package de.frauas.jdemandmodel.cli;

import de.frauas.jdemandmodel.appliance.ApplianceManager;
import de.frauas.jdemandmodel.occupancy.CSVMarkovReader;
import de.frauas.jdemandmodel.occupancy.OccupancyModeler;
import de.frauas.jdemandmodel.seed.Seed;
import de.frauas.jdemandmodel.simulator.Simulator;
import de.frauas.jdemandmodel.util.JDMFileCreator;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.JDMTimeConverter;
import de.frauas.jdemandmodel.util.Matrix;

/**
 * This class contains the main method and combines the command line interface
 * with the simulator. Therefore, all features the simulator has are included in
 * this method. For details, look below.
 * 
 * @author lukas
 */
public class Main {

	/**
	 * This method puts all functionality of this project together.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		/**
		 * First, a parser is created, to parse the passed arguments. For
		 * further detail see {@link Parser}. Note that the parser will directly
		 * take action if the options "help" or "app-list" were chosen. In these
		 * cases, no further steps are required and the program terminates.
		 * However, if those options were not chosen the parser now holds all
		 * important variables that can be accessed by using the getters.
		 */
		Parser parser = new Parser();
		parser.parse(args);

		if (!parser.printedOption()) {

			/**
			 * First, it is checked whether a seed is active (meaning the
			 * "load-seed" option was chosen by the user), because in this case
			 * the parser will need to be called again with different arguments,
			 * i.e. the ones that were originally used when this seed was saved.
			 */
			if (Seed.isActive()) {
				String[] seedArgs = Seed.loadSeeds(parser.getSeed());
				parser.parse(seedArgs);
			}

			/**
			 * Some of the input variables are declared globally in the class
			 * Input. These include the number of residents, the month and the
			 * weekend flag. The setters check whether the values lie within the
			 * correct range and might throw an exception.
			 */
			Input.setResidentCount(parser.getResidentCount());
			Input.setMonth(parser.getMonth());
			Input.setWeekend(parser.isWeekend());

			/**
			 * Next, an ApplianceManager and an OccupancyModeler are created, as
			 * the simulator needs both to run. The occupancy modeler uses the
			 * resident count and weekend flag internally while the appliance
			 * manager needs the list of appliances passed as a direct
			 * parameter.
			 */
			OccupancyModeler occupancyModeler = new OccupancyModeler(new CSVMarkovReader());
			ApplianceManager applianceManager = new ApplianceManager(parser.getAppliances());

			/**
			 * The constructor of a Simulator needs occupancy values, which can
			 * now be created with the occupancy modeler, and an a appliance
			 * list, that the appliance manager holds. The result of the
			 * simulation run has to be cached for further operations.
			 */
			Simulator simulator = new Simulator(occupancyModeler.getOccupancyValues(true),
					applianceManager.getAppliances());
			Matrix result = simulator.runDemandModelSimulation(parser.getBulbConfig());

			/**
			 * Next the JDMTimeConverter is used to convert the result matrix to
			 * the time resolution chosen by the user.
			 */
			JDMTimeConverter.setTimeIntervalLength(parser.getTimeResolution());
			result = JDMTimeConverter.convert(result);

			/**
			 * To create an output file, a result matrix is passed to the
			 * JDMFileCreator constructor, as well as the used device to put in
			 * the header. The file is then created by passing the chosen
			 * filename to the "createNewFile" method of the creator.
			 */
			JDMFileCreator jdmFileCreator = new JDMFileCreator(result, applianceManager.getDevices());
			jdmFileCreator.createNewFile(parser.getFilename());

			/**
			 * Finally, in case no seed was loaded, the current simulation
			 * result should be saved to a file. Here, the same name as the
			 * chosen filename is used (but will be created in a different
			 * folder and with the .seed extension). The used arguments must be
			 * saved as well, as a seed only works if the conditions of the
			 * simulation are exactly the same as during the original run.
			 */
			if (!Seed.isActive())
				Seed.writeToFile(parser.getFilename(), args);
		}
	}
}