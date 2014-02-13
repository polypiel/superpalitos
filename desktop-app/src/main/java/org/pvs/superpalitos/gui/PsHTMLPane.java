/*
 * PsHTMLPane
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.superpalitos.gui;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

/**
 * @author Angel Luis Calvo Ortega
 */
public class PsHTMLPane extends JScrollPane {
  private static final long serialVersionUID = -7033635729402179291L;
  private static final String RUTA = "/org/pvs/superpalitos/gui/";
  
  public PsHTMLPane(String path) {
    JEditorPane text = new JEditorPane("text/html", readLic(path));
    text.setEditable(false);
    setPreferredSize(new Dimension(SPFrame.WIDTH, SPFrame.HEIGHT));
    setViewportView(text);
    text.setCaretPosition(0);
    //text.scrollRectToVisible(new Rectangle(new Point(20, 100), new Dimension(10, 10)));
  }
  
  private String readLic(String path) {
    String s = null;
    InputStreamReader isr = null;
    BufferedReader fin = null;
    
    try {
    	isr = new InputStreamReader(getClass().getResourceAsStream((RUTA + path)));
      fin = new BufferedReader(isr);
      StringBuffer buf = new StringBuffer();
      while((s = fin.readLine()) != null) {
        buf.append(s);
        buf.append("\n");
      }
      s = buf.toString();
    } catch(IOException e) {
      s = "Error:_" + e;
    } finally {
    	if(fin != null) {
    		try {
					fin.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
    	}
    }
    return s;
  }
}
