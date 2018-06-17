package de.frauas.jdemandmodel.lighting;

import org.junit.Test;

import de.frauas.jdemandmodel.occupancy.CSVMarkovReader;
import de.frauas.jdemandmodel.occupancy.OccupancyModeler;
import de.frauas.jdemandmodel.seed.Seed;

public class TestLighting {

	/**
	 * Checks if the Lighting constructor was called with an integer array of
	 * length 1440.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void occupancyValuesLengthTest() {
		Seed.setActive(false);
		OccupancyModeler occupancyModeler = new OccupancyModeler(new CSVMarkovReader());
		new Lighting(occupancyModeler.getOccupancyValues(false));
	}

	/**
	 * Checks the behavior if an invalid value is passed to the setter of the
	 * house number.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void setHouseNumberTest() {
		Seed.setActive(false);
		OccupancyModeler occupancyModeler = new OccupancyModeler(new CSVMarkovReader());
		Lighting lighting = new Lighting(occupancyModeler.getOccupancyValues(false));
		lighting.setHouseNumber(-2);
	}
}