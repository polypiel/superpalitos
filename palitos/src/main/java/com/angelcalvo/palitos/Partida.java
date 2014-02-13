/*
 * Partida
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.palitos;

import java.util.Vector;


/**
 * Clase que maneja una partida.
 * 
 * @author Angel Luis Calvo Ortega
 */
public class Partida extends Thread {
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
  
  private Palito palitos;
  private Hueco huecos;
  private Estado estado;
  private boolean turno;
  private Jugador j1, j2;
  private Tablero tablero;
  private Vector<PartidaListener> partidaListeners;
  /** Identificador de la partida */
  //private long id;
  
  /**
   * Constructor, crea una nueva partida.
   * @param j1 Primer jugador.
   * @param j2 Segundo jugador.
   * @param tablero El Tablero
   * @param turno Indica si j1 es el primero en jugar
   */
  public Partida(Jugador j1, Jugador j2, Tablero tablero, boolean turno) {
    palitos = new Palito();
    huecos = new Hueco();
    estado = new Estado();
    this.tablero = tablero;
    this.j1 = j1;
    this.j2 = j2;
    //this.id = id;
    this.turno = turno;
    
    partidaListeners = new Vector<PartidaListener>();
  }
  
  /**
   * Metodo para establecer el partidaListener de la partida.
   * @param partidaListener El partidaListener.
   */
  public void addPartidaListener(PartidaListener partidaListener) {
     partidaListeners.addElement(partidaListener);
  }
  
  /*public long getId() {
    return id;
  }*/
  
  @Override
  public void run() {
    Jugador j = null;
    Jugada jug = null;
    
    tablero.iniciar();
    
    while(!esFin()) {
      j = (turno) ? j1 : j2;
      fireCambiaTurnoEvent(j.getNombre());

      j.actualiza(jug, palitos, huecos, estado);
      
      do {
        jug = j.getMovimiento();
        if(jug == null) {
          return;
        }
      } while(!palitos.jugadaValida(jug));
      
      insertaJugada(jug);
      
      if(jug.isCoord()) {   
        tablero.dibujar(jug.getX1(), jug.getY1(), jug.getX2(), jug.getY2(), j.getColor());
      } else {
        tablero.dibujar(jug.getHInicio(), jug.getHFin(), j.getColor());
      }
      
      turno = !turno;
      
      try {
        Thread.sleep(TURN_DELAY);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
    Jugador per = (turno) ? j1 : j2;
    per.actualiza(jug, palitos, huecos, estado);
    
    tablero.terminar();
    
    fireFinPartidaEvent(j == j1);
  }
  
  /**
   * Inserta una jugada y actualiza la partida.
   * @param j La jugada a insertar
   */
  public void insertaJugada(Jugada j) {
    for(int i = 0; i < j.getLon(); i++) {
      palitos.tacha(j.getPInicio() + i);
    }
    for(int i = 1; i < j.getLon(); i++) {
      huecos.tacha(j.getHInicio() + i);
    }
    
    if(Hueco.isBoundary(j.getHInicio())) {
      huecos.tacha(j.getHInicio());
    } else if(!palitos.getEstado(j.getPInicio() - 1)) {
      huecos.tacha(j.getHInicio());
    }
    
    if(Hueco.isBoundary(j.getHFin())) {
      huecos.tacha(j.getHFin());
    } else if(!palitos.getEstado(j.getPFin() + 1)) {
      huecos.tacha(j.getHFin());
    }
    
    estado.actualiza(palitos);
  }
  
  /**
   * Metodo que dice si la partida a llegado a su fin.
   * @return Si la partida ha terminado o sigue.
   */
  public boolean esFin() {
    return palitos.vivos() == 1;
  }
  
  /**
   * Finaliza la partida bruscamente.
   */
  public void terminar() {
    tablero.terminar();
    this.interrupt();
  }
  
  /**
   * Devuelve el primer jugador
   * @return El jugador 1
   */
  public Jugador getJ1() {
    return j1;
  }
  
  /**
   * Devuelve el segundo jugador
   * @return El jugador 2
   */
  public Jugador getJ2() {
    return j2;
  }

  private void fireCambiaTurnoEvent(String jug) {
  	for(int i = 0; i < partidaListeners.size(); i++) {
  		PartidaListener listener = (PartidaListener) partidaListeners.elementAt(i);
  		listener.cambiaTurno(jug);
  	}
  }

  private void fireFinPartidaEvent(boolean j1Winner) {
  	for(int i = 0; i < partidaListeners.size(); i++) {
  		PartidaListener listener = (PartidaListener) partidaListeners.elementAt(i);
  		listener.finPartida(j1Winner);
  	}
  }
}
