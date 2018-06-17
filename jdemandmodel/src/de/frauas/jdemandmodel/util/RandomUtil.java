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

package de.frauas.jdemandmodel.util;

import java.util.Random;

import de.frauas.jdemandmodel.seed.Seed;

/**
 * This is a utilizing class for whenever random behavior is needed throughout
 * the program. It is important to always use this class instead of
 * java.util.Random, in order for the seed functionality to work. The class uses
 * only one instance of java.util.Random and all methods call the "setSeed"
 * method of {@link Seed}, that will determine, whether a given or a random seed
 * will be used.
 * 
 * @author lukas
 */
public class RandomUtil {

	private static Random random = new Random();

	/**
	 * @return a random double value similar to the "nextDouble" method of
	 *         {@link Random}.
	 */
	public static double nextDouble() {
		Seed.setSeed(random);
		return random.nextDouble();
	}

	/**
	 * @param min
	 *            the lower boundary
	 * @param max
	 *            the upper boundary
	 * @return a random double value that lies within the specified boundaries.
	 */
	public static double nextDouble(double min, double max) {
		Seed.setSeed(random);
		double rdm = random.nextDouble();
		return min + rdm * Math.abs(max - min);
	}

	/**
	 * @param trueProbability
	 *            the probability the method should return true with.
	 * @return true with the specified probability, false with 1 - the specified
	 *         probability.
	 */
	public static boolean nextBoolean(double trueProbability) {
		Seed.setSeed(random);
		return random.nextDouble() < trueProbability;
	}

	/**
	 * @return a random integer value similar to the "nextInt" method of
	 *         {@link Random}.
	 */
	public static int nextInt(int i) {
		Seed.setSeed(random);
		return random.nextInt(i);
	}
}