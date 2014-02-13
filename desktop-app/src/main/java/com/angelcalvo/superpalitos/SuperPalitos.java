/*
 * Superpalitos
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package org.pvs.superpalitos;

import java.util.LinkedList;
import javax.swing.SwingUtilities;
import org.pvs.palitos.IA;
import org.pvs.palitos.Jugador;
import org.pvs.palitos.Partida;
import org.pvs.palitos.Tablero;
import org.pvs.superpalitos.gui.FinJuegoDialog;
import org.pvs.superpalitos.gui.SPFrame;
import org.pvs.superpalitos.net.PNClient;
import org.pvs.superpalitos.net.PNServer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Clase controlador
 *
 * @author Angel Luis Calvo Ortega
 */
public class SuperPalitos {
  /** Tipo un jugador con IA facil */
  public static final int JUEGO_1J_FACIL = 0;
  /** Tipo un jugador con IA normal */
  public static final int JUEGO_1J_NORMAL = 1;
  /** Tipo un jugador con IA dificil */
  public static final int JUEGO_1J_DIFICIL = 2;
  /** Tipo dos jugadores */
  public static final int JUEGO_2J = 3;
  /** Tipo en red */
  public static final int JUEGO_MJ = 4;
  /** Tipo de juego por defecto */
  private static final int DEFAULT_TIPO_JUEGO = JUEGO_1J_NORMAL;
  
  /** Opcion continuar en el dialogo fin de partida */
  public static final int FIN_JUEGO_CONTINUAR = 0;
  //public static final int FIN_JUEGO_REPETIR = 1;
  /** Opcion terminar en el dialogo fin de partida */
  public static final int FIN_JUEGO_TERMINAR = 2;
  
  private static final String tags[] = {"vs IA Facil", "vs IA", "vs_IA_Dificil", "vs_2º_Player", "vs_Remote_Player"};
  
  private static final String DEFAULT_J1_NAME = "PlayerOne";
  private static final String DEFAULT_J2_NAME = "PlayerTwo";
  
  private static final String PROPS_NAME = ".superpalitos";
  private static final String PROPS_FILE = System.getProperty("user.home") + "/" + PROPS_NAME;
  
//  private static SuperPalitos instance;
  
  private boolean serverON;
  private PNServer pnServer;
  
  private LinkedList<PartidaManager> partidas; // De PartidaManager
  private SPFrame frame;

  /* Opciones */
  private String j1Name, j2Name;
  private int j1Color, j2Color;
  private boolean anim, sound;
  //
  private SPConf conf;
  
  private SuperPalitos() {
    partidas = new LinkedList<PartidaManager>();
    pnServer = new PNServer(this);
    
    j1Name = DEFAULT_J1_NAME;
    j2Name = DEFAULT_J2_NAME;
    j1Color = Partida.BLUE_COLOR;
    j2Color = Partida.RED_COLOR;
    anim = true;
    sound = true;
    
    conf = new SPConf();
    conf.read(PROPS_FILE);
  }
  
  /**
   * Metodo que devuelve la instancia de la clase.
   * @return La &uacute;nica instancia de Main.
   */
  /*
  public static SuperPalitos getInstance() {
    if(instance == null) {
      instance = new SuperPalitos();
    }
    return instance;
  }*/
  
  /**
   * LLama a jugar con el modo de partida por defecto.
   *
   */
  public void nuevaPartida() {
    nuevaPartida(DEFAULT_TIPO_JUEGO, null, true);
  }
  
  /**
   * Empieza una partida.
   * @param tipo Tipo de partida
   * @param pnPlayer
   * @param first
   */
  public void nuevaPartida(int tipo, PNClient pnPlayer, boolean first) {
    Tablero t = frame.createTablero(tags[tipo]);
    Jugador j1 = t.createJugador(j1Name, j1Color);
    Jugador j2 = null;
    if(tipo == JUEGO_1J_FACIL) {						// FACIL
      j2 = new IA(IA.FACIL, Partida.BLACK_COLOR);
    } else if(tipo == JUEGO_1J_NORMAL) {		// NORMAL
      j2 = new IA(IA.NORMAL, Partida.BLACK_COLOR);
    } else if(tipo == JUEGO_1J_DIFICIL) {	 // DIFICIL
      j2 = new IA(IA.DIFICIL, Partida.BLACK_COLOR);
    } else if(tipo == JUEGO_2J) {					 // 2 JUGADORES
      j2 = t.createJugador(j2Name, j2Color);
    } else if(tipo == JUEGO_MJ) {
    	j2 = pnPlayer;
    	pnPlayer.setChat(frame.addChat(pnPlayer));
    } else {
    	return;
    }
    
//    PartidaManager pm = new PartidaManager(j1, j2, t, first);
    //pm.addPartidaListener(this);
//    partidas.addLast(pm);
    PartidaManager pm = createPM(j1, j2, t, first);
    pm.play();
  }
  
