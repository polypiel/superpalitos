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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Game abstraction
 */
public class Game {
  private static final int STANDBY_STATE = 0;
  private static final int PLAYING_STATE = 1;
  private static final int FINISHED_STATE = 2;
	
  private GameState state;
  private int playingState;
  
  private boolean isPlayerOneTurn;
  private Player pOne, pTwo;
  private Collection<GameListener> gameListeners;
  
  /**
   * @param isPlayerOneTurn true if player one starts, false if player two stars.
   */
  public Game(Player pOne, Player pTwo, boolean isPlayerOneTurn) {
    state = new GameState();
    playingState = STANDBY_STATE;
    this.pOne = pOne;
    this.pTwo = pTwo;
    this.isPlayerOneTurn = isPlayerOneTurn;
    
    gameListeners = new ArrayList<>();
  }

  public void addGameListener(GameListener partidaListener) {
     gameListeners.add(partidaListener);
  }
  
  public void play() {
    Player player = null;
    Move move = null;
    playingState = PLAYING_STATE;
    
    fireGameStartEvent(state);
    
    while(!isFinished() && playingState == PLAYING_STATE) {
    	// Turn
      player = (isPlayerOneTurn) ? pOne : pTwo;

      // updates player state
      // player.update(move, state);
      
      // Checks move is valid
      // TODO fix
      do {
        move = player.move(state);
      } while(!state.isValid(move));
      
      // applys move
      state.move(move);
      
      fireMoveEvent(player, move, state);
      
      // Changes move
      isPlayerOneTurn = !isPlayerOneTurn;
    }
    playingState = FINISHED_STATE;
    Player winner = (isPlayerOneTurn) ? pTwo : pOne;
    fireGameEndEvent(winner);
  }

  public boolean isFinished() {
    return playingState == FINISHED_STATE;
  }
  
  /**
   * Finishes the game immediately and notifies the board and the players.
   */
  public void finish() {
  	playingState = FINISHED_STATE;
    fireGameEndEvent(null);
  }

  public Player getPlayerOne() {
    return pOne;
  }

  public Player getPlayerTwo() {
    return pTwo;
  }
  
  private void fireGameStartEvent(GameState state) {
  	gameListeners.forEach(l -> { l.onGameStarted(state); });
  }

  private void fireMoveEvent(Player player, Move move, GameState state) {
  	gameListeners.forEach(l -> { l.onMoved(player, move, state); });
  }

  private void fireGameEndEvent(Player player) {
  	gameListeners.forEach(l -> { l.onGameFinished(player); });
  }

  @Override
  public String toString() {
	return state.toString();
  }
}
