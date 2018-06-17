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

package de.frauas.jdemandmodel.appliance;

/**
 * This class encapsulates five attributes of an appliance:
 * <p>
 * ON power: power[W] when the appliance is turned on
 * </p>
 * <p>
 * OFF power: power[W] when the appliance is turned off
 * </p>
 * <p>
 * cycle time: time[min] a cycle of this appliance being turned on lasts
 * </p>
 * <p>
 * restart delay: time[min] to delay before this appliance can be turned on
 * again after being turned off
 * </p>
 * <p>
 * scale factor: probability scaling factor to achieve a certain amount of
 * on-power-cycles per year
 * </p>
 * 
 * @author lukas
 */
public class ApplianceData {

	private int onPower;
	private int offPower;
	private int cycleTime;
	private int restartDelay;
	private double scaleFactor;

	/**
	 * Constructs a new ApplianceData instance.
	 * 
	 * @param onPower
	 *            power[W] when the appliance is turned on
	 * @param offPower
	 *            power[W] when the appliance is turned off
	 * @param cycleTime
	 *            time[min] a cycle of this appliance being turned on lasts
	 * @param restartDelay
	 *            time[min] to delay before this appliance can be turned on
	 *            again after being turned off
	 * @param scaleFactor
	 *            probability scaling factor to achieve a certain amount of
	 *            on-power-cycles per year
	 * @param deviation
	 *            optional deviation to be added to onPower, use 0 for this
	 *            value to have no effect
	 * @throws IllegalArgumentException
	 *             if onPower is less than or equal to 0
	 * @throws IllegalArgumentException
	 *             if offPower is less than 0
	 * @throws IllegalArgumentException
	 *             if the cycle time is less than or equal to 0
	 * @throws IllegalArgumentException
	 *             if the restart delay is less than 0
	 * @throws IllegalArgumentException
	 *             if the scale factor is less than 0 or greater than 1
	 * @throws IllegalArgumentException
	 *             if the deviation is less than -1 or greater than 1
	 */
	public ApplianceData(int onPower, int offPower, int cycleTime, int restartDelay, double scaleFactor,
			double deviation) {

		if (onPower <= 0)
			throw new IllegalArgumentException("ON power value must be greater than 0.");
		if (offPower < 0)
			throw new IllegalArgumentException("OFF power value must be 0 or positive.");
		if (cycleTime < 0)
			throw new IllegalArgumentException("Cycle time of an appliance must be 0 or positive.");
		if (restartDelay < 0)
			throw new IllegalArgumentException("Restart delay of an appliance must be 0 or positive.");
		if (scaleFactor < 0 || scaleFactor > 1)
			throw new IllegalArgumentException("The scale factor must be a double value [0,1].");
		if (deviation < -1 || deviation > 1)
			throw new IllegalArgumentException("The deviation must be a double value [-1,1].");

		this.onPower = (int) (onPower + deviation * onPower);
		this.offPower = offPower;
		this.cycleTime = cycleTime;
		this.restartDelay = restartDelay;
		this.scaleFactor = scaleFactor;
	}

	/**
	 * @return the power[W] when the appliance is turned on.
	 */
	public int getOnPower() {
		return onPower;
	}

	/**
	 * @return the power[W] when the appliance is turned off (stand-by).
	 */
	public int getOffPower() {
		return offPower;
	}

	/**
	 * @return the time[min] an appliance will be turned on for.
	 */
	public int getCycleTime() {
		return cycleTime;
	}

	/**
	 * @return the delay[min] for the appliance to be turned on again after being turned off.
	 */
	public int getRestartDelay() {
		return restartDelay;
	}

	/**
	 * @return the scale factor of the using probability.
	 */
	public double getScaleFactor() {
		return scaleFactor;
	}

	/**
	 * This method prints an overview of the appliance's data to the console.
	 */
	public void print() {
		System.out.println("ON power:\t" + this.onPower);
		System.out.println("OFF power:\t" + this.offPower);
		System.out.println("cycleTime:\t" + this.cycleTime);
		System.out.println("restartDelay:\t" + this.restartDelay);
		System.out.println("scaleFactor:\t" + this.scaleFactor);
	}
}
