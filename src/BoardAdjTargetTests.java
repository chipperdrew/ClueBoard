import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BoardAdjTargetTests {
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = new Board();
		board.loadConfigFiles();
	}

	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<Integer> testList = board.getAdjList(Board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(Board.calcIndex(3, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(Board.calcIndex(14, 7));
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(Board.calcIndex(15, 1));
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(Board.calcIndex(2, 7));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(Board.calcIndex(0, 6));
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the walkway. 
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<Integer> testList = board.getAdjList(Board.calcIndex(2, 3));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(Board.calcIndex(2, 4)));
		// TEST DOORWAY LEFT
		testList = board.getAdjList(Board.calcIndex(13, 10));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(Board.calcIndex(13, 9)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(Board.calcIndex(3, 7));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(Board.calcIndex(4, 7)));
		//TEST DOORWAY UP
		testList = board.getAdjList(Board.calcIndex(14, 6));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(Board.calcIndex(13, 6)));
		
	}

	// Test a variety of walkway scenarios
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		LinkedList<Integer> testList = board.getAdjList(Board.calcIndex(0, 3));
		Assert.assertTrue(testList.contains(Board.calcIndex(0, 4)));
		Assert.assertEquals(1, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(Board.calcIndex(5, 0));
		Assert.assertTrue(testList.contains(Board.calcIndex(5, 1)));
		Assert.assertTrue(testList.contains(Board.calcIndex(4, 0)));
		Assert.assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(Board.calcIndex(16, 8));
		Assert.assertTrue(testList.contains(Board.calcIndex(15, 8)));
		Assert.assertTrue(testList.contains(Board.calcIndex(17, 8)));
		Assert.assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(Board.calcIndex(12,5));
		Assert.assertTrue(testList.contains(Board.calcIndex(12, 4)));
		Assert.assertTrue(testList.contains(Board.calcIndex(12, 6)));
		Assert.assertTrue(testList.contains(Board.calcIndex(13, 5)));
		Assert.assertTrue(testList.contains(Board.calcIndex(11, 5)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(Board.calcIndex(18, 17));
		Assert.assertEquals(0, testList.size());
		
		// Test on ridge edge of board, next to 1 room piece
		testList = board.getAdjList(Board.calcIndex(7, 17));
		Assert.assertTrue(testList.contains(Board.calcIndex(7, 16)));
		Assert.assertTrue(testList.contains(Board.calcIndex(8, 17)));
		Assert.assertEquals(2, testList.size());

	}

	// Test adjacency at entrance to rooms
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<Integer> testList = board.getAdjList(Board.calcIndex(2, 4));
		Assert.assertTrue(testList.contains(Board.calcIndex(2, 3)));
		Assert.assertTrue(testList.contains(Board.calcIndex(2, 5)));
		Assert.assertTrue(testList.contains(Board.calcIndex(3, 4)));
		Assert.assertTrue(testList.contains(Board.calcIndex(1, 4)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(Board.calcIndex(4, 7));
		Assert.assertTrue(testList.contains(Board.calcIndex(4, 8)));
		Assert.assertTrue(testList.contains(Board.calcIndex(4, 6)));
		Assert.assertTrue(testList.contains(Board.calcIndex(5, 7)));
		Assert.assertTrue(testList.contains(Board.calcIndex(3, 7)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(Board.calcIndex(13, 9));
		Assert.assertTrue(testList.contains(Board.calcIndex(13, 10)));
		Assert.assertTrue(testList.contains(Board.calcIndex(13, 8)));
		Assert.assertTrue(testList.contains(Board.calcIndex(12, 9)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(Board.calcIndex(13, 6));
		Assert.assertTrue(testList.contains(Board.calcIndex(13, 5)));
		Assert.assertTrue(testList.contains(Board.calcIndex(13, 7)));
		Assert.assertTrue(testList.contains(Board.calcIndex(12, 6)));
		Assert.assertTrue(testList.contains(Board.calcIndex(14, 6)));
		Assert.assertEquals(4, testList.size());
	}

	
	// Tests of just walkways
	@Test
	public void testTargetsWalkways() {
		// System.out.println("Entered testTargetsWalkways");
		board.calcTargets(Board.calcIndex(11, 6), 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(11, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(11, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(12, 6))));

		board.calcTargets(Board.calcIndex(18, 13), 2);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(17, 12))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(18, 11))));			
	
		board.calcTargets(Board.calcIndex(14, 17), 3);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(14, 16))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(14, 14))));			
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(13, 17))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(13, 15))));
		
		board.calcTargets(Board.calcIndex(18, 1), 2);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(17, 2))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(18, 3))));			
	}
	
	// Test getting into a room
	@Test 
	public void testTargetsIntoRoom()
	{
		// Going to room
		board.calcTargets(Board.calcIndex(8, 4), 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(8, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(8, 3))));	
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(7, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(9, 4))));			

		board.calcTargets(Board.calcIndex(7, 4), 1);
		targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(7, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(7, 3))));	
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(6, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(8, 4))));
	}

	// Test getting out of a room
	@Test
	public void testRoomExit()
	{
		// Leaving room
		board.calcTargets(Board.calcIndex(17, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(17, 8))));
		
		board.calcTargets(Board.calcIndex(11, 14), 1);
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(Board.calcIndex(11, 13))));
	}

}