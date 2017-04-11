package gstats.random;

import java.util.Random;

public class RandomCauchy extends RandomNumberGenerator {

	private final Random r;
	private final double location;
	private final double scale;

	/**
	 * creates a new random generator with the cauchy distribution, the given
	 * location and scale parameters are used to scale the distribution.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param location
	 *            location parameter
	 * @param scale
	 *            scale parameter
	 */
	public RandomCauchy(Random r, double location, double scale) {
		this.r = r;
		this.location = location;
		this.scale = scale;
	}

	@Override
	public double generate() {
		// get a uniform number
		double uniform = r.nextDouble();

		// just use the percent point function on the uniform and scale appropriately.
		return -scale/Math.tan(Math.PI * uniform) + location;
	}

}
