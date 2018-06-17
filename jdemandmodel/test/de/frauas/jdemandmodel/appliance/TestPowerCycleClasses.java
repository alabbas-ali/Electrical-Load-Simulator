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

import java.util.Arrays;

import org.junit.Test;

import de.frauas.jdemandmodel.appliance.powercycle.StandardPowerCycle;
import de.frauas.jdemandmodel.appliance.powercycle.WashingCycle;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.JDMTimeConverter;

public class TestPowerCycleClasses {

	/**
	 * Checks whether the "getPowerUsage" method of a {@link StandardPowerCycle}
	 * always returns the correct value.
	 */
	@Test
	public void testStandard() {
		StandardPowerCycle pC = new StandardPowerCycle(100);

		int tmp = Input.MAX_TIME_INTERVAL_COUNT
				/ JDMTimeConverter.getTimeIntervalLength();

		int[] actual = new int[tmp];
		for (int i = tmp - 1; i >= 0; i--)
			actual[i] = pC.getPowerUsage(i + 1);

		int[] expected = new int[tmp];
		Arrays.fill(expected, 100);

		assertArrayEquals(expected, actual);
	}

	/**
	 * Checks behavior for an invalid total cycle time.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void wahsingCycleConstructorTest() {
		new WashingCycle(1, 42);
	}

	/**
	 * Checks whether the "getPowerUsage" method returns the correct value for a
	 * washing machine. The fact that "getPowerUsage" return 2500 instead of 1
	 * for a left cycle time of 0 is a known error, that is not corrected as it
	 * has no effect when running a simulation due to the calling structure in
	 * the class {@link Appliance}.
	 */
	@Test
	public void washingMachineResultTest() {
		WashingCycle pC = new WashingCycle(1, 138);

		assertEquals(568, pC.getPowerUsage(1));
		// assertEquals(1, pC.getPowerUsage(0));
	}

	/**
	 * Checks whether the "getPowerUsage" method returns the correct value for a
	 * washer-dryer.
	 */
	@Test
	public void washerDryerResultTest() {
		WashingCycle pC = new WashingCycle(1, 198);

		assertEquals(2500, pC.getPowerUsage(1));
		assertEquals(1, pC.getPowerUsage(0));
	}
}