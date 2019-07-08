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

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final static int MIN_PT_ON_LINE = 4;

    private final Point[] points;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
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
        int n = points.length;

        List<LineSegment> lineSegments = new LinkedList<>();

        for (int i = 0; i < n; i++)
            for (int j = i; j < n; j++) {
                double s1 = points[i].slopeTo(points[j]);

                for (int k = j; k < n; k++)
                    for (int l = k; l < n; l++) {
                        double s2 = points[k].slopeTo(points[l]);

                        if (abs(s1 - s2) <= 10e-8)
                            lineSegments.add(getLineSeg(points[i], points[j], points[k], points[l]));

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

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);

        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
