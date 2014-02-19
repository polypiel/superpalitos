/*
 * PartidaListener
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.palitos;

/**
 * Un supervisor es una clase que monitorea los eventos nuevo
 * turno y fin de partida de una partida.
 * 
 * @author Angel Calvo
 */
public interface GameListener {
  /**
   * Es llamado cuando se empieza un nuevo turno.
   * @param player The player who moves
   */
  void newTurn(Player player);
  
  /**
   * Es llamado cuando se termina la partida.
   * @param player The winner
   */
  void finish(Player player);
}
