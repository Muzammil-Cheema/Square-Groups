import java.util.List;

/**
 * This class is given to you as an outline for testing your code. You can modify this as you want, but please keep in
 * mind that the lines already provided here as expected to work exactly as they are.
 *
 * @author Ritwik Banerjee
 */
public class GeometryTest {

    public static void main(String... args) {
        testSquareSymmetries();
    }

    private static void testSquareSymmetries() {
        Square s1 = new Square(new Point("OneTwo", 1, 2), new Point("ZeroTwo", 0, 2), new Point("ZeroOne", 0, 1), new Point("OneOne", 1, 1));
//        Square s2 = s1.rotateBy(30);
//        Square s3 = s1.rotateBy(180);

        SquareSymmetries squareSymmetries = new SquareSymmetries();
//        System.out.println(squareSymmetries.areSymmetric(s1, s2)); // expected to return false
//        System.out.println(squareSymmetries.areSymmetric(s1, s3)); // expected to return true
//        List<Square> symmetries = squareSymmetries.symmetriesOf(s1);

        // Your code must ensure that s1.toString() abides by the followin:
        // 1. Any non-integer coordinate value must be correctly rounded and represented with two decimal places.
        // 2. Keep in mind that the order of the four vertices of a square is important! Consult the examples in the
        //    assignment PDF for details.
//        for (Square s : symmetries)
//            System.out.println(s.toString());

        System.out.println(s1.rotateBy(270));
//        System.out.println(s1.rotateBy(270).isValid(new Point("OneTwo", 1, 2), new Point("ZeroTwo", 0, 2), new Point("ZeroOne", 0, 1), new Point("OneOne", 1, 1)));
    }
}