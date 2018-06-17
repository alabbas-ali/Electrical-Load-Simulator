package de.frauas.jdemandmodel.appliance;

import de.frauas.jdemandmodel.appliance.powercycle.PowerCycle;
import de.frauas.jdemandmodel.util.Input;
import de.frauas.jdemandmodel.util.RandomUtil;

/**
 * An electric space heating is a special appliance, where the probability of
 * being turned on depends on the month of the year.
 * 
 * @author lukas
 */
public class ElectricSpaceHeating extends Appliance {

	private double[] probabilities = { 0, 1.63, 1.821, 1.595, 0.867, 0.763, 0.191, 0.156, 0.087, 0.399, 0.936, 1.561,
			1.994 };

	/**
	 * Creates a new ElectricSpaceHeating.
	 * 
	 * @param data
	 *            the associated ApplianceData
	 * @param powerCycle
	 *            the associated power cycle
	 */
	public ElectricSpaceHeating(ApplianceData data, PowerCycle powerCycle) {
		super(Device.ELEC_SPACE_HEATING, data, powerCycle);
	}

	/**
	 * @return true, if the appliance should be turned on, otherwise false.
	 */
	protected boolean checkStartEvent(int activeOccupancy) {
		if (activeOccupancy == 0)
			return false;
		return RandomUtil.nextBoolean(probabilities[Input.getMonth()] * super.getApplianceData().getScaleFactor());
	}
}