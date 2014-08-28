/*
 * Partida
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.palitos;

import java.util.Vector;


/**
 * Clase que maneja una partida.
 * 
 * @author Angel Calvo
 */
public class Game {
  private GameState state;
  
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
    
    board.started(state);
    
    while(!isFinished()) {
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
    return state.alive() == 1;
  }
  
  /**
   * Finaliza la partida bruscamente.
   */
  public void finish() {
    board.finished(null);
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