  private PartidaManager createPM(Jugador j1, Jugador j2, Tablero t, boolean first) {
    PartidaManager pm = new PartidaManager(j1, j2, t, first, this);
    partidas.addLast(pm);
    return pm;
  }
  
  /**
   * Metodo principal, inicia la aplicaci&oacute;n.
   * @param args Argumentos de entrada.
   */
  public static void main(String[] args) {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("org/pvs/superpalitos/sp.xml"));
		final SuperPalitos sp = (SuperPalitos) factory.getBean("sp");

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				sp.startGui();
			}
		});
		// SuperPalitos.getInstance();
	}
  
  private void startGui() {
      frame.setVisible(true);
  }
  /*public void cambiaTurno(String s) {
    if(s != null && frame != null) {
      frame.setStatus("Turno de " + s);
    }
  }*/
  
  /**
   * Muestra el mensaje de fin de juego
   */
  public int finJuego(String s, long time) {
    //frame.setStatus("Game Over :: " + s + " ha ganado");
    //return frame.showFinJuego(s, time);
  	return FinJuegoDialog.showFinJuegoDialog(frame, s, time);
  }
  
  /**
   * Se ha pulsado el boton repetir
   * @param id El identficador del tablero
   */
  public void repetirCmd(long id) {
  	if(id != -1) {
  		getPartidaManager(id).replay();
  	}
  }
  
  private PartidaManager getPartidaManager(long id) {
  	for(PartidaManager p: partidas) {
    //Iterator it = partidas.iterator();
    //while(it.hasNext()) {
    //  PartidaManager p = (PartidaManager)it.next();
      if(p.getId() == id) {
        return p;
      }
    }
    return null;
  }
  
  private PartidaManager getPartidaManager(Jugador j) {
  	for(PartidaManager p: partidas) {
    //Iterator it = partidas.iterator();
    //while(it.hasNext()) {
    //  PartidaManager p = (PartidaManager)it.next();
      if(p.estaJugandgo(j)) {
        return p;
      }
    }
    return null;
  }
  
  /**
   * Utilizado por PartidaManager
   * @param p El gestor de partidas
   */
  public void removePartida(PartidaManager p) {
  	frame.destroyTablero(p.getId());
  	partidas.remove(p);
  }
  
  /**
   * Utilizado por SPFrame
   * @param id El identificador del tablero
   */
  public void cerrarPartida(long id) {
  	PartidaManager pm = getPartidaManager(id);
  	pm.end();
  	partidas.remove(pm);
  }
  
  /**
   * Cambia el L&f
   */
  public void changeLaF() {
    frame.changeLaF();
  }
  
  public void salir() {
    conf.write(PROPS_FILE);
  }
  
  // PalitosNet ---------------------------------------------------------------
  
  /**
   * Inicia el servidor de PalitosNet
   */
  protected void palitosNetON() {
  	if(!serverON) {
	  	if(pnServer == null) {
	  		pnServer = new PNServer(this);
	  	}
	    if(pnServer.setON()) {
	    	serverON = true;
	    } else {
	    	frame.showErrMsg(java.util.ResourceBundle.getBundle("org/pvs/superpalitos/gui/Bundle").getString("No_se_ha_podido_iniciar_el_servidor"));
	    }
  	}
  }
  
  /**
   * Termina el servidor de PalitosNet
   */
  protected void palitosNetOFF() {
  	if(serverON) {
	    //desconectar();
	    pnServer.setOFF();
	    serverON = false;
  	}
  }
  
  /**
   * Acepta una conexi&oacute;n de un usuario remoto en una partida PalitosNet.
   * @param pnClient El nombre del contrario.
   * @param ip Direccion del contrario.
   * @return Si la conexion es aceptado o denegada.
   */
  public boolean aceptarConexion(PNClient pnClient, String ip) {
    if(frame.showNewConnMsg(pnClient.getNombre(), ip, pnClient.getColor())) {
    	nuevaPartida(JUEGO_MJ, pnClient, true);
      return true;
    }
    return false;
  }
 
  /**
   * Metodo que dice si el servidor de PalitosNet est&aacute; levantado.
   * @return Si el servidor est&aacute; activo
   */
  public boolean isServerON() {
    return serverON;
  }
  
  /*
   * Metodo para terminar una conexi&oacute;n PalitosNet
   */
  /*public void desconectar() {
    //pnClient.disconnet();
    //if(Main.getInstancia().isJugando())
    //	partida.terminar();
    frame.desconectar();
  }*/
  
  /**
   * Metodo para indicar que el extremo (jugador contrario) se ha desconectado.
   */
  public void desconectado(Jugador j) {
    PartidaManager pm = getPartidaManager(j);
  	frame.destroyTablero(pm.getId());
  	pm.end();
  	partidas.remove(pm);
    frame.showAbortPartidaMsg();
    //TODO cerrar tab del chat
  }
  
  /**
   * Metodo para establecer una conexi&oacute;n PalitosNet.
   * @param dir La direcci&oacute;n del contrario.
   */
  public void conectar(String dir, int puerto) {
    PNClient pnClient = new PNClient(this);
    pnClient.setNombre(j1Name);
    pnClient.setColor(j1Color);
    pnClient.connect(dir, puerto);
  }
  
  public void errConn(String msg) {
  	frame.showConnErrorMsg(msg);
  }
  
  /**
   * Indica que la conexi&oacute;n se ha realizado correctamente.
   * @param pnClient El nombre del contrario.
   */
  public void confirmarConexion(PNClient pnClient) {
    frame.endsConectar();
    nuevaPartida(JUEGO_MJ, pnClient, false);
  }
  
  /*
   * Metodo que nos indica si hay una conexi&oacute;n en marcha.
   * @return Si hay una conexi&oacute;n en marcha o no.
   */
  /*public boolean isConectado() {
    /*if(pnServer == null)
        return false;
    return pnServer.isConectado();
    return pnClient != null;
  }*/
  
  /*
   * M&eacute; que termina una partida bruscamente.
   */
  /* public void finPartida(long id) {
    Partida p = getPartida(id);
    p.terminar();
    partidas.remove(p);
  }*/
  
  /**
   * Activa o desactiva PalitosNet
   */
  public void palitosNet() {
    if(isServerON()) {
      palitosNetOFF();
    } else {
      palitosNetON();
    }
    frame.setServer(isServerON());
  }
  
  
  // Metodso getters y setters ------------------------------------------------
  
  /**
   * M&etodo para establecer el puerto de palitosNet
   * @param puerto El nuevo puerto.
   */
  public void setPuerto(int puerto) {
    pnServer.setPort(puerto);
  }
  
  /**
   *  M&eacute;todo que devuelve el puerto utilizado en palitosNet.
   * @return El puerto.
   */
  public int getPuerto() {
    return pnServer.getPort();
  }
  
  /**
   * @return Devuelve j1Color.
   */
  public int getJ1Color() {
    return j1Color;
  }
  
  /**
   * @return Devuelve j1Name.
   */
  public String getJ1Name() {
    return j1Name;
  }
  
  /**
   * @return Devuelve j2Color.
   */
  public int getJ2Color() {
    return j2Color;
  }
  
  /**
   * @param color El j2Color a establecer.
   */
  public void setJ2Color(int color) {
    j2Color = color;
  }
  
  /**
   * @return Devuelve j2Name.
   */
  public String getJ2Name() {
    return j2Name;
  }
  
  /**
   * @param name El j2Name a establecer.
   */
  public void setJ2Name(String name) {
    j2Name = name;
  }
  
  /**
   * @param name El j1Name a establecer.
   */
  public void setJ1Name(String name) {
    j1Name = name;
  }
  
  /**
   * @param color El j1Color a establecer.
   */
  public void setJ1Color(int color) {
    j1Color = color;
  }
  
  /*public void continuar(long id) {
    
  }
  
  public void repetir(long id) {
    Partida p = getPartida(id);
    Partida p2 = (Partida)p.clone();
    partidas.remove(p);
    partidas.addLast(p2);
    p2.start();
  }
  
  public void terminar(long id) {
    Partida p = getPartida(id);
    partidas.remove(p);
  }*/

  /**
   * @return Si la animacion esta activada
   */
	public boolean isAnim() {
		return anim;
	}

	/**
	 * Establece si estan activadas las animaciones
	 * @param anim
	 */
	public void setAnim(boolean anim) {
		this.anim = anim;
	}
	
	/**
	 * @return Si el sonido esta activado
	 */
	public boolean isSound() {
		return sound;
	}
	
	/**
	 * Establece si esta activado el sonido
	 * @param sound
	 */
	public void setSound(boolean sound) {
		this.sound = sound;
	}
  
  public SPConf getConf() {
    return conf;
  }

	public SPFrame getFrame() {
		return frame;
	}

	public void setFrame(SPFrame frame) {
		this.frame = frame;
	}
}
