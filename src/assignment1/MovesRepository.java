package assignment1;

import java.util.ArrayList;
import java.util.List;

public class MovesRepository{
	
	private final Adapter adapter;
	private final List<Move> moves = new ArrayList<>();
	private moveIterator moveiterator;
	
	public MovesRepository() {
		this.adapter = new Adapter();
	}
	
	public void createText(String filename) {
		adapter.convertTextfileName(filename);
	}
	
	public void addMove(Move move, Piece.Type turn) {
		this.moves.add(move);
		adapter.convertAddMove(move, turn);
	}
	
	public Move removeMove() {
		adapter.convertRemoveMove();
		return this.moves.remove(moves.size() - 1);
	}
	
	public void randomizedboard() {
		adapter.convertRandomizeText();
	}
	
	public int size() {
		return this.moves.size();
	}
	
	public moveIterator iterator() {
		moveiterator = new moveIterator(moves);
		return moveiterator;
		
	}
	
	
}