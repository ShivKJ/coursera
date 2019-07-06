package others;

public interface UF {
	void connect(int a, int b);

	boolean connected(int a, int b);

	int root(int a);
}
