/*
 * AboutPane
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.superpalitos.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Muestra la ventana de creditos.
 *
 * @author Angel Luis Calvo Ortega
 */
public class AboutPane extends JPanel {
	private static final long serialVersionUID = 3977019539542128438L;
	private static final String TEXT = "<html><font_color=\"#FFFF00\"_size=\"6\"_face=\"courier\"><center>Super_Palitos_v3.0_M3<p>Pollo_Verde_Software_2006</center></font><font_size=\"5\"_color=\"#00FF00\"_face=\"sans\"><p><br>Todo_hecho_por:<br>&Aacute;ngel_Luis_Calvo_Ortega</font>";

	public AboutPane() {
		super(new GridLayout(2, 1, 10, 10));

		// jPane.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		setBackground(Color.BLACK);
		JLabel img = new JLabel(SPFrame.II_PVS);
		add(img);
		JLabel cred = new JLabel();
		cred.setHorizontalAlignment(JLabel.CENTER);
		cred.setText(TEXT);
		cred.setFont(img.getFont());
		cred.setBackground(img.getBackground());
		// cred.setEditable(false);
		add(cred);
	}
}
