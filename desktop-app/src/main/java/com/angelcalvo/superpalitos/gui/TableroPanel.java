/*
 * TableroPanel
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.superpalitos.gui;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import com.angelcalvo.palitos.GameState;
import com.angelcalvo.palitos.Gaps;
import com.angelcalvo.palitos.Move;
import com.angelcalvo.palitos.Player;
import com.angelcalvo.palitos.Sticks;
import com.angelcalvo.palitos.Game;
import com.angelcalvo.palitos.Board;
import com.angelcalvo.superpalitos.SuperPalitos;

/**
 * El panel con el tablero
 * 
 * @author Angel Luis Calvo Ortega
 */
public class TableroPanel extends JPanel implements Board {
  private static final long serialVersionUID = 3616447899681305654L;
  
  private static final int STATE_OFF = 0;
  private static final int STATE_READY = 1;
  private static final int STATE_1ST_CLICK = 2;
  private static final int STATE_2ND_CLICK = 3;
  
  private static final int WAITING = 500;
  
  private static final int ANIM_FRAME = 50;
  private static final int XINC = 16;
  private static final int QUAD_DELAY = 60;
  
  private static final String IMAGE_FILE = "/com/angelcalvo/superpalitos/gui/fondo.png";
  private static final Image FONDO = Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource(IMAGE_FILE));

  private static final int huecos[][] = {
    {176, 81}, {208, 81}, {161, 113}, {192, 113}, {224, 113}, {145, 144},
    {176, 144}, {208, 144}, {240, 144}, {129, 176}, {161, 176}, {192, 176},
    {224, 176}, {256, 176}, {113, 207}, {145, 207}, {176, 207}, {208, 207},
    {240, 207}, {272, 207}
  };
  private static final int ANCHO = 16;
  private int state;
  private LinkedList<Linea> lineas;
  private Humano j;
  
  private final static Image[] bolis = {
  	Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliAzul.png")),
  	Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliNegro.png")),
  	Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliRojo.png")),
  	Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliVerde.png"))
  };
  private final static Cursor[] cursores = {
    Toolkit.getDefaultToolkit().createCustomCursor(bolis[SPFrame.AZUL], new Point(0, 0), "Boli Azul"),
    Toolkit.getDefaultToolkit().createCustomCursor(bolis[SPFrame.NEGRO], new Point(0, 0), "Boli Negro"),
    Toolkit.getDefaultToolkit().createCustomCursor(bolis[SPFrame.ROJO], new Point(0, 0), "Boli Rojo"),
    Toolkit.getDefaultToolkit().createCustomCursor(bolis[SPFrame.VERDE], new Point(0, 0), "Boli Verde")
  };
  private long id;
  
  private int boli;
  private int boliX, boliY;
  private AudioClip clip;
  private Timer timer;
  private String marcador;
  private SuperPalitos sp;
  
  /**
   * Crea un TableroPanel
   */
  public TableroPanel(SuperPalitos sp) {
  	this.sp = sp;
    lineas = new LinkedList<Linea>();
    state = STATE_OFF;
    boli = -1;
    
    setPreferredSize(new Dimension(FONDO.getWidth(null), FONDO.getHeight(null)));
    
    addMouseListener(new MouseAdapter() {
    	@Override
      public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
          this_mouseRigthPressed(e);
        } else {
          this_mouseLeftPressed(e);
        }
      }
    });
    addMouseMotionListener(new MouseMotionAdapter() {
    	@Override
      public void mouseMoved(MouseEvent e) {
        this_mouseMoved(e);
      }
    });
    
    /* Inicializa los cursores */
    /*TableroPanel.cursores = new Cursor[4];
    TableroPanel.cursores[SPFrame.AZUL] = Toolkit.getDefaultToolkit().createCustomCursor(bolis[SPFrame.AZUL], new Point(0, 0), "Boli Azul");
    TableroPanel.cursores[SPFrame.NEGRO] = Toolkit.getDefaultToolkit().createCustomCursor(bolis[SPFrame.NEGRO], new Point(0, 0), "Boli Negro");
    TableroPanel.cursores[SPFrame.ROJO] = Toolkit.getDefaultToolkit().createCustomCursor(bolis[SPFrame.ROJO], new Point(0, 0), "Boli Rojo");
    TableroPanel.cursores[SPFrame.VERDE] = Toolkit.getDefaultToolkit().createCustomCursor(bolis[SPFrame.VERDE], new Point(0, 0), "Boli Verde");
    */
    timer = new Timer();
    clip = Applet.newAudioClip(getClass().getResource("/com/angelcalvo/superpalitos/gui/boli.wav"));
  }
  
  
  // Metodos de Tablero -------------------------------------------------------
  @Override
  public void started() {
    clear();
    setCursor(cursores[color2index(sp.getJ1Color())]);
  }
  
  @Override
  public void finished() {
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }
  
  @Override
  public void drawLine(int x1, int y1, int x2, int y2, int c) {
    Linea l = new Linea(x1, y1, x2, y2, false);
    l.setColor(c);
    drawLine(l, sp.isAnim());
    if(sp.isSound()) {
    	clip.loop();
    	timer.schedule(new StopSound(), (Math.abs(x2 - x1) / ANCHO) * QUAD_DELAY);
    }
  }
  
  @Override
  public void drawLine(int h1, int h2, int c) {
    int x1Aux = huecos[h1][0] + Math.round((float)Math.random() * ANCHO);
    int y1Aux = huecos[h1][1] + Math.round((float)Math.random() * ANCHO);
    int x2Aux = huecos[h2][0] + Math.round((float)Math.random() * ANCHO);
    int y2Aux = huecos[h2][1] + Math.round((float)Math.random() * ANCHO);
    drawLine(x1Aux, y1Aux, x2Aux, y2Aux, c);
  }
  
  @Override
  public Player createPlayer(String name, int c) {
    return new Humano(name, c);
  }
  
  @Override
  public void setScore(String marcador) {
  	this.marcador = marcador;
  	repaint();
  }
  
  @Override
  public void setId(long id) {
    this.id = id;
  }
  
  @Override
  public long getId() {
    return id;
  }
  //-----------------------------------------------------------
  
  private void this_mouseRigthPressed(MouseEvent e) {
    if(state == STATE_READY) {
      j.x1 = e.getX();
      j.y1 = e.getY();
      if((j.h1 = cursorIn(j.x1, j.y1)) != -1 && j.h.getEstado(j.h1)) {
        state = STATE_1ST_CLICK;
      }
    } else if(state == STATE_1ST_CLICK) {
      j.x2 = e.getX();
      j.y2 = e.getY();
      if((j.h2 = cursorIn(j.x2, j.y2)) != -1 && j.h.getEstado(j.h2)) {
        if(j.h1 != j.h2 && huecos[j.h1][1] == huecos[j.h2][1] && j.p.jugadaValida(new Move(j.h1, j.h2, Move.HUECO))) {
          state = STATE_2ND_CLICK;
          //despertar();
          //super.notifyAll();
        }
      }
    }
  }
  
  private void this_mouseLeftPressed(MouseEvent e) {
    if(state == STATE_1ST_CLICK) {
      state = STATE_READY;
      repaint();
    }
  }
  
  private void this_mouseMoved(MouseEvent e) {
    if(state == STATE_1ST_CLICK) {
      j.x2 = e.getX();
      j.y2 = e.getY();
      Linea l = new Linea(j.x1, j.y1, j.x2, j.y2, true);
      l.setColor(j.getColor());
      lineas.addLast(l);
      repaint();
    }
  }
  
  private int cursorIn(int x, int y) {
    for(int i = 0; i < huecos.length; i++) {
      if(x >= huecos[i][0] && x <= huecos[i][0] + ANCHO && y >= huecos[i][1] && y <= huecos[i][1] + ANCHO)
        return i;
    }
    return -1;
  }
  
  @Override
  public void paint(Graphics g) {
    //Graphics2D g2d = (Graphics2D)g;
    Graphics g2d = g;
    g2d.drawImage(FONDO, 0, 0, null);
    
    for(Linea l: lineas) {
    //Iterator it = lineas.iterator();
    //while(it.hasNext()) {
    //  Linea l = (Linea)it.next();
      g2d.setColor(toColor(l.getColor()));
      g2d.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
      
      if(boli != -1) {
      	g2d.drawImage(bolis[boli], boliX, boliY, null);
      }
      
      if(marcador != null) {
      	g2d.setColor(SPFrame.COLORS[SPFrame.AZUL]);
      	g2d.drawString(marcador, SPFrame.WIDTH / 2, 10);
      }
      if(l.isVolatil()) {
        //it.remove();
      	//lineas.remove(l);
      }
    }
  }
  
  private Color toColor(int c) {
  	if(c == Game.BLUE_COLOR) {
  		return Color.BLUE;
  	}
  	if(c == Game.BLACK_COLOR) {
  		return Color.BLACK;
  	}
  	if(c == Game.RED_COLOR) {
  		return Color.RED;
  	}
  	return Color.GREEN;
  }
  
  private void drawLine(Linea l, boolean anim) {
    if(anim) {
      boli = color2index(l.getColor());
      float a = (float)(l.getY2() - l.getY1()) / (float)(l.getX2() - l.getX1());
      float b = -l.getX1()*a + l.getY1();
      
      for(int xoffset = 0; xoffset < l.getX2() - l.getX1(); xoffset += XINC) {
        boliX = (int)(l.getX1() + xoffset);
        boliY = (int)(a*boliX + b);
        
        Linea l2 = new Linea(l.getX1(), l.getY1(), boliX, boliY, true);
        l2.setColor(l.getColor());
        lineas.addLast(l2);
        
        repaint();
        
        try {
          Thread.sleep(ANIM_FRAME);
        } catch(InterruptedException e) {
          e.printStackTrace();
        }
      }
      boli = -1;
    }
    
    lineas.addLast(l);
    repaint();
  }
  
  /**
   * Limpia el tablero de lineas
   */
  public void clear() {
    lineas.clear();
    repaint();
  }
  
  private int color2index(int color) {
   /* for(int i = 0; i < SPFrame.COLORS.length; i++) {
      if(color.getRGB() == SPFrame.COLORS[i].getRGB()) {
        return i;
      }
    }
    return 0;*/
  	return color;
  }
