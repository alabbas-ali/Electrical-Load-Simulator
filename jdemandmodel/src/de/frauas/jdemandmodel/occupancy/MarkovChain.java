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
import de.frauas.jdemandmodel.util.RandomUtil;

/**
 * This class represents a Markov chain.
 * 
 * @author lukas
 */
public class MarkovChain {

	private double[] initialProbabilities;
	private ArrayList<Matrix> transitionMatrices;
	private int size;

	/**
	 * Creates a Markov chain with the given initial probabilities and
	 * transition matrices. Note that the initial probabilities array should
	 * contain as many probabilities as there are states. Similarly each
	 * transition matrix should be a #states x #states matrix. The
	 * transitionMatrices is a list in case the Markov chain is dependent on
	 * time. In this case, each transition matrix is valid for one time
	 * interval, in the same way "time" is used in the "getStateAt" method.
	 * 
	 * @param initialProbabilities
	 *            initial probabilities for each state
	 * @param transitionMatrices
	 *            list of transition matrices for each time interval
	 */
	public MarkovChain(double[] initialProbabilities, ArrayList<Matrix> transitionMatrices) {
		this.initialProbabilities = initialProbabilities;
		this.transitionMatrices = transitionMatrices;
		size = transitionMatrices.size();
	}

	/**
	 * @return the initial state using the initial probabilities.
	 */
	public int getInitialState() {
		return doStateTransition(initialProbabilities);
	}

	/**
	 * @param time
	 *            the current time interval
	 * @param previousState
	 *            the current state before calling this method
	 * @return the next state at the given time with the previous state.
	 * @throws IllegalArgumentException
	 *             if the time parameter is less than 0 or greater than the
	 *             number of transition matrices
	 */
	public int getStateAt(int time, int previousState) {
		if (time < 0 || time >= size - 1) {
			throw new IllegalArgumentException("Time value must be within the specified boundaries: " + time);
		} else
			return doStateTransition(transitionMatrices.get(time).getRow(previousState));
	}

	/**
	 * @return the size of the Markov chain, i.e. the number of time intervals
	 *         it can be used for.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns the next state using the given probabilities.
	 * 
	 * @param probabilities
	 *            the probabilities of each state being chosen next - they must
	 *            add up to 1.
	 * @return the next state.
	 */
	private int doStateTransition(double[] probabilities) {

		checkStateProbabilities(probabilities);

		int index = 0;
		double cumulativeProbability = probabilities[index];

		do {
			if (RandomUtil.nextBoolean(cumulativeProbability)) {
				return index;
			} else {
				cumulativeProbability += probabilities[++index];
			}
		} while (true);

	}

	/**
	 * Checks whether the sum of the values within the probability array is less
	 * than 0.99. If so, it throws an exception.
	 * 
	 * @param probabilities
	 * @throws IllegalArgumentException
	 *             if the sum of the probabilities is less than 0.99
	 */
	private void checkStateProbabilities(double[] probabilities) {
		double sum = 0; // = DoubleStream.of(probabilities).sum();
		for (int i = 0; i < probabilities.length; i++) {
			sum += probabilities[i];
		}
		if (sum < 0.99) {
			throw new IllegalArgumentException("Probabilities don't add up to one: " + sum);
		}
	}
}