/*
 * Pollo Verde Software 2006-2014
 * 
 * This file is part of SuperPalitos.
 * 
 * SuperPalitos is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * SuperPalitos is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.angelcalvo.palitos;

/**
 * Esta clase representa una jugada.
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
  
  protected int startStick;
  protected int endStick;
  protected int startGap;
  protected int endGap;
  protected int length;

  public static Move fromSticks(int stick1, int stick2) {
  	if (stick1 < 0 || stick2 < 0 || stick1 >= GameState.NSTICKS || stick2 >= GameState.NSTICKS) {
  		throw new IllegalArgumentException();
  	}
  	
  	return stick1 < stick2 ? new Move(stick1, stick2) : new Move(stick2, stick1);
  }
  
  public static Move fromGaps(int gap1, int gap2) {
  	if (gap1 < 0 || gap2 < 0 || gap1 >= GameState.NGAPS || gap2 >= GameState.NGAPS) {
  		throw new IllegalArgumentException();
  	}
  	
  	int stick1 = H2PD[gap1 < gap2 ? gap1 : gap2];
  	int stick2 = H2PI[gap1 < gap2 ? gap2 : gap1];

  	return new Move(stick1, stick2);
  }
  
  protected Move(int startStick, int endStick) {
  	this.startStick = startStick;
  	this.endStick = endStick;
  	
    startGap = P2HI[startStick];
    endGap = P2HD[endStick];
    
    length = endGap - startGap;
  }

  /**
   * Metodo que devuelve el 1er palito de la jugada.
   * @return Devuelve el 1er palito de la jugada.
   */
  public int getStartStick() {
    return startStick;
  }

  /**
   * Metodo que devuelve el ultimo palito de la jugada.
   * @return Devuelve el ultimo palito de la jugada.
   */
  public int getEndStick() {
    return endStick;
  }

  /**
   * Metodo que devuelve el 1er hueco de la jugada.
   * @return Devuelve el 1er huecoo de la jugada.
   */
  public int getStartGap() {
    return startGap;
  }

  /**
   * Metodo que devuelve el ultimo hueco de la jugada.
   * @return Devuelve el ultimo hueco de la jugada.
   */
  public int getEndGap() {
    return endGap;
  }

  /**
   * Metodo que devuelve el numero de palitos tachados en la jugada.
   * @return La  longitud del la jugada.
   */
  public int length() {
    return length;
  }

	@Override
	public String toString() {
		return "Move [Sticks: " + startStick + "-" + endStick + "; gaps=" + startGap + "-"	+ endGap + "]";
	}
}
