package gstats.random;

import java.util.Random;

public class RandomWiebull extends RandomNumberGenerator {

	private final Random r;
	private final double gamma;

	/**
	 * creates a random number generator using a wiebull distribution with the
	 * given gamma parameter.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param gamma
	 *            gamma value for the distribution
	 */
	public RandomWiebull(Random r, double gamma) {
		this.r = r;
		this.gamma = gamma;
	}

	@Override
	public double generate() {
		// get the uniform
		double uniform = r.nextDouble();

		// map using the percent point function of the wiebull distribution
		return Math.pow(-Math.log(1 - uniform), 1 / gamma);
	}

}
