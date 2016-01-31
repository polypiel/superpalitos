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
 * along with SuperPalitos If not, see <http://www.gnu.org/licenses/>.
 */

package com.angelcalvo.palitos;

import java.awt.*;
import java.util.Collection;
import java.util.Random;
import java.util.Vector;

/**
 * AI player that uses a mini-max backtracking algorithm
 */
public class PlayerAI implements Player {
	public enum AiLevel {
		EASY (0),
		NORMAL (1),
		HARD (2);
		
		private int backLevel;
		
		private AiLevel(int backLevel) {
			this.backLevel = backLevel;
		}

		public int getBackLevel() {
			return backLevel;
		}
	}

	private static final int[][][] WINNER_MOVES = {
			{ { 1, 0, 0, 0, 0 }, { 3, 0, 0, 0, 0 }, { 0, 2, 0, 0, 0 } },
			{ { 5, 0, 0, 0, 0 }, { 7, 0, 0, 0, 0 }, { 2, 2, 0, 0, 0 }, { 1, 1, 1, 0, 0 }, { 0, 0, 2, 0, 0 } },
			{ { 9, 0, 0, 0, 0 }, { 0, 4, 0, 0, 0 }, { 2, 0, 2, 0, 0 }, { 0, 0, 0, 2, 0 }, { 2, 0, 0, 2, 0 } } };

	protected static final double COTA = 0.1;
	protected final static int MIN = -1000;
	protected final static int MAX = 1000;

	private final AiLevel aiLevel;
	private final Color color;
	private final String name = "AI";
	private final Random r;
	private GameState state;

	public PlayerAI(AiLevel level, Color color) {
		this.aiLevel = level;
		this.color = color;
		r = new Random();
		r.setSeed(System.currentTimeMillis());
	}

