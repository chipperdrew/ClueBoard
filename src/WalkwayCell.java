
public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int row, int col) {
		super(row, col);
	//	this.calcAdjacencies();
	}

	@Override
	public boolean isWalkway()
	{
		return true;
	}
	
	// Create Adjacency List for Walkways
//	@Override
//	public void calcAdjacencies() {
//		Board board = new Board();
//		//board.loadConfigFiles();
//		BoardCell left = board.getCellAt(Board.calcIndex(getRow(), getCol()-1));
//		BoardCell right = board.getCellAt(Board.calcIndex(getRow(), getCol()+1));
//		BoardCell top = board.getCellAt(Board.calcIndex(getRow()-1, getCol()));
//		BoardCell bottom = board.getCellAt(Board.calcIndex(getRow()+1, getCol()));
//		if(Board.calcIndex(getRow(),getCol()-1) > 0 && !left.isRoom())
//			adjList.add(Board.calcIndex(getRow(),getCol()-1));
//		if(Board.calcIndex(getRow(),getCol()+1) > 0 && !right.isRoom())
//			adjList.add(Board.calcIndex(getRow(),getCol()+1));
//		if(Board.calcIndex(getRow()-1,getCol()) > 0 && !top.isRoom())
//			adjList.add(Board.calcIndex(getRow()-1,getCol()));
//		if(Board.calcIndex(getRow()+1,getCol()) > 0 && !bottom.isRoom())
//			adjList.add(Board.calcIndex(getRow()+1,getCol()));
//	}
}
