package de.frauas.jdemandmodel.appliance;

import de.frauas.jdemandmodel.appliance.powercycle.PowerCycle;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.RandomUtil;

/**
 * A storage heater is a special appliance, where the probability of being
 * turned on depends on the month of the year and the time of day.
 * 
 * @author lukas
 */
public class StorageHeater extends Appliance {

	/**
	 * Creates a new StorageHeater.
	 * 
	 * @param data
	 *            the associated ApplianceData
	 * @param powerCycle
	 *            the associated power cycle
	 */
	public StorageHeater(ApplianceData data, PowerCycle powerCycle) {
		super(Device.STORAGE_HEATER, data, powerCycle);
	}

	/**
	 * In this method, it is decided whether the storage heater should be turned
	 * on. The following conditions must be fulfilled: It only ever is turned on
	 * at 00:30:00 each day. In months 1 to 4 and 10 to 12 the probability is 1,
	 * in months 5 and 9 it's 0.5 and in months 6 to 8 it is 0.
	 * 
	 * @return true, if the appliance should be turned on, otherwise false.
	 */
	protected boolean checkStartEvent(int activeOccupancy) {
		if (time == 30) {
			int month = Input.getMonth();
			if (month == 5 || month == 9)
				return RandomUtil.nextBoolean(0.5);
			else if (month > 5 && month < 9)
				return false;
			else
				return true;
		} else
			return false;
	}
}
