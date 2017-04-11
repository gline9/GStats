package gstats.plots;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import gcore.util.WrapperUtils;
import gstats.sample.Sample;
import gstats.sample.SampleStatistics;

/**
 * acts as a variable for a box plot that can be drawn onto graphics.
 * 
 * @author Gavin
 *
 */
public class BoxPlot extends Plot {

	private final double q0;
	private final double q1;
	private final double q2;
	private final double q3;
	private final double q4;

	private final double maxVal;
	private final double minVal;

	private final double[] outliers;

	/**
	 * creates the box plot class from the sample given to it.
	 * 
	 * @param s
	 */
	public BoxPlot(Sample s) {
		// set the inner quartiles
		q1 = SampleStatistics.firstQuartile(s);
		q2 = SampleStatistics.median(s);
		q3 = SampleStatistics.thirdQuartile(s);

		// set the maximum and minimum values.
		maxVal = s.getLargest();
		minVal = s.getSmallest();

		// get the inner quartile range and the maximum and minimum values for
		// the plot.
		double iqr = q3 - q1;

		double max = q3 + 1.5 * iqr;
		double min = q1 - 1.5 * iqr;

		// copy the sample
		Sample copy = new Sample(s);

		// generate the list of outliers for the sample
		ArrayList<Double> outliers = new ArrayList<>();
		while (copy.getSmallest() < min) {
			double smallest = copy.getSmallest();
			copy.removeSmallest();
			outliers.add(smallest);
		}

		while (copy.getLargest() > max) {
			double largest = copy.getLargest();
			copy.removeLargest();
			outliers.add(largest);
		}

		// set the outer boundaries as the largest and smallest values
		q0 = copy.getSmallest();
		q4 = copy.getLargest();

		// set the outliers as the appropriate values
		Double[] outlierArray = new Double[outliers.size()];

		outliers.toArray(outlierArray);

		this.outliers = WrapperUtils.toPrimitive(outlierArray);
	}

	/**
	 * draws the plot onto the graphics with the given width and height
	 * 
	 * @param g
	 *            graphics to draw to
	 * @param width
	 *            how wide it is
	 * @param height
	 *            how tall it is
	 * @since Mar 29, 2016
	 */
	public void paint(Graphics g, int width, int height) {
		// stores how wide half of an x is in the plot
		int xHalfWidth = 5;

		// this variable is for shift in the height at the top and at the bottom
		// if their are outliers
		int topShift = 0;
		int bottomShift = 0;

		if (q0 != minVal)
			bottomShift = xHalfWidth;

		if (q4 != maxVal)
			topShift = xHalfWidth;

		// change the height
		height -= topShift + bottomShift;

		// save the color information to reset the graphics once it is done
		Color color = g.getColor();

		// set the color to balck
		g.setColor(Color.BLACK);

		// get the pixels to draw each part.
		int upperWhisker = (int) (height - height * ((q4 - minVal) / (maxVal - minVal))) + topShift;
		int lowerWhisker = (int) (height - height * ((q0 - minVal) / (maxVal - minVal))) + topShift;
		int upperBox = (int) (height - height * (q3 - minVal) / (maxVal - minVal)) + topShift;
		int lowerBox = (int) (height - height * (q1 - minVal) / (maxVal - minVal)) + topShift;
		int medianLine = (int) (height - height * (q2 - minVal) / (maxVal - minVal)) + topShift;

		// draw the top and the bottom whisker of the plot.
		g.drawLine((int) (2.0 / 6 * width), upperWhisker, (int) (4.0 / 6 * width), upperWhisker);
		g.drawLine((int) (2.0 / 6 * width), lowerWhisker, (int) (4.0 / 6 * width), lowerWhisker);
		g.drawLine((int) (1 / 2.0 * width), upperWhisker, (int) (1 / 2.0 * width), upperBox);
		g.drawLine((int) (1 / 2.0 * width), lowerWhisker, (int) (1 / 2.0 * width), lowerBox);

		// draw the box and the median line
		g.drawRect(0, upperBox, width, lowerBox - upperBox);
		g.drawLine(0, medianLine, width, medianLine);

		// draw in the outliers
		for (double val : outliers) {
			// translate the value to a position
			int position = (int) (height - height * (val - minVal) / (maxVal - minVal)) + topShift;

			// draw the x at that position in the middle
			drawX(g, (int) (1 / 2.0 * width), position, xHalfWidth, xHalfWidth);
		}

		// set the color back to what it was originally
		g.setColor(color);
	}

	/**
	 * helper method that draws an x onto the graphics at the given position
	 * 
	 * @param g
	 *            graphics to draw to
	 * @param xPos
	 *            x position of the x
	 * @param yPos
	 *            y position of the x
	 * @param halfWidth
	 *            how wide the x is
	 * @param halfHeight
	 *            how tall the x is
	 * @since Mar 29, 2016
	 */
	private void drawX(Graphics g, int xPos, int yPos, int halfWidth, int halfHeight) {
		// just draw two lines
		g.drawLine(xPos - halfWidth, yPos - halfHeight, xPos + halfWidth, yPos + halfHeight);
		g.drawLine(xPos + halfWidth, yPos - halfHeight, xPos - halfWidth, yPos + halfHeight);
	}
}
