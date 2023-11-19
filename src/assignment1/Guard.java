package assignment1;

public class Guard extends Piece {

	private static String guardsymbol = "O";
	
    public Guard() {
        super(guardsymbol, Type.GUARD);
    }
    
    public static void setSymbol(String usersymbol) {
    	guardsymbol = usersymbol;
    }

    /**
     * Returns true if the Guard can move onto the given cell.
     * @param cell Cell to check if the Guard can move onto
     * @return True, if Guard can move onto given cell, false otherwise
     */
    @Override
    public boolean canMoveOnto(Cell cell) {
        return cell.getPiece().getType() == Type.EMPTYCELL;
    }
}
