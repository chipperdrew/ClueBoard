//import java.util.LinkedList;


public abstract class BoardCell {
	private int row;
	private int col;
	//protected LinkedList<Integer> adjList;

	public BoardCell(int row, int col) {
		//adjList = new LinkedList<Integer>();
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	//public abstract void calcAdjacencies();
	
	public boolean isWalkway() {
		return false;
	}
	public boolean isRoom() {
		return false;
	}
	public boolean isDoorway() {
		return false;
	}
	
}
