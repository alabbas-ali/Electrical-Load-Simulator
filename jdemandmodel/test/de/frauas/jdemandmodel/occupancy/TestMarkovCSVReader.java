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
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.frauas.jdemandmodel.util.Input;

public class TestMarkovCSVReader {

	/**
	 * Tests whether the reader returns a correct result for the initial probability array.
	 */
	@Test
	public void initialResultTest() {
		
		Input.setResidentCount(2);
		Input.setWeekend(false);
		
		CSVMarkovReader markovCSVReader = new CSVMarkovReader();
		assertArrayEquals(new double[] { 0.798, 0.145, 0.058, 0.000, 0.000, 0.000 }, markovCSVReader.readInitialProbabilities(), 0);
	}
	
	/**
	 * Checks a specific value from the returned Markov list.
	 */
	@Test
	public void markovListResultTest() {
		
		Input.setResidentCount(2);
		Input.setWeekend(false);
		
		CSVMarkovReader markovCSVReader = new CSVMarkovReader();
		assertEquals(0.032, markovCSVReader.readTransitionMatrices().get(127).get(2, 1), 0);
	}
}