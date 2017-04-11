package gstats.random;

import java.util.Random;

public class RandomNormal extends RandomNumberGenerator {

	private final Random r;
	private final double mu;
	private final double sigma;

	/**
	 * creates a new random number generator to generate normal numbers with the
	 * given mean, and standard deviation.
	 * 
	 * @param r
	 *            generator to generate random numbers from
	 * @param mean
	 *            mean of the normal distribution
	 * @param standardDeviation
	 *            standard deviation of the normal distribution
	 */
	public RandomNormal(Random r, double mean, double standardDeviation) {
		this.r = r;
		mu = mean;
		sigma = standardDeviation;
	}

	@Override
	public double generate() {
		// get a standard normal value
		double standard = r.nextGaussian();

		// scale by the correct amount
		return standard * sigma + mu;
	}

}
