import java.util.Collection;

public class SquareSymmetries implements Symmetries<Square> {

    @Override
    public boolean areSymmetric(Square s1, Square s2) {
        return false;
    }

    @Override
    public Collection<Square> symmetriesOf(Square square) {
        return null;
    }
}
