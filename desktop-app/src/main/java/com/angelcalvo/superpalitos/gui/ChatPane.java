/*
 * ChatPane
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */

package com.angelcalvo.superpalitos.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.angelcalvo.superpalitos.SuperPalitos;
import com.angelcalvo.superpalitos.net.ChatComponent;
import com.angelcalvo.superpalitos.net.SPChat;

/**
 * Componente que representa un chat.
 * 
 * @author &Aacute;ngel Luis Calvo Ortega
 */
public class ChatPane extends JPanel implements ChatComponent {
	private static final long serialVersionUID = 4049640083550188085L;
  private static final String INICIO = "<html><body>";
  private static final String FIN = "</html><body>";
	//private static Font fuente = new Font("Arial", Font.PLAIN, 12);
	private JTextField entrada;
	private JEditorPane salida;
  private StringBuffer texto;
  private JScrollPane scrollPane;
  private JButton enviar;
  private transient SPChat chat;
  private SuperPalitos sp;
  
  /**
   * Crea la GUI del Chat
   */
  public ChatPane(SPChat chat, SuperPalitos sp) {
    super(new BorderLayout());
    this.chat = chat;
    this.sp = sp;
    
    //setBorder(new TitledBorder(new EtchedBorder(), " Chat "));
    //setBackground(Color.WHITE);
    
    JPanel pane = new JPanel(new BorderLayout());
    entrada = new JTextField();
		//entrada.setFont(fuente);
    Dimension d1 = entrada.getPreferredSize();
    entrada.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					enviar();            
				}
			}
		});
    pane.add(entrada, BorderLayout.CENTER);
    
    enviar = new JButton("Enviar");
    enviar.setFocusCycleRoot(true);
    //enviar.setCursor(new Curor(Cursor.DEFAULT_CURSOR));
    //enviar.setDefaultCapable(true);
    enviar.addActionListener(e -> { enviar(); });
    pane.add(enviar, BorderLayout.EAST);
    add(pane, BorderLayout.SOUTH);
    
		texto = new StringBuffer();
    
		salida = new JEditorPane();
    salida.setContentType("text/html");
    //salida.setText("<html><body><b>hola</b></body></hmtl>");
    salida.setEditable(false);
    //salida.setVerticalAlignment(JLabel.TOP);
		//salida.setBackground(Color.WHITE);
    salida.setPreferredSize(new Dimension(d1.width, 64));
		//salida.setFont(fuente);
    //salida.setRows(4);

    scrollPane = new JScrollPane(salida);
    add(scrollPane, BorderLayout.CENTER);
  }

  protected void enviar() {
    String s = entrada.getText();
    if(s.length() > 0) {
      if(chat != null )  {
        chat.writeLine(s);
      }
      showMessage(sp.getJ1Name(), sp.getJ1Color(), s);
      entrada.setText("");
    }
  }

  public void showMessage(String nick, Color c, String msg) {
    texto.append("<font face=\"arial\" color=\"#");
    texto.append(color2hex(c));
    texto.append("\"><b>");
    texto.append(nick);
    texto.append(": </b>");
    texto.append(msg);
    texto.append("<br>");
		salida.setText(INICIO + texto.toString() + FIN);
  }
  
  private String color2hex(Color color) {
    String hexColour = Integer.toHexString(color.getRGB() & 0xffffff);
    if (hexColour.length() < 6) {
      hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
    }
    return "#" + hexColour;
  }
}
