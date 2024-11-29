import java.math.BigDecimal;
import java.math.RoundingMode;
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



    @Override
    public Square rotateBy(int degrees) {
        Square sq = this.clone();
        Point center = sq.center();
        double radians = Math.PI * degrees/180;

        sq = sq.translateBy(-center.x, -center.y);
        for (Point p : sq.points) {
            double x = p.x;
            double y = p.y;
            p.x = x*Math.cos(radians) - y*Math.sin(radians);
            p.y = x*Math.sin(radians) + y*Math.cos(radians);
        }
        sq = sq.translateBy(center.x, center.y);

        Point[] unordered = sq.points;
        for (int i = 0; i < 4; i++){
            unordered[i] = round(unordered[i]);
        }

        if (isValid(unordered[3], unordered[0], unordered[1], unordered[2]))
            sq.points = new Point[] {unordered[3], unordered[0], unordered[1], unordered[2]};
        else if (isValid(unordered[2], unordered[3], unordered[0], unordered[1]))
            sq.points = new Point[] {unordered[2], unordered[3], unordered[0], unordered[1]};
        else if (isValid(unordered[1], unordered[2], unordered[3], unordered[0]))
            sq.points = new Point[] {unordered[1], unordered[2], unordered[3], unordered[0]};
        return sq;
    }

    @Override
    public Square translateBy(double x, double y) {
        Square sq = this.clone();
        for (int i = 0; i < 4; i++) {
            sq.points[i] = round(new Point(sq.points[i].name, sq.points[i].x+x, sq.points[i].y+y));
        }
        return sq;
    }

    @Override
    public Point center() {
        return new Point("center",points[1].x + (points[3].x-points[1].x)/2,points[2].y + (points[0].y - points[2].y)/2);
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
    protected Square clone(){
        Square square = new Square();
        for (int i = 0; i < 4; i++){
            square.points[i] = points[i].clone();
        }
        return square;
    }

    public boolean isValid(Point... vertices) {
        //Checks if all points are the same
        int count = 0;
        for (int i = 0; i < 3; i++){
            if (vertices[i] == vertices[i+1]){
                count++;
            }
        }
        if (count == 3)
            return true;

        //Checks if square, then points ordering
        Point a = vertices[0];
        Point b = vertices[1];
        Point c = vertices[2];
        Point d = vertices[3];
        return distance(a, b) == distance(b, c) && distance(b, c) == distance(c,d) && distance(c, d) == distance(d, a) &&
                a.x > b.x && a.x > c.x && a.y >= c.y && a.y > d.y && b.x <= d.x && b.y > d.y && c.x <= d.x;
    }

    private double distance(Point p1, Point p2){
        return Math.sqrt(Math.pow(p1.x-p2.x,2) + Math.pow(p1.y-p2.y,2));
    }

    protected Square swapPoints(int i, int j) throws IllegalArgumentException {
        if (!(i>=0 && i<=3 && j>=0 && j<=3))
            throw new IllegalArgumentException("Both indices must be between 0 and 3 inclusive.");

        //Swapping x and y coordinates
        Square square = this.clone();
        square.points[j].x = points[i].x;
        square.points[j].y = points[i].y;
        square.points[i].x = points[j].x;
        square.points[i].y = points[j].y;
        //Reordering points to keep counterclockwise order.
        Point temp = square.points[i];
        square.points[i] = square.points[j];
        square.points[j] = temp;

        return square;
    }

    private static Point round (Point p){
        BigDecimal x = new BigDecimal(p.x).setScale(2, RoundingMode.HALF_UP);
        BigDecimal y = new BigDecimal(p.y).setScale(2, RoundingMode.HALF_UP);
        return new Point(p.name, x.doubleValue(), y.doubleValue());
    }

    protected boolean equals(Square s) {
        for (int i = 0; i < 4; i++){
            if (!points[i].equalsIgnoreName(s.points[i]))
                return false;
        }
        return true;
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
        System.out.println(sq2.translateBy(3, 1.4597));
        System.out.println(sq2);
        System.out.println(sq2.swapPoints(0, 3).swapPoints(1, 2));
    }
}

