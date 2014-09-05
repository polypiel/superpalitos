package com.angelcalvo.palitos;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import com.angelcalvo.palitos.mock.MockBoard;

public class GameTest {

	@Test
	public void test() {
		Game game = new Game(new PlayerAI(PlayerAI.FACIL, Color.BLACK), new PlayerAI(PlayerAI.DIFICIL,
						Color.RED), new MockBoard(), true);
		assertFalse(game.isFinished());
		
		game.finish();
		assertTrue(game.isFinished());
	}

}
