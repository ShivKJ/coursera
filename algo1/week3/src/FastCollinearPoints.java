
import static java.util.Arrays.sort;
import static java.util.Arrays.stream;
import static java.util.Collections.max;
import static java.util.Collections.min;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class FastCollinearPoints {
    private final static int MIN_PT_ON_LINE = 4;

    private final Point[]          pts;
    private LineSegment[]          lineSegments;
    private final Set<LineSegment> containedSegments;

    public FastCollinearPoints(Point[] points) {
        this.pts = checkInput(points);
        this.containedSegments = new HashSet<>();
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
        List<LineSegment> output = new LinkedList<>();

        for (int i = 0; i < pts.length; i++) {
            Point p = pts[i];

            Point[] points = pts.clone();
            sort(points, p.slopeOrder());

            Collection<Point> sameSlopePoints = new ArrayList<>(MIN_PT_ON_LINE);
            double slope = p.slopeTo(points[1]), s = slope;

            for (int j = 1; j < points.length; sameSlopePoints.clear(), slope = s) {

                do
                    sameSlopePoints.add(points[j++]);
                while (j < points.length && slope == (s = p.slopeTo(points[j])));

                if (sameSlopePoints.size() >= MIN_PT_ON_LINE - 1) {
                    sameSlopePoints.add(p);

                    LineSegment ls = new LineSegment(min(sameSlopePoints), max(sameSlopePoints));

                    if (containedSegments.add(ls))
                        output.add(ls);

                }
            }
        }

        this.lineSegments = output.toArray(LineSegment[]::new);
    }

    public int numberOfSegments() {
        return lineSegments.length;

    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

}
