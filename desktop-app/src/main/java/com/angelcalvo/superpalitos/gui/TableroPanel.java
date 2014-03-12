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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.angelcalvo.palitos.Board;
import com.angelcalvo.palitos.GameState;
import com.angelcalvo.palitos.Move;
import com.angelcalvo.palitos.Player;
import com.angelcalvo.superpalitos.SuperPalitos;
import com.angelcalvo.superpalitos.XyMove;

/**
 * El panel con el tablero
 * 
 * @author Angel Calvo
 */
public class TableroPanel extends JPanel implements Board {
  private static final long serialVersionUID = 3616447899681305654L;
  
  private static final int STATE_OFF = 0;
  private static final int STATE_READY = 1;
  private static final int STATE_1ST_CLICK = 2;
  private static final int STATE_2ND_CLICK = 3;
  
  private static final int WAITING = 500;
  
  private static final int ANIM_FRAME = 100;
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
  private LinkedList<Line> lineas;
  private Humano j;
  
  private final static Image[][] PEN_CURSORS = {
  	{
  		Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliAzul.png")),
  		Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliAzul-sel.png"))},
  	{
  		Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliNegro.png")),
  		Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliNegro-sel.png")),
  	},
  	{
  		Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliRojo.png")),
    	Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliRojo-sel.png")),
  	},
  	{
  		Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliVerde.png")),
    	Toolkit.getDefaultToolkit().getImage(TableroPanel.class.getResource("/com/angelcalvo/superpalitos/gui/boliVerde-sel.png"))

  	}
  };
  private final static Cursor[][] CURSORS = {
  	{
  		Toolkit.getDefaultToolkit().createCustomCursor(PEN_CURSORS[SPFrame.AZUL][0], new Point(0, 0), "Boli Azul"),
  		Toolkit.getDefaultToolkit().createCustomCursor(PEN_CURSORS[SPFrame.AZUL][1], new Point(0, 0), "Boli Azul Sel"),
  	},
    {
    	Toolkit.getDefaultToolkit().createCustomCursor(PEN_CURSORS[SPFrame.NEGRO][0], new Point(0, 0), "Boli Negro"),
    	Toolkit.getDefaultToolkit().createCustomCursor(PEN_CURSORS[SPFrame.NEGRO][1], new Point(0, 0), "Boli Negro Sel"),
    },
    {
    	Toolkit.getDefaultToolkit().createCustomCursor(PEN_CURSORS[SPFrame.ROJO][0], new Point(0, 0), "Boli Rojo"),
    	Toolkit.getDefaultToolkit().createCustomCursor(PEN_CURSORS[SPFrame.ROJO][1], new Point(0, 0), "Boli Rojo Sel"),
    },
    {
    	Toolkit.getDefaultToolkit().createCustomCursor(PEN_CURSORS[SPFrame.VERDE][0], new Point(0, 0), "Boli Verde"),
    	Toolkit.getDefaultToolkit().createCustomCursor(PEN_CURSORS[SPFrame.VERDE][1], new Point(0, 0), "Boli Verde Sel")
    }
  };
  
  private int boli;
  private int boliX, boliY;
  private AudioClip clip;
  private Timer timer;
  private String marcador;
  private SuperPalitos sp;
  private JButton newGameButton;
  private JLabel msgLabel;
  private GameState gameState;
  
  /**
   * Crea un TableroPanel
   */
  public TableroPanel(final SuperPalitos sp) {
  	this.sp = sp;
    lineas = new LinkedList<Line>();
    state = STATE_OFF;
    boli = -1;
    
    setPreferredSize(new Dimension(FONDO.getWidth(null), FONDO.getHeight(null)));
    
    setLayout(new BorderLayout());
    
    msgLabel = new JLabel();
  	msgLabel.setForeground(SPFrame.COLORS[SPFrame.AZUL]);
    msgLabel.setFont(getFont().deriveFont(Font.BOLD, 20));
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
    panel.add(msgLabel);
    add(panel, BorderLayout.NORTH);
    
    newGameButton = new JButton("Jugar de nuevo");
    newGameButton.setVisible(false);
    newGameButton.addActionListener(new ActionListener() {
			@Override	public void actionPerformed(ActionEvent arg0) {
				sp.repetirCmd(0); // TODO
			}
		});
    panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
    panel.add(newGameButton);
    add(panel, BorderLayout.SOUTH);
    
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
  public void started(GameState gameState) {
  	this.gameState = gameState;
    clear();
  }
  
  @Override
  public void finished(Player winner) {
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    if(winner == null) {
    	msgLabel.setText("Fin de la partida");
    } else if(j == winner) {
    	msgLabel.setText("¡Has ganado!");
    } else {
    	msgLabel.setText("Has perdido :(");
    }
    newGameButton.setVisible(true);
    repaint();
  }
  
  
  @Override
	public void move(Player player, Move move, GameState state) {
  	gameState = state;
  	Line line = null;
  	
  	if(move instanceof XyMove) {
  		XyMove xyMove = (XyMove) move;
      line = new Line(xyMove.getX1(), xyMove.getY1(), xyMove.getX2(), xyMove.getY2(), false);
  	} else {
      int x1Aux = huecos[move.getHInicio()][0] + Math.round((float)Math.random() * ANCHO);
      int y1Aux = huecos[move.getHInicio()][1] + Math.round((float)Math.random() * ANCHO);
      int x2Aux = huecos[move.getHFin()][0] + Math.round((float)Math.random() * ANCHO);
      int y2Aux = huecos[move.getHFin()][1] + Math.round((float)Math.random() * ANCHO);
      line = new Line(x1Aux, y1Aux, x2Aux, y2Aux, false);
  	}
  	
    line.setColor(player.getColor());
    drawLine(line, player != j);
    if(sp.isSound()) {
    	clip.loop();
    	timer.schedule(new StopSound(), (Math.abs(line.getX1() - line.getX2()) / ANCHO) * QUAD_DELAY);
    }
    
    setCursor(player != j ? CURSORS[color2index(sp.getJ1Color())][0] : new Cursor(Cursor.DEFAULT_CURSOR));
	}

  public Player createPlayer(String name, Color c) {
    return new Humano(name, c);
  }
  
  public void setScore(String marcador) {
  	this.marcador = marcador;
  	repaint();
  }
  //-----------------------------------------------------------
  
  private void this_mouseRigthPressed(MouseEvent e) {
    if(state == STATE_READY) {
      j.x1 = e.getX();
      j.y1 = e.getY();
      if((j.h1 = cursorIn(j.x1, j.y1)) != -1 && gameState.getGap(j.h1)) {
        state = STATE_1ST_CLICK;
      }
    } else if(state == STATE_1ST_CLICK) {
      j.x2 = e.getX();
      j.y2 = e.getY();
      if((j.h2 = cursorIn(j.x2, j.y2)) != -1 && gameState.getGap(j.h2)) {
        if(j.h1 != j.h2 && huecos[j.h1][1] == huecos[j.h2][1] && j.s.isValid(new Move(j.h1, j.h2, Move.HUECO))) {
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
      Line l = new Line(j.x1, j.y1, j.x2, j.y2, true);
      l.setColor(j.getColor());
      lineas.addLast(l);
      repaint();
    }
    if(state == STATE_READY) {
    	int gap = cursorIn(e.getX(), e.getY());
    	int cursor = gap != -1 && gameState.getGap(gap) ? 1 : 0;
    	setCursor(CURSORS[color2index(sp.getJ1Color())][cursor]);
    } else if(state == STATE_1ST_CLICK) {
    	int gap = cursorIn(e.getX(), e.getY());
    	int cursor = gap != -1 && gameState.isValid(new Move(j.h1, gap, Move.HUECO)) ? 1 : 0;
    	setCursor(CURSORS[color2index(sp.getJ1Color())][cursor]);
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
  	super.paint(g);
  	
    //Graphics2D g2d = (Graphics2D)g;
    Graphics g2d = g;
    g2d.drawImage(FONDO, 0, 0, null);
    
    ArrayList<Line> toRemove = new ArrayList<Line>(lineas.size());
    for(Line l: lineas) {
      g2d.setColor(l.getColor());
      g2d.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
      
      if(l.isVolatil()) {
      	toRemove.add(l);
      }
    }
    lineas.removeAll(toRemove);
    
    if(boli != -1) {
    	g2d.drawImage(PEN_CURSORS[boli][0], boliX, boliY, null);
    }
    
    if(marcador != null) {
    	g2d.setColor(SPFrame.COLORS[SPFrame.AZUL]);
    	g2d.setFont(getFont());
    	g2d.drawString(marcador, SPFrame.WIDTH / 2, 10);
    }
  }

  private void drawLine(Line l, boolean anim) {
    if(anim) {
      boli = color2index(l.getColor());
      float a = (float)(l.getY2() - l.getY1()) / (float)(l.getX2() - l.getX1());
      float b = -l.getX1()*a + l.getY1();
      
      for(int xoffset = 0; xoffset < l.getX2() - l.getX1(); xoffset += XINC) {
        boliX = (int)(l.getX1() + xoffset);
        boliY = (int)(a*boliX + b);
        
        Line l2 = new Line(l.getX1(), l.getY1(), boliX, boliY, true);
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
   	msgLabel.setText("");
    newGameButton.setVisible(false);
    repaint();
  }
  
  private int color2index(Color color) {
  	if(SuperPalitos.BLUE_COLOR.equals(color)) {
  		return 0;
  	}
  	if(SuperPalitos.RED_COLOR.equals(color)) {
  		return 1;
  	}
  	if(SuperPalitos.BLACK_COLOR.equals(color)) {
  		return 2;
  	}
  	if(SuperPalitos.GREEN_COLOR.equals(color)) {
  		return 3;
  	}
  	throw new IllegalArgumentException("Non standard color");
  }
  
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
    private GameState s;
    private Color color;
    private String nombre;
    private int h1, h2;
    private int x1, y1, x2, y2;
    
    /**
     * Crea un jugador
     * @param nombre
     * @param color
     */
    private Humano(String nombre, Color color) {
      this.nombre = nombre;
      this.color = color;
    }
    
    @Override
    public Move move() {
      repaint();
      j = this;
      state = STATE_READY;

      try {
        while(state != STATE_2ND_CLICK) {
          Thread.sleep(WAITING);
        }
      } catch(InterruptedException e) {
      	e.printStackTrace();
        return null;
      }
      state = STATE_OFF;
      XyMove jug = new XyMove(h1, h2, Move.HUECO);
      jug.setCoords(x1, y1, x2, y2);
      return jug;
    }
    
    @Override
    public Color getColor() {
      return color;
    }
    
    @Override
    public String getName() {
      return nombre;
    }
    
    @Override
    public void update(Move j, GameState s) {
      this.s = s;
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
