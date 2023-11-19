package assignment1;

public class Musketeer extends Piece {
	
	private static String musketeersymbol = "X";

    public Musketeer() {
        super(musketeersymbol, Type.MUSKETEER);
    }

    public static void setSymbol(String usersymbol) {
    	musketeersymbol = usersymbol;
    }
    /**
     * Returns true if the Musketeer can move onto the given cell.
     * @param cell Cell to check if the Musketeer can move onto
     * @return True, if Musketeer can move onto given cell, false otherwise
     */
    @Override
    public boolean canMoveOnto(Cell cell) {
        return cell.hasPiece() && cell.getPiece().getType() == Type.GUARD;
    }
}
