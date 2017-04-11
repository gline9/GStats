package gstats.random;

import java.util.Random;

public class RandomT extends RandomNumberGenerator {

	private final Random r;
	private final RandomChiSquared chiSquared;
	private final int nu;

	/**
	 * creates a new random number generator using a t-distribution with the
	 * given degrees of freedom as the pdf.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param degreesOfFreedom
	 *            how many degrees of freedom it has
	 */
	public RandomT(Random r, int degreesOfFreedom) {
		this.r = r;
		nu = degreesOfFreedom;
		chiSquared = new RandomChiSquared(r, degreesOfFreedom);
	}

	@Override
	public double generate() {

		// get the bottom chi squared value
		double chi = chiSquared.generate();

		// get the upper normal value
		double normal = r.nextGaussian();
		return normal / Math.sqrt(chi / nu);
	}

}
