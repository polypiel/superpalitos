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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.angelcalvo.superpalitos.ConfManager;
import com.angelcalvo.superpalitos.ResourceManager;
import com.angelcalvo.superpalitos.SuperPalitos;
import com.angelcalvo.superpalitos.net.ChatComponent;
import com.angelcalvo.superpalitos.net.SPChat;


/**
 * Main window
 * 
 * @author Angel Calvo
 */
public class SPFrame extends JFrame {
  private static final long serialVersionUID = 3834308445011456822L;
  
  protected static final int WIDTH = 400;
  protected static final int HEIGHT = 300;

  /** Constante para identificar el color azul */
  public static final int AZUL = 0;
  /** Constante para identificar el color negro */
  public static final int NEGRO = 1;
  /** Constante para identificar el color rojo */
  public static final int ROJO = 2;
  /** Constante para identificar el color verde */
  public static final int VERDE = 3;
  public static final Color[] COLORS = {new Color(723857), new Color(197379), new Color(15073280), new Color(1951517)};
  
  // Tab ids
  private static final String WELCOME_TAB = "welcome";
  private static final String MATCH_TAB = "match";
  private static final String HELP_TAB = "help";
  private static final String ABOUT_TAB = "about";
  
  private JTabbedPane tabbedPane;
  private JMenuItem jMICerrarPes, jMIConectar, jMINuevo, jMIShowChat;
  private JTextField status;
  private ConnectedLabel cstatus;
  //private PreferencesPane dPreferencias;
  private PsHTMLPane pAyuda;
  private AboutPane pAcerca;
  
  private boolean isHelpShowed, isAboutShowed;
  private JButton bCerrarTab;
  private int ntabs;
  
  private ChatDialog chatDialog;
  private SuperPalitos sp;
  private ResourceManager resourceManager;
  private ConfManager confManager;
  
  /**
   * Crea la ventana principal
   */
  public SPFrame() {
    super("Super Palitos");
  }


	@SuppressWarnings("unused")
	private void init() {
    setResizable(false);
    setIconImage(((ImageIcon)resourceManager.getResource(ResourceManager.II_SP)).getImage());
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    
    JPanel p = new JPanel();
    
    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    
    /* Menu */
    initMenu();
    
    /* tabs */
    tabbedPane = new JTabbedPane();
    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); 
    
    /* welcome tab */
    JLabel l = new JLabel((ImageIcon)resourceManager.getResource(ResourceManager.II_TITLE));
    l.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    addTab(l, WELCOME_TAB, "Welcome", (ImageIcon)resourceManager.getResource(ResourceManager.II_SP));
    p.add(tabbedPane);
    
    /* status */
    JPanel p2 = new JPanel();
    p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
    
    status = new JTextField();
    status.setEditable(false);
    status.setFocusable(false);
    p2.add(status);
    
		cstatus = new ConnectedLabel((ImageIcon) resourceManager.getResource(ResourceManager.II_CONNECT),
				(ImageIcon) resourceManager.getResource(ResourceManager.II_DISCONNECT));
    p2.add(cstatus);
    p.add(p2);
    
    getContentPane().add(p, BorderLayout.CENTER);
    
