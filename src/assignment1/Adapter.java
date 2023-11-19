package assignment1;

public class Adapter {
	
	private MoveLog moveLog;
	
	public Adapter() {
		this.moveLog = new MoveLog();
	}
	
	public void convertTextfileName(String filename) {
		moveLog.createTextFile(filename);
	}
	
	public void convertAddMove(Move move, Piece.Type turn) {
		String mainMove = move.toString();
		Boolean boardTurn;
		if (turn == Piece.Type.MUSKETEER) {
			boardTurn = true;
		} else {
			boardTurn = false;
		}
		moveLog.addText(mainMove, boardTurn);
	}
	
	public void convertRemoveMove() {
		moveLog.removeText();
	}
	
	public void convertRandomizeText() {
		moveLog.addRandomized("|   !!Randomized Board!!   |");
	}
	
}