	@Override
	public Move move(GameState state) {
		this.state = state;
		int max = MIN;
		SimpleGameState resultado = null;

		SimpleGameState e = new SimpleGameState(state);
		for (SimpleGameState aux : e.children()) {
			int valor = backtraking(aux, 0);
			if ((valor > max) || resultado == null || (valor == max && r.nextFloat() < COTA)) {
				resultado = aux;
				max = valor;
			}
		}
		int d[] = e.getMove(resultado);
		Move j = defineJugada(d[0], d[1], d[2]);
    
		return j;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public String getName() {
		return name + "(" + aiLevel.name() + ")";
	}

	private int backtraking(SimpleGameState sig, int nivel) {
		int max = MIN;
		int min = MAX;
		int v = sig.value(aiLevel);

		if (v != 0 || aiLevel.getBackLevel() == nivel) {
			return v;
		}
		for (SimpleGameState aux : sig.children()) {
			int b = backtraking(aux, nivel + 1);
			if (nivel % 2 == 0 && b < min) {
				min = b;
			} else if (nivel % 2 != 0 && b > max) {
				max = b;
			}
		}
		return (nivel % 2 != 0) ? min : max;
	}

	private Move defineJugada(int lon, int lon2, int desp) {
		int aux = 0, indice, p1 = 0, j;
		for (int i = 0; i < 5; i++) {
			indice = 0;
			aux += i;
			for (j = 0; j <= i; j++) {
				if (state.getStick(aux + j)) {
					indice++;
				} else if (indice == lon2) {
					p1 = (i * i + i) / 2 + j - lon2 + desp;
					return Move.fromSticks(p1, p1 + lon - 1);
				}
			}
			if (indice == lon2) {
				p1 = (i * i + i) / 2 + j - lon2 + desp;
				return Move.fromSticks(p1, p1 + lon - 1);
			}
		}
		return null;
	}

	/**
	 * Internal state representation
	 */
	static class SimpleGameState implements Cloneable {
		/** est[i] de longitud i+1 con 0 <= i < 5 */
		private int est[];

		/**
		 * Constructor, inicializa el array a {1, 1, 1, 1, 1}
		 */
		public SimpleGameState() {
			est = new int[5];
			for (int i = 0; i < est.length; i++) {
				est[i] = 1;
			}
		}

		public SimpleGameState(GameState state) {
			est = new int[5];
			update(state);
		}

		/**
		 * Metodo para indicar la optimidad del estado.
		 * <p>
		 * 1 => bueno
		 * <p>
		 * -1 => malo
		 * <p>
		 * 0 => regular
		 * 
		 * @param aiLevel
		 *          La level del contrario.
		 * @return La optimidad del estado.
		 */
		protected int value(AiLevel aiLevel) {
			int v;

			if ((v = esJugada(aiLevel)) != 0) {
				return v;
			}
			if ((v = esPreJugada(aiLevel)) != 0) {
				return v;
			}
			return 0;
		}

		private int esPreJugada(AiLevel aiLevel) {
			int cont;

			for (int i = 0; i <= aiLevel.getBackLevel(); i++) {
				for (int j = 0; j < WINNER_MOVES[i].length; j++) {
					cont = 0;
					for (int k = 0; k < est.length; k++) {
						if (est[k] - WINNER_MOVES[i][j][k] == 1) {
							cont++;
						} else if (est[k] - WINNER_MOVES[i][j][k] != 0) {
							cont = 2;
							break;
						}
					}
					if (cont == 1) {
						return PlayerAI.MIN - (i + 1);
					}
				}
			}
			return 0;
		}

		private int esJugada(AiLevel aiLevel) {
			for (int i = 0; i <= aiLevel.getBackLevel(); i++) {
				for (int j = 0; j < WINNER_MOVES[i].length; j++) {
					int cont = 0;
					for (int k = 0; k < est.length; k++) {
						if (WINNER_MOVES[i][j][k] == est[k]) {
							cont++;
						}
					}
					if (cont == est.length) {
						return PlayerAI.MAX + i + 1;
					}
				}
			}
			return 0;
		}

		/**
		 * Actualiza el estado cuando se ha introduzido una jugada.
		 */
		private void update(GameState state) {
			int aux = 0, indice;

			for (int i = 0; i < est.length; i++) {
				est[i] = 0;
				aux += i;
				indice = 0;
				for (int j = 0; j <= i; j++) {
					if (state.getStick(aux + j)) {
						indice++;
					} else if (indice > 0) {
						est[indice - 1]++;
						indice = 0;
					}
				}
				if (indice > 0) {
					est[indice - 1]++;
					indice = 0;
				}
			}
		}

		/**
		 * Obtiene una jugada valida a partir de un estado
		 * 
		 * @param e
		 *          El estado
		 * @return
		 */
		public int[] getMove(SimpleGameState e) {
			int jug[] = new int[3];

			jug[0] = numSticksAlive() - e.numSticksAlive();
			jug[2] = 0;
			for (int i = est.length - 1; i >= 0; i--) {
				if (est[i] > e.est[i]) {
					jug[1] = i + 1;
				} else if (est[i] < e.est[i]) {
					jug[2] = i + 1;
				}
			}
			return jug;
		}

		/**
		 * Metodo para saber el numero de palitos sin tachar.
		 * 
		 * @return El numero de palitos sin tachar.
		 */
		public int numSticksAlive() {
			int n = 0;

			for (int i = 0; i < est.length; i++) {
				n += est[i] * (i + 1);
			}
			return n;
		}

		/**
		 * Incrementa una meseta de longitudes de palitos.
		 * 
		 * @param indice
		 *          La meseta a incrementar.
		 */
		private void inc(int indice) {
			est[indice]++;
		}

		/**
		 * Decrementa una meseta de longitudes de palitos.
		 * 
		 * @param indice
		 *          La meseta a incrementar.
		 */
		private void dec(int indice) {
			est[indice]--;
		}

		/**
		 * Metodo que devuelve todos los posibles estados por realizar una jugada.
		 * 
		 * @return Un iterador con todos los estados.
		 */

		protected Collection<SimpleGameState> children() {
			Vector<SimpleGameState> v = new Vector<SimpleGameState>();

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j <= i && est[i] > 0; j++) {
					for (int k = 0; k <= (i - j) / 2; k++) {
						SimpleGameState e = (SimpleGameState) clone();

						e.dec(i);
						if (i - j - k > 0) {
							e.inc(i - j - k - 1);
						}
						if (k > 0) {
							e.inc(k - 1);
						}

						if (e.numSticksAlive() > 0) {
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
				SimpleGameState nuevoEstado = (SimpleGameState) super.clone();
				nuevoEstado.est = (int[]) est.clone();
				return nuevoEstado;
			} catch (CloneNotSupportedException e) {
				return null;
			}
		}

		@Override
		public String toString() {
			StringBuffer buf = new StringBuffer();

			for (int i = 0; i < est.length; i++) {
				buf.append(est[i]);
				buf.append(" ");
			}
			return buf.toString();
		}
	}
}
