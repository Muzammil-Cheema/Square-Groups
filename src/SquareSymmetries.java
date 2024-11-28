import java.util.ArrayList;
import java.util.List;

public class SquareSymmetries extends Square implements Symmetries<Square> {

    @Override
    public boolean areSymmetric(Square s1, Square s2) {
        for (Square s : symmetriesOf(s1))
            if (s2.equals(s))
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
        symmetries.add(square.swapPoints(0, 3).swapPoints(1, 2));
        symmetries.add(square.swapPoints(0, 1).swapPoints(2, 3));
        symmetries.add(square.swapPoints(1,3));
        symmetries.add(square.swapPoints(0,2));

        return symmetries;
    }

}
