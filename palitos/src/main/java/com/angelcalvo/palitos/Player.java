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

import java.awt.Color;

/**
 * Interfaz de un jugador
 * 
 * @author Angel Luis Calvo Ortega
 */
public interface Player {
	/**
	 * Metodo por el cual el jugador realiza una jugada.
	 * @return La jugada realizada.
	 */
  Move move();
  
  /**
   * Metodo por el cual un jugador es notificado del movimiento del contrario.
   * @param move La jugada realizada.
   * @param state El estado actual de los palitos.
   */
  void update(Move move, GameState state);
  
  /**
   * Metodo para conseguir el color de un jugador.
   * @return El color del jugador.
   */
  Color getColor();
  
  /**
   * Metodo para conseguir el nombre de un jugador.
   * @return El nombre del jugador.
   */
  String getName();
  
  /**
   * Metodo para avisar al jugador que se ha terminado la partida
   *
   */
  void finish();
}
