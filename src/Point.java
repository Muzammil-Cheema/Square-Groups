import java.util.StringJoiner;

/**
 * A point in the standard two-dimensional Euclidean space. The coordinates of such a point are given by exactly two
 * doubles specifying its <code>x</code> and <code>y</code> values. Each point also has a unique unmodifiable name,
 * which is a <code>String</code> value.
 */
public class Point implements Cloneable {

    public double x, y;
    public final String name;

    public Point(String name, double x, double y) {
        this.name = name;
        this.x    = x;
        this.y    = y;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "(", ")").add(name).add(String.format("%.2f", x)).add(String.format("%.2f", y)).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        if (Double.compare(point.x, x) != 0) return false;
        if (Double.compare(point.y, y) != 0) return false;
        return name.equals(point.name);
    }

    public boolean equalsIgnoreName(Object o) {
        if (this == o)
            return true;
        if(!(o instanceof Point))
            return false;
        Point p = (Point) o;
        if (Double.compare(p.x, x) != 0) return false;
        if (Double.compare(p.y, y) != 0) return false;
        return true;
    }

    @Override
    public Point clone() {
        return new Point(name, x, y);
    }
}