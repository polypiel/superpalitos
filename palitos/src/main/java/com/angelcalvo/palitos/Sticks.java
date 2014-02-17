/*
 * Superpalitos
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.palitos;

/**
 * Esta clase representa los palitos
 * 
 * @author Angel Calvo
 */
public class Sticks extends Thing {
  private static final int Y[] = {0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4};
  public static final int NPALS = 15;
  
  /**
   * Constructor. Pone el estado de cada palito a true
   *
   */
  public Sticks() {
    estados = new boolean[NPALS];
    for(int i = 0; i < estados.length; i++) {
      estados[i] = true;
    }
  }
  
  /**
   * Obtiene la variable y del palito, que representa la fila. La fila superior
   * es la numero 0 y la inferior es la numero 4.
   * @param indice El palito del que se desea obtener la fila
   * @return La fila del palito
   */
  public int getRow(int indice) {
    return Y[indice];
  }

  /**
   * Comprueba que una jugada es valida, que el palito inicial y final esten en
   * la misma fila, que los palitos no esten tachados ya y que no se tachen
   * todos los palitos.
   * @param j La jugada a validar
   * @return Si la jugada es valida o no
   */
  public boolean jugadaValida(Move j) {
  	if(j == null) {
  		return false;
  	}
    if(getRow(j.getPInicio()) != getRow(j.getPFin())) {
      return false;
    }
    for(int i = 0; i < j.getLon(); i++) {
      if(!getEstado(j.getPInicio() + i)) {
        return false;
      }
    }
    if(vivos() - j.getLon() == 0) {
      return false;
    }
    return true;
  }
}
