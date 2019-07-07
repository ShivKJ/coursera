import static java.lang.Math.abs;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.sort;
import static java.util.Objects.isNull;

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
                double slope = points[i].slopeTo(points[j]);
            
                for (int k = j; k < n; k++)
                    for (int l = k; l < n; l++)
                        if (abs(slope - points[k].slopeTo(points[l])) <= 10e-8)
                            lineSegments.add(getLineSeg(points[i], points[j], points[k], points[l]));

            }

        this.lineSegments = lineSegments.toArray(LineSegment[]::new);
    }

    private static LineSegment getLineSeg(Point... pts) {
        double maxDis = 0;
        Point a = null, b = null;;

        for (int i = 0; i < pts.length; i++)
            for (int j = i; j < pts.length; j++) {
                if (pts[i].distance(pts[j]) >= maxDis) {
                    a = pts[i];
                    b = pts[j];
                }
            }
        return new LineSegment(a, b);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }
}
