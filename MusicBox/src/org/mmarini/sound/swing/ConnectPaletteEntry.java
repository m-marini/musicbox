/*
 * ConnectPaletteEntry.java
 *
 * $Id: ConnectPaletteEntry.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 *
 * 17/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import org.mmarini.sound.model.SoundProcessor;
import org.mmarini.sound.model.TransformNode;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: ConnectPaletteEntry.java,v 1.1 2008/11/20 21:07:25 marco Exp $
 * 
 */
public class ConnectPaletteEntry extends AbstractPaletteEntry {

	/**
         * 
         */
	public ConnectPaletteEntry() {
	}

	/**
	 * @see org.mmarini.sound.swing.PaletteEntry#createSampleTransformer(org.mmarini.sound.model.SoundProcessor)
	 */
	@Override
	public TransformNode createSampleTransformer(SoundProcessor processor) {
		return processor.createConnectNode();
	}

}
