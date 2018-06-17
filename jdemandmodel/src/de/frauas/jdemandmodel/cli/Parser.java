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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.frauas.jdemandmodel.appliance.Device;
import de.frauas.jdemandmodel.lighting.Lighting;
import de.frauas.jdemandmodel.seed.Seed;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.JDMTimeConverter;

/**
 * This class can be used to parse a command line. Its core method is the method
 * "parse" that retrieves all information and fills the variables that can later
 * be accessed by using the getters.
 * 
 * @author lukas
 */
public class Parser {

	private Options options;
	private CommandLine line;

	private int residentCount = Input.DEFAULT_RESIDENT_COUNT;
	private int month = Input.DEFAULT_MONTH;
	private boolean weekend = false;
	private int timeResolution = JDMTimeConverter.CREST_TIME_INTERVAL_LENGTH;
	private int bulbConfig = Lighting.DEFAULT_BULB_CONFIG;
	private Device[] appliances;
	private String filename;
	private String seed;

	private boolean printOption = false;

	/**
	 * Creates a new Parser. The options are initialized.
	 */
	public Parser() {
		initializeOptions();
	}

	/**
	 * @return the chosen number of residents, if not chosen, returns a default
	 *         2.
	 */
	public int getResidentCount() {
		return residentCount;
	}

	/**
	 * @return the chosen month or default month 3.
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return true if option "weekend" was set, otherwise false.
	 */
	public boolean isWeekend() {
		return weekend;
	}

	/**
	 * @return the chosen time resolution, otherwise default 10.
	 */
	public int getTimeResolution() {
		return timeResolution;
	}

	/**
	 * @return the chosen bulb configuration, if not chosen, returns -1 (random
	 *         configuration)
	 */
	public int getBulbConfig() {
		return bulbConfig;
	}

	/**
	 * @return a list of the chosen devices, else a random list of devices.
	 */
	public Device[] getAppliances() {
		return appliances;
	}

	/**
	 * @return the chosen filename or a filename containing program title and
	 *         timestamp.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @return the filename of the seed to be loaded or the current filename
	 *         (for saving a seed).
	 */
	public String getSeed() {
		if (seed == null)
			return filename;
		return seed;
	}

	/**
	 * @return true, if printing options have been chosen, otherwise false
	 */
	public boolean printedOption() {
		return printOption;
	}

