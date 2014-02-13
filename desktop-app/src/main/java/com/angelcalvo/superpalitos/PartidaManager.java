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
package org.pvs.superpalitos;

import java.util.LinkedList;

import org.pvs.palitos.Jugador;
import org.pvs.palitos.Partida;
import org.pvs.palitos.PartidaListener;
import org.pvs.palitos.Tablero;

/**
 * 
 * @author Angel Luis Calvo Ortega
 */
public class PartidaManager implements PartidaListener {
	private static long ID_COUNT = 0;
	
	/* Modo de la partida */
  public static final int PARTIDA_FREE = 0;
  public static final int PARTIDA_TIMEATTACK = 1;
  public static final int PARTIDA_2 = 2;
  
  private Jugador j1, j2;
  private Tablero tablero;
  //private int type;
  // private int mode;
  private Partida partida;
  
  // accounting
  private long time;
  private int j1Score, j2Score;
  private long id;
  
  private boolean jugando, j1Turn;
  private LinkedList<PartidaListener> partidaListeners;
  private SuperPalitos sp;
  
  /**
   * @param j1 Jugador uno
   * @param j2 Jugador dos
   * @param tablero El tablero
   */
  public PartidaManager(Jugador j1, Jugador j2, Tablero tablero, SuperPalitos sp) {
  	this(j1, j2, tablero, true, sp);
	}
  
  /**
   * @param j1 Jugador uno
   * @param j2 Jugadoro dos
   * @param tablero El tablero
   * @param j1Turn Indica si mueve el jugador uno primero
   */
  public PartidaManager(Jugador j1, Jugador j2, Tablero tablero, boolean j1Turn, SuperPalitos sp) {
  	id = ID_COUNT++;
  	
    this.j1 = j1;
    this.j2 = j2;
    this.tablero = tablero;
    this.j1Turn = j1Turn;
    this.sp = sp;
    
    this.tablero.setId(id);
    
    partidaListeners = new LinkedList<PartidaListener>();
    jugando = false;
  }
  
  /**
   * Comienza la partida
   */
  public void play() {
    if(!jugando) {
    	partida = new Partida(j1, j2, tablero, j1Turn);
    	partida.addPartidaListener(this);
    	tablero.setMarcador(j1.getNombre() + "  " + j1Score + " - " + j2Score + "  " + j2.getNombre());
    	/*Iterator it = partidaListeners.iterator();
    	while(it.hasNext()) {
    		partida.addPartidaListener((PartidaListener)it.next());
    	}*/
    	partida.start();
    	time = System.currentTimeMillis();
    	jugando = true;
    	j1Turn = !j1Turn;
    }
  }

  @Override
  public void cambiaTurno(String s) {
  }

  @Override
  public void finPartida(boolean j1Winner) {
    time = System.currentTimeMillis() - time;
    jugando = false;
    if(j1Winner) {
    	j1Score++;
    } else {
    	j2Score++;
    }
    
    int opt = sp.finJuego((j1Winner)?j1.getNombre():j2.getNombre(), time);
    if(opt == SuperPalitos.FIN_JUEGO_CONTINUAR) {
    	replay();
    } else if(opt == SuperPalitos.FIN_JUEGO_TERMINAR) {
    	sp.removePartida(this);
    	end();
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
  
  /**
   * Termina la partida
   */
  public void end() {
  	j1.terminar();
  	j2.terminar();
  	tablero.terminar();
  }
  
  public void addPartidaListener(PartidaListener partidaListener) {
  	partidaListeners.addLast(partidaListener);
  }

  public long getId() {
  	return id;
  }
  
  public boolean estaJugandgo(Jugador j) {
  	return j1 == j || j2 == j;
  }
  
  @Override
	public boolean equals(Object obj) {
		if(!(obj instanceof PartidaManager)) {
			return false;
		}
		
		PartidaManager pm = (PartidaManager)obj;
		return id == pm.getId();
	}

  @Override
	public int hashCode() {
		return (int)id;
	}
}
