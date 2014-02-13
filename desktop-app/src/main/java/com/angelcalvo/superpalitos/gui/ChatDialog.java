/*
 * ChatDialog
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.superpalitos.gui;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.angelcalvo.superpalitos.SuperPalitos;
import com.angelcalvo.superpalitos.net.ChatComponent;
import com.angelcalvo.superpalitos.net.SPChat;

/**
 * 
 * @author Angel Luis Calvo Ortega
 */
public class ChatDialog extends JDialog {
	private static final long serialVersionUID = 5494849455791943340L;
	private JTabbedPane tabbedPane;
	private SuperPalitos sp;
	
	public ChatDialog(JFrame frame, SuperPalitos sp) {
		super(frame, "PalitosNet_Chat");
		this.sp = sp;
		
		setSize(new Dimension(400, 175));
		
		tabbedPane = new JTabbedPane();
		setContentPane(tabbedPane);
		
		setLocationRelativeTo(frame);
	}
	
	public ChatComponent addChat(SPChat cc) {
		ChatPane p = new ChatPane(cc, sp);
		tabbedPane.addTab("Chat", p);
		return p;
	}
}
