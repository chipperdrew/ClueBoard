import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Board {
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private Set<BoardCell> targets;
	private int numRows;
	private int numColumns;

	public Board() {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		targets = new HashSet<BoardCell>();
		numRows = 0;
		numColumns = 0;
	}
	
	public LinkedList<Integer> getAdjList(int index) {
		return null;
	}
	
	// Calls functions to load files, handles exception if needed.
	public void loadConfigFiles() {
		try{
			loadCSV();
			loadTXT();
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
			/* System.out.println(data.length);
			System.out.println(numRows);
			System.out.println(numColumns); */
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
	public int calcIndex(int row, int col) {
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
	public void calcTargets(int index, int roll) {
	}
	public Set<BoardCell> getTargets() {
		return null;
	}
	public BoardCell getCellAt(int index) {
		return cells.get(index);
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
