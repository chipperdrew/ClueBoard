import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

//import RoomCell.DoorDirection;


public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private Set<BoardCell> targets;
	public static int numRows;
	public static int numColumns;
	private Map<Integer, LinkedList<Integer>> adjMtx;
	private Vector<BoardCell> path;
	private boolean[] visited;

	public Board() {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		targets = new HashSet<BoardCell>();
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		numRows = 0;
		numColumns = 0;
		path = new Vector<BoardCell>();
	}

	// Fills our map of indices and their corresponding adj lists.
	private void fillAdjMtx() {
		for(int i=0; i < numRows; i++) {
			for(int j=0; j < numColumns; j++) {
				LinkedList<Integer> adjList = new LinkedList<Integer>();
				// Walkway -- up to 4 possible movement locations
				if(cells.get(calcIndex(i,j)).isWalkway()){
					BoardCell left = this.getCellAt(Board.calcIndex(i, j-1));
					BoardCell right = this.getCellAt(Board.calcIndex(i, j+1));
					BoardCell top = this.getCellAt(Board.calcIndex(i-1, j));
					BoardCell bottom = this.getCellAt(Board.calcIndex(i+1, j));
					if(calcIndex(i,j-1) > 0) {
						if(left instanceof RoomCell){
							RoomCell roomLeft = (RoomCell) left;
							if(roomLeft.getDoorDirection() == RoomCell.DoorDirection.RIGHT)
								adjList.add(calcIndex(i,j-1));
						}
						else
							adjList.add(calcIndex(i,j-1));
					}
					if(calcIndex(i,j+1) > 0) {
						if(right instanceof RoomCell){
							RoomCell roomRight = (RoomCell) right;
							if(roomRight.getDoorDirection() == RoomCell.DoorDirection.LEFT)
								adjList.add(calcIndex(i,j+1));
						}
						else
							adjList.add(calcIndex(i,j+1));
					}
					if(calcIndex(i-1,j) > 0) {
						if(top instanceof RoomCell){
							RoomCell roomTop = (RoomCell) top;
							if(roomTop.getDoorDirection() == RoomCell.DoorDirection.DOWN)
								adjList.add(calcIndex(i-1,j));
						}
						else
							adjList.add(calcIndex(i-1,j));
					}
					if(calcIndex(i+1,j) > 0) {
						if(bottom instanceof RoomCell){
							RoomCell roomBottom = (RoomCell) bottom;
							if(roomBottom.getDoorDirection() == RoomCell.DoorDirection.UP)
								adjList.add(calcIndex(i+1,j));
						}
						else
							adjList.add(calcIndex(i+1,j));
					}
				}
				// Doorway -- only has one potential movement location
				else if(cells.get(calcIndex(i,j)).isDoorway()) {
					RoomCell currentCell = (RoomCell) cells.get(calcIndex(i,j));
					if(currentCell.getDoorDirection() == RoomCell.DoorDirection.UP) {
						adjList.add(Board.calcIndex(i-1, j));
					}
					else if(currentCell.getDoorDirection() == RoomCell.DoorDirection.DOWN) {
						adjList.add(Board.calcIndex(i+1, j));
					}
					else if(currentCell.getDoorDirection() == RoomCell.DoorDirection.LEFT) {
						adjList.add(Board.calcIndex(i, j-1));
					}
					else if(currentCell.getDoorDirection() == RoomCell.DoorDirection.RIGHT) {
						adjList.add(Board.calcIndex(i, j+1));
					}
				}
				// Note that there can be no movement in rooms
				adjMtx.put(calcIndex(i,j), adjList);
			}
		}
	}
	
	// Returns adjacency list at index provided
	public LinkedList<Integer> getAdjList(int index) {
		return adjMtx.get(index);
	}
	
	// Determines possible destinations
	public void calcTargets(int start, int numSteps) {
		// Will ONLY run when calcTargets is initially called
		if(visited[start] == false) {
			visited = new boolean[numRows*numColumns];
			targets.clear();
		}
		visited[start] = true;
		// Each for loop iteration adds another 'step'
		for(int i=0; i < adjMtx.get(start).size(); i++) {
			int adjCell = adjMtx.get(start).get(i);
			// We can only add 'unseen' steps to our path. Otherwise, we move
			// on to the next for loop iteration
			if(visited[adjCell] == false) {
				path.add(cells.get(adjCell));
				visited[adjCell] = true;
			}
			else
				continue;
			// If its a doorway, add to targets
			if(cells.get(adjCell).isDoorway()) {
				targets.add(cells.get(adjCell));
				path.remove(cells.get(adjCell));
				visited[adjCell] = false;
				continue;
			}

			// We will add any valid paths to our target set, which will remove duplicates.
			// We also remove the last 'step' and mark it as unseen.
			// If our size ins't correct, we call the function again to add another 'step'
			if(path.size() == numSteps) {
				//System.out.println("Adding to targets");
				targets.add(path.lastElement());
				path.remove(path.lastElement());
				visited[adjCell] = false;
			}
			else {
				calcTargets(adjCell, numSteps);
			} 
		}
		// If we have exited out of for loop, we want to remove the last 'step'
		// we were observing, if it exists.
		if(path.size() > 0) {			
			BoardCell lastElement = path.lastElement();
			int lastRow = lastElement.getRow();
			int lastCol = lastElement.getCol();
			visited[calcIndex(lastRow,lastCol)] = false;
			path.remove(path.lastElement());
		}
	}
	
	// Returns set of targets
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	// Calls functions to load files, handles exception if needed.
	public void loadConfigFiles() {
		try{
			loadCSV();
			loadTXT();
			this.fillAdjMtx();
		} catch(BadConfigFormatException e) {
			System.out.println(e);
		}
	}
	
	// Loads CSV file, fills ArrayList of BoardCells
	public void loadCSV() throws BadConfigFormatException {
		Scanner in;
		try {
			in = new Scanner(new FileReader("ClueBoardCommaSep.csv"));
			String values = new String();
			int rowcount = 0;
			while(in.hasNext()) {
				// Add comma so we can split on line breaks
				values += in.nextLine() + ",";
				rowcount++;
			}
			numRows = rowcount;
			String[] data = values.split(",");
			numColumns = data.length / numRows;
			// We would initialize this in constructor, but we don't know the board size.
			// Since calcTargets is recursive (and the first 'if' calls it), we can't put it in there either.
			visited = new boolean[numRows*numColumns];
			for(int i=0; i < numRows; i++) {
				for(int j=0; j < numColumns; j++) {
					if(data[calcIndex(i,j)].equals("W")) {
						WalkwayCell w = new WalkwayCell(i,j);
						cells.add(w);
					}
					else {
						RoomCell r = new RoomCell(i,j, data[calcIndex(i,j)]);
						cells.add(r);
					}		
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	
	// Loads text file, fills map
	public void loadTXT() throws BadConfigFormatException {
		Scanner in;
		try {
			in = new Scanner(new FileReader("ClueLegend.txt"));
			String var = new String();
			while(in.hasNext()) {	
				var = in.nextLine();
				String[] data = var.split(", ");
				Character c = data[0].charAt(0);
				String roomName = data[1];
				rooms.put(c, roomName);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

	}
	
	// Returns index of cell given a row and col, and -1 if not a valid cell
	public static int calcIndex(int row, int col) {
		if((row < numRows && col < numColumns) && ((0 <= row) && (0 <= col)))
			return (row*numColumns + col);
		else
			return -1;
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		if(cells.get(calcIndex(row,col)) instanceof RoomCell)
			return (RoomCell) cells.get(calcIndex(row,col));
		else
			return null;
	}
	public BoardCell getCellAt(int index) {
		if(index >= 0 && index < numRows*numColumns)
			return cells.get(index);
		return new WalkwayCell(0,0);
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public Map<Character, String> getRooms()
	{
		return rooms;
	}

}
