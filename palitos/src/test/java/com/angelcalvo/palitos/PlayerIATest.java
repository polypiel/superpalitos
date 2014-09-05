package com.angelcalvo.palitos;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class PlayerIATest {
	GameState state;
	Player player;
	
	@Before
	public void setUp() {
		state = new GameState();
		player = new PlayerAI(PlayerAI.NORMAL, Color.BLACK);
		player.update(null, state);
	}
	
	@Test
	public void testMove() {		
		assertTrue(state.isValid(player.move()));
	}
	
	@Test
	public void testUpdate() {
		Move m1 = new Move(10, 14, Move.PALITO);
		state.move(m1);
		player.update(m1, state);
		for(int i = 0; i < 1000; i++) {
			Move move = player.move();
			assertTrue(move.getPInicio() < 14);
		}
	}

}
