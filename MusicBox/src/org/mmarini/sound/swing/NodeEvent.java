/*
 * SoundEvent.java
 *
 * $Id: NodeEvent.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 05/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.awt.Point;
import java.util.EventObject;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: NodeEvent.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class NodeEvent extends EventObject {
	private MusicBox musicBox;

	private Point location;

	private int direction;

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	/**
	 * @param source
	 */
	public NodeEvent(Object source) {
		super(source);
	}

	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return the musicBox
	 */
	public MusicBox getMusicBox() {
		return musicBox;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * @param musicBox
	 *            the musicBox to set
	 */
	public void setMusicBox(MusicBox musicBox) {
		this.musicBox = musicBox;
	}
}
