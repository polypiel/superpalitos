/*
 * PartidaManager
 * 
 * Pollo Verde Software
 * 
 * Created on 15-06-2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.superpalitos;

import java.util.LinkedList;

import com.angelcalvo.palitos.Game;
import com.angelcalvo.palitos.GameListener;
import com.angelcalvo.palitos.GameState;
import com.angelcalvo.palitos.Move;
import com.angelcalvo.palitos.Player;
import com.angelcalvo.superpalitos.gui.BoardPanel;

/**
 * 
 * @author Angel Luis Calvo Ortega
 */
public class PartidaManager implements GameListener {
  private static final int TURN_DELAY = 500;
  
	/* Modo de la partida */
  public static final int PARTIDA_FREE = 0;
  public static final int PARTIDA_TIMEATTACK = 1;
  public static final int PARTIDA_2 = 2;
  
  private Player j1, j2;
  private BoardPanel tablero;
  //private int type;
  // private int mode;
  private Game partida;
  
  // accounting
  private long time;
  private int j1Score, j2Score;
  
  private boolean jugando, j1Turn;
  private LinkedList<GameListener> partidaListeners;
  
  /**
   * @param j1 Jugador uno
   * @param j2 Jugador dos
   * @param tablero El tablero
   */
  public PartidaManager(Player j1, Player j2, BoardPanel tablero) {
  	this(j1, j2, tablero, true);
	}
  
  /**
   * @param j1 Jugador uno
   * @param j2 Jugadoro dos
   * @param tablero El tablero
   * @param j1Turn Indica si mueve el jugador uno primero
   */
  public PartidaManager(Player j1, Player j2, BoardPanel tablero, boolean j1Turn) {
    this.j1 = j1;
    this.j2 = j2;
    this.tablero = tablero;
    this.j1Turn = j1Turn;
    
    partidaListeners = new LinkedList<GameListener>();
    jugando = false;
  }
  
  /**
   * Comienza la partida
   */
  public void play() {
    if(!jugando) {
    	partida = new Game(j1, j2, j1Turn);
    	partida.addGameListener(this);
    	partida.addGameListener(tablero);
    	tablero.setScore(j1.getName() + "  " + j1Score + " - " + j2Score + "  " + j2.getName());
    	time = System.currentTimeMillis();
    	jugando = true;
    	j1Turn = !j1Turn;
    	
    	new Thread()  {
    		@Override public void run() {
    			partida.play();
    		}
    	}.start();
    }
  }

  @Override
  public void onMoved(Player player, Move move, GameState gameState) {
  	// Adds delay
    try {
      Thread.sleep(TURN_DELAY);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onGameFinished(Player winner) {
    time = System.currentTimeMillis() - time;
    jugando = false;
    if(j1.equals(winner)) {
    	j1Score++;
    } else {
    	j2Score++;
    }
  }
  
  /**
   * Comienza una nueva partida
   */
  public void replay() {
  	if(!jugando) {
  		play();
  	}
  }
  
  public void addPartidaListener(GameListener partidaListener) {
  	partidaListeners.addLast(partidaListener);
  }

  public boolean estaJugandgo(Player j) {
  	return j1 == j || j2 == j;
  }

	@Override
	public void onGameStarted(GameState state) {

	}
	
	public BoardPanel getBoard() {
		return tablero;
	}
}
