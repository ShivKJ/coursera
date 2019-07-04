import static java.util.stream.IntStream.range;

public final class UF {
	private final int[] cache;

	public UF(int n) {
		this.cache = range(0, n).toArray();

	}

	public void connect(int a, int b) {
		cache[parent(b)] = parent(a);
	}

	public boolean connected(int a, int b) {
		return parent(a) == parent(b);
	}

	public int parent(int a) {
		int p = cache[a];
		return a == p ? a : (cache[a] = parent(p));
	}

	public int connectedComponent() {
		return (int) range(0, cache.length).map(this::parent).distinct().count();
	}

	public static void main(String[] args) {
		UF uf = new UF(10);

		uf.connect(1, 2);
		uf.connect(3, 4);
		uf.connect(5, 6);
		uf.connect(7, 8);
		uf.connect(7, 9);
		uf.connect(2, 8);
		uf.connect(0, 5);
		uf.connect(1, 9);

		range(0, 10).forEach(uf::parent);
		System.out.println(uf.connectedComponent());
	}
}
