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

package com.angelcalvo.palitos.mock;

import com.angelcalvo.palitos.Board;
import com.angelcalvo.palitos.GameState;
import com.angelcalvo.palitos.Move;
import com.angelcalvo.palitos.Player;

public class BoardMock implements Board {

	@Override
	public void started(GameState gameState) {}

	@Override
	public void finished(Player winner) {}

	@Override
	public void move(Player player, Move move, GameState gameState) {}

}
