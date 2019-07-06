package others;
import static java.util.Arrays.sort;
import static java.util.Comparator.comparingInt;

import java.util.LinkedList;
import java.util.List;

public final class Kruskal {
	private static final int U = 0, V = 1, W = 2;

	private final UF      uf;
	private final int[][] edges;

	private Kruskal(int n, int[][] edges) {
		this.uf = new UnionFind(n);
		this.edges = edges.clone();
	}

	public static List<int[]> kruskal(int nodes, int[][] edges) {
		return new Kruskal(nodes, edges).process();
	}

	private List<int[]> process() {
		sort(edges, comparingInt(arr -> arr[W]));

		List<int[]> out = new LinkedList<>();

		for (int[] e : edges)
			if (!uf.connected(e[U], e[V])) {
				out.add(e);
				uf.connect(e[U], e[V]);
			}

		return out;
	}

}
