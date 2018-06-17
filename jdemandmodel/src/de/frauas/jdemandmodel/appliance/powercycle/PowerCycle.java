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
 * With a power cycle, the power an appliance will use up during a power cycle
 * can be defined.
 * 
 * @author lukas
 */
public interface PowerCycle {

	/**
	 * @param leftCycleTime
	 *            time[min] left of the current power cycle
	 * @return the power the appliances uses at the specified point in time.
	 */
	public int getPowerUsage(int leftCycleTime);
}