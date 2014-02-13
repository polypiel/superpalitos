/*
 * ChatPane
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */

package org.pvs.superpalitos.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.pvs.palitos.Partida;
import org.pvs.superpalitos.SuperPalitos;
import org.pvs.superpalitos.net.ChatComponent;
import org.pvs.superpalitos.net.SPChat;

/**
 * Componente que representa un chat.
 * 
 * @author &Aacute;ngel Luis Calvo Ortega
 */
public class ChatPane extends JPanel implements ChatComponent {
	private static final long serialVersionUID = 4049640083550188085L;
  private static final String INICIO = java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("<html><body>");
  private static final String FIN = java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("</html><body>");
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
    
    enviar = new JButton(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("Enviar"));
    enviar.setFocusCycleRoot(true);
    //enviar.setCursor(new Curor(Cursor.DEFAULT_CURSOR));
    //enviar.setDefaultCapable(true);
    enviar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        enviar();
      }
    });
    pane.add(enviar, BorderLayout.EAST);
    add(pane, BorderLayout.SOUTH);
    
		texto = new StringBuffer();
    
		salida = new JEditorPane();
    salida.setContentType(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("text/html"));
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
      entrada.setText(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString(""));
    }
  }

  public void showMessage(String nick, int c, String msg) {
    texto.append(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("<font_face=arial_color=#"));
    texto.append(color2hex(c));
    texto.append(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("><b>"));
    texto.append(nick);
    texto.append(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString(":_</b>"));
    texto.append(msg);
    texto.append(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("<br>"));
		salida.setText(INICIO + texto.toString() + FIN);
  }
  
  private String color2hex(int color) {
  	if(color == Partida.BLUE_COLOR) {
  		return "0000FF";
  	}
  	if(color == Partida.BLACK_COLOR) {
  		return "000000";
  	}
  	if(color == Partida.RED_COLOR) {
  		return "FF0000";
  	}
  	return "00FF00";
  }
  /*
  private String dec2hex(Color color) {
  	return Integer.toHexString(color.getRGB() & 0x00FFFFFF);
  }*/
}
