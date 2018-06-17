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

package de.frauas.jdemandmodel.random;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.frauas.jdemandmodel.util.RandomUtil;

public class TestRandomUtil {

	/**
	 * Tests the method of {@link RandomUtil} that takes a minimum and maximum
	 * value as parameter.
	 */
	@Test
	public void randomMinMaxTest() {
		assertEquals(RandomUtil.nextDouble(-0.1, 0.1), 0, 0.1);
	}

	/**
	 * Tests whether the "nextBoolean" returns true for probability 1 and false
	 * for probability 0.
	 */
	@Test
	public void nextBooleanTest() {

		boolean testTrue = RandomUtil.nextBoolean(1);
		boolean testFalse = RandomUtil.nextBoolean(0);

		assertEquals(testTrue, true);
		assertEquals(testFalse, false);
	}
}