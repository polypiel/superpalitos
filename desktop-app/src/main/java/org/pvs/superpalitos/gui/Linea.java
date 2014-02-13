/*
 * Linea
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.superpalitos.gui;

import org.pvs.palitos.Partida;

/**
 * Representa una l&iacute;nea.
 *  
 * @author &Aacute;ngel Luis Calvo Ortega
 */
public class Linea {
  private int x1, y1, x2, y2;
  private boolean volatil;
  private int color;

  /**
   * Crea una linea.
   * @param x1 La x del 1er punto de la l&iacute;nea.
   * @param y1 La x del &Uacute;ltimo punto de la l&iacute;nea.
   * @param x2 La y del 1er punto de la l&iacute;nea.
   * @param y2 La y del &Uacute;ltimo punto de la l&iacute;nea.
   * @param volatil Indica si la l&iacute;nea se deber&aacute; borrar o no.
   */
  public Linea(int x1, int y1, int x2, int y2, boolean volatil) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.volatil = volatil;
    color = Partida.BLUE_COLOR;
  }

  /**
   * Metodo para obtener la x del 1er punto de la linea.
   * @return La x del 1er punto de la l&iacute;nea.
   */
  public int getX1() {
    return x1;
  }

  /**
   * Metodo para obtener la y del 1er punto de la l&iacute;nea.
   * @return La y del 1er punto de la l&iacute;nea.
   */
  public int getY1() {
    return y1;
  }

  /**
   * Metodo para obtener la x del &Uacute;ltimo punto de la l&iacute;nea.
   * @return La x del &Uacute;ltimo punto de la l&iacute;nea.
   */
  public int getX2() {
    return x2;
  }

  /**
   * Metodo para obtener la y del &Uacute;ltimo punto de la l&iacute;nea.
   * @return La y del &Uacute;ltimo punto de la l&iacute;nea.
   */
  public int getY2() {
    return y2;
  }

  /**
   * Metodo que nos dice si la l&iacute;nea es volatil o no.
   * @return La volatilidad de la l&iacute;nea.
   */
  public boolean isVolatil() {
    return volatil;
  }

  /**
   * Metodo para obtener el color de la linea.
   * @return El color de la l&iacute;nea.
   */
  public int getColor() {
    return color;
  }

  /**
   * Metodo para establecer el color de la l&iacute;nea.
   * @param color El nuevo color.
   */
  public void setColor(int color) {
    this.color = color;
  }
}
