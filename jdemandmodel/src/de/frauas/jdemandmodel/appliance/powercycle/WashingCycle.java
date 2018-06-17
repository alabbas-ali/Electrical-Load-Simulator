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

package de.frauas.jdemandmodel.appliance.powercycle;

import de.frauas.jdemandmodel.appliance.Appliance;

/**
 * This class simulates the power cycle of a washing machine or a washer-dryer.
 * 
 * @author lukas
 */
public class WashingCycle implements PowerCycle {

	private int offPower;
	private int totalCycleTime;

	/**
	 * Creates a new WashingCycle
	 * 
	 * @param offPower
	 *            the stand-by power [W] of the washer-dryer
	 * @param totalCycleTime
	 *            the total cycle time [min] of the washer-dryer
	 */
	public WashingCycle(int offPower, int totalCycleTime) {
		this.offPower = offPower;
		if (totalCycleTime != 138 && totalCycleTime != 198)
			throw new IllegalArgumentException("total cycle time: " + totalCycleTime
					+ ", a washing cycle can only be created with a total cycle time of 138 or 198");
		this.totalCycleTime = totalCycleTime;
	}

	/**
	 * Warning: For a WASHING_MACHINE (total cycle time 138) this method returns
	 * a wrong result for a left cycle time of 0 (returns 2500, but should
	 * return 1). However, the calling structure of the {@link Appliance} class
	 * handles this case in a simulation.
	 * 
	 * @param leftCycleTime
	 *            the time [min] that is left of the power cycle
	 * @return the power usage for the specified left cycle time.
	 */
	public int getPowerUsage(int leftCycleTime) {
		int cycleTime = (totalCycleTime - leftCycleTime) + 1;
		return getPower(cycleTime);
	}

	/**
	 * @param cycleTime
	 *            the current power cycle time [min]
	 * @return the power usage at the specified cycle time.
	 */
	private int getPower(int cycleTime) {

		if (between(cycleTime, 1, 8))
			return 73;
		else if (between(cycleTime, 9, 29))
			return 2056;
		else if (between(cycleTime, 30, 81))
			return 73;
		else if (between(cycleTime, 82, 92))
			return 73;
		else if (between(cycleTime, 93, 94))
			return 250;
		else if (between(cycleTime, 95, 105))
			return 73;
		else if (between(cycleTime, 106, 107))
			return 250;
		else if (between(cycleTime, 108, 118))
			return 73;
		else if (between(cycleTime, 119, 120))
			return 250;
		else if (between(cycleTime, 121, 131))
			return 73;
		else if (between(cycleTime, 132, 133))
			return 250;
		else if (between(cycleTime, 134, 138))
			return 568;
		else if (between(cycleTime, 139, 198))
			return 2500;
		else
			return offPower;
	}

	/**
	 * A method to check, whether an integer value lies within certain
	 * boundaries
	 * 
	 * @param x
	 *            the integer value to be checked
	 * @param lowerBoundary
	 *            the lower boundary
	 * @param upperBoundary
	 *            the upper boundary
	 * @return true, if x lies within the boundaries, otherwise false.
	 */
	private boolean between(int x, int lowerBoundary, int upperBoundary) {
		return lowerBoundary <= x && x <= upperBoundary;
	}
}