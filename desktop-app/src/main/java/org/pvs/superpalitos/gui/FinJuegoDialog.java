/*
 * FinJuegoDialog
 * 
 * Created on 13 de septiembre de 2006, 19:06
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.superpalitos.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.pvs.superpalitos.SuperPalitos;

/**
 * @author Angel Luis Calvo Ortega
 */
public class FinJuegoDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = -1885012284234830355L;
	private static final String FORMAT = "<html><p>Ha_ganado_<b>%1$s</b>_en_%2$tS_segundos<br>"; 
	private static FinJuegoDialog dialog;
	private JLabel texto;
	private int result;
	
	/** Creates new form FinJuegoDialog */
  private FinJuegoDialog(JFrame parent) {
    super(parent, "Fin_del_juego", true);
    setResizable(false);
    initComponents();
  }
  
  public static int showFinJuegoDialog(JFrame parent, String winner, long time) {
  	if(dialog == null) {
  		dialog = new FinJuegoDialog(parent);
  	}
  	dialog.setTexto(winner, time);
  	dialog.setVisible(true);
  	return dialog.getResult();
  }
  
  public int getResult() {
  	return result;
  }
  
  public void setTexto(String ganador, long tiempo) {
  	texto.setText(String.format(FORMAT, ganador, Long.valueOf(tiempo)));
  	pack();
  	setLocationRelativeTo(getParent());
  }
  
  private void initComponents() {
  	JLabel iconLabel = new JLabel(SPFrame.II_SP32);
  	iconLabel.setBorder(new EmptyBorder(8, 8, 8, 8));
  	getContentPane().add(iconLabel, BorderLayout.WEST);
  	texto = new JLabel();
  	//texto.setPreferredSize(new Dimension(100, 25));
  	texto.setBorder(new EmptyBorder(8, 8, 8, 8));
  	
  	getContentPane().add(texto, BorderLayout.CENTER);
  	
  	// Botones
  	JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
  	/*JButton button = new JButton("Continuar", SPFrame.II_PAUSE);
  	button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continuar_ActionPerformed(e);
			}
  	});
  	p.add(button);*/
  	JButton button = new JButton("Continuar", SPFrame.II_PLAY);
  	button.addActionListener(new ActionListener() {
  		@Override
			public void actionPerformed(ActionEvent e) {
				continuar_ActionPerformed(e);
			}
  	});
  	p.add(button);
  	button = new JButton("Terminar", SPFrame.II_STOP);
  	button.addActionListener(new ActionListener() {
  		@Override
			public void actionPerformed(ActionEvent e) {
				terminar_ActionPerformed(e);
			}
  	});
  	p.add(button);
  	getContentPane().add(p, BorderLayout.SOUTH);
  }

	protected void terminar_ActionPerformed(ActionEvent e) {
		result = SuperPalitos.FIN_JUEGO_TERMINAR;
		setVisible(false);
	}

	/*protected void repetir_ActionPerformed(ActionEvent e) {
		result = SuperPalitos.FIN_JUEGO_REPETIR;
		setVisible(false);
	}*/

	protected void continuar_ActionPerformed(ActionEvent e) {
		result = SuperPalitos.FIN_JUEGO_CONTINUAR;
		setVisible(false);
	}
}
