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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCSVApplianceFactory {

	/**
	 * Tests whether the scaleFactor value is read in and returned correctly.
	 */
	@Test
	public void scaleFactorResultTest() {
		
		CSVApplianceFactory factory = CSVApplianceFactory.getInstance();
		Appliance appliance = factory.createAppliance(Device.IRON);
		
		assertEquals(0.0080018246, appliance.getApplianceData().getScaleFactor(), 0);
	}
	
	/**
	 * Tests whether a specific value from the use profile is read in and returned correctly.
	 */
	@Test
	public void useProfileResultTest() {
		
		CSVApplianceFactory factory = CSVApplianceFactory.getInstance();
		Appliance appliance = factory.createAppliance(Device.ELECTRIC_SHOWER);
		
		assertEquals(0.074, appliance.getUseProfile().get(1,9), 0);
	}
}