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

/**
 * In a standard power cycle the power on value doesn't vary.
 * 
 * @author lukas
 */
public class StandardPowerCycle implements PowerCycle {

	private int onPower;

	/**
	 * Creates a new StandardPowerCycle.
	 * 
	 * @param onPower
	 *            the on power value to be returned, if the appliance is turned
	 *            on
	 */
	public StandardPowerCycle(int onPower) {
		this.onPower = onPower;
	}

	/**
	 * @param leftCycleTime
	 *            the time [min] that is left of the power cycle
	 * @return a static on power value, regardless of the left cycle time.
	 */
	public int getPowerUsage(int leftCycleTime) {
		return onPower;
	}
}