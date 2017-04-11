package gstats.random;

import java.util.Random;

public class RandomBernoulli extends RandomNumberGenerator {

	private final Random r;
	private final double successProb;

	/**
	 * creates a random number generator that returns 1 if there was a success
	 * and 0 if there was a failure with the given success probability.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param successProb
	 *            probability of a sucess
	 */
	public RandomBernoulli(Random r, double successProb) {
		this.r = r;
		this.successProb = successProb;
	}

	@Override
	public double generate() {
		// get a uniform distribution
		double uniform = r.nextDouble();

		// if it is above the sucess prob return 0 otherwise 1
		if (uniform > successProb)
			return 0;

		return 1;
	}
}
