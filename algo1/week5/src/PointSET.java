
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private final Set<Point2D> points;

    public PointSET() {
        this.points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(checkInputArg(p));
    }

    public boolean contains(Point2D p) {
        return points.contains(checkInputArg(p));
    }

    public void draw() {
        points.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkInputArg(rect);

        return () -> points.stream().filter(rect::contains).iterator();
    }

    public Point2D nearest(Point2D p) {
        checkInputArg(p);

        return points.stream()
                     .min(Comparator.comparingDouble(p::distanceSquaredTo))
                     .orElse(null);
    }

    private static <T> T checkInputArg(T o) {
        if (o == null)
            throw new IllegalArgumentException();

        return o;
    }
}