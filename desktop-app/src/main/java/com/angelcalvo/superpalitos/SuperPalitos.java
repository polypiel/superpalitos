/*
 * Superpalitos
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.superpalitos;

import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import com.angelcalvo.palitos.Player;
import com.angelcalvo.palitos.PlayerAI;
import com.angelcalvo.palitos.PlayerAI.AiLevel;
import com.angelcalvo.superpalitos.gui.BoardPanel;
import com.angelcalvo.superpalitos.gui.SPFrame;
import com.angelcalvo.superpalitos.net.PNClient;
import com.angelcalvo.superpalitos.net.PNServer;

/**
 * Clase controlador
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
  
  private static final String tags[] = {"vs IA Facil", "vs IA", "vs IA Dificil", "vs 2º Player", "vs Remote Player"};
  
  private static final String DEFAULT_J1_NAME = "PlayerOne";
  private static final String DEFAULT_J2_NAME = "PlayerTwo";
  
  public final static Color BLUE_COLOR = Color.BLUE;
  public final static Color BLACK_COLOR = Color.BLACK;
  public final static Color RED_COLOR = Color.RED;
  public final static Color GREEN_COLOR = Color.GREEN;
  
  private boolean serverON;
  private PNServer pnServer;
  
  private Collection<PartidaManager> partidas;
  
  private final SPFrame frame;
  
  /* Opciones */
  private String j1Name, j2Name;
  private Color j1Color, j2Color;

  private SuperPalitos() {
  	frame = new SPFrame(this);
    partidas = new LinkedList<PartidaManager>();
    pnServer = new PNServer(this);
    
    // TODO conf manager
    j1Name = DEFAULT_J1_NAME;
    j2Name = DEFAULT_J2_NAME;
    j1Color = BLUE_COLOR;
    j2Color = RED_COLOR;
    
    ConfManager.get().save(ConfManager.SOUND_OPT, true);
  }
  
  /**
   * LLama a jugar con el modo de partida por defecto.
   *
   */
  public void nuevaPartida() {
    nuevaPartida(DEFAULT_TIPO_JUEGO, null, true, null);
  }
  /**
   * Empieza una partida.
   * @param tipo Tipo de partida
   * @param pnPlayer
   * @param first
   */
  public void nuevaPartida(int tipo, PNClient pnPlayer, boolean first, Integer tab) {
    BoardPanel t = frame.createTablero(tags[tipo], tab);
    Player j1 = t.createPlayer(j1Name, j1Color);
    Player j2 = null;
    if(tipo == JUEGO_1J_FACIL) {						// FACIL
      j2 = new PlayerAI(AiLevel.EASY, BLACK_COLOR);
    } else if(tipo == JUEGO_1J_NORMAL) {		// NORMAL
      j2 = new PlayerAI(AiLevel.NORMAL, BLACK_COLOR);
    } else if(tipo == JUEGO_1J_DIFICIL) {	 // DIFICIL
      j2 = new PlayerAI(AiLevel.HARD, BLACK_COLOR);
    } else if(tipo == JUEGO_2J) {					 // 2 JUGADORES
      j2 = t.createPlayer(j2Name, j2Color);
    } else if(tipo == JUEGO_MJ) {
    	j2 = pnPlayer;
    	pnPlayer.setChat(frame.addChat(pnPlayer));
    } else {
    	throw new IllegalArgumentException("Game type is not valid: " + tipo);
    }
    
    // creates the partida manager
    PartidaManager pm = new PartidaManager(j1, j2, t, first);
    partidas.add(pm);
    pm.play();
  }
  
  public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SuperPalitos().startGui();
			}
		});
	}
  
  private void startGui() {
     frame.setVisible(true);
  }

  private PartidaManager getPartidaManager(BoardPanel board) {
  	for(PartidaManager pm: partidas) {
  		if(pm.getBoard().equals(board)) {
  			return pm;
  		}
  	}
  	return null;
  }
  /**
   * Se ha pulsado el boton repetir
   * @param id El identficador del tablero
   */
  public void repetirCmd(BoardPanel panel) {
  	getPartidaManager(panel).replay();
  }

  /**
   * Utilizado por SPFrame
   * @param id El identificador del tablero
   */
  public void cerrarPartida(BoardPanel panel) {
  	PartidaManager pm = getPartidaManager(panel);
  	pm.onGameFinished(null);
  	partidas.remove(pm);
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
	    	frame.showErrMsg("No_se_ha_podido_iniciar_el_servidor");
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
    if(frame.showNewConnMsg(pnClient.getName(), ip, pnClient.getColor())) {
    	nuevaPartida(JUEGO_MJ, pnClient, true, null);
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
  public void desconectado(BoardPanel panel) {
    PartidaManager pm = getPartidaManager(panel);
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
    pnClient.setName(j1Name);
    // pnClient.setColor(j1Color);
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
    nuevaPartida(JUEGO_MJ, pnClient, false, null);
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

  public Color getJ1Color() {
    return j1Color;
  }

  public String getJ1Name() {
    return j1Name;
  }
  
  public Color getJ2Color() {
    return j2Color;
  }

  public String getJ2Name() {
    return j2Name;
  }

  public void setJ1Name(String j1Name) {
		this.j1Name = j1Name;
	}

	public void setJ2Name(String j2Name) {
		this.j2Name = j2Name;
	}

	public void setJ1Color(Color j1Color) {
		this.j1Color = j1Color;
	}

	public void setJ2Color(Color j2Color) {
		this.j2Color = j2Color;
	}
	
}
