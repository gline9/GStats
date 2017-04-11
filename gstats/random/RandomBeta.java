package gstats.random;

import java.util.Random;

public class RandomBeta extends RandomNumberGenerator {

	private final RandomGamma xGen;
	private final RandomGamma yGen;

	/**
	 * creates a new random number generator with density of the beta
	 * distribution with the given shape parameters alpha and beta.
	 * 
	 * @param r
	 *            underlying random number generator
	 * @param alpha
	 *            alpha shape parameter
	 * @param beta
	 *            beta shape parameter
	 */
	public RandomBeta(Random r, double alpha, double beta) {
		xGen = new RandomGamma(r, alpha);
		yGen = new RandomGamma(r, beta);
	}

	@Override
	public double generate() {
		// get the two gamma values
		double x = xGen.generate();
		double y = yGen.generate();

		// scale appropriately
		return x / (x + y);
	}

}
