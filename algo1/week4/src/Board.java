import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class Board {
    private final int[][] tiles;
    private final int     blankrow, blankCol;
    private Integer       manhattanDistance, hammingDistance;
    private Boolean       isGoal;

    public Board(int[][] tiles) {
        this(tiles, BlackIndex.newInstance(tiles));

    }

    private Board(int[][] tiles, int blankRow, int blackCol) {
        this.tiles = tiles;
        this.blankrow = blankRow;
        this.blankCol = blackCol;
    }

    private Board(int[][] tiles, BlackIndex blackIndex) {
        this.tiles = tiles;
        this.blankrow = blackIndex.row;
        this.blankCol = blackIndex.col;
    }

    private final static class BlackIndex {
        final int row, col;

        BlackIndex(int row, int col) {
            this.row = row;
            this.col = col;
        }

        static BlackIndex newInstance(int[][] tiles) {
            int row = -1, col = -1;
            for (int i = 0; i < tiles.length; i++)
                for (int j = 0; j < tiles.length; j++)
                    if (tiles[i][j] == 0) {
                        row = i;
                        col = j;
                        break;
                    }
            return new BlackIndex(row, col);
        }

    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(dimension()).append("\n");

        for (int[] arr : tiles) {
            for (int i : arr)
                builder.append(i + "\t");

            builder.append("\n");
        }

        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    public int dimension() {
        return tiles.length;
    }

    public int hamming() {
        if (hammingDistance == null) {
            int out = 0, n = dimension(), blocks = n * n - 1;

            for (int i = 0; i < blocks; i++)
                if (tiles[i / n][i % n] != i + 1)
                    out++;

            hammingDistance = out;
        }

        return hammingDistance;

    }

    public int manhattan() {
        if (manhattanDistance == null) {
            int out = 0, n = dimension(), blocks = n * n;

            for (int blockIndex = 0; blockIndex < blocks; blockIndex++)
                out += manhattanDistance(blockIndex);

            manhattanDistance = out;
        }

        return manhattanDistance;
    }

    private int manhattanDistance(int blockIndex) {
        int n = dimension();
        int r = blockIndex / n, c = blockIndex % n;
        int e = tiles[r][c];

        if (e == 0)
            return 0;

        e = e - 1;

        return Math.abs(r - e / n) + Math.abs(c - e % n);
    }

    public boolean isGoal() {
        if (isGoal == null) {
            int n = dimension(), blocks = n * n;

            for (int i = 0; i < blocks - 1; i++)
                if (tiles[i / n][i % n] != i + 1)
                    return isGoal = false;

            if (tiles[n - 1][n - 1] != 0)
                return isGoal = false;

            return isGoal = true;
        }

        return isGoal;

    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y instanceof Board)
            return Arrays.deepEquals(tiles, ((Board) y).tiles);

        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> out = new LinkedList<>();

        if (blankrow > 0)
            out.add(moveUp());

        if (blankCol > 0)
            out.add(moveleft());

        int n = dimension();

        if (blankrow < n - 1)
            out.add(moveDown());

        if (blankCol < n - 1)
            out.add(moveRight());

        return out;
    }

    private int[][] copyTiles() {
        int[][] out = tiles.clone();
        int n = dimension();

        for (int i = 0; i < n; i++)
            out[i] = out[i].clone();

        return out;
    }

    private Board moveUp() {
        int[][] newTiles = copyTiles();
        swap(newTiles, blankrow, blankCol, blankrow - 1, blankCol);
        return new Board(newTiles, blankrow - 1, blankCol);
    }

    private Board moveDown() {
        int[][] newTiles = copyTiles();
        swap(newTiles, blankrow, blankCol, blankrow + 1, blankCol);
        return new Board(newTiles, blankrow + 1, blankCol);
    }

    private Board moveleft() {
        int[][] newTiles = copyTiles();
        swap(newTiles, blankrow, blankCol, blankrow, blankCol - 1);
        return new Board(newTiles, blankrow, blankCol - 1);
    }

    private Board moveRight() {
        int[][] newTiles = copyTiles();
        swap(newTiles, blankrow, blankCol, blankrow, blankCol + 1);
        return new Board(newTiles, blankrow, blankCol + 1);
    }

    private static void swap(int[][] tiles, int a1, int b1, int a2, int b2) {
        int t1 = tiles[a1][b1], t2 = tiles[a2][b2];

        tiles[a1][b1] = t2;
        tiles[a2][b2] = t1;

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = copyTiles();

        if (blankrow == 0)
            swap(newTiles, blankrow + 1, 0, blankrow + 1, 1);
        else
            swap(newTiles, 0, 0, 0, 1);

        return new Board(newTiles, blankrow, blankCol);
    }

}
