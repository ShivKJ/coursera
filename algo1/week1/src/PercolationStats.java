import static edu.princeton.cs.algs4.StdOut.printf;
import static edu.princeton.cs.algs4.StdRandom.permutation;
import static java.lang.Integer.parseInt;
import static java.lang.Math.sqrt;
import static java.util.Arrays.stream;
import static java.util.stream.DoubleStream.generate;

import java.util.PrimitiveIterator.OfInt;

import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final int      trials;
	private final double[] outcome;

	public PercolationStats(int n, int trials) {
		Percolation.numberGreaterThanZero(n);
		this.trials = Percolation.numberGreaterThanZero(trials);
		this.outcome = process(n, trials);
	}

	public double mean() {
		return StdStats.mean(outcome);
	}

	public double stdDev() {
		return StdStats.stddev(outcome);
	}

	public double confidenceLo() {
		return mean() - 1.96 * StdStats.stddev(outcome) / sqrt(trials);
	}

	public double confidenceHi() {
		return mean() + 1.96 * StdStats.stddev(outcome) / sqrt(trials);
	}

	private static double[] process(int n, int trials) {
		return generate(() -> process(n)).limit(trials).toArray();
	}

	private static double process(int n) {
		int blocks = n * n;
		Percolation p = new Percolation(n);

		OfInt itr = stream(permutation(blocks)).iterator();

		while (!p.percolates()) {
			int s = itr.nextInt();
			int i = 1 + s / n;
			int j = 1 + s % n;

			p.open(i, j);
		}

		return (1. * p.numberOfOpenSites()) / blocks;

	}

	public static void main(String[] args) {
		int n = parseInt(args[0]), trials = parseInt(args[1]);

		PercolationStats stats = new PercolationStats(n, trials);
		printf("%-23s = %f\n", "mean", stats.mean());
		printf("%-23s = %f\n", "stddev", stats.stdDev());
		printf("%-23s = [%f, %f]\n", "95% confidence interval", stats.confidenceLo(), stats.confidenceHi());

	}
}
