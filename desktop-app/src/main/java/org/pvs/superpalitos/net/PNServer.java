/*
 * PNServer
 * 
 * Created on 2 de Agosto de 2005
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.superpalitos.net;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.pvs.superpalitos.SuperPalitos;

/**
 * Esta Clase representa un servidor del protocolo palitosNet
 * 
 * @author Angel Luis Calvo Ortega
 */
public class PNServer extends Thread {
	private static final int DEFAULT_PORT = 11111;
  private ServerSocket server;
  private boolean on;
  private int port;
  private SuperPalitos sp;
  
  /**
   * Crea un servidor, que escucha por el puerto por defecto
   */
  public PNServer(SuperPalitos sp) {
  	port = DEFAULT_PORT;
  	this.sp = sp;
  }
  
  /**
   *
   */
  public boolean setON() {
    try {
			server = new ServerSocket(port);
	    on = true;
	    start();
	    return true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
  }
  
  /**
   *
   */
  public void setOFF() {
    on = false;
    interrupt();
  }
  
  /**
   * 
   * @param port
   */
  public void setPort(int port) {
  	this.port = port;
  }
  
  /**
   * 
   * @return
   */
  public int getPort() {
  	return port;
  }
  
  @Override
  public void run() {
    try {
      while(on) {
      	Socket sock = server.accept();
      	//System.out.println("[server]: Conexion Recibida");
        new PNClient(sp).connect(sock);       
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}
