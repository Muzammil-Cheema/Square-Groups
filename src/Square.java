import java.util.Arrays;
/**
 * The class implementing squares.
 * Note: you can add more methods if you want, but additional methods must be <code>private</code> or <code>protected</code>.
 *
 * @author Muzammil Cheema
 */
public class Square implements Shape, Cloneable {
    private Point[] points;

    protected Square(){
        points = new Point[4];
    }

    /**
     * The constructor accepts an array of <code>Point</code>s to form the vertices of the square. If more than four
     * points are provided, only the first four are considered by this constructor.
     *
     * If less than four points are provided, or if the points do not form a valid square, the constructor throws
     * <code>java.lang.IllegalArgumentException</code>.
     *
     * @param vertices the array of vertices (i.e., <code>Point</code> instances) provided to the constructor.
     */
    public Square(Point... vertices) {
        if (vertices.length < 4)
            throw new IllegalArgumentException("A square needs 4 points.");
        points = Arrays.copyOfRange(vertices, 0, 4);
        if (!isValid(points))
            throw new IllegalArgumentException("Point ordering is invalid and will not form a square.");
    }

    private boolean isValid(Point... vertices) {
        Point a = vertices[0];
        Point b = vertices[1];
        Point c = vertices[2];
        Point d = vertices[3];
        return a.x >= b.x && a.x >= c.x && a.y >= c.y && a.y >= d.y && b.x <= d.x && b.y >= d.y && c.x <= d.x;
    }

    @Override
    public Shape rotateBy(int degrees) {
        Square ans;
        try {
            ans = this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        Point center = ans.center();
        double radians = Math.PI * degrees/180;

        ans = (Square) ans.translateBy(-center.x, -center.y);
        System.out.println(ans);
        for (Point p : ans.points) {
            double x = p.x;
            double y = p.y;
            p.x = x*Math.cos(radians) - y*Math.sin(radians);
            p.y = x*Math.sin(radians) + y*Math.cos(radians);
        }
        System.out.println(ans);
        ans = (Square) ans.translateBy(center.x, center.y);
        System.out.println(ans);

        Point[] unordered = ans.points;
        if (isValid(unordered[3], unordered[0], unordered[1], unordered[2]))
            ans.points = new Point[] {unordered[3], unordered[0], unordered[1], unordered[2]};
        else if (isValid(unordered[2], unordered[3], unordered[0], unordered[1]))
            ans.points = new Point[] {unordered[2], unordered[3], unordered[0], unordered[1]};
        else if (isValid(unordered[1], unordered[2], unordered[3], unordered[0]))
            ans.points = new Point[] {unordered[1], unordered[2], unordered[3], unordered[0]};
        return ans;
    }

    @Override
    public Shape translateBy(double x, double y) {
        Square ans;
        try {
            ans = this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        for (Point p : ans.points) {
            p.x += x;
            p.y += y;
        }
        return ans;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (Point p : points) {
            str.append(p).append("; ");
        }
        str.delete(str.length()-2, str.length()).append("]\n");
        return str.toString();
    }

    @Override
    public Point center() {
        return new Point("center",points[1].x + (points[3].x-points[1].x)/2,points[2].y + (points[0].y - points[2].y)/2);
    }

    @Override
    protected Square clone() throws CloneNotSupportedException {
        Square ans = new Square();
        for (int i = 0; i < 4; i++){
            ans.points[i] = new Point(points[i].name, points[i].x, points[i].y);
        }
        return ans;
    }







    public static void main(String... args) {
        Point  a = new Point("A", 1, 4);
        Point  b = new Point("B", 1, 1);
        Point  c = new Point("C", 4, 1);
        Point  d = new Point("D", 4, 4);

        Point p = new Point("P", 0.3, 0.3);

//        Square sq1 = new Square(a, b, c, d); // throws an IllegalArgumentException
        Square sq2 = new Square(d, a, b, c); // forms a square
        Square sq3 = new Square(p, p, p, p); // forms a "trivial" square (this is a limiting case, but still valid)

        // prints: [(D, 4.0, 4.0); (A, 1.0, 4.0); (B, 1.0, 1.0); (C, 4.0, 1.0)]
        System.out.println(sq2);

        // prints: [(C, 4.0, 4.0); (D, 1.0, 4.0); (A, 1.0, 1.0); (B, 4.0, 1.0)]
        // note that the names denote which point has moved where
        System.out.println(sq2.rotateBy(90));
        System.out.println(sq2);
        System.out.println(sq2.translateBy(3, 3));
        System.out.println(sq2);

    }
}

