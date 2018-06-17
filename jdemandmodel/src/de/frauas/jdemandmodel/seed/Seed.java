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

package de.frauas.jdemandmodel.seed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import de.frauas.jdemandmodel.util.RandomUtil;

/**
 * This class takes care of the seeds. Note that one seed is actually a list of
 * many seeds - namely all system nano times when {@link RandomUtil} was used
 * during the simulation. Therefore, when using a seed (setting active true and
 * calling the "loadSeeds" method) it must be ensured, that the simulation
 * procedure will be exactly the same as it was when the seed was saved.
 * Otherwise, the simulation result will NOT be the same. Similarly, when using
 * a command line interface the same arguments should be parsed.
 * 
 * @author lukas
 *
 */
public class Seed {

	private static ArrayList<Long> seeds = new ArrayList<Long>();
	private static boolean active = false;
	private static int callCounter = 0;

	private static final String DIR = "data/seeds";
	private static final String EXT = ".seed";
	private static final String SEP = " "; // command line

	/**
	 * Activates the seed. This method should be called before or after loading
	 * a seed, as the seed will only be used if active equals true. In the same
	 * way active should be set false (default) before starting the simulation
	 * procedure if no seed should be used / a seed should be saved.
	 * 
	 * @param setActive
	 *            set true if an existing seed should be used, else set false
	 *            (default)
	 */
	public static void setActive(boolean setActive) {
		if (setActive)
			callCounter = 0;
		else
			seeds.clear();
		active = setActive;
	}

	/**
	 * @return true, if a seed is active (there's a seed to be used), otherwise
	 *         false
	 */
	public static boolean isActive() {
		return active;
	}

	/**
	 * This method saves a seed at the relative path "seeds/< filename >.seed"
	 * with the used arguments (command line interface) added in the first line.
	 * Those arguments can be read when the file is loaded. If no CLI is used,
	 * an empty string array can be passed.
	 * 
	 * @param filename
	 *            the seed file will be saved under this name
	 * @param args
	 *            the command line arguments/options
	 * @throws FileNotFoundException
	 */
	public static void writeToFile(String filename, String[] args) {

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			stringBuilder.append(args[i] + SEP);
		}
		File file = new File(DIR + "/" + filename + EXT);
		if( file.exists() )
			System.err.println("Warning: File \"" + file.getName() + "\" is overwritten.");
		file.getParentFile().mkdirs();
		try {
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.println(stringBuilder.toString());
			for (int i = 0; i < seeds.size(); i++) {
				printWriter.println(seeds.get(i));
			}
			printWriter.close();
		} catch (FileNotFoundException e) {
			System.err.println("Invalid filename: \"" + file.getName() + "\".");
			System.exit(1);
		}
	}

	/**
	 * This method loads a seed from the file with the given filename. The file
	 * is expected to contain one line with possible command line
	 * options/arguments and a list with as many seeds as will be needed for the
	 * simulation process.
	 * 
	 * @param filename filename of the file of the seeds that should be used
	 * @return an array of arguments (command line).
	 */
	public static String[] loadSeeds(String filename) {
		seeds.clear();
		BufferedReader bufferedReader = null;
		String[] result = null;
		String currentLine;
		try {
			bufferedReader = new BufferedReader(new FileReader(DIR + "/" + filename + EXT));
			String tmp = bufferedReader.readLine();
			result = tmp.split(SEP);
			while ((currentLine = bufferedReader.readLine()) != null) {
				seeds.add(Long.valueOf(currentLine));
			}
			if (bufferedReader != null)
				bufferedReader.close();

		} catch (IOException e) {
			System.err.println(e.toString());
			System.exit(1);
		}
		callCounter = 0;
		return result;
	}

	/**
	 * This method should be called whenever a randomly generated variable is
	 * used in the program, so either the seed used for it can be saved or a
	 * given seed can be loaded in case Seed is set active.
	 * 
	 * @param random
	 *            the Random instance of which the seed is to be set
	 */
	public static void setSeed(Random random) {
		if (isActive()) {
			random.setSeed(seeds.get(callCounter++));
		} else {
			long randomSeed = System.nanoTime();
			seeds.add(randomSeed);
			random.setSeed(randomSeed);
		}
	}
}