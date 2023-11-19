package assignment1;

import java.util.Stack;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class moveIterator {
	private Stack<Move> guardIterator = new Stack<>();
	private Stack<Move> musketeerIterator = new Stack<>();
	int index = 0;
	
	public moveIterator(List<Move> moves) {
		for(int i =0;i<moves.size();i++) {
			if(moves.get(i).fromCell.getPiece().getType() == Piece.Type.GUARD) {
				guardIterator.push(moves.get(i));
			}
			else {
				musketeerIterator.push(moves.get(i));
			}
		}
	}
	
	public boolean guardHasPrev() {
		return !guardIterator.empty();
	}
	
	public boolean musketeerHasPrev() {
		return !musketeerIterator.empty();
	}
	
	public Move guardPrev() {
		index += 1;
		return guardIterator.pop();
	}
	
	public Move musketeerPrev() {
		index += 1;
		return musketeerIterator.pop();
	}
	
	
	
}