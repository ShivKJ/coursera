import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {
    private final Board board;
    private Node        lastNode;

    public Solver(Board initial) {
        this.board = check(initial);
        process();
    }

    private static Board check(Board board) {
        if (board == null)
            throw new IllegalArgumentException();

        return board;
    }

    private void process() {
        MinPQ<Node> pq1 = new MinPQ<>();

        pq1.insert(new Node(board));

        while (lastNode == null)
            lastNode = expand(pq1);

    }

    private Node expand(MinPQ<Node> pq) {
        if (!pq.isEmpty()) {

            Node n = pq.delMin();

            if (n.board.isGoal())
                return n;

            for (Board b : n.board.neighbors())
                if (n.prevNode == null || !n.prevNode.board.equals(b))
                    pq.insert(new Node(n, b));
        }

        return null;
    }

    public boolean isSolvable() {
        return lastNode != null;
    }

    public int moves() {
        return isSolvable() ? lastNode.moves : -1;
    }

    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<>();
        Node n = lastNode;

        while (n != null) {
            stack.push(n.board);
            n = n.prevNode;
        }
        return stack;
    }

    private static class Node implements Comparable<Node> {
        private final Node  prevNode;
        private final Board board;
        private final int   moves;

        private Node(Node prevNode, Board currentBoard) {
            this.prevNode = prevNode;
            this.board = currentBoard;
            this.moves = prevNode == null ? 0 : prevNode.moves + 1;
        }

        private Node(Board board) {
            this(null, board);
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(board.manhattan() + moves, o.board.manhattan() + o.moves);
        }

    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In("data/puzzle17.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
