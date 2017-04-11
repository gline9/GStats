package gstats.plots;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import gstats.sample.Sample;

/**
 * plot for the histogram plot, is used so the information doesn't need to be
 * re-computed every time it needs to be drawn.
 * 
 * @author Gavin
 *
 */
public class HistogramPlot extends Plot {

	// stores the occurrences in each of the ranges for every value in the
	// histogram.
	private final int[] occurrences;

	// stores the maximum number of occurrences any of the bars have.
	private final int maxOccurrences;

	private boolean isFilled = true;

	/**
	 * creates a new histogram plot with the given number of bars to use. This
	 * method will position the bars so that all of the data fits inside, and
	 * each one is equally spaced apart.
	 * 
	 * @param s
	 * @param barStart
	 * @param barWidth
	 */
	public HistogramPlot(Sample s, int numberOfBars) {

		// position information for the histogram
		double minValue = s.getSmallest();
		double maxValue = s.getLargest();
		double stepSize = (maxValue - minValue) / numberOfBars;

		// initialize the occurrences array
		occurrences = new int[numberOfBars];

		// loop through every value in the sample and add an occurrence to the
		// corresponding area of the histogram.
		for (double value : s) {
			int position = (int) ((value - minValue) / stepSize);

			// if it is the maxValue decrement the position as it is the only
			// one that is out of bounds of the histogram
			if (position == numberOfBars)
				position--;

			// increment the counter at the given position
			occurrences[position]++;

		}

		// get the maximum value of the occurrences and set the max value to it
		maxOccurrences = Arrays.stream(occurrences).reduce(0, Integer::max);

	}

	/**
	 * set if the histogram should be filled or not
	 * 
	 * @param isFilled
	 *            if the histogram should be filled or not
	 */
	public void setFilled(boolean isFilled) {
		this.isFilled = isFilled;
	}

	@Override
	public void paint(Graphics g, int width, int height) {
		// set the number of bars as occurrences.length
		int n = occurrences.length;

		// get the starting color and set the color of the graphics to black
		Color color = g.getColor();
		g.setColor(Color.BLACK);

		// loop through each of the values and draw their appropriate box for
		// the histogram

		for (int i = 0; i < n; i++) {
			if (isFilled)
				g.fillRect((int) (i * ((double) (width) / n)),
						height - (int) ((double) (occurrences[i]) / maxOccurrences * height), width / n,
						(int) ((double) (occurrences[i]) / maxOccurrences * height));
			else
				g.drawRect((int) (i * ((double) (width) / n)),
						height - (int) ((double) (occurrences[i]) / maxOccurrences * height), width / n,
						(int) ((double) (occurrences[i]) / maxOccurrences * height));
		}

		// set the color back to what it was
		g.setColor(color);
	}

}
