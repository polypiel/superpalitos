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

/**
 * Representa un tablero donde se pueden dibujar las lineas del
 * (tachones) juego.
 */
public interface Board {
  /**
   * Indica al tablero que la partida se ha iniciado
   */
  void started(GameState gameState);
  
  /**
   * Indica al tablero que la partidad ha terminado
   */
  void finished(Player winner);
  

  /**
   * Dibuja una l&iacute;nea desde un hueco a otro.
   * @param player
   * @param move
   */
  void move(Player player, Move move, GameState gameState);
}
