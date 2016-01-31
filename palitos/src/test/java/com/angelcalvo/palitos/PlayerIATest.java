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

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import com.angelcalvo.palitos.PlayerAI.AiLevel;

public class PlayerIATest {
	GameState state;
	Player player;
	
	@Before
	public void setUp() {
		state = new GameState();
		player = new PlayerAI(AiLevel.NORMAL, Color.BLACK);
	}
	
	@Test
	public void testMove() {		
		assertTrue(state.isValid(player.move(state)));
	}
	
	@Test
	public void testUpdate() {
		Move m1 = Move.fromSticks(10, 14);
		state.move(m1);
		for(int i = 0; i < 1000; i++) {
			Move move = player.move(state);
			assertTrue(move.getStartStick() < 14);
		}
	}

}
