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

import de.frauas.jdemandmodel.appliance.powercycle.PowerCycle;
import de.frauas.jdemandmodel.appliance.powercycle.StandardPowerCycle;
import de.frauas.jdemandmodel.appliance.powercycle.WashingCycle;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.JDMTimeConverter;
import de.frauas.jdemandmodel.util.Matrix;
import de.frauas.jdemandmodel.util.RandomUtil;

/**
 * This class is the representation of an appliance. There are 3 different types
 * of appliances:
 * <p>
 * Group A) A standard appliance has an activity value between 0 and 5 and
 * therefore a use profile (see {@link Activity}) - whether it's turned on is
 * decided upon the active occupancy, the scale factor and the use profile.
 * </p>
 * <p>
 * Group B) An appliance with an activity value of -1 has no use profile -
 * whether it's turned on is decided upon the active occupancy and the scale
 * factor.
 * </p>
 * Group C) An appliance with an activity value of -2 has no use profile -
 * whether it's turned on is decided solely upon the scale factor.
 * <p>
 * Also worth mentioning are the classes {@link ElectricSpaceHeating} and
 * {@link StorageHeater} which extend an Appliance and overwrite the
 * "checkStartEvent" method.
 * </p>
 * <p>
 * The class {@link WashingCycle} is used for
 * a WASHING_MACHINE and WASHER_DRYER, the only two appliances that use a
 * power cycle different from the {@link StandardPowerCycle}. They count as
 * group B appliances, but have the additional characteristic of not being
 * turned off, when the occupancy turns 0 (see method "nextWattage").
 * </p>
 * 
 * @author lukas
 */
public class Appliance {

	private Device device;
	private Matrix useProfile;
	private ApplianceData data;
	private PowerCycle powerCycle;
	protected static int time = -1;

	private int power;
	private int leftCycleTime = 0;
	private int leftRestartDelay = 0;

	/**
	 * Constructs a new object Appliance.
	 * 
	 * @param device
	 *            the device associated with this appliance
	 * @param data
	 *            an ApplianceData instance holding the appliance's data
	 * @param useProfile
	 *            matrix that for every possible occupancy contains an array of
	 *            activity probabilities for all time intervals
	 * @param powerCycle
	 *            the power cycle for this appliance
	 */
	public Appliance(Device device, ApplianceData data, Matrix useProfile, PowerCycle powerCycle) {
		this.device = device;
		checkUseProfile(useProfile);
		this.useProfile = useProfile;
		this.data = data;
		this.power = data.getOffPower();
		this.powerCycle = powerCycle;
	}

	/**
	 * Constructs a new object Appliance. No use profile is needed.
	 * 
	 * @param device
	 *            the device associated with this appliance
	 * @param data
	 *            an ApplianceData instance holding the appliance's data
	 * @param powerCycle
	 *            the power cycle for this appliance
	 */
	public Appliance(Device device, ApplianceData data, PowerCycle powerCycle) {
		this.device = device;
		this.data = data;
		this.power = data.getOffPower();
		this.powerCycle = powerCycle;
		this.leftRestartDelay = (int) RandomUtil.nextDouble() * data.getRestartDelay() * 2;
	}

	/**
	 * @return the appliance's associated device.
	 */
	public Device getDevice() {
		return device;
	}
	

	/**
	 * @return Returns on and off power, cycle time, restart delay and scale
	 *         factor of the appliance.
	 */
	public ApplianceData getApplianceData() {
		return data;
	}
	
	/**
	 * @return the use profile (activity probabilities for all time intervals)
	 *         of the appliance.
	 */
	public Matrix getUseProfile() {
		return useProfile;
	}
	
	/**
	 * @return the appliance's associated power cycle.
	 */
	public PowerCycle getPowerCycle() {
		return powerCycle;
	}

	/**
	 * This method checks the validity of the passed useProfile array list.
	 * (size = 6, each array length = 144, each value 0 <= x <= 1)
	 * 
	 * @param useProfile
	 *            the use profile to be checked
	 * @throws IllegalArgumentException
	 *             if a probability value within the use profile is less than 0
	 *             or greater than 1
	 */
	private void checkUseProfile(Matrix useProfile) {
		if (device == Device.STORAGE_HEATER || device == Device.ELEC_SPACE_HEATING)
			return;
		boolean goodDimensions = false;
		boolean goodArrays = true;
		for (int i = 0; i < useProfile.getLength(); i++) {
			int j;
			for (j = 0; j < useProfile.getSize(); j++) {
				if (i == 5 && j == Input.CREST_TIME_INTERVAL_COUNT - 1 && goodArrays)
					goodDimensions = true;
				double tmp = useProfile.get(i, j);
				if (tmp < 0 || tmp > 1)
					throw new IllegalArgumentException("Invalid data in use profile of " + device.name() + ".");
			}
			if (j != Input.CREST_TIME_INTERVAL_COUNT)
				goodArrays = false;
		}
		if (!goodDimensions)
			throw new IllegalArgumentException("Invalid dimensions in use profile of " + device.name() + ".");
	}

