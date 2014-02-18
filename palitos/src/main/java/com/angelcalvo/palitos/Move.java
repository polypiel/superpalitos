/*
 * Jugada
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.palitos;

/**
 * Esta clase representa una jugada.
 * 
 * @author Angel Luis Calvo Ortega
 */
public class Move {
  /** Transformacion de palito al hueco de la izquierda */
  protected static final int P2HI[] = {0, 2, 3, 5, 6, 7, 9, 10, 11, 12, 14, 15, 16, 17, 18};
  /** Transformacion de palito al hueco de la derecha */
  protected static final int P2HD[] = {1, 3, 4, 6, 7, 8, 10, 11, 12, 13, 15, 16, 17, 18, 19};
  /** Transformacion de hueco al palito de la derecha */
  protected static final int H2PD[] = {0, -1, 1, 2, -1, 3, 4, 5, -1, 6, 7, 8, 9, -1, 10, 11, 12, 13, 14, -1};
  /** Transformacion de hueco al palito de la izquierda */
  protected static final int H2PI[] = {-1, 0, -1, 1, 2, -1, 3, 4, 5, -1, 6, 7, 8, 9, -1, 10, 11, 12, 13, 14};
  
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
  public Move(int c1, int c2, int modo) {
    if(modo == PALITO) {
      if(c1 > c2) {
        pInicio = c2;
        pFin = c1;
      } else {
        pInicio = c1;
        pFin = c2;
      }
      hInicio = P2HI[pInicio];
      hFin = P2HD[pFin];
    } else if(modo == HUECO) {
      if(c1 > c2) {
        hInicio = c2;
        hFin = c1;
      } else {
        hInicio = c1;
        hFin = c2;
      }
      pInicio = H2PD[hInicio];
      pFin = H2PI[hFin];
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
