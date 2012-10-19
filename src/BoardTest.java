import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


public class BoardTest {
	public static final int NUM_ROOMS = 11;
	private static final int NUM_ROWS = 19;
	private static final int NUM_COLS = 18;
	private static Board myBoard;
	
	@BeforeClass
	public static void setUp() {
		myBoard = new Board();
		myBoard.loadConfigFiles();
	}
	
	@Test
	public void testRoomLegend() {
		// Tests room numbers and room keys
		Map<Character, String> boardMap = myBoard.getRooms();
		assertEquals(boardMap.size(), NUM_ROOMS);
		assertEquals("Garage", boardMap.get('G'));
		assertEquals("Computing Room", boardMap.get('C'));
		assertEquals("Kitchen", boardMap.get('K'));
		assertEquals("Laundry Room", boardMap.get('L'));
		assertEquals("Bathroom", boardMap.get('B'));
		assertEquals("Music Room", boardMap.get('M'));
		assertEquals("Fancy Deck", boardMap.get('F'));
		assertEquals("Attic", boardMap.get('A'));
		assertEquals("Theatre Room", boardMap.get('T'));
		assertEquals("Closet", boardMap.get('X'));
		assertEquals("Walkway", boardMap.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, myBoard.getNumRows());
		assertEquals(NUM_COLS, myBoard.getNumColumns());		
	}
	
	// Test a doorway in each direction, plus two cell that is not
		// a doorway
		@Test
		public void FourDoorDirections() {
			// Test one each RIGHT/LEFT/UP/DOWN
			RoomCell room = myBoard.getRoomCellAt(2, 3);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
			room = myBoard.getRoomCellAt(3, 7);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
			room = myBoard.getRoomCellAt(13, 10);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
			room = myBoard.getRoomCellAt(14, 6);
			assertTrue(room.isDoorway());
			assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
			// Test that room pieces that aren't doors know it
			room = myBoard.getRoomCellAt(0, 0);
			assertFalse(room.isDoorway());	
			// Test that walkways are not doors
			BoardCell cell = myBoard.getCellAt(myBoard.calcIndex(0, 6));
			assertFalse(cell.isDoorway());		
		}
		
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			int totalCells = myBoard.getNumColumns() * myBoard.getNumRows();
			Assert.assertEquals(342, totalCells);
			for (int i=0; i<totalCells; i++)
			{
				BoardCell cell = myBoard.getCellAt(i);
				if (cell.isDoorway())
					numDoors++;
			}
			Assert.assertEquals(13, numDoors);
		}

		
		@Test
		public void testCalcIndex() {
			// Test each corner of the board
			assertEquals(0, myBoard.calcIndex(0, 0));
			assertEquals(NUM_COLS-1, myBoard.calcIndex(0, NUM_COLS-1));
			assertEquals(324, myBoard.calcIndex(NUM_ROWS-1, 0));
			assertEquals(341, myBoard.calcIndex(NUM_ROWS-1, NUM_COLS-1));
			// Test a couple others
			assertEquals(19, myBoard.calcIndex(1, 1));
			assertEquals(41, myBoard.calcIndex(2, 5));		
		}
		
		// Test a few room cells to ensure the room initial is
		// correct.
		@Test
		public void testRoomInitials() {
			assertEquals('G', myBoard.getRoomCellAt(0, 0).getInitial());
			assertEquals('M', myBoard.getRoomCellAt(8, 2).getInitial());
			assertEquals('F', myBoard.getRoomCellAt(15, 2).getInitial());
			assertEquals('C', myBoard.getRoomCellAt(1, 8).getInitial());
			assertEquals('T', myBoard.getRoomCellAt(16, 10).getInitial());
		}

}
