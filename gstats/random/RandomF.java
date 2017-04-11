package gstats.random;

import java.util.Random;

public class RandomF extends RandomNumberGenerator {

	private final RandomChiSquared chiSquared1;
	private final RandomChiSquared chiSquared2;
	private final int nu1;
	private final int nu2;

	/**
	 * creates a new random number generator that uses the f-distribution with
	 * corresponding upper and lower degrees of freedom as its density.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param upperDegreesOfFreedom
	 *            degrees of freedom for numerator
	 * @param lowerDegreesOfFreedom
	 *            degrees of freedom for denominator
	 */
	public RandomF(Random r, int upperDegreesOfFreedom, int lowerDegreesOfFreedom) {
		nu1 = upperDegreesOfFreedom;
		nu2 = lowerDegreesOfFreedom;
		chiSquared1 = new RandomChiSquared(r, nu1);
		chiSquared2 = new RandomChiSquared(r, nu2);
	}

	@Override
	public double generate() {
		// get the upper and lower chi squared values
		double upperChiSquared = chiSquared1.generate();
		double lowerChiSquared = chiSquared2.generate();

		// return the appropriate value
		return (upperChiSquared / nu1) / (lowerChiSquared / nu2);
	}

}
