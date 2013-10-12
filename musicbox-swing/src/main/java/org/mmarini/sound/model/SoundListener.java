/*
 * SoundListener.java
 *
 * $Id: SoundListener.java,v 1.1 2008/11/20 21:07:26 marco Exp $
 *
 * 13/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.model;

import java.util.EventListener;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: SoundListener.java,v 1.1 2008/11/20 21:07:26 marco Exp $
 * 
 */
public interface SoundListener extends EventListener {
	/**
	 * 
	 * @param event
	 */
	public abstract void nodeChanged(SoundEvent event);

	/**
	 * @param event
	 */
	public abstract void rootChanged(SoundEvent event);

	/**
	 * @param event
	 */
	public abstract void structureChanged(SoundEvent event);
}
