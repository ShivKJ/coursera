
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final Entry<Point2D, Double> NULL = createEntry(null, Double.MAX_VALUE);

    private Node root;
    private int  size;

    private double xMin, xMax, yMin, yMax;

    public KdTree() {
        this.size = 0;

        this.xMin = this.yMin = Double.MAX_VALUE;
        this.xMax = this.yMax = Double.MIN_VALUE;

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        updateRectContainingPoints(p);

        if (root == null) {
            this.root = new Node(p);
            size = 1;
        } else
            fix(p, true);

    }

    private void updateRectContainingPoints(Point2D p) {
        xMin = Math.min(xMin, p.x());
        yMin = Math.min(yMin, p.y());

        xMax = Math.max(xMax, p.x());
        yMax = Math.max(yMax, p.y());
    }

    private static boolean inLeftOrBelow(Node p, Point2D q) {
        if (p.isVertical)
            return q.x() < p.p.x();

        return q.y() < p.p.y();
    }

    private Node fix(Point2D p, boolean insert) {
        Node parent = root;
        boolean found = parent.p.equals(p);

        while (!found) {
            if (inLeftOrBelow(parent, p)) {
                if (parent.left == null)
                    break;

                parent = parent.left;
            } else {
                if (parent.right == null)
                    break;

                parent = parent.right;
            }

            found = parent.p.equals(p);
        }

        if (found)
            return parent;

        Node n = null;

        if (insert) {
            n = new Node(p);

            n.isVertical = !parent.isVertical;

            if (inLeftOrBelow(parent, p))
                parent.left = n;
            else
                parent.right = n;

            size++;
        }

        return n;
    }

    public boolean contains(Point2D p) {
        return root != null && fix(p, false) != null;
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);

        RectHV hv = new RectHV(xMin, yMin, xMax, yMax);
        hv.draw();

        draw(root, hv);

    }

    private static void draw(Node n, RectHV hv) {
        if (n == null)
            return;
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(n.p.x(), n.p.y(), 0.01);

        if (n.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), hv.ymin(), n.p.x(), hv.ymax());
            draw(n.left, new RectHV(hv.xmin(), hv.ymin(), n.p.x(), hv.ymax()));
            draw(n.right, new RectHV(n.p.x(), hv.ymin(), hv.xmax(), hv.ymax()));

        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(hv.xmin(), n.p.y(), hv.xmax(), n.p.y());

            draw(n.left, new RectHV(hv.xmin(), hv.ymin(), hv.xmax(), n.p.y()));
            draw(n.right, new RectHV(hv.xmin(), n.p.y(), hv.xmax(), hv.ymax()));

        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        checkInputArg(rect);

        Collection<Point2D> out = new LinkedList<>();
        update(root, rect, out);

        return out;
    }

    private static boolean cross(RectHV hv, Node n) {
        if (n.isVertical)
            return hv.xmin() <= n.p.x() && n.p.x() <= hv.xmax();
        else
            return hv.ymin() <= n.p.y() && n.p.y() <= hv.ymax();
    }

    private static void update(Node n, RectHV hv, Collection<Point2D> data) {
        if (n == null)
            return;

        if (cross(hv, n)) {
            if (hv.contains(n.p))
                data.add(n.p);

            update(n.left, hv, data);
            update(n.right, hv, data);

        } else {
            if (n.isVertical) {
                if (hv.xmax() < n.p.x())
                    update(n.left, hv, data);
                else
                    update(n.right, hv, data);
            } else {
                if (hv.ymax() < n.p.y())
                    update(n.left, hv, data);
                else
                    update(n.right, hv, data);
            }
        }

    }

    public Point2D nearest(Point2D p) {
        checkInputArg(p);

        return nearestFromNode(root, p).getKey();
    }

    private static Entry<Point2D, Double> nearestFromNode(Node n, Point2D p) {
        if (n == null)
            return NULL;

        if (n.noChild())
            return createEntry(n.p, n.p.distanceTo(p));

        Entry<Point2D, Double> middle = createEntry(n.p, n.p.distanceTo(p));
        Entry<Point2D, Double> left = n.left == null ? NULL : createEntry(n.left.p, n.left.p.distanceTo(p));
        Entry<Point2D, Double> right = n.right == null ? NULL : createEntry(n.right.p, n.right.p.distanceTo(p));

        if (inLeftOrBelow(n, p)) {
            if (n.isVertical) {
                if (left.getValue() < n.p.x() - p.x())
                    return nearestFromNode(n.left, p);
            } else {
                if (left.getValue() < n.p.y() - p.y())
                    return nearestFromNode(n.left, p);
            }

        } else {
            if (n.isVertical) {
                if (right.getValue() < p.x() - n.p.x())
                    return nearestFromNode(n.right, p);
            } else {
                if (right.getValue() < p.y() - n.p.y())
                    return nearestFromNode(n.right, p);
            }
        }

        Builder<Entry<Point2D, Double>> builder = Stream.builder();
        builder.accept(middle);

        if (left != NULL) {
            builder.accept(left);
            builder.accept(nearestFromNode(n.left, p));
        }
        if (right != NULL) {
            builder.accept(right);
            builder.accept(nearestFromNode(n.right, p));
        }

        return builder.build().min(Entry.comparingByValue()).get();

    }

    private static Entry<Point2D, Double> createEntry(Point2D p, double distance) {
        return new SimpleEntry<>(p, distance);
    }

    private static <T> T checkInputArg(T o) {
        if (o == null)
            throw new IllegalArgumentException();

        return o;
    }

    private static class Node {
        boolean       isVertical;
        final Point2D p;

        Node left, right;

        Node(Point2D p) {
            this.p = p;
            this.isVertical = true;
        }

        boolean noChild() {
            return left == null && right == null;
        }

    }

}
