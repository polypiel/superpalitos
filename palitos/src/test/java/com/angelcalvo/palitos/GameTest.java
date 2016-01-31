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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

import com.angelcalvo.palitos.PlayerAI.AiLevel;

public class GameTest {

	@Test
	public void test() {
		Game game = new Game(new PlayerAI(AiLevel.EASY, Color.BLACK), new PlayerAI(AiLevel.HARD,
						Color.RED), true);
		assertFalse(game.isFinished());

		game.kill();
		assertTrue(game.isFinished());
	}

}
