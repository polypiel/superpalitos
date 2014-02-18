/*
 * Jugador
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.palitos;

/**
 * Interfaz de un jugador
 * 
 * @author Angel Luis Calvo Ortega
 */
public interface Player {
	/**
	 * Metodo por el cual el jugador realiza una jugada.
	 * @return La jugada realizada.
	 */
  Move move();
  
  /**
   * Metodo por el cual un jugador es notificado del movimiento del contrario.
   * @param j La jugada realizada.
   * @param p El estado actual de los palitos.
   * @param h El estado actual de los huecos.
   * @param e El estado actual.
   */
  void update(Move j, Sticks p, Gaps h);
  
  /**
   * Metodo para conseguir el color de un jugador.
   * @return El color del jugador.
   */
  int getColor();
  
  /**
   * Metodo para conseguir el nombre de un jugador.
   * @return El nombre del jugador.
   */
  String getName();
  
  /**
   * Metodo para avisar al jugador que se ha terminado la partida
   *
   */
  void finish();
}
