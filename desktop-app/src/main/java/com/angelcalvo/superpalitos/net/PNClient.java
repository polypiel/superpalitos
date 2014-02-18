/*
 * PNClient
 * 
 * Created on 2 de Agosto de 2005
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */

package com.angelcalvo.superpalitos.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.angelcalvo.palitos.Gaps;
import com.angelcalvo.palitos.Move;
import com.angelcalvo.palitos.Player;
import com.angelcalvo.palitos.Sticks;
import com.angelcalvo.superpalitos.SuperPalitos;

/**
 * Esta clase representa un cliente del protocolo palitosNet
 *
 *  @author Angel Luis Calvo Ortega
 */
public class PNClient extends Thread implements Player, SPChat {
	private final static int REQUEST_CONNECTION = 0;
	private final static int CONNECTED = 1;
	private final static int PLAYING = 2;
	private final static int CLOSE = 3;

	private int state;
	private Socket sock;
	private DataInputStream in;
	private DataOutputStream out;
	private ChatComponent chat;
	private String name;
	private int color;
	private Move jugada;
	private SuperPalitos sp;
	
	public PNClient(SuperPalitos sp) {
		this.sp = sp;
	}
	
	/**
	 * Iniciar una conexion
	 * @param dir
	 * @param port
	 */
	public void connect(String dir, int port) {
		try {
			sock = new Socket(dir, port);
			in = new DataInputStream(sock.getInputStream());
			out = new DataOutputStream(sock.getOutputStream());

			Paquete paq = new Paquete(Paquete.HOLA);
			paq.setNombre(sp.getJ1Name());
			paq.setColor(sp.getJ1Color());
			paq.send(out);
			//timeout (en el paq?) pa que? pa na
			state = REQUEST_CONNECTION;
			start();
		} catch(IOException e) {
			sp.errConn(e.toString());
		}
	}

	/**
	 * Aceptar una conexion
	 */
	public void connect(Socket sock) {
		this.sock = sock;
		try {
			in = new DataInputStream(sock.getInputStream());
			out = new DataOutputStream(sock.getOutputStream());

			Paquete paq = Paquete.receive(in);
			if(paq.getTipo() == Paquete.HOLA) {
				name = paq.getNombre();
				color = paq.getColor();
				if(sp.aceptarConexion(this, sock.getInetAddress().getCanonicalHostName())) {
					paq = new Paquete(Paquete.HOLA);
					paq.setColor(sp.getJ1Color());
					paq.setNombre(sp.getJ1Name());
					paq.send(out);
					//turno = true;
					//SuperPalitos.getInstance().nuevaPartida(SuperPalitos.JUEGO_MJ, this);
					state = PLAYING;
					start();
				} else {
					paq = new Paquete(Paquete.ADIOS);
					paq.send(out);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnet() {
		new Paquete(Paquete.ADIOS).send(out);
		state = CLOSE;
	}
	
	@Override
	public void run() {
		while(state < CLOSE) {
			Paquete paq = Paquete.receive(in);
			int tipo = paq.getTipo();

			if(tipo == Paquete.HOLA) {					// Paq HOLA
				if(state == REQUEST_CONNECTION) {
					name = paq.getNombre();
					color = paq.getColor();
					sp.confirmarConexion(this);
					state = PLAYING;
				}
				
			} else if(tipo == Paquete.ADIOS) {	// Paq ADIOS
				if(state == REQUEST_CONNECTION) {
					sp.errConn("El_extremo_ha_rechazado_la_partida");
			  } else {
					sp.desconectado(this);
				}
				state = CLOSE;
				
			} else if(tipo == Paquete.JUGADA) {	// Paq JUGADA
				if(state == PLAYING) {
					jugada = paq.getJugada();
					synchronized(this) {
						notify();
					}
				}
				
			} else if(tipo == Paquete.CHAT) {		// Paq CHAT	
				if(state >= CONNECTED) {
					if(chat != null) {
						chat.showMessage(name, color, paq.getMensaje());
					}
				}
			}
		}
	}

	/**
	 * Método para establecer el chat.
	 * 
	 * @param chat
	 * Un ChatComponent
	 */
	public void setChat(ChatComponent chat) {
		this.chat = chat;
	}

	
	// Métodos de Jugador -------------------------------------------------------
	
	/* Metodo de la interfaz SPChat */
	@Override
	public void writeLine(String line) {
		if(state >= CONNECTED) {
			Paquete paq = new Paquete(Paquete.CHAT);
			paq.setMensaje(line);
			paq.send(out);
		}
	}

	/* Metodos de la interfaz Jugador */
	@Override
	public synchronized Move move() {
		try {
			wait();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		return jugada;
	}

	@Override
	public void update(Move j, Sticks p, Gaps h) {
		if(j != null) { // para palitosNet
			Paquete paq = new Paquete(Paquete.JUGADA);
			paq.setJugada(j);
			paq.send(out);
		}
	}

	@Override
	public int getColor() {
		return color;
	}

	/*
	public void setName(String n) {}

	
	public String getName() {
		return name;
	}*/

	@Override
	public void finish() {
		state = CLOSE;
		Paquete p = new Paquete(Paquete.ADIOS);
		p.send(out);
		interrupt();
	}
}
