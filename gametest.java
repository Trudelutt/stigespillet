package Stigespillet;

import static org.junit.Assert.*;

import org.junit.Test;

public class gametest {
	Player p1 = new Player('x');
	Player p2 = new Player('o');
	game g = new game(new Board(10,9));

	@Test
	public void testTurn() {
		g.getPlayers().add(p1);
		g.getPlayers().add(p2);
		g.nextTurn();
		assertEquals(g.getTurn(), 0);
		g.nextTurn();
		assertEquals(g.getTurn(), 1);
	}
	@Test
	public void TestMovePlayerOver90(){
		assertEquals(g.MovePlayer(89+4), "87");
	}
	@Test
	public void WinnerTest(){
		p1.setCurrentPlace(g.MovePlayer(91));
		assertFalse(g.hasWon(p1.getMark(), p1.getCurrentPlace()));
		p1.setCurrentPlace(g.MovePlayer(90));
		assertTrue(g.hasWon(p1.getMark(), p1.getCurrentPlace()));
	}
	@Test
	public void testBoardLayout(){
		g.getBoard().updateBoadCoordinates('$', 0, 0);
		g.getBoard().convertPlaceToCoordinatesAnUpdate("90", '#');
		assertFalse(g.getBoard().getValue(0, 0)=='$');
		assertTrue(g.getBoard().getValue(0, 0)=='#');
		g.getBoard().convertPlaceToCoordinatesAnUpdate("10", '.');
		assertTrue(g.getBoard().getValue(8, 8)=='.');
		g.getBoard().convertPlaceToCoordinatesAnUpdate("1", '+');
		assertTrue(g.getBoard().getValue(9, 0)=='+');
		g.getBoard().convertPlaceToCoordinatesAnUpdate("11", '%');
		assertTrue(g.getBoard().getValue(8, 7)=='%');
		g.getBoard().convertPlaceToCoordinatesAnUpdate("3", '0');
		assertTrue(g.getBoard().getValue(9, 2)=='0');
		g.getBoard().convertPlaceToCoordinatesAnUpdate("2", '?');
		assertTrue(g.getBoard().getValue(9, 1)=='?');
	}

}