    addWindowListener(new WindowAdapter() {
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
   
  public TableroPanel createTablero(String title, Integer tab) {
    TableroPanel spp = new TableroPanel(sp, confManager);
    if(tab == null) {
    	addTab(spp, MATCH_TAB, title, (ImageIcon)resourceManager.getResource(ResourceManager.II_SP));
    } else {
    	tabbedPane.setComponentAt(tab, spp);
    	tabbedPane.setTitleAt(tab, title);
    }
    return spp;
  }
  
  private int addTab(JComponent comp, String name, String title, ImageIcon icono) {
    comp.setName(name);
    tabbedPane.addTab(title, icono, comp);
    int tabNumber = tabbedPane.getTabCount() - 1;
    tabbedPane.setSelectedComponent(comp);
    
    ntabs++;
    bCerrarTab.setEnabled(ntabs > 0);
    jMICerrarPes.setEnabled(ntabs > 0);
    
    return tabNumber;
  }
  private void closeTab(int nTab) {
    String name = tabbedPane.getSelectedComponent().getName();
    if(ABOUT_TAB.equals(name)) {
      isAboutShowed = false;
    } else if(HELP_TAB.equals(name)) {
      isHelpShowed = false;
    } else if(MATCH_TAB.equals(name)) {
    	Component tab = tabbedPane.getSelectedComponent();
    	if(tab instanceof TableroPanel) {
    		sp.cerrarPartida((TableroPanel) tab);
    	}
    }
    tabbedPane.removeTabAt(nTab);
    
    ntabs--;
    bCerrarTab.setEnabled(ntabs > 0);
    jMICerrarPes.setEnabled(ntabs > 0);
  }
  
  /**
   * M&eacute;todo que termina una partida bruscamente.
   */
  public void showAbortPartidaMsg() {
    JOptionPane.showMessageDialog(this, "Partida Abortada", "Fin de la partida", JOptionPane.INFORMATION_MESSAGE, (ImageIcon)resourceManager.getResource(ResourceManager.II_SP32));
  }
  
  public boolean showConfirmarTerminarJuegoMsg() {
    return JOptionPane.showConfirmDialog(this,
		    "¿Estas seguro de que quieres terminar la partida actual?",
		    "Nueva_Partida", JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE, (ImageIcon)resourceManager.getResource(ResourceManager.II_SP32)) != 0;
  }
  
  public void showErrMsg(String msg) {
  	JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
  }
  
  // Actions ------------------------------------------------------------------
  private void nuevaPartida_ActionPerformed(ActionEvent e) {
		NewGameDialog gameDialog = new NewGameDialog(
				(ImageIcon) resourceManager.getResource(ResourceManager.II_ACCEPT),
				(ImageIcon) resourceManager.getResource(ResourceManager.II_CANCEL));
		final int nTab = addTab(gameDialog, MATCH_TAB, "Super Palitos",
				(ImageIcon) resourceManager.getResource(ResourceManager.II_SP));
    
    gameDialog.addDialogListner(new NewGameDialog.DialogListener() {
			@Override public void cancelled() {
				closeTab(nTab);
			}
			@Override	public void accepted(int mode) {
				sp.nuevaPartida(mode, null, true, nTab);
			}
		});
  }
  
  private void partidaRapida_ActionPerformed(ActionEvent e) {
    sp.nuevaPartida();
  }
  
  private void cerrar_ActionPerformed() {
    int cerrar = JOptionPane.showConfirmDialog(this,
				"¿Realmente_quieres_cerrar_SuperPalitos?", "Salir",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				(ImageIcon)resourceManager.getResource(ResourceManager.II_SP32));
		if(cerrar == JOptionPane.OK_OPTION) {
			System.exit(0);
    }
  }
  
  private void salir_ActionPerformed() {
  	System.exit(0);
  }
  
  private void conectar_ActionPerformed(ActionEvent e) {
    sp.palitosNet();
  }
  
  private void jugarCon_ActionPerformed(ActionEvent e) {
    //ConectarDialog.iniciar(this);
		NewConnectionDialog dialog = new NewConnectionDialog(this, sp,
				(ImageIcon) resourceManager.getResource(ResourceManager.II_ACCEPT),
				(ImageIcon) resourceManager.getResource(ResourceManager.II_CANCEL));
    dialog.setVisible(true);
  }
  
  private void showChat_ActionPerformed(ActionEvent e) {
  	showChat(false);
  }
  
  private void ayuda_ActionPerformed(ActionEvent e) {
    showHelp();
  }
  
  private void acerca_ActionPerformed(ActionEvent e) {
    showAbout();
  }

  private void cerrarTab_ActionPerformed(ActionEvent e) {
  	closeTab(tabbedPane.getSelectedIndex());
  }

  // PalitosNet ---------------------------------------------------------------
  /**
   * M&eacute;todo para indicar una petici&oacute;n de conexi&oacute;n.
   * @param nick El jugador que la pide.
   * @param ip La direccion del contrario.
   * @return La aceptaci&oacute;n de la partida o no.
   */
  public boolean showNewConnMsg(String nick, String ip, Color c) {
  	String mensaje = "<html>¿Quieres jugar con " /* + "<font-color=\"#"
				+ Integer.toHexString(c.getRGB() & 0x00FFFFFF)*/ + "\"><b>" + nick
				+ "</b></font>_[" + ip + "]?";
    return JOptionPane.showConfirmDialog(this, mensaje, "Nueva Partida PalitosNet",
      JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, (ImageIcon)resourceManager.getResource(ResourceManager.II_SP32)) == 0;
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
  
  /*
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
  
  protected void showHelp() {
    if(!isHelpShowed) {
      if(pAyuda == null) {
        pAyuda = new PsHTMLPane("sp.html");
      }
      addTab(pAyuda, HELP_TAB, "Ayuda", (ImageIcon)resourceManager.getResource(ResourceManager.II_HELP));
      isHelpShowed = true;
    }
  }

  protected void showAbout() {
    if(!isAboutShowed) {
      if(pAcerca == null) {
        pAcerca = new AboutPane((ImageIcon)resourceManager.getResource(ResourceManager.II_PVS));
      }
      addTab(pAcerca, ABOUT_TAB, "Acerca_de_...", (ImageIcon)resourceManager.getResource(ResourceManager.II_ABOUT));
      isAboutShowed = true;
    }
  }
 
  private void initMenu() {
    /* Menu */
    JMenuBar menu = new JMenuBar();
    
    // Menu Archivo
    JMenu jMArchivo = new JMenu("Archivo");
    JMenuItem jMINuevaPart = new JMenuItem("Nueva_partida...");
    jMINuevaPart.setIcon((ImageIcon)resourceManager.getResource(ResourceManager.II_NEW));
    jMINuevaPart.setToolTipText("Crea_una_nueva_partida");
    jMINuevaPart.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        nuevaPartida_ActionPerformed(e);
      }
    });
    jMINuevaPart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
    jMArchivo.add(jMINuevaPart);
    
