package assignment1;

import java.util.List;
import java.util.Random;

public class BestMove {
	
	ThreeMusketeers musketeer;
	RandomAgent randomAgent;
	Move chosenMove;
	Board boardCopy;
	
	public BestMove(Board board) {
		boardCopy = board;
	}
	
    /**
     * Returns a random move
     * @return A string of a random best move to make
     */
	public String randomMove() {
		List<Cell> possibleCells = boardCopy.getPossibleCells();
        Cell fromCell = possibleCells.get(new Random().nextInt(possibleCells.size()));

        List<Cell> possibleDestinations = boardCopy.getPossibleDestinations(fromCell);
        Cell toCell = possibleDestinations.get(new Random().nextInt(possibleDestinations.size()));

        chosenMove = new Move(fromCell, toCell);
        
		return chosenMove.toString();
	}
	

}
