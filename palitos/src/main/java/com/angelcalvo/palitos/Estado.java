/*
 * Estado
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.palitos;
import java.util.*;

/**
 * Representa el estado de la partida, guardando el numero de
 * palitos seguidos sin tachar de diferentes longitudes.
 *
 * @author Angel Luis Calvo Ortega
 */
public class Estado implements Cloneable {
  /** Jugadas ganadoras */
  private static final int[][][] jugadas = {
      {{1, 0, 0, 0, 0}, {3, 0, 0, 0, 0}, {0, 2, 0, 0, 0}},
      {{5, 0, 0, 0, 0}, {7, 0, 0, 0, 0}, {2, 2, 0, 0, 0}, {1, 1, 1, 0, 0}, {0, 0, 2, 0, 0}},
      {{9, 0, 0, 0, 0}, {0, 4, 0, 0, 0}, {2, 0, 2, 0, 0}, {0, 0, 0, 2, 0}, {2, 0, 0, 2, 0}}
  };
	/** est[i] de longitud i+1 con 0 <= i < 5 */
  private int est[];
  
  /**
   * Constructor, inicializa el array a {1, 1, 1, 1, 1}
   */
  public Estado() {
    est = new int[5];
    for(int i = 0; i < est.length; i++) {
      est[i] = 1;
    }
  }

  /**
   * Actualiza el estado cuando se ha introduzido una jugada.
   */
  public void actualiza(Palito palitos) {
    int aux = 0, indice;

    for(int i = 0; i < est.length; i++) {
      est[i] = 0;
      aux += i;
      indice = 0;
      for(int j = 0; j <= i; j++) {
        if(palitos.getEstado(aux + j)) {
          indice++;
        } else if(indice > 0) {
          est[indice - 1]++;
          indice = 0;
        }
      }
      if(indice > 0) {
        est[indice - 1]++;
        indice = 0;
      }
    }
  }

  private int esPreJugada(int dif) {
  	int cont;
  	
  	for(int i = 0; i <= dif; i++) {
  		for(int j = 0; j < jugadas[i].length; j++) {
  			cont = 0;
  			for(int k = 0; k < est.length; k++) {
  				if(est[k] - jugadas[i][j][k] == 1) {
  					cont++;
  				} else if(est[k] - jugadas[i][j][k] != 0) {
  					cont = 2;
  					break;
  				}
  			}
  			if(cont == 1) {
  				return IA.MIN - (i + 1);
  			}
  		}
  	}
  	return 0;
  }

  /**
   * Obtiene una jugada valida a partir de un estado
   * @param e El estado
   * @return
   */
  public int[] getJugada(Estado e) {
    int jug[] = new int[3];

    jug[0] = numPalitos() - e.numPalitos();
    jug[2] = 0;
    for(int i = est.length - 1; i >= 0; i--) {
      if(est[i] > e.est[i]) {
        jug[1] = i + 1;
      } else if(est[i] < e.est[i]) {
        jug[2] = i + 1;
      }
    }
    return jug;
  }

  private int esJugada(int dif) {
    for(int i = 0; i <= dif; i++) {
      for(int j = 0; j < jugadas[i].length; j++) {
        int cont = 0;
        for(int k = 0; k < est.length; k++) {
          if(jugadas[i][j][k] == est[k]) {
            cont++;
          }
        }
        if(cont == est.length) {
          return IA.MAX + i + 1;
        }
      }
    }
    return 0;
  }

  /**
   * Metodo para indicar la optimidad del estado.
   * <p> 1 => bueno
   * <p>-1 => malo
   * <p> 0 => regular
   * @param dif La dificultad del contrario.
   * @return La optimidad del estado.
   */
  public int valor(int dif) {
		int v;
		
    if((v = esJugada(dif)) != 0) {
      return v;
    }
    if((v = esPreJugada(dif)) != 0) {
      return v;
    }
    return 0;
  }

  /**
   * Metodo para saber el numero de palitos sin tachar.
   * @return El numero de palitos sin tachar.
   */
  public int numPalitos() {
    int n = 0;

    for(int i = 0; i < est.length; i++) {
      n += est[i] * (i + 1);
    }
    return n;
  }

  /**
   * Incrementa una meseta de longitudes de palitos.
   * @param indice La meseta a incrementar.
   */
  public void inc(int indice) {
    est[indice]++;
  }

  /**
   * Decrementa una meseta de longitudes de palitos.
   * @param indice La meseta a incrementar.
   */
  public void dec(int indice) {
    est[indice]--;
  }

  /**
   * Metodo que devuelve todos los posibles estados por realizar una jugada.
   * @return Un iterador con todos los estados.
   */

  public Collection<Estado> hijos() {
    Vector<Estado> v = new Vector<Estado>();

    for(int i = 0; i < 5; i++) {
      for(int j = 0; j <= i && est[i] > 0; j++) {
        for(int k = 0; k <= (i - j) / 2; k++) {
          //System.out.print("("+i+","+j+","+k+") o> ");
          Estado e = (Estado)clone();

          e.dec(i);
          if(i - j - k > 0) {
            e.inc(i - j - k - 1);
          }
          if(k > 0) {
            e.inc(k - 1);
          }

          if(e.numPalitos() > 0) {
            v.addElement(e);
          }
        }
      }
    }
    return v;
  }

  @Override
  public Object clone() {
    try {
      Estado nuevoEstado = (Estado)super.clone();
      nuevoEstado.est = (int[])est.clone();
      return nuevoEstado;
    } catch(CloneNotSupportedException e) {
      return null;
    }
  }

  @Override
  public String toString() {
  	StringBuffer buf = new StringBuffer(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("Estado:_"));

    for(int i = 0; i < est.length; i++) {
    	buf.append(est[i]);
    	buf.append(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("_"));
    }
    return buf.toString();
  }
}
