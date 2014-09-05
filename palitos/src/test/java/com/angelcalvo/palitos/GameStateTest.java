/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.angelcalvo.palitos;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author angel
 */
public class GameStateTest {
	GameState state;
  
	@Test
	public void testGetStick() {
		assertTrue(state.getStick(0));
		assertTrue(state.getGap(0));
		state.move(new Move(0, 0, Move.PALITO));
		assertFalse(state.getStick(0));
		assertFalse(state.getGap(0));
		assertFalse(state.getGap(1));
	}
  @Test
  public void testIsValid() {
  	assertTrue(state.isValid(new Move(0, 0, Move.PALITO)));
  	assertFalse(state.isValid(new Move(2, 2, Move.HUECO)));
  	assertFalse(state.isValid(new Move(0, 1, Move.PALITO)));
  }
  
  @Test
  public void testCantCrossLastStick() {
  	state.move(new Move(1, 2, Move.PALITO));
  	state.move(new Move(3, 5, Move.PALITO));
  	state.move(new Move(6, 9, Move.PALITO));
  	state.move(new Move(10, 14, Move.PALITO));
  	assertFalse(state.isValid(new Move(0, 1, Move.HUECO)));
  }
  @Test
  public void testCantCrossAlreadyCrossed() {
  	state.move(new Move(2, 2, Move.PALITO));
  	assertFalse(state.isValid(new Move(1, 2, Move.PALITO)));
  }
  
  @Test
  public void testAlive() {
  	assertEquals(GameState.NSTICKS, state.alive());
  	state.move(new Move(3, 3, Move.PALITO));
  	assertEquals(GameState.NSTICKS - 1, state.alive());
  }
  
  @Test
  public void testNoGaps() {
  	state.move(new Move(4, 4, Move.PALITO));
  	state.move(new Move(3, 3, Move.PALITO));
  	assertFalse(state.getGap(6));
  	state.move(new Move(5, 5, Move.PALITO));
  	assertFalse(state.getGap(7));
  }
  
  @Before
  public void setUp() {
  	state = new GameState();
  }
}
