/*
 * SPFrame
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.angelcalvo.superpalitos.SuperPalitos;
import com.angelcalvo.superpalitos.net.ChatComponent;
import com.angelcalvo.superpalitos.net.SPChat;


/**
 * Ventana principal de la aplicaci&oacute;n
 * 
 * @author &Aacute;ngel Luis Calvo Ortega
 */
public class SPFrame extends JFrame {
  private static final long serialVersionUID = 3834308445011456822L;
  
  protected static final int WIDTH = 400;
  protected static final int HEIGHT = 300;
  //TODO mapa de iconos
  protected static ImageIcon II_ABOUT, II_ACCEPT, II_BLANK, II_CANCEL, II_CLOSE_16,
    II_CLOSE_24, II_CONNECT, II_DISCONNECT, II_EXIT, II_HELP, II_LICENSE,
    II_NEW, II_OPTIONS, II_PAUSE, II_PNCHAT, II_PNNEW, II_PNSERVER, II_PLAY,
    II_PVS, II_REPEAT, II_SP, II_SP32, II_STOP;
  /*public static final ImageIcon II_ABOUT = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/about.png"));
  public static final ImageIcon II_ACCEPT =new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/apply.png"));
  public static final ImageIcon II_BLANK = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/blank.png"));
  public static final ImageIcon II_CANCEL = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/cancel.png"));
  public static final ImageIcon II_CLOSE_16 = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/close16.png"));
  public static final ImageIcon II_CLOSE_24 = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/close24.png"));
  public static final ImageIcon II_CONNECT = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/connect.png"));
  public static final ImageIcon II_DISCONNECT = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/disconnect.png"));
  public static final ImageIcon II_EXIT = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/exit.png"));
  public static final ImageIcon II_HELP= new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/help.png"));
  public static final ImageIcon II_LICENSE = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/license.png"));
  public static final ImageIcon II_NEW = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/new.png"));
  public static final ImageIcon II_OPTIONS = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/options.png"));
  public static final ImageIcon II_PAUSE = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/pause.png"));
  public static final ImageIcon II_PNCHAT = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/chat.png"));
  public static final ImageIcon II_PNNEW = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/pnnew.png"));
  public static final ImageIcon  II_PNSERVER = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/pnserver.png"));
  public static final ImageIcon  II_PLAY = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/play.png"));
  public static final ImageIcon II_PVS = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/pvs.png"));
  public static final ImageIcon II_REPEAT = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/refresh.png"));
  public static final ImageIcon II_SP = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/sp.png"));
  public static final ImageIcon II_SP32 = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/sp32.png"));
  public static final ImageIcon II_STOP = new ImageIcon(JFrame.class.getResource("/com/angelcalvo/resources/stop.png"));*/
  
  /** Constante para identificar el color azul */
  public static final int AZUL = 0;
  /** Constante para identificar el color negro */
  public static final int NEGRO = 1;
  /** Constante para identificar el color rojo */
  public static final int ROJO = 2;
  /** Constante para identificar el color verde */
  public static final int VERDE = 3;
  
  public static final Color[] COLORS = {new Color(723857), new Color(197379), new Color(15073280), new Color(1951517)};
  //private static final String[] colorsTags = {"Azul", "Negro", "Rojo", "Verde"};
  
  private static final String WELCOME_TAB = "welcome";
  private static final String MATCH_TAB = "match";
  private static final String PREFERENCES_TAB = "preferences";
  private static final String HELP_TAB = "help";
  private static final String ABOUT_TAB = "about";
  private static final String LICENSE_TAB = "license";
  
  private JTabbedPane tabbedPane;
  private JMenuBar menu;
  private JMenu jMArchivo, jMPalitosNet, jMHerramientas, jMAyuda;
  private JMenuItem jMINuevaPart, jMIPartRapida, jMICerrarPes, /*jMIRepetir,*/ jMISalir,
  		jMIConectar, jMINuevo, jMIShowChat, jMIOpciones, jMIAyuda, jMILicencia, jMIAcerca;
  private JTextField status;
  private ConnectedLabel cstatus;
  private PreferencesPane dPreferencias;
  private PsHTMLPane pAyuda, pLicencia;
  private AboutPane pAcerca;
  
  private boolean isPreferencesShowed, isHelpShowed, isLicenseShowed, isAboutShowed;
  private JButton bCerrarTab;
  private int ntabs;
  
