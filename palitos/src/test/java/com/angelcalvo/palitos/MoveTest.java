package com.angelcalvo.palitos;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveTest {

	@Test
	public void testInverse() {
		Move move1 = new Move(2, 1, Move.PALITO);
		Move move2 = new Move(1, 2, Move.PALITO);
		assertEquals(move1.getPInicio(), move2.getPInicio());
		assertEquals(move1.getPFin(), move2.getPFin());
		assertEquals(move1.getHInicio(), move2.getHInicio());
		assertEquals(move1.getHFin(), move2.getHFin());
		
		Move move3 = new Move(0, 1, Move.HUECO);
		Move move4 = new Move(1, 0, Move.HUECO);
		assertEquals(move3.getPInicio(), move4.getPInicio());
		assertEquals(move3.getPFin(), move4.getPFin());
		assertEquals(move3.getHInicio(), move4.getHInicio());
		assertEquals(move3.getHFin(), move4.getHFin());
	}
	
	@Test
	public void testGaps() {
		Move move = new Move(4, 5, Move.PALITO);
		assertEquals(6, move.getHInicio());
		assertEquals(8, move.getHFin());
	}
	
	@Test
	public void testSticks() {
		Move move = new Move(2, 4, Move.HUECO);
		assertEquals(1, move.getPInicio());
		assertEquals(2, move.getPFin());
	}
	
	@Test
	public void testLong() {
		assertEquals(1, new Move(0, 0, Move.PALITO).getLon());
		assertEquals(2, new Move(2, 4, Move.HUECO).getLon());
	}

}
