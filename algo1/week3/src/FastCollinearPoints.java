import static java.util.Arrays.sort;
import static java.util.Collections.max;
import static java.util.Collections.min;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public final class FastCollinearPoints {
    private final static int MIN_PT_ON_LINE = 4;

    private final Point[] points;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        this.points = checkInput(points);
        process();
    }

    private static Point[] checkInput(Point[] points) {
        if (isNull(points) || points.length < MIN_PT_ON_LINE)
            throw new IllegalArgumentException();

        points = points.clone();

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

            sort(points, p.slopeOrder());

            Collection<Point> sameSlopePoints = new ArrayList<>(MIN_PT_ON_LINE);
            double slope = p.slopeTo(points[1]), s = slope;

            for (int j = 1; j < points.length; sameSlopePoints.clear(), slope = s) {
                do
                    sameSlopePoints.add(points[j++]);
                while (j < points.length && slope == (s = p.slopeTo(points[j])));

                if (sameSlopePoints.size() >= MIN_PT_ON_LINE) {
                    sameSlopePoints.add(p);
                    output.add(new LineSegment(min(sameSlopePoints), max(sameSlopePoints)));
                }

            }

        }

        this.lineSegments = output.toArray(LineSegment[]::new);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
