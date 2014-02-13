/*
 * Tablero
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.palitos;

/**
 * Representa un tablero donde se pueden dibujar las lineas del
 * (tachones) juego.
 * 
 * @author Angel Luis Calvo Ortega
 */
public interface Tablero {
  /**
   * Indica al tablero que la partida se ha iniciado
   */
  void iniciar();
  
  /**
   * Indica al tablero que la partidad ha terminado
   */
  void terminar();
  
  /**
   * Dibuja una linea desde un punto a otro.
   * @param x1 La componente horizontal del 1er punto.
   * @param y1 La componente horizontal del ultimo punto.
   * @param x2 La componente vertical del 1er punto.
   * @param y2 La componente vertical del &Uacute;ltimo punto.
   * @param c El color de la linea.
   */
  void dibujar(int x1, int y1, int x2, int y2, int c);
  
  /**
   * Dibuja una l&iacute;nea desde un hueco a otro.
   * @param h1 El primer hueco.
   * @param h2 El &Uacute;ltimo hueco.
   * @param c El color de la l&iacute;nea.
   */
  void dibujar(int h1, int h2, int c);
  
  /**
   * Crea un jugador que juega en el tablero
   * @param name
   * @param c
   * @return
   */
  Jugador createJugador(String name, int c);

  /**
   * Establece el identificador del tablero
   * @param id
   */
	void setId(long id);
	
	/**
	 * Devuelve el identificador del tablero
	 * @return
	 */
	long getId();
	
	/**
	 * Establece el marcador de la partida
	 * @param msg El marcador
	 */
	void setMarcador(String msg);
}
