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
public class Game extends Thread {
  /** Modo 1 jugador */
  public final static int _1P = 0;
  /** Modo 2 jugadores */
  public final static int _2P = 1;
  /** Modo PalitosNet */
  public final static int _MP = 2;
  
  public final static int BLUE_COLOR = 0;
  public final static int BLACK_COLOR = 1;
  public final static int RED_COLOR = 2;
  public final static int GREEN_COLOR = 3;
  
  private static final int TURN_DELAY = 500;
  //private static long ID_CONT;
  
  private GameState state;
  
  private boolean turno;
  private Player j1, j2;
  private Board tablero;
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
    this.tablero = tablero;
    this.j1 = j1;
    this.j2 = j2;
    this.turno = turno;
    
    partidaListeners = new Vector<GameListener>();
  }
  
  /**
   * Metodo para establecer el partidaListener de la partida.
   * @param partidaListener El partidaListener.
   */
  public void addGameListener(GameListener partidaListener) {
     partidaListeners.addElement(partidaListener);
  }
  
  @Override
  public void run() {
    Player j = null;
    Move jug = null;
    
    tablero.started();
    
    while(!isFinished()) {
      j = (turno) ? j1 : j2;
      fireCambiaTurnoEvent(j.getName());

      j.update(jug, state);
      
      do {
        jug = j.move();
        if(jug == null) {
          return;
        }
      } while(!state.isValid(jug));
      
      state.move(jug);
      
      // TODO change!S
      if(jug.isCoord()) {   
        tablero.drawLine(jug.getX1(), jug.getY1(), jug.getX2(), jug.getY2(), j.getColor());
      } else {
        tablero.drawLine(jug.getHInicio(), jug.getHFin(), j.getColor());
      }
      
      turno = !turno;
      
      try {
        Thread.sleep(TURN_DELAY);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
    Player per = (turno) ? j1 : j2;
    per.update(jug, state);
    
    tablero.finished();
    
    fireFinPartidaEvent(j == j1);
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
    tablero.finished();
    this.interrupt();
  }
  
  /**
   * Devuelve el primer jugador
   * @return El jugador 1
   */
  public Player getPlayerOne() {
    return j1;
  }
  
  /**
   * Devuelve el segundo jugador
   * @return El jugador 2
   */
  public Player getPlayerTwo() {
    return j2;
  }

  private void fireCambiaTurnoEvent(String jug) {
  	for(int i = 0; i < partidaListeners.size(); i++) {
  		GameListener listener = (GameListener) partidaListeners.elementAt(i);
  		listener.cambiaTurno(jug);
  	}
  }

  private void fireFinPartidaEvent(boolean j1Winner) {
  	for(int i = 0; i < partidaListeners.size(); i++) {
  		GameListener listener = (GameListener) partidaListeners.elementAt(i);
  		listener.finPartida(j1Winner);
  	}
  }

	@Override
	public String toString() {
		return state.toString();
	}
}
