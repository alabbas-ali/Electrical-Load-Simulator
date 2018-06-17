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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestMarkovChain {

	/**
	 * Tests if the start states are read in correctly by the
	 * {@link MarkovReader}.
	 */
	@Test
	public void testGetStartStates() {
		DummyMarkovReader dummy = new DummyMarkovReader();
		MarkovChain markovChain = new MarkovChain(dummy.readInitialProbabilities(), dummy.readTransitionMatrices());

		assertEquals(0, markovChain.getInitialState(), 0);
	}

	/**
	 * Tests if a state transition works properly.
	 */
	@Test
	public void testGetStateAt() {
		DummyMarkovReader dummy = new DummyMarkovReader();
		MarkovChain markovChain = new MarkovChain(dummy.readInitialProbabilities(), dummy.readTransitionMatrices());

		assertEquals(1, markovChain.getStateAt(1, 0), 0);
	}
}