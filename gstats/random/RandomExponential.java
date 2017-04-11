package gstats.random;

import java.util.Random;

public class RandomExponential extends RandomNumberGenerator {

	private final Random r;
	private final double beta;

	/**
	 * creates a new random exponential number generator using the random
	 * generator as the underlying random number generator and the expected
	 * value as the mean for the numbers generated.
	 * 
	 * @param r
	 *            number generator to use
	 * @param expectedValue
	 *            expected value for the generator
	 */
	public RandomExponential(Random r, double expectedValue) {
		this.r = r;
		beta = expectedValue;
	}

	@Override
	public double generate() {
		// get a uniform number
		double uniform = r.nextDouble();

		// this is the equivalent of F^-1(x) function for the exponential
		// distribution.
		return -beta * Math.log(1 - uniform);
	}

}
