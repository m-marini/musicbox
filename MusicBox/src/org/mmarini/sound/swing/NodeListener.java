/*
 * SoundListener.java
 *
 * $Id: NodeListener.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 *
 * 05/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import java.util.EventListener;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: NodeListener.java,v 1.2 2008/11/20 21:07:25 marco Exp $
 * 
 */
public interface NodeListener extends EventListener {

	/**
	 * @param nodeEvent
	 */
	public abstract void nodeSelected(NodeEvent nodeEvent);
}
