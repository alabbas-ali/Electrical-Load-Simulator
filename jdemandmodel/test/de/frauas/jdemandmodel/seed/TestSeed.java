package de.frauas.jdemandmodel.seed;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import de.frauas.jdemandmodel.occupancy.CSVMarkovReader;
import de.frauas.jdemandmodel.occupancy.OccupancyModeler;

public class TestSeed {

	/**
	 * Compares the the modeling results when first saving a seed and then using
	 * it as an input (they should be the same).
	 */
	@Test
	public void testOccupancy() {
		Seed.setActive(false);
		OccupancyModeler occModeler = new OccupancyModeler(new CSVMarkovReader());
		int[] expected = occModeler.getOccupancyValues(true);

		Seed.writeToFile("../test/de/frauas/jdemandmodel/seed/test1", new String[0]);

		Seed.loadSeeds("../test/de/frauas/jdemandmodel/seed/test1");
		Seed.setActive(true);
		OccupancyModeler occModeler2 = new OccupancyModeler(new CSVMarkovReader());
		int[] actual = occModeler2.getOccupancyValues(true);

		assertArrayEquals(expected, actual);
	}
}