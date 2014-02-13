/*
 * Hueco
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.palitos;

/**
 * Esta clase representa los huecos que hay entre los palitos.
 *
 * @author Angel Luis Calvo Ortega
 */
public class Hueco extends Cosa {
	private static final int Y[] = { 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4,
			4, 4, 4, 4, 4 };

	/*
	 * private static final int x[] = { -1, 1, -2, 0, 2, -3, -1, 1, 3, -4, -2, 0,
	 * 2, 4, -5, -3, -1, 1, 3, 5 };
	 */
	/** Indica si el hueco es exterior (true) o interior (false) */
	private static final boolean BOUND[] = { true, true, true, false, true, true,
			false, false, true, true, false, false, false, true, true, false, false,
			false, false, true };
  
  /**
   * Constructor, pone el estado de los huecos a true, es decir, que se pueden
   * utilizar.
   */
  public Hueco() {
    estados = new boolean[20];
    for(int i = 0; i < estados.length; i++) {
      estados[i] = true;
    }
  }
  
  /**
   * Metodo que devuelve la variable y. Fila superior y = 0 y fila inferior y = 4
   * @param indice El hueco
   * @return La altura del hueco
   */
  public int getY(int indice) {
    return Y[indice];
  }
  
  /**
   * Indica si el hueco es interior o exterior
   * @param index
   * @return Cierto si el hueco es exterior
   */
  public static boolean isBoundary(int index) {
  	return BOUND[index];
  }
  
  @Override
  public String toString() {
    return java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("Hueco:_") + super.toString();
  }
}