    JMenuItem jMIPartRapida = new JMenuItem("Partida_rapida");
    jMIPartRapida.setIcon((ImageIcon)resourceManager.getResource(ResourceManager.II_NEW));
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
    jMICerrarPes.setIcon((ImageIcon)resourceManager.getResource(ResourceManager.II_CLOSE_16));
    jMICerrarPes.setToolTipText("Cierra_la_pestaña_y/o_termina_la_partida");
    jMICerrarPes.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        cerrarTab_ActionPerformed(e);
      }
    });
    jMICerrarPes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_MASK));
    jMArchivo.add(jMICerrarPes);
    
    jMArchivo.addSeparator();
    JMenuItem jMISalir = new JMenuItem("Salir");
    jMISalir.setIcon((ImageIcon)resourceManager.getResource(ResourceManager.II_EXIT));
    jMISalir.setToolTipText("Termina_el_programa");
    jMISalir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        salir_ActionPerformed();
      }
    });
    jMArchivo.add(jMISalir);
    menu.add(jMArchivo);
    
    // Menu PalitosNet
    JMenu jMPalitosNet = new JMenu("PalitosNet");
    jMIConectar = new JMenuItem("Iniciar");
    jMIConectar.setIcon((ImageIcon)resourceManager.getResource(ResourceManager.II_PNSERVER));
    jMIConectar.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        conectar_ActionPerformed(e);
      }
    });
    jMPalitosNet.add(jMIConectar);
    //
    jMINuevo = new JMenuItem("Jugar_Con...");
    jMINuevo.setIcon((ImageIcon)resourceManager.getResource(ResourceManager.II_PNNEW));
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
    jMIShowChat.setIcon((ImageIcon)resourceManager.getResource(ResourceManager.II_PNCHAT));
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
    
    // Menu Opciones
    JMenu jMHerramientas = new JMenu("Opciones");
    JMenu langMenu = new JMenu("Idioma");
    ButtonGroup group = new ButtonGroup();
    JRadioButtonMenuItem spaMenuItem = new JRadioButtonMenuItem("Español");
    spaMenuItem.setSelected(true);
    JRadioButtonMenuItem engMenuItem = new JRadioButtonMenuItem("Ingles");
    group.add(spaMenuItem);
    group.add(engMenuItem);
    langMenu.add(spaMenuItem);
    langMenu.add(engMenuItem);
    jMHerramientas.add(langMenu);
    
    JMenu lafMenu = new JMenu("Apariencia");
    ButtonGroup group2 = new ButtonGroup();
    for(final LookAndFeelInfo laf: UIManager.getInstalledLookAndFeels()) {
    	JRadioButtonMenuItem lafMenuItem = new JRadioButtonMenuItem(laf.getName());
    	lafMenuItem.setSelected(laf.getName().equals(UIManager.getLookAndFeel().getName()));
    	group2.add(lafMenuItem);
    	lafMenuItem.addActionListener(new ActionListener() {
    		@Override	public void actionPerformed(ActionEvent e) {
    			lafChanged(laf.getClassName());
				}
			});
    	lafMenu.add(lafMenuItem);
    }
    jMHerramientas.add(lafMenu);
    
    final JCheckBoxMenuItem soundMenuItem = new JCheckBoxMenuItem("Sonido");
    soundMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confManager.save(ConfManager.SOUND_OPT, soundMenuItem.isSelected());
			}
		});
    soundMenuItem.setSelected(true);
    jMHerramientas.add(soundMenuItem);
    menu.add(jMHerramientas);
    
    // Menu Ayuda
    JMenu jMAyuda = new JMenu("Ayuda");
    JMenuItem jMIAyuda = new JMenuItem("Temas_de_Ayuda");
    jMIAyuda.setIcon((ImageIcon)resourceManager.getResource(ResourceManager.II_HELP));
    jMIAyuda.setToolTipText("Muestra_la_ayuda");
    jMIAyuda.addActionListener(new ActionListener() {
    	@Override
      public void actionPerformed(ActionEvent e) {
        ayuda_ActionPerformed(e);
      }
    });
    jMIAyuda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
    jMAyuda.add(jMIAyuda);
    //acerca
    JMenuItem jMIAcerca = new JMenuItem("Acerca_de...");
    jMIAcerca.setIcon((ImageIcon)resourceManager.getResource(ResourceManager.II_ABOUT));
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
    bCerrarTab = new JButton((ImageIcon)resourceManager.getResource(ResourceManager.II_CLOSE_24));
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
  
  protected void lafChanged(String className) {
  	try {
			UIManager.setLookAndFeel(className);
	    SwingUtilities.updateComponentTreeUI(this);
	    bCerrarTab.setBorder(null);
	    pack();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public SuperPalitos getSp() {
		return sp;
	}
	public void setSp(SuperPalitos sp) {
		this.sp = sp;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}
	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}
	
  
  public ConfManager getConfManager() {
		return confManager;
	}
	public void setConfManager(ConfManager confManager) {
		this.confManager = confManager;
	}
}
