/*
 * Jugador
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.palitos;

/**
 * Interfaz de un jugador
 * 
 * @author Angel Luis Calvo Ortega
 */
public interface Jugador {
	/**
	 * Metodo por el cual el jugador realiza una jugada.
	 * @return La jugada realizada.
	 */
  Jugada getMovimiento();
  
  /**
   * Metodo por el cual un jugador es notificado del movimiento del contrario.
   * @param j La jugada realizada.
   * @param p El estado actual de los palitos.
   * @param h El estado actual de los huecos.
   * @param e El estado actual.
   */
  void actualiza(Jugada j, Palito p, Hueco h, Estado e);
  
  /**
   * Metodo para cambiar el color de un jugador.
   * @param c El nuevo color.
   */
  void setColor(int c);
  
  /**
   * Metodo para conseguir el color de un jugador.
   * @return El color del jugador.
   */
  int getColor();
  
  /**
   * Metodo para cambiar el nombre de un jugador.
   * @param n El nuevo nombre.
   */
  void setNombre(String n);
  
  /**
   * Metodo para conseguir el nombre de un jugador.
   * @return El nombre del jugador.
   */
  String getNombre();
  
  /**
   * Metodo para avisar al jugador que se ha terminado la partida
   *
   */
  void terminar();
}
