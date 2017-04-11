package gstats.random;

import java.util.Random;

public class RandomChiSquared extends RandomNumberGenerator {

	private final Random r;
	private final int nu;

	/**
	 * creates a new random number generator using a Chi squared distribution
	 * with the given degrees of freedom.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param degreesOfFreedom
	 *            degrees of freedom for the chi squared distribution
	 */
	public RandomChiSquared(Random r, int degreesOfFreedom) {
		this.r = r;
		nu = degreesOfFreedom;
	}

	@Override
	public double generate() {
		// simply sum up nu squared normals.
		double sum = 0;
		for (int i = 0; i < nu; i++) {
			sum += Math.pow(r.nextGaussian(), 2);
		}

		return sum;
	}

}
