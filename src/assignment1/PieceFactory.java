package assignment1;

public class PieceFactory {
	public PieceFactory() {}
	
	public Piece createPiece(String type) {
		if(type == "Guard") {
			return new Guard();
		}
		else if (type == "Musketeer") {
			return new Musketeer();
		}
		else if (type == "EmptyCell") {
			return new EmptyCell();
		}
		return new EmptyCell();
	}

}
