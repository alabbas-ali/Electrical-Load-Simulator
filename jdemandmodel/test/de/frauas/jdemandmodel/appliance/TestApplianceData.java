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

import org.junit.Test;

public class TestApplianceData {

	/**
	 * Checks the behavior if the power on parameter doesn't comply with the
	 * restrictions.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void constructorPowerOnTest() {

		new ApplianceData(-1, 0, 0, 0, 0, 0);
	}

	/**
	 * Checks the behavior if the power off parameter doesn't comply with the
	 * restrictions.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void constructorPowerOffTest() {

		new ApplianceData(0, -1, 0, 0, 0, 0);
	}

	/**
	 * Checks the behavior if the cycle time parameter doesn't comply with the
	 * restrictions.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void constructorCycleTimeTest() {

		new ApplianceData(0, 0, -1, 0, 0, 0);
	}

	/**
	 * Checks the behavior if the restart delay parameter doesn't comply with
	 * the restrictions.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void constructorRestartDelayTest() {

		new ApplianceData(0, 0, 0, -1, 0, 0);
	}

	/**
	 * Checks the behavior if the scale factor parameter doesn't comply with the
	 * restrictions.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void constructorScaleFactorTest() {

		new ApplianceData(0, 0, 0, 0, 3.0, 0);
	}

	/**
	 * Checks the behavior if the deviation parameter doesn't comply with the
	 * restrictions.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void constructorDeviationTest() {

		new ApplianceData(0, 0, 0, 0, 0, 3.0);
	}
}