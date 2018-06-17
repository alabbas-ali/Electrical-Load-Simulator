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

package de.frauas.jdemandmodel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.frauas.jdemandmodel.appliance.TestAppliance;
import de.frauas.jdemandmodel.appliance.TestApplianceData;
import de.frauas.jdemandmodel.appliance.TestCSVApplianceFactory;
import de.frauas.jdemandmodel.appliance.TestPowerCycleClasses;
import de.frauas.jdemandmodel.cli.TestParser;
import de.frauas.jdemandmodel.lighting.TestLighting;
import de.frauas.jdemandmodel.occupancy.TestMarkovCSVReader;
import de.frauas.jdemandmodel.occupancy.TestMarkovChain;
import de.frauas.jdemandmodel.occupancy.TestOccupancyModeler;
import de.frauas.jdemandmodel.random.TestRandomUtil;
import de.frauas.jdemandmodel.reader.TestCSVReader;
import de.frauas.jdemandmodel.seed.TestSeed;
import de.frauas.jdemandmodel.util.TestJDMFileCreator;
import de.frauas.jdemandmodel.util.TestTimeConverter;

@RunWith(Suite.class)
@SuiteClasses({ TestCSVReader.class, TestMarkovCSVReader.class,
		TestMarkovChain.class, TestOccupancyModeler.class,
		TestCSVApplianceFactory.class, TestAppliance.class,
		TestApplianceData.class, TestPowerCycleClasses.class,
		TestRandomUtil.class, TestJDMFileCreator.class,
		TestTimeConverter.class, TestSeed.class, TestParser.class,
		TestLighting.class })
/**
 * Run this class to run all tests.
 * 
 * @author Lukas Wiederhold
 */
public class AllTests {

}