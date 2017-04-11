package gstats.random;

import java.util.Random;

public class RandomTukeyLambda extends RandomNumberGenerator {

	private final Random r;
	private final double shape;

	/**
	 * creates a new random number generator with density of the tukey-lambda
	 * distribution with the given shape parameter
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param shape
	 *            shape parameter for the tukey-lambda distribution.
	 */
	public RandomTukeyLambda(Random r, double shape) {
		this.r = r;
		this.shape = shape;
	}

	@Override
	public double generate() {
		// get a uniform value
		double uniform = r.nextDouble();

		// scale the uniform to the tukey-lambda
		return (Math.pow(uniform, shape) - Math.pow(1 - uniform, shape)) / shape;
	}

}
