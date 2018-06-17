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

package de.frauas.jdemandmodel.occupancy;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class TestOccupancyModeler {
	
	/**
	 * Tests the functionality of the occupancy modeler with dummy input data.
	 */
	@Test
	public void testGetOccValues() {
		
		OccupancyModeler occupancyModeler = new OccupancyModeler(new DummyMarkovReader());
		
		assertArrayEquals(new int[] { 0, 1, 2 }, occupancyModeler.getOccupancyValues(false));
	}
}