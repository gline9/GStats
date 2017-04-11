package gstats.random;

import java.util.Random;

public class RandomBirnbaumSanders extends RandomNumberGenerator {

	private final RandomNormal standardNormal;
	private final double gamma;

	/**
	 * creates a new random number generator with density of a birnbaum-sanders
	 * distribution with the given gamma as the gamma parameter.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param gamma
	 *            gamma parameter for the birnbaum-sanders distribution
	 */
	public RandomBirnbaumSanders(Random r, double gamma) {
		standardNormal = new RandomNormal(r, 0, 1);
		this.gamma = gamma;
	}

	@Override
	public double generate() {
		// get the standard normal value
		double normal = standardNormal.generate();

		// return the value based off the percent point function
		return .25 * Math.pow(gamma * normal + Math.sqrt(4 + Math.pow(gamma * normal, 2)), 2);
	}

}
