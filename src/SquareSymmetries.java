import java.util.ArrayList;
import java.util.List;

public class SquareSymmetries implements Symmetries<Square> {

    @Override
    public boolean areSymmetric(Square s1, Square s2) {
        for (Square s : symmetriesOf(s1))
            if (equalSquares(s, s2))
                return true;
        return false;
    }

    @Override
    public List<Square> symmetriesOf(Square square) {
        List<Square> symmetries = new ArrayList<>();

        //All rotations
        for (int i = 0; i <= 3; i++){
            symmetries.add(square.rotateBy(90*i));
        }

        //All reflections
        symmetries.add(swapPoints(swapPoints(square, 0, 3), 1, 2));
        symmetries.add(swapPoints(swapPoints(square, 0, 1),2, 3));
        symmetries.add(swapPoints(square,1,3));
        symmetries.add(swapPoints(square,0,2));

        return symmetries;
    }

    private Square swapPoints(Square s, int i, int j) throws IllegalArgumentException {
        if (!(i>=0 && i<=3 && j>=0 && j<=3))
            throw new IllegalArgumentException("Both indices must be between 0 and 3 inclusive.");

        //Swapping x and y coordinates
        Square square = s.clone();
        square.getPoints()[j].x = s.getPoints()[i].x;
        square.getPoints()[j].y = s.getPoints()[i].y;
        square.getPoints()[i].x = s.getPoints()[j].x;
        square.getPoints()[i].y = s.getPoints()[j].y;
        //Reordering points to keep counterclockwise order.
        Point temp = square.getPoints()[i];
        square.getPoints()[i] = square.getPoints()[j];
        square.getPoints()[j] = temp;

        return square;
    }

    private boolean equalSquares(Square s1, Square s2) {
        for (int i = 0; i < 4; i++){
            if (s1.getPoints()[i].x != s2.getPoints()[i].x || s1.getPoints()[i].y != s2.getPoints()[i].y)
                return false;
        }
        return true;
    }
}
