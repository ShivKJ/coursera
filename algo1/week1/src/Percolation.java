import static java.util.Arrays.fill;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int                  n;
	private final WeightedQuickUnionUF uf;
	private final boolean[]            blocked;
	private int                        openSites;

	public Percolation(int n) {
		this.n = n;
		this.uf = new WeightedQuickUnionUF(n * n + 2);
		this.blocked = blocked(n * n + 2);
	}

	public void open(int i, int j) {
		int index = index(i, j);

		if (isOpen(i, j))
			return;

		blocked[index] = false;
		openSites++;

		tryConnectUp(i, j);
		tryConnectLeft(i, j);
		tryConnectRight(i, j);
		tryConnectDown(i, j);
		
	}

	public boolean isOpen(int i, int j) {
		return !blocked[index(i, j)];
	}

	public boolean isFull(int i, int j) {
		return uf.connected(n * n, index(i, j));
	}

	public int numberOfOpenSites() {
		return openSites;
	}

	public boolean percolates() {
		return uf.connected(n * n, n * n + 1);
	}

	private int index(int i, int j) {
		return n * (i - 1) + j - 1;
	}

	private void connect(int a1, int b1, int a2, int b2) {
		uf.union(index(a1, b1), index(a2, b2));
	}

	private void connectToDownSentinal(int c) {
		connect(n + 1, 2, n, c);
	}

	private void connectToUpSentinal(int c) {
		connect(n + 1, 1, 1, c);
	}

	private void tryConnectUp(int i, int j) {
		if (i == 1)
			connectToUpSentinal(j);
		else if (isOpen(i - 1, j))
			connect(i, j, i - 1, j);

	}

	private void tryConnectLeft(int i, int j) {
		if (j > 1 && isOpen(i, j - 1))
			connect(i, j, i, j - 1);
	}

	private void tryConnectRight(int i, int j) {
		if (j < n && isOpen(i, j + 1))
			connect(i, j, i, j + 1);
	}

	private void tryConnectDown(int i, int j) {

		if (i == n)
			connectToDownSentinal(j);
		else if (isOpen(i + 1, j))
			connect(i, j, i + 1, j);

	}

	private static boolean[] blocked(int n) {
		boolean[] out = new boolean[n];

		fill(out, true);

		return out;
	}

}
