import static java.util.Arrays.copyOf;
import static java.util.Arrays.sort;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparingDouble;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.groupingBy;

import java.util.LinkedList;
import java.util.List;

public final class FastCollinearPoints {
    private final static int MIN_PT_ON_LINE = 4;

    private final Point[] points;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        this.points = copyOf(checkInput(points), points.length);
        process();
    }

    private static Point[] checkInput(Point[] points) {

        if (isNull(points) || points.length < MIN_PT_ON_LINE)
            throw new IllegalArgumentException();

        sort(points);

        for (int i = 0; i < points.length - 1; i++)
            if (points[i].equals(points[i + 1]))
                throw new IllegalArgumentException();

        return points;
    }

    private void process() {
        List<LineSegment> output = new LinkedList<>();

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];

            // in case other points makes slope -Inf then putting "p" before all other such points.
            sort(points, p.slopeOrder().thenComparingInt(o -> o == p ? -1 : 1));

            stream(points).skip(1)// skipping the "p" Point.
                          .collect(groupingBy(p::slopeTo))// as po
                          .values()
                          .stream()
                          .filter(ps -> ps.size() < MIN_PT_ON_LINE)
                          .map(ps -> ps.stream().max(comparingDouble(p::distance)).get())
                          .map(pt -> new LineSegment(p, pt))
                          .filter(ls -> !output.contains(ls))
                          .forEach(output::add);
        }

        this.lineSegments = output.toArray(LineSegment[]::new);
    }

    public int numberOfSegments() {
        return lineSegments.length;

    }

    public LineSegment[] segments() {
        return lineSegments;
    }
}
