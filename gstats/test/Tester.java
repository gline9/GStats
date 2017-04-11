package gstats.test;

import java.util.ArrayList;
import java.util.Random;

import gstats.plots.HistogramPlot;
import gstats.random.RandomLogNormal;
import gstats.random.RandomNumberGenerator;
import gstats.random.RandomUtils;
import gstats.sample.Sample;
import gstats.sample.SampleStatistics;

public class Tester {
	public static void main(String[] args) {

		ArrayList<Double> s = new ArrayList<>();

		Random r = new Random();
		RandomNumberGenerator er = RandomUtils.applyFunction(new RandomLogNormal(r, 0, 1), Math::log);
		for (int i = 0; i < 1000000; i++) {
			s.add(er.generate());
		}

		System.out.println("found");

		Sample exponential = new Sample(s);

		System.out.println("sorted");

		System.out.println(exponential.getLargest());
		System.out.println(SampleStatistics.mean(exponential));

		// exponential.dropOutliers(5);

		// System.out.println("dropped");

		HistogramPlot histogram = new HistogramPlot(exponential, 1000);
		histogram.setFilled(false);

		new TestGui(histogram);

	}
}
