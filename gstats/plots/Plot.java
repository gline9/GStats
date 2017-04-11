package gstats.plots;

import java.awt.Graphics;

/**
 * class for adding operations that every type of plot has.
 * 
 * @author Gavin
 *
 */
public abstract class Plot {

	/**
	 * used to paint the plot onto the given graphics at 0, 0 with the given
	 * width and height.
	 * 
	 * @param g
	 *            graphics to draw to
	 * @param width
	 *            width of the plot
	 * @param height
	 *            height of the plot
	 * @since Mar 29, 2016
	 */
	public abstract void paint(Graphics g, int width, int height);

	/**
	 * draws the given plot at the corresponding x position and y position with
	 * the appropriate width and height.
	 * 
	 * @param g
	 *            graphics to draw to
	 * @param xPos
	 *            x position of the draw
	 * @param yPos
	 *            y position of the draw
	 * @param width
	 *            width of the plot
	 * @param height
	 *            height of the plot
	 * @since Mar 29, 2016
	 */
	public void paint(Graphics g, int xPos, int yPos, int width, int height) {
		// translate the graphics to the appropriate location
		g.translate(xPos, yPos);

		// draw the plot
		paint(g, width, height);

		// translate the graphics back to the original position
		g.translate(-xPos, -yPos);
	}

}
