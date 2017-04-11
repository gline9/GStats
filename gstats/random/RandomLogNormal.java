package gstats.random;

import java.util.Random;

public class RandomLogNormal extends RandomNumberGenerator {

	private final RandomNormal normal;

	/**
	 * creates a random number generator with the density of the log normal
	 * distribution with given means and standard deviation for the underlying
	 * normal below the log normal.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param logMean
	 *            mean of underlying normal distribution
	 * @param logStandardDeviation
	 *            standard deviation of underlying normal distribution
	 */
	public RandomLogNormal(Random r, double logMean, double logStandardDeviation) {
		normal = new RandomNormal(r, logMean, logStandardDeviation);
	}

	@Override
	public double generate() {
		// get the normal value
		double normalValue = normal.generate();

		// scale appropriately
		return Math.exp(normalValue);
	}

}
