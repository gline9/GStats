package gstats.random;

import java.util.Random;

public class RandomGamma extends RandomNumberGenerator {

	private final Random r;
	private final double shape;

	/**
	 * creates a random number generator with density of a gamma distribution
	 * using the given shape parameter.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param shape
	 *            shape parameter for the gamma distribution
	 */
	public RandomGamma(Random r, double shape) {
		this.r = r;
		this.shape = shape;
	}

	@Override
	public double generate() {
		// shape is greater than or equal to a just use the basal
		if (shape >= 1)
			return generateBasal(shape);

		// otherwise use this method
		double uniInverseShape = Math.pow(r.nextDouble(), 1 / shape);
		return generateBasal(shape + 1) * uniInverseShape;
	}

	private double generateBasal(double shape) {
		// use the Marsaglia-Tsang Method for generating a random gamma
		// distribution

		double d = shape - 1.0 / 3;
		double c = 1 / Math.sqrt(9 * d);

		double z, logU, v;

		do {
			z = r.nextGaussian();
			logU = Math.log(r.nextDouble());
			v = Math.pow(1 + c * z, 3);
		} while (z <= -1.0 / c || logU >= .5 * Math.pow(z, 2) + d - d * v + d * Math.log(v));

		return d * v;
	}

}
