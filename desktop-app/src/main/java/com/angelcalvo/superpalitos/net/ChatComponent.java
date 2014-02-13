/*
 * ChatComponent
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */

package com.angelcalvo.superpalitos.net;

/**
 * Interfaz para una ventana que muestre un chat de PalitosNet.
 * 
 * @author Angel Luis Calvo Ortega
 */
public interface ChatComponent {
	/**
	 * Muestra una l&iacute;nea recivida.
	 * @param nombre Nombre del jugador que ha escrito la l&iacute;nea.
	 * @param msg Mensaje enviado.
	 */
  void showMessage(String nombre, int c, String msg);
}