	/**
	 * This method uses the statistical data retrieved from the csv files to
	 * decide, whether an appliance should be turned on at the given time
	 * interval and with the specified current active occupancy. The probability
	 * for being turned on of appliances that don't have a use profile depends
	 * on the scale factor and only so far on the occupancy as they can't be
	 * turned on, if it equals 0 (no residents present or awake), except for
	 * freezers or the refrigerator (activity value -2).
	 * 
	 * @param activeOccupancy
	 *            the current active occupancy of the dwelling
	 * @return true, if the appliance should be turned on, otherwise false.
	 */
	protected boolean checkStartEvent(int activeOccupancy) {
		if (useProfile == null) {
			if (activeOccupancy == 0 && device.getActivity() != -2)
				return false;
			return RandomUtil.nextBoolean(data.getScaleFactor());
		}
		return RandomUtil.nextBoolean(
				((useProfile.get(activeOccupancy, time / JDMTimeConverter.CREST_TIME_INTERVAL_LENGTH)) / 10)
						* data.getScaleFactor());
	}

	/**
	 * Simulates the event of turning on the appliance. A new using cycle begins
	 * and the current power of the appliance is set to the first power usage
	 * value of the cycle.
	 */
	private void doStart() {
		leftCycleTime = data.getCycleTime();
		power = powerCycle.getPowerUsage(leftCycleTime);
	}

	/**
	 * If an appliance is not used anymore this internal method is called,
	 * setting the power to the stand-by value and starting a delay for the
	 * appliance to be used again.
	 */
	private void doStop() {
		power = data.getOffPower();
		leftRestartDelay = data.getRestartDelay();
		if (leftRestartDelay > 0)
			leftRestartDelay--;
	}

	/**
	 * @return Returns the current power of the appliance.
	 */
	public int getPower() {
		return power;
	}

	/**
	 * @return Returns the current restart delay of the appliance.
	 */
	public int getLeftRestartDelay() {
		return leftRestartDelay;
	}

	/**
	 * @return Returns the currently left cycle time of the appliance.
	 */
	public int getLeftCycleTime() {
		return leftCycleTime;
	}

	/**
	 * This method manages the stages of the appliance through time and handles
	 * three cases.
	 * <p>
	 * First, if the left cycle time and the restart time of an appliance equal
	 * 0, that means that the appliance is turned off, but ready to be turned on
	 * and it is checked for a start event (using the statistical data retrieved
	 * from the csv files).
	 * </p>
	 * <p>
	 * Second, if the cycle time is greater 0, which means that the appliance is
	 * turned on, several things can happen: Appliances that depend on the
	 * active occupancy will be turned off, if no one is present or awake. For
	 * other occupancy values, in any case, the left cycle time will be reduced
	 * by one and the next power usage value will be calculated. However, if the
	 * left cycle time reaches 0 and the appliance's restart delay equals 0, it
	 * has to be checked right away whether the appliance should be started
	 * again. If not, the appliance is turned off.
	 * </p>
	 * <p>
	 * Finally, if the left cycle time is 0, but there's some restart delay
	 * left, the restart delay is decreased by one.
	 * </p>
	 * 
	 * @param activeOccupancy
	 *            the current active occupancy of the dwelling
	 */
	public void nextWattage(int activeOccupancy) {

		if (leftCycleTime == 0 && leftRestartDelay == 0 && checkStartEvent(activeOccupancy)) {
			doStart();
		} else if (leftCycleTime > 0) {
			if (activeOccupancy == 0 && device.getActivity() != -2 && device != Device.WASHING_MACHINE
					&& device != Device.WASHER_DRYER)
				power = data.getOffPower();
			else {
				leftCycleTime--;
				if (leftCycleTime != 0)
					power = powerCycle.getPowerUsage(leftCycleTime);
				else {
					if (data.getRestartDelay() == 0 && checkStartEvent(activeOccupancy))
						doStart();
					else
						doStop();
				}
			}
		} else if (leftRestartDelay > 0)
			leftRestartDelay--;
	}

	/**
	 * This method iterates through the time intervals and intended to be used
	 * in a while-loop.
	 * 
	 * @return false, if the next time interval equals 1440 (the last minute of
	 *         the day), otherwise true. When reaching the last time interval,
	 *         the time of all appliances is reset to -1.
	 */
	public static boolean hasNextTimeInterval() {
		if (time++ >= Input.MAX_TIME_INTERVAL_COUNT - 1) {
			time = -1;
			return false;
		}
		return true;
	}
}
