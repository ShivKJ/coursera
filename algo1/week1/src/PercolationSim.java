import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.List;

public class PercolationSim {

	public static void main(String[] args) {
		int n = 1000;
		int simulation = 1000;
		
		List<Double> averages = new ArrayList<>();

		for (int i = 1; i < 100; i++) {
			double blockingProb = i / 100.;
			averages.add(range(0, simulation).parallel()
			                                 .mapToDouble(j -> Percolation.percolates(n, blockingProb) ? 1 : 0)
			                                 .average()
			                                 .getAsDouble());
		}

		System.out.println(averages);
	}

}
