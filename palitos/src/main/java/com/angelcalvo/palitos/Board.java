/*
 * Tablero
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.palitos;

/**
 * Representa un tablero donde se pueden dibujar las lineas del
 * (tachones) juego.
 * 
 * @author Angel Calvo 
 */
public interface Board {
  /**
   * Indica al tablero que la partida se ha iniciado
   */
  void started();
  
  /**
   * Indica al tablero que la partidad ha terminado
   */
  void finished();
  

  /**
   * Dibuja una l&iacute;nea desde un hueco a otro.
   * @param player
   * @param move
   */
  void move(Player player, Move move);
}