  private ChatDialog chatDialog;
  private SuperPalitos sp;
  
  /**
   * Crea la ventana principal
   */
  public SPFrame() {
    super("Super Palitos 3 M3");
    
    loadResources();
    
    setResizable(false);
    setIconImage(II_SP.getImage());
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JPanel p = new JPanel();
    
    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    
    /* Menu */
    initMenu();
    
    /* opciones de tabs */
    /*JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    bCerrarTab = new JButton(II_CLOSE_24);
    bCerrarTab.setBorder(null);
    bCerrarTab.setToolTipText("Cerrar pesta\u00F1a actual");
    bCerrarTab.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cerrarTab_ActionPerformed(e);
      }
    });
    p1.add(bCerrarTab);
    p.add(p1);*/
    
    /* tabs */
    tabbedPane = new JTabbedPane();
    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); 
    
    /* welcome tab */
    JLabel l = new JLabel(new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/title.png")));
    l.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    addTab(l, WELCOME_TAB, "Welcome", II_SP);
    p.add(tabbedPane);
    
    /* status */
    JPanel p2 = new JPanel();
    p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
    
    status = new JTextField();
    status.setEditable(false);
    status.setFocusable(false);
    p2.add(status);
    
    cstatus = new ConnectedLabel();
    p2.add(cstatus);
    p.add(p2);
    
    getContentPane().add(p, BorderLayout.CENTER);
    
    addWindowListener(new WindowAdapter() {
      //public void windowOpened(WindowEvent e) {}
    	@Override
      public void windowClosing(WindowEvent e) {
        cerrar_ActionPerformed();
      }
    });
    
    pack();
    tabbedPane.setPreferredSize(tabbedPane.getSize());
    this.setLocationRelativeTo(null);
  }
  
  /**
   * v 3.0
   * @return
   */
  public TableroPanel createTablero(String title) {
    TableroPanel spp = new TableroPanel(sp);
    addTab(spp, MATCH_TAB, title, II_SP);
    return spp;
  }
  
  /**
   * v 3.0
   * @param id
   */
  public void destroyTablero(long id) {
  	Component[] comps = tabbedPane.getComponents();
  	Component tab = null;
  	/*
  	for(int i = 0; i < comps.length; i++) {
  		if(comps[i] instanceof TableroPanel) {
  			TableroPanel t = (TableroPanel)comps[i];
  			if(t.getId() == id) {
  				tab = t;
  				break;
  			}
  		}
  	}
  	*/
  	// TODO
  	if(tab != null) {
	  	tabbedPane.remove(tab);
	  	
	  	ntabs--;
	    bCerrarTab.setEnabled(ntabs > 0);
	    jMICerrarPes.setEnabled(ntabs > 0);
  	}
  }
  
  private void addTab(JComponent comp, String name, String title, ImageIcon icono) {
    comp.setName(name);
    tabbedPane.addTab(title, icono, comp);
    tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, new SPTabComponent(title, icono));
    tabbedPane.setSelectedComponent(comp);
    
