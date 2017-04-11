package gstats.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JFrame;

import gstats.plots.Plot;

public class TestGui extends JFrame {
	private static final long serialVersionUID = 1L;

	private final Plot plot;

	public TestGui(Plot p) {
		plot = p;

		pack();
		Insets i = getInsets();

		setSize(400 + i.left + i.right, 500 + i.top + i.bottom);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		Insets i = getInsets();
		g.translate(i.left + 2, i.top + 2);
		plot.paint(g, getWidth() - 4 - i.left - i.right, getHeight() - 4 - i.top - i.bottom);
	}

}
