package org.pvs.superpalitos.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SPTabComponent2 extends JPanel {
	private static final long serialVersionUID = -4371850142412659701L;
	private JButton closeButton;
	
	public SPTabComponent2(String title, ImageIcon icon) {
		setOpaque(false);
		add(new JLabel(title, icon, JLabel.LEFT));
		closeButton = new JButton(SPFrame.II_CLOSE_16);
		closeButton.setBorder(null);
		//closeButton.setContentAreaFilled(false);
		add(closeButton);
	}
}
