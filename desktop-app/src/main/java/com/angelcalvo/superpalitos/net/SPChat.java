/*
 * SPChat
 * 
 * Created on 2 de Agosto de 2005
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.superpalitos.net;

/**
 * Interfaz del chat de PalitosNet
 * 
 * @author Angel Luis Calvo Ortega
 */
public interface SPChat {
  /**
   * Manda una l&iacute;nea al chat contrario.
   * @param line La l&iacute;nea.
   */
  void writeLine(String line);
}
