/*
 * Jugada
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.palitos;

/**
 * Esta clase representa una jugada.
 * 
 * @author Angel Luis Calvo Ortega
 */
public class Jugada {
	/** Indica que la jugada se crea indicando los numeros de los palitos */
  public static final int PALITO = 0;
	/** Indica que la jugada se crea indicando los numeros de los huecos */
  public static final int HUECO = 1;
  
  private int pInicio;
  private int pFin;
  private int hInicio;
  private int hFin;
  private int lon;
  private boolean coord;
  private int x1, y1, x2, y2;

  /**
   * Constructor que crea una jugada.
   * @param c1 cosa1
   * @param c2 cosa2
   * @param modo palito o hueco
   */
  public Jugada(int c1, int c2, int modo) {
    if(modo == PALITO) {
      if(c1 > c2) {
        pInicio = c2;
        pFin = c1;
      } else {
        pInicio = c1;
        pFin = c2;
      }
      hInicio = Cosa.P2HI[pInicio];
      hFin = Cosa.P2HD[pFin];
    } else if(modo == HUECO) {
      if(c1 > c2) {
        hInicio = c2;
        hFin = c1;
      } else {
        hInicio = c1;
        hFin = c2;
      }
      pInicio = Cosa.H2PD[hInicio];
      pFin = Cosa.H2PI[hFin];
    }
    lon = hFin - hInicio;
  }

  /**
   * Metodo que devuelve el 1er palito de la jugada.
   * @return Devuelve el 1er palito de la jugada.
   */
  public int getPInicio() {
    return pInicio;
  }

  /**
   * Metodo que devuelve el ultimo palito de la jugada.
   * @return Devuelve el ultimo palito de la jugada.
   */
  public int getPFin() {
    return pFin;
  }

  /**
   * Metodo que devuelve el 1er hueco de la jugada.
   * @return Devuelve el 1er huecoo de la jugada.
   */
  public int getHInicio() {
    return hInicio;
  }

  /**
   * Metodo que devuelve el ultimo hueco de la jugada.
   * @return Devuelve el ultimo hueco de la jugada.
   */
  public int getHFin() {
    return hFin;
  }

  /**
   * Metodo que devuelve el numero de palitos tachados en la jugada.
   * @return La  longitud del la jugada.
   */
  public int getLon() {
    return lon;
  }

  /**
   * Indica si la jugada guarda las coordenadas de las lineas.
   * @return True si las guarda, false si no.
   */
  public boolean isCoord() {
    return coord;
  }

  /**
   * Metodo que establece que la jugada guarda las coordenadas.
   * @param x1 La x del 1er punto de la linea.
   * @param y1 La y del 1er punto de la linea.
   * @param x2 La x del ultimo punto de la linea.
   * @param y2 La y del ultimo punto de la linea.
   */
  public void setCoord(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    coord = true;
  }

  /**
   * Metodo que devuelve la x del 1er punto de la linea.
   * @return La x del 1er punto de la linea.
   */
  public int getX1() {
    return x1;
  }

  /**
   * Metodo que devuelve la y del 1er punto de la linea.
   * @return La y del 1er punto de la linea.
   */
  public int getY1() {
    return y1;
  }

  /**
   * Metodo que devuelve la x del ultimo punto de la linea.
   * @return La x del ultimo punto de la linea.
   */
  public int getX2() {
    return x2;
  }
  
  /**
   * Metodo que devuelve la y del ultimo punto de la linea.
   * @return La y del ultimo punto de la linea.
   */
  public int getY2() {
    return y2;
  }
}
