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

import org.junit.Test;

import de.frauas.jdemandmodel.appliance.Device;

public class TestJDMFileCreator {

	/**
	 * Tests whether the data in the result matrix complies with the number of given devices.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCheckCompliance() {
		Matrix matrix = new Matrix(new double[][] { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } });
		new JDMFileCreator(matrix, new Device[] { Device.HOB });
	}
}