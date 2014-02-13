/*
 * IA
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.palitos;
import java.util.Random;


/**
 * Esta clase implementa a un contrario
 * 
 * @author Angel Luis Calvo Ortega
 */
public class IA implements Jugador {
  /** Modo facil de la IA */
  public static final int FACIL = 0;
  /** Modo normal de la IA */
  public static final int NORMAL = 1;
  /** Modo dificil de la IA */
  public static final int DIFICIL = 2;

  private static final String s[] = {"facil", "normal", "dificil"};
  
  protected static final double COTA = 0.1;
	protected final static int MIN = -1000;
	protected final static int MAX = 1000;
	
  private int dificultad;
  private Estado e;
  private Palito p;
  private int color;
  private String nombre = "IA";
  private Random r;
  
  /**
   * M&eacute;todo para establecer la dificultad de la IA.
   * @param dificultad La dificultad de la IA
   */
  public IA(int dificultad, int color) {
    this.dificultad = dificultad;
    this.color = color;
    r = new Random();
    r.setSeed(System.currentTimeMillis());
  }

  public void actualiza(Jugada j, Palito p, Hueco h, Estado e) {
    this.p = p;
    this.e = e;
  }
  
  public Jugada getMovimiento() {
    int max = MIN;
    Estado resultado = null;
		
    for(Estado aux: e.hijos()) {
      int valor = backtraking(aux, 0);
      //System.out.println(e + " => " + valor);
      if((valor > max) || resultado == null || (valor == max && r.nextFloat() < COTA)) {
        resultado = aux;
        max = valor;
      }
    }
    int d[] = e.getJugada(resultado);
    Jugada j = defineJugada(d[0], d[1], d[2]);
    //System.out.println(resultado + " -> " + d[0] + " " + d[1] + " " + d[2]);
    return j;
  }

  @Override
  public void setColor(int c) {}

  @Override
  public int getColor() {
    return color;
  }

  @Override
  public void setNombre(String n) {}

  @Override
  public String getNombre() {
    return nombre + "(" + s[dificultad] + ")";
  }
  
  @Override
  public void terminar() {}

	private int backtraking(Estado sig, int nivel) {
    int max = MIN; 
    int min = MAX; 
    int v = sig.valor(dificultad);
		
    if(v != 0 || nivel == dificultad) {
      return v;
    }
    for(Estado aux: sig.hijos()) {
      int b = backtraking(aux, nivel + 1);
      if(nivel % 2 == 0 && b < min) {
        min = b;
      } else if(nivel % 2 != 0 && b > max) {
        max = b;
    	}
    }
    return(nivel % 2 != 0) ? min : max;
  }

  private Jugada defineJugada(int lon, int lon2, int desp) {
    int aux = 0, indice, p1 = 0, j;
    for(int i = 0; i < 5; i++) {
      indice = 0;
      aux += i;
      for(j = 0; j <= i; j++) {
        if(p.getEstado(aux + j)) {
          indice++;
        } else if(indice == lon2) {
          p1 = (i * i + i) / 2 + j - lon2 + desp;
          return new Jugada(p1, p1 + lon - 1, Jugada.PALITO);
        }
      }
      if(indice == lon2) {
        p1 = (i * i + i) / 2 + j - lon2 + desp;
        return new Jugada(p1, p1 + lon - 1, Jugada.PALITO);
      }
    }
    return null;
  }
}
