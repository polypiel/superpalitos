package com.angelcalvo.palitos.mock;

import com.angelcalvo.palitos.Board;
import com.angelcalvo.palitos.GameState;
import com.angelcalvo.palitos.Move;
import com.angelcalvo.palitos.Player;

public class MockBoard implements Board {

	@Override
	public void started(GameState gameState) {}

	@Override
	public void finished(Player winner) {}

	@Override
	public void move(Player player, Move move, GameState gameState) {}

}
