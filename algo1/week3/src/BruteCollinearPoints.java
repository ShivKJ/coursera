
//import static java.lang.Math.abs;
//import static java.util.Arrays.asList;
//import static java.util.Arrays.copyOf;
//import static java.util.Arrays.sort;
//import static java.util.Collections.max;
//import static java.util.Collections.min;
//import static java.util.Objects.isNull;

import static java.lang.Double.isInfinite;
import static java.util.Arrays.asList;
import static java.util.Arrays.sort;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BruteCollinearPoints {
    private final static int    MIN_PT_ON_LINE = 4;
    private final static double TOL            = 1e-10;

    private final Point[] points;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        this.points = checkInput(points);
        process();
    }

    private static Point[] checkInput(Point[] points) {
        if (isNull(points) || points.length < MIN_PT_ON_LINE || stream(points).anyMatch(Objects::isNull))
            throw new IllegalArgumentException();

        points = points.clone();

        sort(points);

        for (int i = 0; i < points.length - 1; i++)
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();

        return points;
    }

    private void process() {
        int n = points.length;

        List<LineSegment> lineSegments = new LinkedList<>();

        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                double s1 = points[i].slopeTo(points[j]);
                boolean isInf = isInfinite(s1);

                for (int k = j + 1; k < n; k++) {
                    double s = points[j].slopeTo(points[k]);

                    if (Math.abs(s1 - s) <= TOL || (isInf && isInfinite(s)))
                        for (int l = k + 1; l < n; l++) {
                            double s2 = points[k].slopeTo(points[l]);

                            if (Math.abs(s1 - s2) <= TOL || (isInf && isInfinite(s2)))
                                lineSegments.add(getLineSeg(points[i], points[j], points[k], points[l]));

                        }
                }
            }

        this.lineSegments = lineSegments.toArray(LineSegment[]::new);
    }

    private static LineSegment getLineSeg(Point... pts) {
        Collection<Point> points = asList(pts);
        return new LineSegment(Collections.min(points), Collections.max(points));
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }
}
