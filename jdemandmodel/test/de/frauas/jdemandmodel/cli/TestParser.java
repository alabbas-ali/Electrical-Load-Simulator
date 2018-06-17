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

package de.frauas.jdemandmodel.cli;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestParser {
	
	/**
	 * Checks for exception if help option is combined with other options.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void inputCombinedHelpTest() {
		String[] args = { "-h", "-r", "3" };
		Parser parser = new Parser();
		parser.parse(args);
	}

	/**
	 * Checks for exception if app-list option is combined with other options.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void inputCombinedAppListTest() {
		String[] args = { "-al", "-we" };
		Parser parser = new Parser();
		parser.parse(args);
	}

	/**
	 * Checks for exception if seed option is combined with other options.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void inputCombinedSeedTest() {
		String[] args = { "-s", "test1", "-al" };
		Parser parser = new Parser();
		parser.parse(args);
	}

	/**
	 * Tests whether setting the weekend flag works.
	 */
	@Test
	public void weekendTest() {
		String[] args = { "-we" };
		Parser parser = new Parser();
		assertEquals(false, parser.isWeekend());
		parser.parse(args);
		assertEquals(true, parser.isWeekend());
	}

	/**
	 * Tests the default value of month and whether the parsing works.
	 */
	@Test
	public void monthTest() {
		String[] args = { "-m", "4" };
		Parser parser = new Parser();
		assertEquals(3, parser.getMonth());
		parser.parse(args);
		assertEquals(4, parser.getMonth());
	}

	/**
	 * Tests the default time resolution and whether the parsing works.
	 */
	@Test
	public void timeTest() {
		String[] args = { "-t", "30" };
		Parser parser = new Parser();
		assertEquals(10, parser.getTimeResolution());
		parser.parse(args);
		assertEquals(30, parser.getTimeResolution());
	}
}