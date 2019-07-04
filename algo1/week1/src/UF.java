import static java.lang.System.out;
import static java.util.stream.IntStream.range;

public final class UF {
	/**
	 * Storing parent of i^th node.
	 */
	private final int[] parent;

	public UF(int n) {
		this.parent = range(0, n).toArray();
	}

	/**
	 * connects the two node a and b.
	 * 
	 * It makes parent of root node of b to parent of a
	 * 
	 * @param a
	 * @param b
	 */
	public void connect(int a, int b) {
		parent[root(b)] = parent[a];
	}

	/**
	 * finds if node a and b are connected that is if they have same root node.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean connected(int a, int b) {
		return root(a) == root(b);
	}

	/**
	 * finds root of node a. A node is root if it is parent of itself.
	 * 
	 * @param a
	 * @return
	 */
	public int root(int a) {
		while (a != parent[a]) {
			parent[a] = parent[parent[a]];
			a = parent[a];
		}

		return a;
	}

	/**
	 * finds connected component in given nodes.
	 * 
	 * @return
	 */
	public int connectedComponent() {
		return (int) range(0, parent.length).map(this::root).distinct().count();
	}

	public static void main(String[] args) {
		UF uf = new UF(10);

		uf.connect(5, 6);
		uf.connect(0, 5);
		uf.connect(1, 2);
		uf.connect(3, 4);
		uf.connect(7, 8);
		uf.connect(7, 9);
		uf.connect(2, 8);
		uf.connect(1, 9);

		out.println(uf.connectedComponent());
	}
}
