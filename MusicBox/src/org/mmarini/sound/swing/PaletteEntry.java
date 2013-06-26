/*
 * PaletteEntry.java
 *
 * $Id: PaletteEntry.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 05/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import javax.swing.AbstractButton;

import org.mmarini.sound.model.SoundProcessor;
import org.mmarini.sound.model.TransformNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: PaletteEntry.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public interface PaletteEntry {

	/**
	 * 
	 * @param processor
	 * @return
	 */
	public abstract TransformNode createSampleTransformer(
			SoundProcessor processor);

	/**
	 * @return
	 */
	public abstract AbstractButton getButton();

}
