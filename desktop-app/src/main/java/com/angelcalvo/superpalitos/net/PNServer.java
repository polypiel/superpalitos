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
package com.angelcalvo.superpalitos.net;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import com.angelcalvo.superpalitos.SuperPalitos;

/**
 * Esta Clase representa un servidor del protocolo palitosNet
 * 
 * @author Angel Luis Calvo Ortega
 */
public class PNServer extends Thread {
	private static final int DEFAULT_PORT = 11111;
	private static final int MAX_RETRIES = 3;
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
    	int retries = 0;
    	do {
    		server = createServer(port);
    		if(server == null) {
    			port++;
    		} else {
    			on = true;
    			start();
    		}
    	} while(server == null && retries < MAX_RETRIES);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return on;
  }
  private ServerSocket createServer(int port) throws IOException {
  	ServerSocket s = null;
  	try {
  		s = new ServerSocket(port);
  	} catch( BindException e) { }
  	return s;
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
