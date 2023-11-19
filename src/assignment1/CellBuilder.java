package assignment1;

public class CellBuilder {
    private Coordinate coordinate;
    private Piece piece;
    private Cell copyCell;

    public CellBuilder(Coordinate coordinate) {
    	this.coordinate = coordinate;
    }
    
    public CellBuilder(Cell copyCell) {
    	this.copyCell = copyCell;
    }
    
    public void setPiece(Piece piece) {
    	this.piece = piece;
    }
    
    public Cell getCell() {
    	if (copyCell != null) {
    		return new Cell(this.copyCell);
    	}
  
    	Cell newCell = new Cell(this.coordinate);
    	if (newCell.hasPiece()) {
    		newCell.setPiece(this.piece);
    	}
    	
    	return newCell;
    	
    }

}
