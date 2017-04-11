package gstats.random;

import java.util.Random;

public class RandomDoubleExponential extends RandomNumberGenerator {

	private final Random r;

	/**
	 * creates a random number generator with density of a double exponential
	 * distribution
	 * 
	 * @param r
	 *            underlying random number generator
	 */
	public RandomDoubleExponential(Random r) {
		this.r = r;
	}

	@Override
	public double generate() {
		// uniform distribution
		double uniform = r.nextDouble();
		
		// piecewise definition
		if (uniform < .5)
			return Math.log(2 * uniform);
		else
			return -Math.log(2 * (1 - uniform));
	}

}
