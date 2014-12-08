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

import org.junit.Before;
import org.junit.Test;

public class GameStateTest {
	GameState state;
  
	@Test
	public void testGetStick() {
		assertTrue(state.getStick(0));
		assertTrue(state.getGap(0));
		state.move(Move.fromSticks(0, 0));
		assertFalse(state.getStick(0));
		assertFalse(state.getGap(0));
		assertFalse(state.getGap(1));
	}
	
  @Test
  public void testIsValid() {
  	assertTrue(state.areValidSticks(0, 0));
  }
  
  @Test
  public void thatGapsCannotBeEquals() {
  	assertFalse(state.areValidGaps(2, 2));
  }
  
  @Test
  public void thatSticksHavetoBeInTheSameRow() {
  	assertFalse(state.areValidSticks(0, 1));
  }
  
  @Test
  public void testCantCrossLastStick() {
  	state.move(Move.fromSticks(1, 2));
  	state.move(Move.fromSticks(3, 5));
  	state.move(Move.fromSticks(6, 9));
  	state.move(Move.fromSticks(10, 14));
  	assertFalse(state.areValidGaps(0, 1));
  }
  @Test
  public void testCantCrossAlreadyCrossed() {
  	state.move(Move.fromSticks(2, 2));
  	assertFalse(state.isValid(Move.fromSticks(1, 2)));
  }
  
  @Test
  public void testAlive() {
  	assertEquals(GameState.NSTICKS, state.alive());
  	state.move(Move.fromSticks(3, 3));
  	assertEquals(GameState.NSTICKS - 1, state.alive());
  }
  
  @Test
  public void testNoGaps() {
  	state.move(Move.fromSticks(4, 4));
  	state.move(Move.fromSticks(3, 3));
  	assertFalse(state.getGap(6));
  	state.move(Move.fromSticks(5, 5));
  	assertFalse(state.getGap(7));
  }
  
  @Before
  public void setUp() {
  	state = new GameState();
  }
}
