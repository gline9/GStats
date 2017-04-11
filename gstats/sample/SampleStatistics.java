package gstats.sample;

/**
 * class to compute the statistics from a given sample.
 * 
 * @author Gavin
 *
 */
public final class SampleStatistics {

	// make non-instantiable
	private SampleStatistics() {
	}

	/**
	 * computes the mean of the given sample
	 * 
	 * @param s
	 *            sample for computation.
	 * @return the mean of the sample.
	 */
	public static double mean(Sample s) {
		double total = 0;
		for (double value : s) {
			total += value;
		}
		return total / s.size();
	}

	/**
	 * computes the sample variance of the given sample
	 * 
	 * @param s
	 *            sample for computation.
	 * @return the sample variance of the sample
	 */
	public static double variance(Sample s) {
		double mean = mean(s);
		double total = 0;
		for (double value : s) {
			total += Math.pow(mean - value, 2);
		}

		return total / (s.size() - 1);
	}

	/**
	 * computes the sample standard deviation of the given sample
	 * 
	 * @param s
	 *            sample for computation.
	 * @return the sample standard deviation of the sample
	 */
	public static double standardDeviation(Sample s) {
		// just return the square root of the sample variance
		return Math.sqrt(variance(s));
	}

	/**
	 * computes the modes of the given sample
	 * 
	 * @param s
	 *            sample for computation
	 * @return the modes of the sample
	 */
	public static double[] modes(Sample s) {
		return s.modes();
	}

	/**
	 * returns the median of the sample
	 * 
	 * @param s
	 *            sample for computation.
	 * @return median of the sample
	 */
	public static double median(Sample s) {
		// check if it is an integer index value
		if (s.size() % 2 == 1) {
			// if so return the middle element
			return s.get(s.size() / 2);
		} else {
			// otherwise average the two around it
			return (s.get(s.size() / 2 - 1) + s.get(s.size() / 2)) / 2;
		}
	}

	/**
	 * returns the first quartile of the sample
	 * 
	 * @param s
	 *            sample for computation.
	 * @return first quartile of the sample
	 */
	public static double firstQuartile(Sample s) {
		// get the value that the quartile is
		double value = 1 / 4.0 * (s.size() + 1);

		// check if it is an integer index value
		if (value == (int) value) {
			// if so return that index
			return s.get((int) value - 1);
		} else {
			// otherwise return the weighted average of the two around it
			double bottom = s.get((int) value - 1);
			double top = s.get((int) value);

			return bottom + (top - bottom) * (value - (int) value);
		}
	}

	/**
	 * returns the third quartile of the sample
	 * 
	 * @param s
	 *            sample for computation.
	 * @return third quartile of the sample
	 */
	public static double thirdQuartile(Sample s) {
		// get the value that the quartile is
		double value = 3 / 4.0 * (s.size() + 1);

		// check if it is an integer index value
		if (value == (int) value) {
			// if so return that index
			return s.get((int) value - 1);
		} else {
			// otherwise return the weighted average of the two around it
			double bottom = s.get((int) value - 1);
			double top = s.get((int) value);

			return bottom + (top - bottom) * (value - (int) value);
		}
	}

	/**
	 * gets the value associated with the given quantile.
	 * 
	 * @param s
	 *            sample for computation.
	 * @param quant
	 *            quantile to look at
	 * @return value of the quantile.
	 */
	public static double getQuantile(Sample s, double quant) {
		// check for quantile out of bounds
		if (quant < 0 || quant > 1)
			throw new IllegalArgumentException("Quantile must be between 0 and 1!");

		// check for boundary conditions
		if (quant == 1)
			return s.getLargest();

		if (quant == 0)
			return s.getSmallest();

		// get the value that is below the quantile and the value above the
		// quantile
		int valueIndex = (int) (quant * s.size()) - 1;

		// return the appropriate entry
		return s.get(valueIndex);
	}

}
