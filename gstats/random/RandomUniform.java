package gstats.random;

import java.util.Random;

public class RandomUniform extends RandomNumberGenerator {

	private final Random r;
	private final double low;
	private final double range;

	/**
	 * creates a new uniform random number generator that will generate numbers
	 * using the given generator from the low value to the high value.
	 * 
	 * @param r
	 *            generator to use
	 * @param low
	 *            lower bound for the uniform distribution
	 * @param high
	 *            upper bound for the uniform distribution
	 */
	public RandomUniform(Random r, double low, double high) {
		this.r = r;
		this.low = low;
		range = high - low;

		// check if the range is negative
		if (range <= 0)
			throw new IllegalArgumentException(
					"Upper bound must be larger than the lower bound for a uniform distribution!");
	}

	@Override
	public double generate() {
		// get a value from 0 to 1
		double uniform = r.nextDouble();

		// scale by range and translate by lower bound
		return uniform * range + low;
	}

}
