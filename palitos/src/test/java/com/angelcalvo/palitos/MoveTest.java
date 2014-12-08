/*
 * Pollo Verde Software 2006-2014
 * 
 * This file is part of SuperPalitos.
 * 
 * SuperPalitos is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * SuperPalitos is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with SuperPalitos. If not, see <http://www.gnu.org/licenses/>.
 */

package com.angelcalvo.palitos;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveTest {

	@Test
	public void testInverse() {
		Move move1 = Move.fromSticks(2, 1);
		Move move2 = Move.fromSticks(1, 2);
		assertEquals(move1.getStartStick(), move2.getStartStick());
		assertEquals(move1.getEndStick(), move2.getEndStick());
		assertEquals(move1.getStartGap(), move2.getStartGap());
		assertEquals(move1.getEndGap(), move2.getEndGap());
		
		Move move3 = Move.fromGaps(0, 1);
		Move move4 = Move.fromGaps(1, 0);
		assertEquals(move3.getStartStick(), move4.getStartStick());
		assertEquals(move3.getEndStick(), move4.getEndStick());
		assertEquals(move3.getStartGap(), move4.getStartGap());
		assertEquals(move3.getEndGap(), move4.getEndGap());
	}
	
	@Test
	public void testGaps() {
		Move move = Move.fromSticks(4, 5);
		assertEquals(6, move.getStartGap());
		assertEquals(8, move.getEndGap());
	}
	
	@Test
	public void testSticks() {
		Move move = Move.fromGaps(2, 4);
		assertEquals(1, move.getStartStick());
		assertEquals(2, move.getEndStick());
	}
	
	@Test
	public void testLong() {
		assertEquals(1, Move.fromSticks(0, 0).length());
		assertEquals(2, Move.fromGaps(2, 4).length());
	}

}
