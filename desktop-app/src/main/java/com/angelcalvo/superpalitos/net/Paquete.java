/*
 * Paquete
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.superpalitos.net;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.pvs.palitos.Jugada;

/**
 * Un paquete del protocolo PalitosNet.
 * <p><br><ul>
 * <li>HOLA = {nombre(String) + color(Color)} //Inicia una conexi&oacute;n.
 * <li>JUGADA = {jugada(Jugada)} //Manda una jugada.
 * <li>ADIOS = {} //Termina una conexi&oacute;n.
 * <li>CHAT = {mensaje(String)} // Un mensaje de chat.
 *
 * @author Angel Luis Calvo Ortega
 */
public class Paquete {
  protected final static int HOLA = 0;
  protected final static int ADIOS = 1;
  protected final static int JUGADA = 2;
  protected final static int CHAT = 3;
  
  private int tipo; 
	private String nombre;
	private int color;
	private Jugada jugada;
	private String mensaje;
  
	/**
	 * Crea un nuevo paquete de un tipo
	 * @param tipo El tipo del paquete.
	 */
	public Paquete(int tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Lee un paquete de la red.
	 * @param in Flujo de donde se lee.
	 * @return El paquete leido.
	 */
	public static Paquete receive(DataInputStream in) {
		if(in == null) {
			return null;
		}
		
		try {
			int tipo = in.readInt();
			Paquete paquete = new Paquete(tipo);
			switch(tipo) {
				case HOLA:
					String nick = in.readUTF();
					paquete.setNombre(nick);
					int rgb = in.readInt();
					paquete.setColor(rgb);
					System.out.println("hola_received");
					break;
				case ADIOS:
					System.out.println("adios_received");
					break;
				case JUGADA:
					int p1 = in.readInt();
					int p2 = in.readInt();
					System.out.println("jugada_received_(" + p1 + "," + p2 + ")");     
					paquete.setJugada(new Jugada(p1, p2, Jugada.PALITO));
					break;
        case CHAT:
          String msj = in.readUTF();
          paquete.setMensaje(msj);
          System.out.println("chat_received");
          break;
			}
			return paquete;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Envia un paquete a la red.
	 * @param out El flujo de salida.
	 */
	public void send(DataOutputStream out) {
		if(out == null) {
			return;
		}
		try {
			out.writeInt(tipo);
			switch(tipo) {
				case HOLA:
					out.writeUTF(nombre);
					out.writeInt(color);
					System.out.println("hola_send");
					break;
				case ADIOS:
					System.out.println("adios_send");
					break;
				case JUGADA:
					out.writeInt(jugada.getPInicio());
					out.writeInt(jugada.getPFin());
					System.out.println("jugada_send_(" + jugada.getPInicio() +"," + jugada.getPFin() + ")");
					break;
        case CHAT:
          System.out.println("chat_received");
          out.writeUTF(mensaje);
          break;
			}
      out.flush();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que devuelve el tipo de un paquete.
	 * @return El tipo del paquete.
	 */
	public int getTipo() {
		return tipo;
	}
	
	/**
	 * Metodo para establecer la jugada de un paquete tipo JUGADA.
	 * @param jugada La jugada a enviar.
	 */
	public void setJugada(Jugada jugada) {
		this.jugada = jugada;
	}
	
	/**
	 * Metodo para devolver la jugada de un paquete tipo JUGADA.
	 * @return La jugada del paquete.
	 */
	public Jugada getJugada() {
		return jugada;
	}
	
	/**
	 * Metodo para establecer el color de un paquete tipo HOLA.
	 * @param color El color a enviar
	 */
	public void setColor(int color) {
		this.color = color;
	}
	
	/**
	 * Metodo para devolver el color de un paquete HOLA.
	 * @return El color del paquete.
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * Metodo para establecer el nombre de un paquete tipo HOLA.
	 * @param nombre El nombre a enviar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Metodo para devolver el nombre de un paquete HOLA.
	 * @return El nombre del paquete.
	 */
	public String getNombre() {
		return nombre;
	}
  
  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }
  
  public String getMensaje() {
    return mensaje;
  }
}
