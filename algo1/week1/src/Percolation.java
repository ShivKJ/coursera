import static java.util.Arrays.stream;
import static java.util.stream.Stream.generate;

import java.util.Random;
import java.util.function.BooleanSupplier;

public class Percolation {
	private final int         n;
	private final UF          uf;
	private final boolean[][] blocked;

	private Percolation(boolean[][] blocked) {
		this.n = blocked.length;
		this.uf = new UnionFind(n * n + 2);
		this.blocked = stream(blocked).map(boolean[]::clone)
		                              .toArray(boolean[][]::new);
	}

	public static boolean percolates(boolean[][] blocked) {
		int n = blocked.length;

		Percolation percolation = new Percolation(blocked);
		percolation.process();

		int up = n * n;

		return percolation.uf.connected(up, up + 1);
	}

	public static boolean percolates(int n, double blockingProbability) {
		Random random = new Random();

		BooleanSupplier isBlocked = () -> random.nextDouble() <= blockingProbability;

		boolean[][] blocked = generate(() -> row(n, isBlocked)).limit(n)
		                                                       .toArray(boolean[][]::new);
		
		return percolates(blocked);
	}

	private static boolean[] row(int n, BooleanSupplier predicate) {
		boolean[] arr = new boolean[n];

		for (int i = 0; i < n; i++)
			arr[i] = predicate.getAsBoolean();

		return arr;

	}

	private void process() {
		for (int i = 0; i < n; i++) {
			if (!blocked[0][i])
				connectToUp(i);

			for (int j = 0; j < n; j++)
				if (!blocked[i][j]) {
					if (j > 0 && !blocked[i][j - 1])
						connect(i, j, i, j - 1);

					if (j < n - 1 && !blocked[i][j + 1])
						connect(i, j, i, j + 1);

					if (i < n - 1 && !blocked[i + 1][j])
						connect(i, j, i + 1, j);

				}

			if (!blocked[n - 1][i])
				connectToDown(i);
		}
	}

	private int index(int a, int b) {
		return n * a + b;
	}

	private void connect(int a1, int b1, int a2, int b2) {
		uf.connect(index(a1, b1), index(a2, b2));
	}

	private void connectToDown(int c) {
		connect(n, 1, n - 1, c);
	}

	private void connectToUp(int c) {
		connect(n, 0, 0, c);
	}
}
