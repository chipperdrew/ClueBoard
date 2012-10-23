import java.util.HashMap;


public class RoomCell extends BoardCell {
	public static enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
	private DoorDirection doorDirection;
	private char Initial;
	
	// Calls super, determines door direction.
	public RoomCell(int row, int col, String lets) {
		super(row, col);
		Initial = lets.charAt(0);
		if(lets.length() == 2) {
			HashMap<Character, DoorDirection> dir = new HashMap<Character, DoorDirection>();
			dir.put('U', DoorDirection.UP);
			dir.put('D', DoorDirection.DOWN);
			dir.put('L', DoorDirection.LEFT);
			dir.put('R', DoorDirection.RIGHT);
			doorDirection = dir.get(lets.charAt(1));
		}
		else {
			doorDirection = DoorDirection.NONE;
		}
		//this.calcAdjacencies();
	}

	// Create adjacency lists for Rooms (should be blank) and Doors (should contain one value)
//	@Override
//	public void calcAdjacencies() {
//		// System.out.println("Entered calcAdjacencies");
//		if(doorDirection == DoorDirection.UP) {
//			adjList.add(Board.calcIndex(getRow()-1, getCol()));
//		}
//		else if(doorDirection == DoorDirection.DOWN) {
//			adjList.add(Board.calcIndex(getRow()+1, getCol()));
//		}
//		else if(doorDirection == DoorDirection.LEFT) {
//			adjList.add(Board.calcIndex(getRow(), getCol()-1));
//		}
//		else if(doorDirection == DoorDirection.RIGHT) {
//			adjList.add(Board.calcIndex(getRow(), getCol()+1));
//		}
//		else
//			return;
//	}
	
	@ Override
	public boolean isRoom() {
		return (DoorDirection.NONE == doorDirection);
	}
	
	@Override
	public boolean isDoorway() {
		return !(DoorDirection.NONE == doorDirection);
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public char getInitial() {
		return Initial;
	}	
}