    ntabs++;
    bCerrarTab.setEnabled(ntabs > 0);
    jMICerrarPes.setEnabled(ntabs > 0);
  }
  
  /**
   * Metodo para indicar que una partida a terminado.
   * @param quien El jugador ganador.
   */
  /*public int showFinJuego(String quien, long cuanto) {
    int opcion = JOptionPane.showOptionDialog(this, "<html><h3>" + quien
      + "   WINS!!!</h3><br>En " + cuanto/60000 + ":" + cuanto%60000 + "s" , "GAME OVER", JOptionPane.DEFAULT_OPTION,
      JOptionPane.INFORMATION_MESSAGE, II_SP32, options, options[0]);
    if(opcion == 0) {
      return SuperPalitos.FIN_JUEGO_CONTINUAR;
    }
    if(opcion == 1) {
      return SuperPalitos.FIN_JUEGO_REPETIR;
    }
    return SuperPalitos.FIN_JUEGO_TERMINAR;
  }*/
  
  /**
   * M&eacute;todo que termina una partida bruscamente.
   */
  public void showAbortPartidaMsg() {
    JOptionPane.showMessageDialog(this, "Partida_Abortada", "Fin_de_partida", JOptionPane.INFORMATION_MESSAGE, II_SP32);
  }
  
  public boolean showConfirmarTerminarJuegoMsg() {
    return JOptionPane.showConfirmDialog(this,
		    "¿Estas_seguro_de_que_quieres_terminar_la_partida_actual?",
		    "Nueva_Partida", JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE, II_SP32) != 0;
  }
  
  public void showErrMsg(String msg) {
  	JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
  }
  
  // Actions ------------------------------------------------------------------
  private void nuevaPartida_ActionPerformed(ActionEvent e) {
    NewGameDialog dialog = new NewGameDialog(this);
    dialog.setVisible(true);
    if(dialog.isAccepted()) {
      sp.nuevaPartida(dialog.getGameMode(), null, true);
    }
  }
  
  private void partidaRapida_ActionPerformed(ActionEvent e) {
    sp.nuevaPartida();
  }
  
  private void cerrar_ActionPerformed() {
    int cerrar = JOptionPane.showConfirmDialog(this,
				"¿Realmente_quieres_cerrar_SuperPalitos?", "Salir",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				II_SP32);
		if(cerrar == JOptionPane.OK_OPTION) {
      sp.salir();
			System.exit(0);
    }
  }
  
  private void salir_ActionPerformed() {
    sp.salir();
  	System.exit(0);
  }
  
  private void conectar_ActionPerformed(ActionEvent e) {
    sp.palitosNet();
  }
  
  private void jugarCon_ActionPerformed(ActionEvent e) {
    //ConectarDialog.iniciar(this);
    NewConnectionDialog dialog = new NewConnectionDialog(this, true, sp);
    dialog.setVisible(true);
  }
  
  private void showChat_ActionPerformed(ActionEvent e) {
  	showChat(false);
  }
  
  private void prefs_ActionPerformed(ActionEvent e) {
    showPreferences();
  }
  
  private void ayuda_ActionPerformed(ActionEvent e) {
    showHelp();
  }
  
  private void acerca_ActionPerformed(ActionEvent e) {
    showAbout();
  }
  
  private void licencia_ActionPerformed(ActionEvent e) {
    showLicense();
  }
  
  private void cerrarTab_ActionPerformed(ActionEvent e) {
    String name = tabbedPane.getSelectedComponent().getName();
    if(name.equals(ABOUT_TAB)) {
      isAboutShowed = false;
    } else if(name.equals(HELP_TAB)) {
      isHelpShowed = false;
    } else if(name.equals(LICENSE_TAB)) {
      isLicenseShowed = false;
    } else if(name.equals(PREFERENCES_TAB)) {
      isPreferencesShowed = false;
    } else if(name.equals(MATCH_TAB)) {
    	// TODO
      sp.cerrarPartida(0);
    }
    tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
    
    ntabs--;
    bCerrarTab.setEnabled(ntabs > 0);
    jMICerrarPes.setEnabled(ntabs > 0);
  }
  
  /*private void repetir_ActionPerformed(ActionEvent e) {
  	SuperPalitos.getInstance().repetirCmd(getTableroId());
  }*/
  
  // PalitosNet ---------------------------------------------------------------
  /**
   * M&eacute;todo para indicar una petici&oacute;n de conexi&oacute;n.
   * @param nick El jugador que la pide.
   * @param ip La direccion del contrario.
   * @return La aceptaci&oacute;n de la partida o no.
   */
  public boolean showNewConnMsg(String nick, String ip, Color c) {
  	String mensaje = "<html>¿Quieres_jugar_con_" /* + "<font-color=\"#"
				+ Integer.toHexString(c.getRGB() & 0x00FFFFFF)*/ + "\"><b>" + nick
				+ "</b></font>_[" + ip + "]?";
    return JOptionPane.showConfirmDialog(this, mensaje, "Nueva_Partida_PalitosNet",
      JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, II_SP32) == 0;
  }
  
  /**
   * Metodo para indicar un error en un intento de conexi&oacute;n.
   * @param msg El mensaje de error.
   */
  public void showConnErrorMsg(String msg) {
    //TODO
    //ConectarDialog.error(msg);
  }
  
  /**
   * Metodo para indicar que el intento de conexi&oacute;n ha tenido exito.
   */
  public void endsConectar() {
    //TODO
    //ConectarDialog.fin();
  }
  
  /**
   * Indica que se ha terminado la conexi&oacute;n.
   */
  /*public void desconectar() {
    status.setText("Desconectado");
    jMINuevo.setText("Jugar Con...");
  }*/
  
  public ChatComponent addChat(SPChat spChat) {
  	showChat(true);
  	return chatDialog.addChat(spChat);
  }
  
  /**
   * Metodo para establecer el texto de la barra de status
   * @param s El jMINuevaPart texto.
   */
  public void setStatus(String s) {
    status.setText(s);
  }
  
  
  public void setServer(boolean on) {
    jMIConectar.setText(on?"Apagar":"Iniciar");
    jMINuevo.setEnabled(on);
    jMIShowChat.setEnabled(on);
    cstatus.setConnected(on);
  }
  
  //---------------------------------------------------------------------------
  /**
   * @param show Si es cierto muestra el chat, si es falso conmuta la
   * visibilidad
   */
  private void showChat(boolean show) {
  	if(chatDialog == null) {
  		chatDialog = new ChatDialog(this, sp);
  	}
  	chatDialog.setVisible(show?true:!chatDialog.isVisible());
  }
  
  public void showPreferences() {
    if(!isPreferencesShowed) {
      if(dPreferencias == null) {
        dPreferencias = new PreferencesPane(/*COLORS, */sp);
      }
      addTab(dPreferencias, PREFERENCES_TAB, "Preferencias", II_OPTIONS);
      isPreferencesShowed = true;
    }
  }
  
  public void showHelp() {
    if(!isHelpShowed) {
      if(pAyuda == null) {
        pAyuda = new PsHTMLPane("sp.html");
      }
      addTab(pAyuda, HELP_TAB, "Ayuda", II_HELP);
      isHelpShowed = true;
    }
  }
  
  public void showLicense() {
    if(!isLicenseShowed) {
      if(pLicencia == null) {
        pLicencia = new PsHTMLPane("gpl.html");
      }
      addTab(pLicencia, LICENSE_TAB, "Licencia", II_LICENSE);
      isLicenseShowed = true;
    }
  }
  
  public void showAbout() {
    if(!isAboutShowed) {
      if(pAcerca == null) {
        pAcerca = new AboutPane();
      }
      addTab(pAcerca, ABOUT_TAB, "Acerca_de_...", II_ABOUT);
      isAboutShowed = true;
    }
  }
  
  private void loadResources() {
    // Iconos
    II_ABOUT = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/about.png"));
    II_ACCEPT =new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/apply.png"));
    II_BLANK = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/blank.png"));
    II_CANCEL = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/cancel.png"));
    II_CLOSE_16 = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/close16.png"));
    II_CLOSE_24 = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/close24.png"));
    II_CONNECT = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/connect.png"));
    II_DISCONNECT = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/disconnect.png"));
    II_EXIT = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/exit.png"));
    II_HELP= new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/help.png"));
    II_LICENSE = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/license.png"));
    II_NEW = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/new.png"));
    II_OPTIONS = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/options.png"));
//    II_PAUSE = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/pause.png"));
    II_PNCHAT = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/chat.png"));
    II_PNNEW = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/pnnew.png"));
    II_PNSERVER = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/pnserver.png"));
    II_PLAY = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/play.png"));
    II_PVS = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/pvs.png"));
    II_REPEAT = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/refresh.png"));
    II_SP = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/sp.png"));
    II_SP32 = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/sp32.png"));
    II_STOP = new ImageIcon(getClass().getResource("/com/angelcalvo/superpalitos/gui/stop.png"));
  }
  
  private void initMenu() {
    /* Menu */
    menu = new JMenuBar();
    
    // Menu Archivo
    jMArchivo = new JMenu("Archivo");
    jMINuevaPart = new JMenuItem("Nueva_partida...");
    jMINuevaPart.setIcon(II_NEW);
    jMINuevaPart.setToolTipText("Crea_una_nueva_partida");
    jMINuevaPart.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        nuevaPartida_ActionPerformed(e);
      }
    });
    jMINuevaPart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
    jMArchivo.add(jMINuevaPart);
    
    jMIPartRapida = new JMenuItem("Partida_rapida");
    jMIPartRapida.setIcon(II_NEW);
    jMIPartRapida.setToolTipText("Empieza_una_partida_inmediatamente");
    jMIPartRapida.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        partidaRapida_ActionPerformed(e);
      }
    });
    jMIPartRapida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
    jMArchivo.add(jMIPartRapida);
    jMArchivo.addSeparator();
    
    jMICerrarPes = new JMenuItem("Cerrar_pestaña");
    jMICerrarPes.setIcon(II_CLOSE_16);
    jMICerrarPes.setToolTipText("Cierra_la_pestaña_y/o_termina_la_partida");
    jMICerrarPes.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        cerrarTab_ActionPerformed(e);
      }
    });
    jMICerrarPes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_MASK));
    jMArchivo.add(jMICerrarPes);
    
    /*jMIRepetir = new JMenuItem("Repetir");
    jMIRepetir.setIcon(II_REPEAT);
    jMIRepetir.setToolTipText("Crea un juego nuevo");
    jMIRepetir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        repetir_ActionPerformed(e);
      }
    });
    jMIRepetir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0, false));
    jMArchivo.add(jMIRepetir);*/
    
    jMArchivo.addSeparator();
    jMISalir = new JMenuItem("Salir");
    jMISalir.setIcon(II_EXIT);
    jMISalir.setToolTipText("Termina_el_programa");
    jMISalir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        salir_ActionPerformed();
      }
    });
    jMArchivo.add(jMISalir);
    menu.add(jMArchivo);
    
    // Menu PalitosNet
    jMPalitosNet = new JMenu("PalitosNet");
    jMIConectar = new JMenuItem("Iniciar");
    jMIConectar.setIcon(II_PNSERVER);
    jMIConectar.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        conectar_ActionPerformed(e);
      }
    });
    jMPalitosNet.add(jMIConectar);
    //
    jMINuevo = new JMenuItem("Jugar_Con...");
    jMINuevo.setIcon(II_PNNEW);
    jMINuevo.setEnabled(false);
    jMINuevo.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        jugarCon_ActionPerformed(e);
      }
    });
    jMPalitosNet.add(jMINuevo);
    //
    jMIShowChat = new JMenuItem("Mostrar/Esconder_Chat");
    jMIShowChat.setIcon(II_PNCHAT);
    jMIShowChat.setEnabled(false);
    jMIShowChat.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        showChat_ActionPerformed(e);
      }
    });
    jMPalitosNet.add(jMIShowChat);
    //
    menu.add(jMPalitosNet);
    //
    jMHerramientas = new JMenu("Opciones");
    jMIOpciones = new JMenuItem("Preferencias...");
    jMIOpciones.setIcon(II_OPTIONS);
    jMIOpciones.setToolTipText("Muestra_el_menú_opciones");
    jMIOpciones.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        prefs_ActionPerformed(e);
      }
    });
    jMHerramientas.add(jMIOpciones);
    menu.add(jMHerramientas);
    
    // Menu Ayuda
    jMAyuda = new JMenu("Ayuda");
    jMIAyuda = new JMenuItem("Temas_de_Ayuda");
    jMIAyuda.setIcon(II_HELP);
    jMIAyuda.setToolTipText("Muestra_la_ayuda");
    jMIAyuda.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        ayuda_ActionPerformed(e);
      }
    });
    jMIAyuda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
    jMAyuda.add(jMIAyuda);
    //licencia
    jMILicencia = new JMenuItem("Licencia");
    jMILicencia.setIcon(II_LICENSE);
    jMILicencia.setToolTipText("Muestra_la_licencia_de_SuperPalitos");
    jMILicencia.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        licencia_ActionPerformed(e);
      }
    });
    jMAyuda.add(jMILicencia);
    //acerca
    jMIAcerca = new JMenuItem("Acerca_de...");
    jMIAcerca.setIcon(II_ABOUT);
    jMIAcerca.setToolTipText("Muestra_información_sobre_SuperPalitos");
    jMIAcerca.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        acerca_ActionPerformed(e);
      }
    });
    jMAyuda.add(jMIAcerca);
    menu.add(jMAyuda);

    JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    p1.setOpaque(false);
    bCerrarTab = new JButton(II_CLOSE_24);
    bCerrarTab.setBorder(null);
    bCerrarTab.setToolTipText("Cerrar_pestaña_actual");
    bCerrarTab.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        cerrarTab_ActionPerformed(e);
      }
    });
    p1.add(bCerrarTab);
    menu.add(p1);
    
    setJMenuBar(menu);
  }
  
  public void changeLaF() {
    SwingUtilities.updateComponentTreeUI(this);
    bCerrarTab.setBorder(null);
    pack();
  }

	public SuperPalitos getSp() {
		return sp;
	}

	public void setSp(SuperPalitos sp) {
		this.sp = sp;
	}
}
