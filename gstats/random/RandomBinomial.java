package gstats.random;

import java.util.Random;

public class RandomBinomial extends RandomNumberGenerator {

	private final RandomBernoulli bernoulli;
	private final int trials;

	/**
	 * creates a random number generator with density of a binomial distribution
	 * with the given success rate and number of trials to perform
	 * 
	 * @param r
	 * @param successRate
	 * @param trials
	 */
	public RandomBinomial(Random r, double successRate, int trials) {
		bernoulli = new RandomBernoulli(r, successRate);
		this.trials = trials;
	}

	@Override
	public double generate() {
		// total how many success there were in the number of trials
		int total = 0;
		for (int i = 0; i < trials; i++) {
			total += bernoulli.generate();
		}
		return total;
	}

}
