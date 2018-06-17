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
 * The activity statistics of the CREST model only include values for the six categories
 * TV, Cooking, Laundry, WashDress, Iron and HouseClean. Each appliance is
 * assigned to a category.
 * @see Device
 * @author lukas
 *
 */
public class Activity {
	
	public static final int TV = 0;
	public static final int COOKING = 1;
	public static final int LAUNDRY = 2;
	public static final int WASHDRESS = 3; 
	public static final int IRON = 4;
	public static final int HOUSECLEAN = 5; 

}
