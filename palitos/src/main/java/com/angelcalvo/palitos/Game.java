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

import java.util.Vector;

/**
 * Clase que maneja una partida.
 */
public class Game {
	private static final int STANDBY_STATE = 0;
	private static final int PLAYING_STATE = 1;
	private static final int FINISHED_STATE = 2;
	
  private GameState state;
  private int playingState;
  
  private boolean turn;
  private Player p1, p2;
  private Board board;
  private Vector<GameListener> partidaListeners;
  
  /**
   * Constructor, crea una nueva partida.
   * @param j1 Primer jugador.
   * @param j2 Segundo jugador.
   * @param tablero El Tablero
   * @param turno Indica si j1 es el primero en jugar
   */
  public Game(Player j1, Player j2, Board tablero, boolean turno) {
    state = new GameState();
    playingState = STANDBY_STATE;
    this.board = tablero;
    this.p1 = j1;
    this.p2 = j2;
    this.turn = turno;
    
    partidaListeners = new Vector<>();
  }
  
  /**
   * Metodo para establecer el partidaListener de la partida.
   * @param partidaListener El partidaListener.
   */
  public void addGameListener(GameListener partidaListener) {
     partidaListeners.addElement(partidaListener);
  }
  
  public void play() {
    Player player = null;
    Move move = null;
    playingState = PLAYING_STATE;
    
    board.started(state);
    
    while(!isFinished() && playingState == PLAYING_STATE) {
    	// Turn
      player = (turn) ? p1 : p2;
      fireCambiaTurnoEvent(player);

      // updates player state
      player.update(move, state);
      
      // Checks move is valid
      // TODO fix
      do {
        move = player.move();
      } while(!state.isValid(move));
      
      // applays move
      state.move(move);
      // notifies board
      board.move(player, move, state);
      
      // Changes move
      turn = !turn;
    }
    playingState = FINISHED_STATE;
    Player per = (turn) ? p1 : p2;
    Player winner = (turn) ? p2 : p1;
    per.update(move, state);
    
    board.finished(winner);
    
    fireFinPartidaEvent(player);
  }
  
  /**
   * Metodo que dice si la partida a llegado a su fin.
   * @return Si la partida ha terminado o sigue.
   */
  public boolean isFinished() {
    return playingState == FINISHED_STATE;
  }
  
  /**
   * Finaliza la partida bruscamente.
   * Signals the board and the players.
   */
  public void finish() {
  	playingState = FINISHED_STATE;
    board.finished(null);
    fireFinPartidaEvent(null);
  }
  
  /**
   * Devuelve el primer jugador
   * @return El jugador 1
   */
  public Player getPlayerOne() {
    return p1;
  }
  
  /**
   * Devuelve el segundo jugador
   * @return El jugador 2
   */
  public Player getPlayerTwo() {
    return p2;
  }

  private void fireCambiaTurnoEvent(Player player) {
  	partidaListeners.forEach(l -> { l.newTurn(player); });
  }

  private void fireFinPartidaEvent(Player player) {
  	partidaListeners.forEach(l -> { l.finish(player); });
  }

	@Override
	public String toString() {
		return state.toString();
	}
}
