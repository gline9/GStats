package gstats.distribution;

/**
 * discrete distribution is a lot like a probability function but the
 * probabilities of each point should be found in the pmf and the probability of
 * a range is found just by adding them together.
 * 
 * @author Gavin
 *
 */
public abstract class DiscreteDistribution extends Distribution {

	// stores the smallest value, largest value and the step size for the
	// distribution.
	private final double lowerBound;
	private final double upperBound;
	private final double stepSize;

	/**
	 * constructs a discrete distribution with the given lower bound (inclusive)
	 * and upper bound (exclusive) with the given step size in-between the two.
	 * 
	 * @param lowerBound
	 * @param upperBound
	 * @param stepSize
	 */
	public DiscreteDistribution(double lowerBound, double upperBound, double stepSize) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.stepSize = stepSize;
	}

	/**
	 * used to call the appropriate pmf of the discrete distribution.
	 * 
	 * @param x
	 *            x value for the pmf
	 * @return value of the pmf.
	 * @since Mar 21, 2016
	 */
	public abstract double pmf(double x);

	@Override
	public final double getProbabilityInRange(double x1, double x2) {
		// make sure both of the bounds are in range, if not adjust so they are.
		if (x1 < lowerBound)
			x1 = lowerBound;

		if (x2 > upperBound)
			x2 = upperBound - stepSize;

		// loop through all of the values in the range and add them together.
		double results = 0;
		for (double x = x1; x <= x2; x += stepSize) {
			// add the current value to the results
			results += pmf(x);
		}

		// return the results
		return results;
	}

}
