/*
 * ConnectGraphNode.java
 *
 * $Id: ConnectGraphNode.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 *
 * 01/nov/08
 *
 * Copyright notice
 */
package org.mmarini.sound.swing;

import javax.swing.ImageIcon;

/**
 * @author marco.marini@mmarini.org
 * @version $Id: ConnectGraphNode.java,v 1.1 2008/11/15 08:49:48 marco Exp $
 * 
 */
public class ConnectGraphNode extends IconGraphNode {

	/**
	 * 
	 * @param northConnection
	 * @param eastConnection
	 * @param southConnection
	 * @param westConnection
	 */
	public ConnectGraphNode(boolean northConnection, boolean eastConnection,
			boolean southConnection, boolean westConnection) {
		this(null, northConnection, eastConnection, southConnection,
				westConnection);
	}

	/**
	 * @param icon
	 * @param northConnection
	 * @param eastConnection
	 * @param southConnection
	 * @param westConnection
	 */
	public ConnectGraphNode(ImageIcon icon, boolean northConnection,
			boolean eastConnection, boolean southConnection,
			boolean westConnection) {
		super(icon, northConnection, eastConnection, southConnection,
				westConnection);
	}
}
