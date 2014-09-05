package com.angelcalvo.palitos;


public class GameState {
  private static final int STICKS_ROWS[] = { 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4 };
	//private static final int GAPS_ROWS[] = { 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4 };
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
    for(int i = 0; i < j.getLon(); i++) {
      crossStick(j.getPInicio() + i);
    }
    for(int i = 1; i < j.getLon(); i++) {
      crossGap(j.getHInicio() + i);
    }
    
    if(isBoundaryGap(j.getHInicio())) {
      crossGap(j.getHInicio());
    } else if(!sticks[j.getPInicio() - 1]) {
      crossGap(j.getHInicio());
    }
    
    if(isBoundaryGap(j.getHFin())) {
      crossGap(j.getHFin());
    } else if(!sticks[j.getPFin() + 1]) {
      crossGap(j.getHFin());
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
  public boolean isValid(Move move) {
  	assert move != null;
  	assert move.getPFin() >= 0 && move.getPInicio() >= 0 || move.getHFin() > 0 || move.getHInicio() >= 0;
  	assert move.getPFin() < NSTICKS && move.getPInicio() < NSTICKS && move.getHFin() < NGAPS && move.getHInicio() < NGAPS;

		if(move.getLon() == 0) {
			return false;
		}
    if(STICKS_ROWS[move.getPInicio()] != STICKS_ROWS[move.getPFin()]) {
      return false;
    }
    for(int i = 0; i < move.getLon(); i++) {
      if(!sticks[move.getPInicio() + i]) {
        return false;
      }
    }
    // it wouldn't left sticks left
    if(alive() - move.getLon() == 0) {
      return false;
    }
    return true;
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
