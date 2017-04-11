package gstats.random;

import java.util.Random;

public class RandomExtremeValueMin extends RandomNumberGenerator {

	private final Random r;

	/**
	 * creates a random number generator with density of the extreme value type
	 * I distribution.
	 * 
	 * @param r
	 *            underlying random number generator
	 */
	public RandomExtremeValueMin(Random r) {
		this.r = r;
	}

	@Override
	public double generate() {
		// get a uniform distribution
		double uniform = r.nextDouble();

		// scale appropriately
		return Math.log(Math.log(1 / (1 - uniform)));
	}

}
