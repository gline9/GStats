package gstats.distribution;

/**
 * continuous distribution is a lot like a probability function but the area
 * under the entire distribution must be 1 for it to work appropriately.
 * 
 * @author Gavin
 *
 */
public abstract class ContinuousDistribution extends Distribution {

	/**
	 * used to call the appropriate pdf of the distribution at the location
	 * given.
	 * 
	 * @param x
	 *            x value for the pdf
	 * @return value of the pdf for the function
	 * @since Mar 21, 2016
	 */
	public abstract double pdf(double x);

	/**
	 * used to call the appropriate cdf of the distribution at the location
	 * given. This should be the integral of the pdf so that lim x -> -infinity
	 * (cdf(x)) = 0
	 * 
	 * @param x
	 *            x value for the cdf
	 * @return value of the cdf for the function
	 * @since Mar 21, 2016
	 */
	public abstract double cdf(double x);

	@Override
	public final double getProbabilityInRange(double x1, double x2) {
		// just returns the difference of the cdf at the two points
		return cdf(x2) - cdf(x1);
	}

}
