package assignment1;

public class EmptyCell extends Piece {
	
	public EmptyCell() {
		super(" ", Type.EMPTYCELL);
	}
	
	@Override
	public boolean canMoveOnto(Cell cell) {
		return false;
	}
}
