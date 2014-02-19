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

import java.awt.Color;

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
   * @param move La jugada realizada.
   * @param state El estado actual de los palitos.
   */
  void update(Move move, GameState state);
  
  /**
   * Metodo para conseguir el color de un jugador.
   * @return El color del jugador.
   */
  Color getColor();
  
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
