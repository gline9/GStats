package gstats.random;

import java.util.function.Function;

/**
 * util class for random number generators.
 * 
 * @author Gavin
 *
 */
public final class RandomUtils {
	// make non-instantiable
	private RandomUtils() {}

	/**
	 * transforms the given random number generator by the given function
	 * 
	 * @param r
	 *            random number generator to transform
	 * @param function
	 *            function to transform with
	 * @return transformed random number generator
	 * @since Apr 8, 2016
	 */
	public static RandomNumberGenerator applyFunction(RandomNumberGenerator r, Function<Double, Double> function) {

		return new RandomNumberGenerator() {

			@Override
			public double generate() {
				return function.apply(r.generate());
			}

		};

	}
}
