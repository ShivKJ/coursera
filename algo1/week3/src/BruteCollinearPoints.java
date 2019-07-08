import static java.lang.Double.isInfinite;
import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.sort;
import static java.util.Collections.max;
import static java.util.Collections.min;
import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private final Point[] points;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        this.points = copyOf(checkInput(points), points.length);
        process();
    }

    private static Point[] checkInput(Point[] points) {

        if (isNull(points) || points.length < 4)
            throw new IllegalArgumentException();

        sort(points);

        for (int i = 0; i < points.length - 1; i++)
            if (points[i].equals(points[i + 1]))
                throw new IllegalArgumentException();

        return points;
    }

    private void process() {
        int n = points.length;

        List<LineSegment> lineSegments = new LinkedList<>();

        for (int i = 0; i < n; i++)
            for (int j = i; j < n; j++) {
                double s1 = points[i].slopeTo(points[j]);
                boolean isInf = isInfinite(s1);

                for (int k = j; k < n; k++)
                    for (int l = k; l < n; l++) {
                        double s2 = points[k].slopeTo(points[l]);

                        if ((isInf && isInfinite(s2)) || abs(s1 - s2) <= 10e-8) {
                            LineSegment ls = getLineSeg(points[i], points[j], points[k], points[l]);

                            if (!lineSegments.contains(ls))
                                lineSegments.add(ls);
                        }
                    }
            }

        this.lineSegments = lineSegments.toArray(LineSegment[]::new);
    }

    private static LineSegment getLineSeg(Point... pts) {
        Collection<Point> points = asList(pts);
        return new LineSegment(min(points), max(points));
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }
}
