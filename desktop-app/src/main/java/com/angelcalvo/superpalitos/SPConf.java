/*
 * SPConf
 * 
 * Created on 8 de diciembre de 2007
 * 
 * Pollo Verde Software 2006
 * 
 * Este programa se distribuye segun la licencia GPL v.2 o posteriores y no
 * tiene garantias de ningun tipo. Puede obtener una copia de la licencia GPL o
 * ponerse en contacto con la Free Software Foundation en http://www.gnu.org
 */
package com.angelcalvo.superpalitos;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angelcalvo.superpalitos.gui.SPFrame;

/**
 *
 * @author angel
 */
public class SPConf {
  private static final Logger LOG = LoggerFactory.getLogger(SPConf.class);
  
  private static final String J1_NAME_PROP = "player.j1.name";
  private static final String J1_COLOR_PROP = "player.j1.color";
  private static final String J2_NAME_PROP = "player.j2.name";
  private static final String J2_COLOR_PROP = "player.j2.color";
  private static final String PORT_PROP = "palitosnet.port";
  private static final String ANIMS_PROP = "game.anims";
  private static final String SOUND_PROP = "game.sound";
  private static final String LNF_PROP = "interface.lnf";
  private static final String LANGUAGE_PROP = "interface.language";
  
  private static final String DEFAULT_J1_NAME = "PlayerOne";
  private static final String DEFAULT_J1_COLOR = String.valueOf(SPFrame.COLORS[SPFrame.AZUL].getRGB());
  private static final String DEFAULT_J2_NAME = "PlayerTwo";
  private static final String DEFAULT_J2_COLOR = String.valueOf(SPFrame.COLORS[SPFrame.ROJO].getRGB());
  private static final String DEFAULT_PORT = String.valueOf(11111);
  private static final String DEFAULT_ANIMS = Boolean.toString(true);
  private static final String DEFAULT_SOUND = Boolean.toString(false);
  
  private Properties props;

  public SPConf() {
    props = new Properties();
    props.put(J1_NAME_PROP, DEFAULT_J1_NAME);
    props.put(J1_COLOR_PROP, DEFAULT_J1_COLOR);
    props.put(J2_NAME_PROP, DEFAULT_J2_NAME);
    props.put(J2_COLOR_PROP, DEFAULT_J2_COLOR);
    props.put(PORT_PROP, DEFAULT_PORT);
    props.put(ANIMS_PROP, DEFAULT_ANIMS);
    props.put(SOUND_PROP, DEFAULT_SOUND);
  }
  
  public void read(String filename) {
    File file = new File(filename);
    if (file.exists()) {
      FileInputStream fis = null;
      try {
        fis = new FileInputStream(file);
        props.load(fis);
      } catch (IOException ex) {
        LOG.error("Error al abrir el fichero de propiedades", ex);
      } finally {
        try {
          fis.close();
        } catch (IOException ex) {}
      }
    }
  }
  
  public void write(String filename) {
    File file = new File(filename);
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);
      props.store(fos, "Superpalitos preferences");
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      try {
        fos.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
  
  //------------------------------------------
  public boolean isAnims() {
    return Boolean.getBoolean(props.getProperty(ANIMS_PROP));
  }

  public void setAnims(boolean anims) {
    props.setProperty(ANIMS_PROP, Boolean.toString(anims));
  }

  public Color getJ1Color() {
    return Color.decode(props.getProperty(J1_COLOR_PROP));
  }

  public void setJ1Color(Color j1Color) {
    props.setProperty(J1_COLOR_PROP, String.valueOf(j1Color.getRGB()));
  }

  public String getJ1Name() {
    return props.getProperty(J1_NAME_PROP);
  }

  public void setJ1Name(String j1Name) {
    props.setProperty(J1_NAME_PROP, j1Name);
  }

  public Color getJ2Color() {
    return Color.decode(props.getProperty(J2_COLOR_PROP));
  }

  public void setJ2Color(Color j2Color) {
    props.setProperty(J2_COLOR_PROP, String.valueOf(j2Color.getRGB()));
  }

  public String getJ2Name() {
    return props.getProperty(J2_NAME_PROP);
  }

  public void setJ2Name(String j2Name) {
    props.setProperty(J2_NAME_PROP, j2Name);
  }

  public String getLanguage() {
    return props.getProperty(LANGUAGE_PROP);
  }

  public void setLanguage(String language) {
    props.setProperty(LANGUAGE_PROP, language);
  }

  public String getLnf() {
    return props.getProperty(LNF_PROP);
  }

  public void setLnf(String lnf) {
    props.setProperty(LNF_PROP, lnf);
  }

  public int getPort() {
    return Integer.parseInt(props.getProperty(PORT_PROP));
  }

  public void setPort(int port) {
    props.setProperty(PORT_PROP, String.valueOf(port));
  }

  public boolean isSound() {
    return Boolean.getBoolean(props.getProperty(SOUND_PROP));
  }

  public void setSound(boolean sound) {
    props.setProperty(SOUND_PROP, Boolean.toString(sound));
  }
}
