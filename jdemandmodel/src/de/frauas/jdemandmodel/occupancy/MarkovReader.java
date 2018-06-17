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

package de.frauas.jdemandmodel.occupancy;

import java.util.ArrayList;

import de.frauas.jdemandmodel.util.Matrix;

/**
 * A class that implements this interface needs two methods. One to read the
 * initial probabilities and one to read transition matrices of a Markov chain.
 * 
 * @author lukas
 */
public interface MarkovReader {

	public double[] readInitialProbabilities();

	public ArrayList<Matrix> readTransitionMatrices();
}