package com.angelcalvo.superpalitos;

import com.angelcalvo.palitos.Move;

public class XyMove extends Move {
	
	private int x1, y1, x2, y2;
	
	public static XyMove fromGaps(int stick1, int stick2) {
		Move m1 = Move.fromGaps(stick1, stick2);
		return new XyMove(m1.getStartGap(), m1.getEndStick());
	}
	
	protected XyMove(int c1, int c2) {
		super(c1, c2);
	}	

  /**
   * Metodo que establece que la jugada guarda las coordenadas.
   * @param x1 La x del 1er punto de la linea.
   * @param y1 La y del 1er punto de la linea.
   * @param x2 La x del ultimo punto de la linea.
   * @param y2 La y del ultimo punto de la linea.
   */
  public void setCoords(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
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
