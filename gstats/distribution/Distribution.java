package gstats.distribution;

/**
 * main abstract class for all of the different types of distributions,
 * continuous and discrete.
 * 
 * @author Gavin
 *
 */
public abstract class Distribution {

	/**
	 * override this to return the probability that a randomly selected event
	 * will fall within the range of x1 to x2. For this function to work
	 * appropriately accross all classes x1 should be smaller than x2
	 * 
	 * @param x1
	 *            beggining of the interval
	 * @param x2
	 *            end of the interval
	 * @return probability of falling inside the interval.
	 * @since Mar 21, 2016
	 */
	public abstract double getProbabilityInRange(double x1, double x2);
}
