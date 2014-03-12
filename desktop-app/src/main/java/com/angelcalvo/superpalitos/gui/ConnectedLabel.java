/*
 * ConnectedLabel
 * 
 * Created on 13 de febrero de 2006, 12:52
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.superpalitos.gui;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Angel Luis Calvo Ortega
 */
public class ConnectedLabel extends JLabel {
	private static final long serialVersionUID = 8682498738153835670L;
	private ImageIcon connectImg, disconnectImg;
	
	/** Creates a new instance of ConnectedLabel */
  public ConnectedLabel(ImageIcon connectImg, ImageIcon disconnectImg) {
  	this.connectImg = connectImg;
  	this.disconnectImg = disconnectImg;
    setConnected(false);
  }
  
  public void setConnected(boolean connected) {
    if(connected) {
      setIcon(connectImg);
      setToolTipText("Conectado");
    } else {
      setIcon(disconnectImg);
      setToolTipText("Desconectado");
    }
  }
}
