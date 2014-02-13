/*
 * Cosa
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.palitos;


/**
 * Clase abstracta de la que heredan Palito y Hueco
 *
 * @author Angel Luis Calvo Ortega
 */
// TODO kill
public abstract class Thing {
  /** Transformacion de palito al hueco de la izquierda */
  protected static final int P2HI[] = {0, 2, 3, 5, 6, 7, 9, 10, 11, 12, 14, 15, 16, 17, 18};
  /** Transformacion de palito al hueco de la derecha */
  protected static final int P2HD[] = {1, 3, 4, 6, 7, 8, 10, 11, 12, 13, 15, 16, 17, 18, 19};
  /** Transformacion de hueco al palito de la derecha */
  protected static final int H2PD[] = {0, -1, 1, 2, -1, 3, 4, 5, -1, 6, 7, 8, 9, -1, 10, 11, 12, 13, 14, -1};
  /** Transformacion de hueco al palito de la izquierda */
  protected static final int H2PI[] = {-1, 0, -1, 1, 2, -1, 3, 4, 5, -1, 6, 7, 8, 9, -1, 10, 11, 12, 13, 14};
  /** true => sin tachar, false => tachado */
  protected boolean[] estados;
  
  /**
   * @param indice El palito del que se desea conocer el estado
   * @return Devuelve el estado.
   */
  public boolean getEstado(int indice) {
    return estados[indice];
  }
  
  /**
   * Tacha un palito/hueco, poner cosas[indice] a falso.
   * @param indice El palito a tachar
   */
  public void cross(int indice) {
    estados[indice] = false;
  }
  
  /**
   * Metodo que devuelve el numero de palitos no tachados.
   * @return El nuemro de palitos vivos (sin tachar)
   */
  public int vivos() {
    int n = 0;
    for(int i = 0; i < estados.length; i++) {
      if(estados[i]) {
        n++;
      }
    }
    return n;
  }
}
