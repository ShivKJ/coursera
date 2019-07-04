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

	public static void main(String[] args) {
		UF uf = new UF(5);

		uf.connect(0, 1);
		uf.connect(1, 2);
		uf.connect(3, 2);

		System.out.println(uf.connected(2, 3));
	}
}
