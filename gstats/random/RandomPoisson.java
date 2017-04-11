package gstats.random;

import java.util.Random;

public class RandomPoisson extends RandomNumberGenerator {
	
	private final RandomExponential re;

	/**
	 * creates a new random Poisson number generator using the random generator
	 * as the underlying random number generator and the rate as the occurrences
	 * per unit time to expect.
	 * 
	 * @param r
	 *            number generator to use
	 * @param rate
	 *            expected rate for the generator
	 */
	public RandomPoisson(Random r, double rate) {
		re = new RandomExponential(r, 1 / rate);
	}

	@Override
	public double generate() {
		// gets the number of people that occurred in 1 unit of time

		// keeps adding unil greater than 1 is reached
		double value = 0;
		int ocurrences = 0;
		while (value < 1) {
			value += re.generate();
			ocurrences++;
		}
		return ocurrences - 1;
	}

}
