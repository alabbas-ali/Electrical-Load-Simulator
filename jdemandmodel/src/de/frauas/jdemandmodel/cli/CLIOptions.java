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

import org.apache.commons.cli.Option;

import de.frauas.jdemandmodel.util.Input;

/**
 * This enum type lists all options that can be set in the command line. 
 * There are getters for the {@link Option} and the longOpt-String.
 * 
 * @author lukas
 */
public enum CLIOptions {

	HELP("h", false, "help", "print this message"),
	RESIDENT_COUNT("r", true, "resident-count",
					"use <arg> (0-5, default: " + Input.DEFAULT_RESIDENT_COUNT + ") as the number of residents in the "
					+ "house for the simulation"), 
	WEEKEND("we", false, "weekend", "use weekend statistical data for the simulation instead of default weekday data"),
	MONTH("m", true, "month", "use <arg> (1-12, default: " + Input.DEFAULT_MONTH + ") to apply corresponding statistics "
					+ "to the simulation"),
	LIGHTING("l", true, "lighting", "add lighting demand to the simulation result; there are 100 different bulb "
					+ "configurations, by using the numbers 1 to 100 as an argument one of them can be chosen, 0 will "
					+ "turn the lighting demand off and -1 randomly selects a bulb configuration (default)"),
	TIME_RESOLUTION("t", true, "time-resolution", Input.TITLE + " simulates demand for all 1440 minutes of the day, but "
					+ "by default uses a 10-minute time interval resolution for the output, to alter this time resolution "
					+ "use <arg> (1-1440, default: 10)"),
	APPLIANCES("a", true, "appliances", "use <arg> (list of strings) to specify the appliances installed in the household,"
					+ "\nfor all possible appliances see -al; if any of the strings equals \"none\", no devices will be "
					+ "used, by default appliances are chosen randomly using statistical data"),
	APP_LIST("al", false, "app-list", "print the names of all possible appliances"),
	FILENAME("o", true, "output", "place output in file <arg>.csv, by default the file is saved with a timestamp"),
	LOAD_SEED("s", true, "seed-input", "use the file <arg>.seed from the seed folder as an input for the simulation (this "
					+ "option cannot be combined with other options)");
	
	private String name;
	private boolean hasArg;
	private String nameLong;
	private String description;

	/**
	 * All options have a short and a long name, a description and a flag, whether they need an argument.
	 * @param name the shortcut name
	 * @param hasArg true, if the option demands an argument, otherwise false
	 * @param nameLong the long name
	 * @param description a description of how to use the option
	 */
	CLIOptions(String name, boolean hasArg, String nameLong, String description) {
		this.name = name;
		this.hasArg = hasArg;
		this.nameLong = nameLong;
		this.description = description;
	}
	
	/**
	 * @return an {@link Option} from the CLIOption's data.
	 */
	public Option getOption() {
		if(name == "a")
			return Option.builder(name).longOpt(nameLong).hasArgs().desc(description).build();
		else return Option.builder(name).longOpt(nameLong).hasArg(hasArg).desc(description).build();
	}

	/**
	 * @return the long name of the CLIOption
	 */
	public String getName() {
		return nameLong;
	}
}