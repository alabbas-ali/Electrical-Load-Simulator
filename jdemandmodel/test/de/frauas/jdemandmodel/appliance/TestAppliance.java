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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import de.frauas.jdemandmodel.reader.TestData;
import de.frauas.jdemandmodel.util.JDMTimeConverter;
import de.frauas.jdemandmodel.util.Matrix;

public class TestAppliance {

	/**
	 * Tests whether the useProfile contains 6 double arrays.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void useProfileArrayListDimensionTest() {

		Matrix useProfile = TestData.getUseProfile(new int[] { 144, 144, 144 }, 1.0);

		new Appliance(Device.DVD_PLAYER, TestData.APPL_DATA, useProfile, TestData.POWER_CYCLE);
	}

	/**
	 * Tests whether the double arrays of useProfile all contain 144 values.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void useProfileArrayDimensionTest() {

		Matrix useProfile = TestData.getUseProfile(new int[] { 144, 55, 144, 144, 144, 144 }, 1.0);

		new Appliance(Device.DVD_PLAYER, TestData.APPL_DATA, useProfile, TestData.POWER_CYCLE);
	}

	/**
	 * Tests whether the values in useProfile are valid probability values (0 <=
	 * x <= 1).
	 */
	@Test(expected = IllegalArgumentException.class)
	public void useProfileValueTest() {

		Matrix useProfile = TestData.getUseProfile(new int[] { 144, 144, 144, 144, 144, 144 }, 4.0);

		new Appliance(Device.DVD_PLAYER, TestData.APPL_DATA, useProfile, TestData.POWER_CYCLE);
	}

	/**
	 * Tests whether a whole run over all time intervals of one specific device
	 * produces the correct power data.
	 */
	@Test
	public void applianceRunResultTest() {

		JDMTimeConverter.setTimeIntervalLength(10);

		Matrix useProfile = TestData.getUseProfile(new int[] { 144, 144, 144, 144, 144, 144 }, 1.0);

		Appliance app = new Appliance(Device.TV1, TestData.APPL_DATA, useProfile, TestData.POWER_CYCLE);
		Integer actual[] = new Integer[1440];

		int i = 1;
		while (Appliance.hasNextTimeInterval()) {
			app.nextWattage(1);
			actual[i - 1] = app.getPower();
			i++;
		}

		int pow = TestData.TEST_POWER_ON;

		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for (int j = 0; j < 50; j++)
			tmp.add(pow);
		for (int j = 0; j < 20; j++)
			tmp.add(5);

		ArrayList<Integer> exp = new ArrayList<Integer>();
		for (int j = 0; j < 20; j++)
			exp.addAll(tmp);
		for (int j = 0; j < 40; j++)
			exp.add(pow);

		Integer[] expected = exp.toArray(new Integer[exp.size()]);

		assertArrayEquals(expected, actual);
	}

	/**
	 * Tests whether the "hasNextTimeInterval" method returns the correct
	 * result.
	 */
	@Test
	public void hasNextTimeIntervalTest() {
		for (int i = 0; i < 1439; i++)
			Appliance.hasNextTimeInterval();
		assertEquals(true, Appliance.hasNextTimeInterval());
		assertEquals(false, Appliance.hasNextTimeInterval());
		assertEquals(true, Appliance.hasNextTimeInterval());
	}
}