package com.angelcalvo.superpalitos;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * Class to dispatch and to load resources (mainly images) on demand
 * @author angel
 *
 */
public class ResourceManager {
	private static final String BASE = "/com/angelcalvo/superpalitos/gui/";
	
	public static final String II_ABOUT = "about.png";
	public static final String II_ACCEPT = "apply.png";
	public static final String II_BLANK = "blank.png";
	public static final String II_CANCEL = "cancel.png";
	public static final String II_CLOSE_16 = "close16.png";
	public static final String II_CLOSE_24 = "close24.png";
	public static final String II_CONNECT = "connect.png";
	public static final String II_DISCONNECT = "disconnect.png";
	public static final String II_EXIT = "exit.png";
	public static final String II_HELP= "help.png";
	public static final String II_LICENSE = "license.png";
  public static final String II_NEW = "new.png";
  public static final String II_OPTIONS = "options.png";
  public static final String II_PNCHAT = "chat.png";
  public static final String II_PNNEW = "pnnew.png";
  public static final String II_PNSERVER = "pnserver.png";
  public static final String II_PLAY = "play.png";
  public static final String II_PVS = "pvs.png";
  public static final String II_REPEAT = "refresh.png";
  public static final String II_SP = "sp.png";
  public static final String II_SP32 = "sp32.png";
  public static final String II_STOP = "stop.png";
  public static final String II_TITLE = "title.png";
  
  public static final String A_PEN = "boli.wav";
  
	private Map<String, Object> resources;

	public ResourceManager() {
		resources = new HashMap<String, Object>();
	}
	
	public Object getResource(String key) {
		ImageIcon resource = null;
		if(resources.containsKey(key)) {
			resource = (ImageIcon) resources.get(key);
		} else {
			resource = new ImageIcon(getClass().getResource(BASE + key));
			resources.put(key, resource);
		}
		return resource;
	}
	
	public ImageIcon getIcon(String key) {
		return (ImageIcon) getResource(key);
	}
}
