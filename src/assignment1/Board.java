package assignment1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Board {
    public int size = 5;

    // 2D Array of Cells for representation of the game board
    public final Cell[][] board = new Cell[size][size];

    private Piece.Type turn;
    private Piece.Type winner;
    private PieceFactory factory = new PieceFactory();
    
    /**
     * Create a Board with the current player turn set.
     */
    public Board() {
        this.loadBoard("Boards/Starter.txt");
    }

    /**
     * Create a Board with the current player turn set and a specified board.
     * @param boardFilePath The path to the board file to import (e.g. "Boards/Starter.txt")
     */
    public Board(String boardFilePath) {
    	
        this.loadBoard(boardFilePath);
    }

    /**
     * Creates a Board copy of the given board.
     * @param board Board to copy
     */
    public Board(Board board) {
        this.size = board.size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
            	this.board[row][col] = constructBoardCell(board.board[row][col]);
            }
        }
        this.turn = board.turn;
        this.winner = board.winner;
    }
    
    /**
     * Creates a Cell based on specifications
     * @param coordinate the Coordinate of the Cell
     * @return Cell that is constructed at the given coordinate
     */
    private Cell constructBoardCell(Cell copyCell) {
    	CellBuilder builder = new CellBuilder(copyCell);
    	return builder.getCell();
    }
    
    /**
     * Shuffles the board cells in a random fashion
     * @return the randomized board 
     */
    
    protected Board randomizeBoard(Board currentBoard) {
    	//int upper_bound = 5;
    	//int random_int = 11;
    	List<Cell> musketList = getMusketeerCells();
    	List<Cell> guardList = getGuardCells();
    	List<Cell> emptyList = getEmptyCells();
    	
    	// shuffle all guard cells 
    	for (int i = 0; 13 > i; i++) {
    		Random rand = new Random();
    		int random1 = rand.nextInt(guardList.size());
    		int random2 = rand.nextInt(guardList.size());	
    		
    		// swap guard cells 
    		while (random1 == random2)
            {
                random2 = rand.nextInt(guardList.size());
            }
    		
    		int row1 = guardList.get(random1).getCoordinate().row;
    		int col1 = guardList.get(random1).getCoordinate().col;
    		
    		int row2 = guardList.get(random2).getCoordinate().row;
    		int col2 = guardList.get(random2).getCoordinate().col;
    		
    		Piece piece1 = currentBoard.board[row1][col1].getPiece();
    		Piece piece2 = currentBoard.board[row2][col2].getPiece();
    		currentBoard.board[row2][col2].setPiece(piece1);
        	currentBoard.board[row1][col1].setPiece(piece2);
    	}
    	
    	// shuffles empty cells 
    	
    	if (emptyList.size() != 0) {
    		for (int i = 0; 5 > i; i++) {
        		Random rand = new Random();
    			int random1 = rand.nextInt(emptyList.size());
        		int random2 = rand.nextInt(guardList.size());	
        		
        		int row1 = emptyList.get(random1).getCoordinate().row;
        		int col1 = emptyList.get(random1).getCoordinate().col;
        		
        		int row2 = guardList.get(random2).getCoordinate().row;
        		int col2 = guardList.get(random2).getCoordinate().col;
        		
        		Piece piece1 = currentBoard.board[row1][col1].getPiece();
        		Piece piece2 = currentBoard.board[row2][col2].getPiece();
        		currentBoard.board[row2][col2].setPiece(piece1);
            	currentBoard.board[row1][col1].setPiece(piece2);		
    			
    		}
    		
    	}
    	// shuffle all muskeeter cells
    	// making sure none are in the same row/col 
    	
    	for (int i = 0; 3 > i; i++) {
    		Random rand = new Random();
    		Cell muskeeterCell = musketList.get(i);
    		List<Integer> rows = getMuskeeterRows();
    		List<Integer> cols = getMuskeeterCols();
        	List<Integer> nums = new ArrayList<>();
        	List<Integer> nums2 = new ArrayList<>();
        	
    		for (int i1 = 0; 5 > i1; i1++) {
    			nums.add(i1);
    			nums2.add(i1);
    		}
    		
    		for (int i2 = 0; 5 > i2; i2++) {
    			//System.out.println(i2);
    			if (rows.contains(i2)) {
    				nums.remove(nums.indexOf(i2)); }
    				
    			if (cols.contains(i2)) {
        			nums2.remove(nums2.indexOf(i2));	
    				}
    			}

    		int random1 = rand.nextInt(nums.size());
    		int random2 = rand.nextInt(nums2.size());
    		
       		// swap muskeeter cells 
    		
    		int row1 = muskeeterCell.getCoordinate().row;
    		int col1 = muskeeterCell.getCoordinate().col;
    		
        	Piece piece = currentBoard.board[nums.get(random1)][nums2.get(random2)].getPiece();

    		currentBoard.board[nums.get(random1)][nums2.get(random2)].setPiece(muskeeterCell.getPiece());
        	currentBoard.board[row1][col1].setPiece(piece);
    		
    	}
    	
    	return this;
    	
    }
    
    /**
     * @return a list of row numbers of each muskeeter cell
     */
    private List<Integer> getMuskeeterRows() {
        List<Integer> rows = new ArrayList<>();
        for (Cell cell: getMusketeerCells()) {
        	rows.add(cell.getCoordinate().row);
        }
        
        return rows;  
    }

    /**
     * @return a list of column numbers of each muskeeter cell
     */
    private List<Integer> getMuskeeterCols() {
        List<Integer> cols = new ArrayList<>();
        for (Cell cell: getMusketeerCells()) {
        	cols.add(cell.getCoordinate().col);
        }
        return cols;  
    }

    /**
     * @return the Piece.Type (Muskeeteer or Guard) of the current turn
     */
    public Piece.Type getTurn() {
        return turn;
    }

    /**
     * Get the cell located on the board at the given coordinate.
     * @param coordinate Coordinate to find the cell
     * @return Cell that is located at the given coordinate
     */
    public Cell getCell(Coordinate coordinate) {
        return this.board[coordinate.row][coordinate.col];
    }

    /**
     * @return the game winner Piece.Type (Muskeeteer or Guard) if there is one otherwise null
     */
    public Piece.Type getWinner() {
        return winner;
    }
    
    /**
     * Gets all the Empty cells on the board.
     * @return List of cells
     */
    public List<Cell> getEmptyCells() {
        return getAllCells()
                .stream()
                .filter(cell -> cell.getPiece().getType() == Piece.Type.EMPTYCELL)
                .toList();
    }

    /**
     * Gets all the Musketeer cells on the board.
     * @return List of cells
     */
    public List<Cell> getMusketeerCells() {
        return getAllCells()
                .stream()
                .filter(cell -> cell.hasPiece() && cell.getPiece().getType() == Piece.Type.MUSKETEER)
                .toList();
    }
    
    protected List<Cell> getAllCells() {
        return Arrays.stream(board).flatMap(Arrays::stream).collect(Collectors.toList());
    }

    /**
     * Gets all the Guard cells on the board.
     * @return List of cells
     */
    public List<Cell> getGuardCells() {
        return getAllCells()
                .stream()
                .filter(cell -> cell.hasPiece() && cell.getPiece().getType() == Piece.Type.GUARD)
                .toList();
    }

    /**
     * Executes the given move on the board and changes turns at the end of the method.
     * @param move a valid move
     */
    public void move(Move move) {
        Piece piece = move.fromCell.getPiece();
        move.toCell.setPiece(piece);
        move.fromCell.setPiece(factory.createPiece("EMPTYCELL"));
        changeTurn();
    }
    
    public void changeTurn() {
        setTurn(getTurn() == Piece.Type.MUSKETEER ? Piece.Type.GUARD : Piece.Type.MUSKETEER);
    }
    
    public void setTurn(Piece.Type turn) {
        this.turn = turn;
    }

    /**
     * Undo the move given.
     * @param move Copy of a move that was done and needs to be undone. The move copy has the correct piece info in the
     *             from and to cell fields. Changes turns at the end of the method.
     */
    public void undoMove(Move move) {
        Cell fromCell = getCell(move.fromCell.getCoordinate());
        Cell toCell = getCell(move.toCell.getCoordinate());
        fromCell.setPiece(move.fromCell.getPiece());
        toCell.setPiece(move.toCell.getPiece());
        changeTurn();
    }

    /**
     * Checks if the given move is valid. Things to check:
     * (1) the toCell is next to the fromCell
     * (2) the fromCell piece can move onto the toCell piece.
     * @param move a move
     * @return     True, if the move is valid, false otherwise
     */
    public Boolean isValidMove(Move move) {
        Cell fromCell = move.fromCell;
        Coordinate fromCoordinate = fromCell.getCoordinate();
        Coordinate toCoordinate = move.toCell.getCoordinate();

        if (!isNextTo(fromCoordinate, toCoordinate)) return false;
        if (!onBoard(toCoordinate)) return false;

        return fromCell.getPiece().canMoveOnto(move.toCell);
    }
    
    private Boolean isNextTo(Coordinate fromCoordinate, Coordinate toCoordinate) {
        int xDiff = Math.abs(fromCoordinate.col - toCoordinate.col);
        int yDiff = Math.abs(fromCoordinate.row - toCoordinate.row);
        return (xDiff == 0 && yDiff == 1) || (xDiff == 1 && yDiff == 0) ;
    }
    
    private Boolean onBoard(Coordinate coordinate) {
        return 0 <= coordinate.col && coordinate.col < this.size &&
                0 <= coordinate.row && coordinate.row < this.size;
    }

    /**
     * Get all the possible cells that have pieces that can be moved this turn.
     * @return      Cells that can be moved from the given cells
     */
    public List<Cell> getPossibleCells() {
        List<Cell> allCellsThisTurn = getTurn() == Piece.Type.MUSKETEER ? getMusketeerCells() : getGuardCells();
        List<Cell> possibleCells = new ArrayList<>();
        for (Cell cell : allCellsThisTurn) {
            if (!getPossibleDestinations(cell).isEmpty())
                possibleCells.add(cell);
        }
        return possibleCells;
    }

    /**
     * Get all the possible cell destinations that is possible to move to from the fromCell.
     * @param fromCell The cell that has the piece that is going to be moved
     * @return List of cells that are possible to get to
     */
    public List<Cell> getPossibleDestinations(Cell fromCell) {
        List<Cell> destinations = new ArrayList<>();
        int[][] possibleMoves = {{-1,0}, {0,1}, {1,0}, {0,-1}};

        for (int[] move: possibleMoves) {
            Coordinate oldCoordinate = fromCell.getCoordinate();
            int row = move[0] + oldCoordinate.row;
            int col = move[1] + oldCoordinate.col;
            Coordinate newCoordinate = new Coordinate(row, col);
            if (!onBoard(newCoordinate)) continue;

            Cell toCell = getCell(newCoordinate);
            if (isValidMove(new Move(fromCell, toCell)))
                destinations.add(toCell);
        }
        return destinations;
    }

    /**
     * Get all the possible moves that can be made this turn.
     * @return List of moves that can be made this turn
     */
    public List<Move> getPossibleMoves() {
        List<Move> moves = new ArrayList<>();
        List<Cell> possibleCells = this.getPossibleCells();
        for (Cell fromCell: possibleCells) {
            List<Cell> possibleDestinations = this.getPossibleDestinations(fromCell);
            for (Cell toCell : possibleDestinations) {
                moves.add(new Move(fromCell, toCell));
            }
        }
        return moves;
    }

    /**
     * Checks if the game is over and sets the winner if there is one.
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        if (inSameRowOrSameCol(getMusketeerCells())) {
            winner = Piece.Type.GUARD;
            return true;
        }
        if (getPossibleCells().isEmpty()) {
            winner = Piece.Type.MUSKETEER;
            return true;
        }
        return false;
    }
    
    private Boolean inSameRowOrSameCol(List<Cell> cells) {
        long numRows = cells.stream().map(cell -> cell.getCoordinate().row).distinct().count();
        long numCols = cells.stream().map(cell -> cell.getCoordinate().col).distinct().count();
        return numRows == 1 || numCols == 1;
    }

    /**
     * Saves the current board state to the boards directory
     */
    public void saveBoard() {
        String filePath = String.format("boards/%s.txt",
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        File file = new File(filePath);

        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row: board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell: row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", filePath);
        }
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("			         | A B C D E\n");
        boardStr.append("			       --+----------\n");
        for (int i = 0; i < size; i++) {
            boardStr.append("			       " + (i + 1)).append(" | ");
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                boardStr.append(cell).append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    /**
     * Loads a board file from a file path.
     * @param filePath The path to the board file to load (e.g. "Boards/Starter.txt")
     */
    //private void loadBoard(String filePath)
    protected void loadBoard(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.printf("File at %s not found.", filePath);
            System.exit(1);
        }

        turn = Piece.Type.valueOf(scanner.nextLine().toUpperCase());

        int row = 0, col = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.trim().split(" ");
            for (String piece: pieces) {
                Cell cell = new Cell(new Coordinate(row, col));
                switch (piece) {
                    case "O" -> cell.setPiece(factory.createPiece("Guard"));
                    case "X" -> cell.setPiece(factory.createPiece("Musketeer"));
                    default -> cell.setPiece(factory.createPiece("EmptyCell"));
                }
                this.board[row][col] = cell;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        scanner.close();
        System.out.printf("Loaded board from %s.\n", filePath);
    }
}