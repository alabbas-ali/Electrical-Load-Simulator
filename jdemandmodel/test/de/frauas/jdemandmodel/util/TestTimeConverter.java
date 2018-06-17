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

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import de.frauas.jdemandmodel.AllTests;
import de.frauas.jdemandmodel.appliance.Appliance;
import de.frauas.jdemandmodel.appliance.CSVApplianceFactory;
import de.frauas.jdemandmodel.appliance.Device;
import de.frauas.jdemandmodel.seed.Seed;

public class TestTimeConverter {

	/**
	 * Tests the conversion of the simulation result of a storage heater. For
	 * some reason this fails when running {@link AllTests}, but doesn't when
	 * tested independently.
	 */
	@Test
	public void convertResultTest() {

		Seed.setActive(false);
		Input.setMonth(1);

		CSVApplianceFactory factory = CSVApplianceFactory.getInstance();
		Appliance storageHeater = factory
				.createAppliance(Device.STORAGE_HEATER);

		double[] expected = new double[10];
		int power = storageHeater.getApplianceData().getOnPower();
		expected[0] = ((144 - 30) * power) / 144;
		expected[1] = power;
		expected[2] = (((storageHeater.getApplianceData().getCycleTime()) - (2 * 144 - 30)) * power) / 144;
		for (int j = 3; j < 10; j++)
			expected[j] = 0;

		Matrix actualMatrix = new Matrix(
				new double[Input.MAX_TIME_INTERVAL_COUNT][1]);
		int i = 0;
		while (Appliance.hasNextTimeInterval()) {
			storageHeater.nextWattage(1);
			actualMatrix.set(i, 0, storageHeater.getPower());
			i++;
		}
		JDMTimeConverter.setTimeIntervalLength(144);
		double[] actual = JDMTimeConverter.convert(actualMatrix).getCol(0);

		assertArrayEquals(expected, actual, 1);
	}
}