	/**
	 * This method parses the command line using the CommandLineParser and
	 * CommandLine class of the Apache Commons CLI
	 * (https://commons.apache.org/proper/commons-cli/). When parsing, a
	 * {@link ParseException} can occur, e.g. unrecognized options or missing
	 * arguments. In this case, the program is terminated. However, unrecognized
	 * arguments are not covered by this and are checked for in a private method
	 * that, in positive case, will produce a warning. Finally, the result of
	 * the parsing is assessed. The method directly takes action for the options
	 * "help" and "app-list", as in those cases no simulation is needed. Another
	 * special case is the option "seed-input", as it demands a second call of
	 * this method, but with the command line that was used when running the
	 * original simulation. How to handle this can be seen in the class
	 * {@link Main}. Lastly, if non of the above options were chosen, the
	 * variables will be filled with the corresponding defaults or chosen
	 * values.
	 * 
	 * @param args
	 *            the arguments passed to the program (command line)
	 * @throws ParseException
	 *             e.g. for unrecognized options or missing arguments
	 * @throws NumberFormatException
	 *             for options that require integers as arguments
	 */
	public void parse(String[] args) {

		String help = CLIOptions.HELP.getName();
		String appList = CLIOptions.APP_LIST.getName();
		String seedOpt = CLIOptions.LOAD_SEED.getName();
		String res = CLIOptions.RESIDENT_COUNT.getName();
		String monthOpt = CLIOptions.MONTH.getName();
		String time = CLIOptions.TIME_RESOLUTION.getName();
		String bulb = CLIOptions.LIGHTING.getName();

		CommandLineParser parser = new DefaultParser();

		try {
			line = parser.parse(options, args);
		} catch (ParseException exp) {
			System.err.println("Parsing failed. " + exp.getMessage());
			System.exit(1);
		}

		checkForUnrecognizedArguments();

		if (line.hasOption(help)) {
			checkCombined(help);
			printOption = true;
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Input.TITLE, options);
		} else if (line.hasOption(appList)) {
			checkCombined(appList);
			printOption = true;
			printAppliancesList();
		} else if (line.hasOption(seedOpt)) {
			checkCombined(seedOpt);
			seed = line.getOptionValue(seedOpt);
			Seed.setActive(true);
		} else {

			weekend = line.hasOption(CLIOptions.WEEKEND.getName());

			String[] appStrings = line.getOptionValues(CLIOptions.APPLIANCES.getName());
			if (appStrings == null)
				appStrings = new String[0];
			appliances = Device.getDevices(appStrings);

			filename = line.getOptionValue(CLIOptions.FILENAME.getName(),
					Input.TITLE + "_" + new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(new Date()));

			try {
				residentCount = Integer.valueOf(line.getOptionValue(res, String.valueOf(Input.DEFAULT_RESIDENT_COUNT)));

				month = Integer.valueOf(line.getOptionValue(monthOpt, String.valueOf(Input.DEFAULT_MONTH)));

				timeResolution = Integer.valueOf(
						line.getOptionValue(time, String.valueOf(JDMTimeConverter.CREST_TIME_INTERVAL_LENGTH)));

				bulbConfig = Integer.valueOf(line.getOptionValue(bulb, String.valueOf(Lighting.DEFAULT_BULB_CONFIG)));

			} catch (NumberFormatException exp) {
				System.err.println(exp.toString() + ". The arguments for " + res + ", " + monthOpt + ", " + time
						+ " and " + bulb + " must all be integers.");
				System.exit(0);
			}
		}
	}

	/**
	 * Initializes the options from the enum type CLIOptions. For further detail
	 * see {@link CLIOptions}.
	 */
	private void initializeOptions() {
		options = new Options();
		options.addOption(CLIOptions.HELP.getOption());
		options.addOption(CLIOptions.RESIDENT_COUNT.getOption());
		options.addOption(CLIOptions.WEEKEND.getOption());
		options.addOption(CLIOptions.MONTH.getOption());
		options.addOption(CLIOptions.TIME_RESOLUTION.getOption());
		options.addOption(CLIOptions.LIGHTING.getOption());
		options.addOption(CLIOptions.APPLIANCES.getOption());
		options.addOption(CLIOptions.APP_LIST.getOption());
		options.addOption(CLIOptions.FILENAME.getOption());
		options.addOption(CLIOptions.LOAD_SEED.getOption());
	}

	/**
	 * Unrecognized arguments are not covered by the {@link ParseException} and
	 * are checked for in this method.
	 * 
	 * @exception warning
	 *                prints a warning if unrecognized arguments were found.
	 */
	private void checkForUnrecognizedArguments() {
		if (line.getArgs().length != 0) {
			System.err.println("Warning: Unrecognized arguments " + Arrays.toString(line.getArgs())
					+ ". These arguments will be ignored.");
		}
	}

	/**
	 * This method checks whether an option is combined with another option. It
	 * should be used for the options "help", "app-list" and "seed-input". In
	 * case of combination it will throw an exception.
	 * 
	 * @param opt
	 *            the option to be checked (should be "help", "app-list" or
	 *            "seed")
	 * @throws IllegalArgumentException
	 *             if there is more than one option
	 */
	private void checkCombined(String opt) {
		if (line.getOptions().length > 1)
			throw new IllegalArgumentException("The option " + opt + " cannot be combined with other options.");
	}

	/**
	 * This method represents the action taken when the option "app-list" was
	 * chosen. It prints a list of all possible device strings.
	 */
	private void printAppliancesList() {
		StringBuilder stringBuilder = new StringBuilder();
		Device[] devices = Device.values();
		for (int i = 0; i < devices.length; i++) {
			Device d = devices[i];
			if (i != 0)
				stringBuilder.append("\n");
			stringBuilder.append(d.name());
			if (d == Device.DESWH)
				stringBuilder.append("\t(domestic electric storage water heater)");
			else if (d == Device.E_INST)
				stringBuilder.append("\t(another water heating appliance)");
		}
		System.out.println(stringBuilder.toString());
	}
}