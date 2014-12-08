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
 * along with SuperPalitos. If not, see <http://www.gnu.org/licenses/>.
 */

package com.angelcalvo.palitos;

public class GameState {
  private static final int STICKS_ROWS[] = { 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4 };
	private static final int GAPS_ROWS[] = { 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4 };
	/** Indica si el hueco es exterior (true) o interior (false) */
	private static final boolean GAPS_BOUNDS[] = { true, true, true, false, true, true, false, false,
			true, true, false, false, false, true, true, false, false, false, false, true };

  public static final int NSTICKS = 15;
  public static final int NGAPS = 20;
  
  /** true => sin tachar, false => tachado */
  private boolean[] sticks;
  private boolean[] gaps;
  
  public GameState() {
    sticks = new boolean[NSTICKS];
    for(int i = 0; i < sticks.length; i++) {
    	sticks[i] = true;
    }
    gaps = new boolean[NGAPS];
    for(int i = 0; i < gaps.length; i++) {
      gaps[i] = true;
    }
  }
  
  public boolean getStick(int index) {
  	return sticks[index];
  }
  
  public boolean getGap(int index) {
  	return gaps[index];
  }
  
  private void crossStick(int index) {
  	sticks[index] = false;
  }
  
  private void crossGap(int index) {
  	gaps[index] = false;
  }
  
  /**
   * Inserta una jugada y actualiza la partida.
   * @param j La jugada a insertar
   */
  // Move to Game state
  public void move(Move j) {
    for(int i = 0; i < j.length(); i++) {
      crossStick(j.getStartStick() + i);
    }
    for(int i = 1; i < j.length(); i++) {
      crossGap(j.getStartGap() + i);
    }
    
    if(isBoundaryGap(j.getStartGap())) {
      crossGap(j.getStartGap());
    } else if(!sticks[j.getStartStick() - 1]) {
      crossGap(j.getStartGap());
    }
    
    if(isBoundaryGap(j.getEndGap())) {
      crossGap(j.getEndGap());
    } else if(!sticks[j.getEndStick() + 1]) {
      crossGap(j.getEndGap());
    }
  }
  
  /**
   * Number of uncrossed sticks
   * @return
   */
  public int alive() {
    int n = 0;
    for(int i = 0; i < sticks.length; i++) {
      if(sticks[i]) {
        n++;
      }
    }
    return n;
  }
  
  /**
   * Comprueba que una jugada es valida, que el palito inicial y final esten en
   * la misma fila, que los palitos no esten tachados ya y que no se tachen
   * todos los palitos.
   * @param move La jugada a validar
   * @return Si la jugada es valida o no
   */
  // TODO move -> params
  public boolean isValid(Move move) {
  	if (move == null) {
  		return false;
  	}
  	
		if(move.length() == 0) {
			return false;
		}

    for(int i = 0; i < move.length(); i++) {
      if(isCrossed(move.getStartStick() + i)) {
        return false;
      }
    }

    if(alive() - move.length() == 0) {
      return false;
    }
    
  	return true;
  }
  
  public boolean areValidGaps(int gap1, int gap2) {
  	if (gap1 < 0 || gap2 < 0 || gap1 >= NGAPS || gap2 >= NGAPS || gap1 == gap2) {
  		return false;
  	}
  	
  	if(GAPS_ROWS[gap1] != GAPS_ROWS[gap1]) {
  		return false;
  	}
  	
  	return isValid(Move.fromGaps(gap1, gap2));
  }
  
  public boolean areValidSticks(int stick1, int stick2) {
  	if (stick1 < 0 || stick2 < 0 || stick1 >= NSTICKS || stick2 >= NSTICKS) {
  		return false;
  	}
  	
  	if(STICKS_ROWS[stick1] != STICKS_ROWS[stick2]) {
  		return false;
  	}
  	
  	return isValid(Move.fromSticks(stick1, stick2));
  }
  
  private boolean isCrossed(int stick) {
  	return !sticks[stick];
  }
  
  /**
   * Indica si el hueco es interior o exterior
   * @param index
   * @return Cierto si el hueco es exterior
   */
  private boolean isBoundaryGap(int index) {
  	return GAPS_BOUNDS[index];
  }

	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i < NSTICKS; i++) {
			str += sticks[i] ? "|" : "-";
		}
		str += "\n";
		for(int i = 0; i < NGAPS; i++) {
			str += gaps[i] ? "1" : "0";
		}
		return str + "\n";
	}
  
}