/*
  private synchronized void dormir() {
    try {
      wait();
    } catch(InterruptedException e) {
      despertar();
    }
  }
 
  private synchronized void despertar() {
    notify();
  }*/
  
  /**
   * Titulo:      Humano
   * Descripcion: Implementa un jugador humano que introduce sus jugadas
   *              pintando en el TableroPanel.
   * Empresa: Pollo Verde Software
   *
   * @author Angel Luis Calvo Ortega
   * @version 1.0
   */
  private class Humano implements Player {
    private Gaps h;
    private Sticks p;
    private int color;
    private String nombre;
    private int h1, h2;
    private int x1, y1, x2, y2;
    
    /**
     * Crea un jugador
     * @param nombre
     * @param color
     */
    private Humano(String nombre, int color) {
      this.nombre = nombre;
      this.color = color;
    }
    
    @Override
    public Move move() {
      repaint();
      j = this;
      state = STATE_READY;
      /*while(moviendo != 3)
        dormir();*/
      try {
        while(state != STATE_2ND_CLICK) {
          Thread.sleep(WAITING);
        }
      } catch(InterruptedException e) {
      	e.printStackTrace();
        return null;
      }
      state = STATE_OFF;
      Move jug = new Move(h1, h2, Move.HUECO);
      jug.setCoord(x1, y1, x2, y2);
      return jug;
    }
    
    @Override
    public int getColor() {
      return color;
    }
    
    @Override
    public String getName() {
      return nombre;
    }
    
    @Override
    public void setColor(int color) {
      this.color = color;
    }
    
    @Override
    public void setName(String nombre) {
      this.nombre = nombre;
    }
    
    @Override
    public void update(Move j, Sticks p, Gaps h, GameState e) {
      this.p = p;
      this.h = h;
    }
    
    @Override
    public void finish() {}
  }
  
  /**
   *
   */
  private class StopSound extends TimerTask {
  	@Override
    public void run() {
      clip.stop();
    }
  }
}
